package pe.edu.unmsm.fisi.market;

import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.util.ConnectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * Created on 30/06/2018.
 *
 * @author Cesardl
 */
public class DatabaseTestContext {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseTestContext.class);

    private static boolean contextAlreadyInitialized = false;

    public static void init() throws InitializationError {
        if (contextAlreadyInitialized) {
            LOG.warn("Context has been initialized");
            return;
        }

        boolean validDatabaseConnection = ConnectionUtils.initialize();
        LOG.info("Initializing database connection '{}'", validDatabaseConnection);

        if (validDatabaseConnection) {
            try {
                initDatabase();
            } catch (IOException | SQLException e) {
                LOG.error(e.getMessage(), e);
                throw new InitializationError("Error during database generation");
            }
        } else {
            throw new InitializationError("Wrong database configuration");
        }
    }

    private static void initDatabase() throws IOException, SQLException {
        String[] split = Files.readAllLines(Paths.get("db", "market-schema.sql"))
                .stream().map(String::trim).collect(Collectors.joining(" ")).split(";");

        Connection connection = ConnectionUtils.openConnection();
        for (String statement : split) {
            String sql = statement.trim();
            try {
                int result = connection.prepareStatement(sql).executeUpdate();
                LOG.debug("{} >>> {}", sql, result);
            } catch (SQLException e) {
                if (e.getSQLState().equals("X0Y32") || e.getSQLState().equals("23505")) {
                    LOG.debug(e.getMessage());
                } else {
                    throw e;
                }
            }
        }
        LOG.info("Database generation was successful");

        contextAlreadyInitialized = true;
    }
}
