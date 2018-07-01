package pe.edu.unmsm.fisi.market.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.model.Manufacturer;
import pe.edu.unmsm.fisi.market.model.Product;
import pe.edu.unmsm.fisi.market.model.ProductCode;
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
    public Collection<Product> getAll() {
        String sql = "SELECT PRODUCT_ID, DESCRIPTION FROM PRODUCT";

        LOG.debug(sql);

        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute(sql);

            try (ResultSet rs = s.getResultSet()) {
                Collection<Product> products = new ArrayList<>();

                while (rs.next()) {
                    Product p = new Product();
                    p.setProductId(rs.getInt("PRODUCT_ID"));
                    p.setDescription(rs.getString("DESCRIPTION"));

                    products.add(p);
                }
                LOG.info("Loading {} products", products.size());
                return products;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean aniadirProducto(Product product) {
        String sql = "INSERT INTO PRODUCT(PRODUCT_ID, MANUFACTURER_ID, PRODUCT_CODE, PURCHASE_COST, QUANTITY_ON_HAND, DESCRIPTION) "
                + "VALUES(?, ?, ?, ?, ?, ?)";

        LOG.debug(sql);

        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, product.getProductId());
            ps.setInt(2, product.getManufacturer().getManufacturerId());
            ps.setString(3, product.getProductCode().getProdCode());
            ps.setDouble(4, product.getPurchaseCost());
            ps.setInt(5, product.getQuantityOnHand());
            ps.setString(6, product.getDescription());

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
        String sql = "SELECT P.PRODUCT_ID, P.DESCRIPTION, P.PURCHASE_COST, P.QUANTITY_ON_HAND, P.AVAILABLE, M.MANUFACTURER_ID, M.NAME, PC.PROD_CODE " +
                "FROM PRODUCT P " +
                "INNER JOIN MANUFACTURER M on P.MANUFACTURER_ID = M.MANUFACTURER_ID " +
                "INNER JOIN PRODUCT_CODE PC on P.PRODUCT_CODE = PC.PROD_CODE " +
                "WHERE PRODUCT_ID = ?";

        LOG.debug(sql);

        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return parseProduct(rs);
                }
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Collection<Product> buscarNombre(String description) {
        String sql = "SELECT P.PRODUCT_ID, P.DESCRIPTION, P.PURCHASE_COST, P.QUANTITY_ON_HAND, P.AVAILABLE, M.MANUFACTURER_ID, M.NAME, PC.PROD_CODE " +
                "FROM PRODUCT P " +
                "INNER JOIN MANUFACTURER M on P.MANUFACTURER_ID = M.MANUFACTURER_ID " +
                "INNER JOIN PRODUCT_CODE PC on P.PRODUCT_CODE = PC.PROD_CODE " +
                "WHERE UPPER(P.DESCRIPTION) LIKE ?";

        LOG.debug(sql);

        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + description.toUpperCase() + "%");

            Collection<Product> vProducts = new ArrayList<>();

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    vProducts.add(parseProduct(rs));
                }
            }

            return vProducts;
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean delete(int productId) {
        String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";

        LOG.debug(sql);

        try (Connection connection = ConnectionUtils.openConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, productId);

            int result = ps.executeUpdate();

            LOG.info("A product has been deleted: {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    private Product parseProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("PRODUCT_ID"));
        p.setDescription(rs.getString("DESCRIPTION"));
        p.setPurchaseCost(rs.getDouble("PURCHASE_COST"));
        p.setQuantityOnHand(rs.getInt("QUANTITY_ON_HAND"));
        p.setAvailable(Boolean.parseBoolean(rs.getString("AVAILABLE")));

        Manufacturer m = new Manufacturer();
        m.setManufacturerId(rs.getInt("MANUFACTURER_ID"));
        m.setName(rs.getString("NAME"));
        p.setManufacturer(m);

        ProductCode pc = new ProductCode();
        pc.setProdCode(rs.getString("PROD_CODE"));
        p.setProductCode(pc);

        return p;
    }
}
