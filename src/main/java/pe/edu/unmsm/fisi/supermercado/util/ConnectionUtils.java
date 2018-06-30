package pe.edu.unmsm.fisi.supermercado.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created on 10/06/2018.
 *
 * @author Cesardl
 */
public class ConnectionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionUtils.class);

    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String SCHEMA;

    public static boolean initialize() {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("db.jdbc");

            DRIVER = read(rb, "jdbc.driverClassName");

            URL = read(rb, "jdbc.url");

            USER = read(rb, "jdbc.username");

            PASSWORD = read(rb, "jdbc.password");

            SCHEMA = read(rb, "jdbc.schema");

            Connection connection = openConnection();
            connection.isValid(1);

            LOG.info("Database properties has been initialized");
            return true;
        } catch (MissingResourceException | SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    private static String read(ResourceBundle from, final String key) {
        String value = from.getString(key);
        LOG.debug("Reading property {}: {}", key, value);
        return value;
    }

    public static Connection openConnection() {
        try {
            Class.forName(DRIVER);

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setSchema(SCHEMA);
            return conn;
        } catch (ClassNotFoundException cnfe) {
            LOG.error("Derby driver not found.", cnfe);
        } catch (SQLException sqle) {
            LOG.error("Error when trying to open a connection.", sqle);
        }
        throw new IllegalArgumentException();
    }
}
