package pe.edu.unmsm.fisi.market.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.dao.CrudSimpleDAO;
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
public class ProductCodeDAO implements CrudSimpleDAO<ProductCode> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCodeDAO.class);

    @Override
    public Collection<ProductCode> obtenerTodos() {
        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute("SELECT PROD_CODE, DESCRIPTION FROM PRODUCT_CODE");

            try (ResultSet rs = s.getResultSet()) {
                Collection<ProductCode> vProductCode = new ArrayList<>();

                while (rs.next()) {
                    ProductCode c = new ProductCode();
                    c.setProdCode(rs.getString("PROD_CODE"));
                    c.setDescription(rs.getString("DESCRIPTION"));

                    vProductCode.add(c);
                }
                LOG.info("Se cargaron {} clientes", vProductCode.size());
                return vProductCode;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }
}
