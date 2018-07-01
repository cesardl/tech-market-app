package pe.edu.unmsm.fisi.market.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.model.Product;
import pe.edu.unmsm.fisi.market.util.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created on 10/06/2018.
 *
 * @author Cesardl
 */
public class ProductDAO implements pe.edu.unmsm.fisi.market.dao.ProductoDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDAO.class);

    @Override
    public Collection<Product> obtenerTodos() {
        String sql = "SELECT PRODUCT_ID, DESCRIPTION, PURCHASE_COST, QUANTITY_ON_HAND, AVAILABLE FROM PRODUCT";

        LOG.debug(sql);

        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute(sql);

            try (ResultSet rs = s.getResultSet()) {
                Collection<Product> vProducts = new ArrayList<>();

                while (rs.next()) {
                    Product p = parseProducto(rs);

                    vProducts.add(p);
                }
                LOG.info("Se cargaron {} productos", vProducts.size());
                return vProducts;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean aniadirProducto(Product product) {
        String sql = "INSERT INTO PRODUCT(PRODUCT_ID, PURCHASE_COST, QUANTITY_ON_HAND, DESCRIPTION) "
                + "VALUES(?, ?, ?, ?)";

        LOG.debug(sql);

        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, product.getCodigo());
            ps.setDouble(2, product.getPurchaseCost());
            ps.setInt(3, product.getQuantityOnHand());
            ps.setString(4, product.getDescription());

            int result = ps.executeUpdate();

            LOG.debug("Total de registros afectados {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error("Error en la consulta para insertar un product: {}. Estado SQL:{}", e.getMessage(), e.getSQLState(), e);
            return false;
        }
    }

    @Override
    public int numElementos() {
        int total = 0;

        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute("SELECT COUNT(1) FROM PRODUCT");

            try (ResultSet rs = s.getResultSet()) {

                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return total;
    }

    @Override
    public Product buscarCodigo(int codigo) {
        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT PRODUCT_ID, DESCRIPTION, PURCHASE_COST, QUANTITY_ON_HAND, AVAILABLE FROM PRODUCT WHERE PRODUCT_ID = ?")) {
            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return parseProducto(rs);
                }
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Collection<Product> buscarNombre(String nombre) {
        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT PRODUCT_ID, DESCRIPTION, PURCHASE_COST, QUANTITY_ON_HAND, AVAILABLE FROM PRODUCT WHERE UPPER(DESCRIPTION) LIKE ?")) {
            ps.setString(1, "%" + nombre.toUpperCase() + "%");

            Collection<Product> vProducts = new ArrayList<>();

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    vProducts.add(parseProducto(rs));
                }
            }

            return vProducts;
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }

    private Product parseProducto(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setCodigo(rs.getInt("PRODUCT_ID"));
        p.setDescription(rs.getString("DESCRIPTION"));
        p.setPurchaseCost(rs.getDouble("PURCHASE_COST"));
        p.setQuantityOnHand(rs.getInt("QUANTITY_ON_HAND"));
        p.setAvailable(Boolean.parseBoolean(rs.getString("AVAILABLE")));
        return p;
    }
}
