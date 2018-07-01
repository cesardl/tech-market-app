package pe.edu.unmsm.fisi.market.util;

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

    private static String driver;
    private static String url;
    private static String user;
    private static String password;
    private static String schema;

    public static boolean initialize() {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("db.jdbc");

            driver = read(rb, "jdbc.driverClassName");
            url = read(rb, "jdbc.url");
            user = read(rb, "jdbc.username");
            password = read(rb, "jdbc.password");
            schema = read(rb, "jdbc.schema");

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
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setSchema(schema);
            return conn;
        } catch (ClassNotFoundException cnfe) {
            LOG.error("Derby driver not found.", cnfe);
        } catch (SQLException sqle) {
            LOG.error("Error when trying to open a connection.", sqle);
        }
        throw new IllegalArgumentException();
    }
}
