package pe.edu.unmsm.fisi.supermercado.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created on 10/06/2018.
 *
 * @author Cesardl
 */
public class ConnectionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionUtils.class);

    public static Connection openConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
            conn.setSchema("APP");
            return conn;
        } catch (ClassNotFoundException cnfe) {
            LOG.error("Derby driver not found.", cnfe);
        } catch (SQLException sqle) {
            LOG.error("Error when trying to open a connection.", sqle);
        }
        throw new IllegalArgumentException();
    }
}
