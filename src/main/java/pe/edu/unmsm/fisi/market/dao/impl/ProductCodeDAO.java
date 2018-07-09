package pe.edu.unmsm.fisi.market.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.dao.MasterCrudDAO;
import pe.edu.unmsm.fisi.market.model.ProductCode;
import pe.edu.unmsm.fisi.market.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created on 14/06/2018.
 *
 * @author Cesardl
 */
public class ProductCodeDAO implements MasterCrudDAO<ProductCode> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCodeDAO.class);

    @Override
    public Collection<ProductCode> getAll() {
        String sql = "SELECT PROD_CODE, DESCRIPTION FROM PRODUCT_CODE";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute(sql);

            try (ResultSet rs = s.getResultSet()) {
                Collection<ProductCode> productCodes = new ArrayList<>();

                while (rs.next()) {
                    ProductCode c = new ProductCode();
                    c.setProdCode(rs.getString("PROD_CODE"));
                    c.setDescription(rs.getString("DESCRIPTION"));

                    productCodes.add(c);
                }
                LOG.info("Loading {} product codes", productCodes.size());
                return productCodes;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }
}
