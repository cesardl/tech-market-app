package pe.edu.unmsm.fisi.market.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.dao.MasterCrudDAO;
import pe.edu.unmsm.fisi.market.model.Manufacturer;
import pe.edu.unmsm.fisi.market.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created on 30/06/2018.
 *
 * @author Cesardl
 */
public class ManufacturerDAO implements MasterCrudDAO<Manufacturer> {

    private static final Logger LOG = LoggerFactory.getLogger(ManufacturerDAO.class);

    @Override
    public Collection<Manufacturer> getAll() {
        String sql = "SELECT MANUFACTURER_ID, NAME, EMAIL FROM MANUFACTURER ORDER BY NAME, EMAIL";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute(sql);

            try (ResultSet rs = s.getResultSet()) {
                Collection<Manufacturer> manufacturers = new ArrayList<>();

                while (rs.next()) {
                    Manufacturer m = new Manufacturer();
                    m.setManufacturerId(rs.getInt("MANUFACTURER_ID"));
                    m.setName(rs.getString("NAME"));
                    m.setEmail(rs.getString("EMAIL"));

                    manufacturers.add(m);
                }
                LOG.info("Loading {} manufacturers", manufacturers.size());
                return manufacturers;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }
}
