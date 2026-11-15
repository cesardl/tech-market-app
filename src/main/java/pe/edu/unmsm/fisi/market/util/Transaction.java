package pe.edu.unmsm.fisi.market.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Runs several statements on one connection as a single unit of work: nothing is committed
 * until the work answers {@code true}, and a {@code false} answer or an exception rolls the
 * whole thing back.
 *
 * <p>Every other DAO method opens and closes its own connection, which is all one statement
 * needs. Two statements that have to agree -- taking units out of stock and recording the
 * order that took them -- cannot be split across two connections, because a crash between
 * them would leave stock spent on an order that does not exist. This is the piece a
 * framework hides: Spring's {@code @Transactional} wraps these same three calls
 * ({@code setAutoCommit(false)}, {@code commit}, {@code rollback}) around a method, and
 * decides the boundary from an annotation instead of from a lambda.
 *
 * <p>Created on 15/11/2026.
 *
 * @author Cesardl
 */
public final class Transaction {

    private static final Logger LOG = LoggerFactory.getLogger(Transaction.class);

    private Transaction() {
    }

    /**
     * The statements to run together. Unlike the DAO methods that own their connection, the
     * work is free to throw: an exception is a rollback, where swallowing it would commit
     * half of the change.
     */
    @FunctionalInterface
    public interface UnitOfWork {

        /**
         * @param connection the one connection every statement of the unit must use.
         * @return true to commit, false to roll back.
         */
        boolean execute(Connection connection) throws SQLException;
    }

    /**
     * @return whether the work committed. False covers both a refusal by the work itself and
     *         a database error, which is what the rest of this layer answers too.
     */
    public static boolean run(final UnitOfWork work) {
        Connection connection = ConnectionUtils.openConnection();
        try {
            connection.setAutoCommit(false);

            boolean complete = work.execute(connection);
            if (complete) {
                connection.commit();
            } else {
                LOG.debug("The unit of work asked to be rolled back");
                connection.rollback();
            }
            return complete;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            rollback(connection);
            return false;
        } catch (RuntimeException e) {
            // Not a database error. Roll back so nothing is left half done, but let it out
            // instead of reporting a bug as a refusal the caller is meant to handle.
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }

    private static void rollback(final Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOG.error("The transaction could not be rolled back.", e);
        }
    }

    private static void close(final Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error("The connection could not be closed.", e);
        }
    }
}
