package pe.edu.unmsm.fisi.market.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.dao.CompleteCrudDAO;
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
public class ProductDAO implements CompleteCrudDAO<Product> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDAO.class);

    @Override
    public Collection<Product> getAll() {
        String sql = "SELECT PRODUCT_ID, DESCRIPTION FROM PRODUCT";

        LOG.debug("[SQL] {}", sql);

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
    public boolean save(Product product) {
        String sql = "INSERT INTO PRODUCT(PRODUCT_ID, MANUFACTURER_ID, PRODUCT_CODE, PURCHASE_COST, QUANTITY_ON_HAND, DESCRIPTION, AVAILABLE) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";

        LOG.debug("[SQL] {}", sql);

        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, product.getProductId());
            ps.setInt(2, product.getManufacturer().getManufacturerId());
            ps.setString(3, product.getProductCode().getProdCode());
            ps.setDouble(4, product.getPurchaseCost());
            ps.setInt(5, product.getQuantityOnHand());
            ps.setString(6, product.getDescription());
            ps.setBoolean(7, product.isAvailable());

            int result = ps.executeUpdate();

            LOG.debug("Total de registros afectados {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE PRODUCT SET MANUFACTURER_ID = ?, PRODUCT_CODE = ?, PURCHASE_COST = ?, QUANTITY_ON_HAND = ?, DESCRIPTION = ?, AVAILABLE = ? WHERE PRODUCT_ID = ?";

        LOG.debug("[SQL] {}", sql);

        try (Connection connection = ConnectionUtils.openConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, product.getManufacturer().getManufacturerId());
            ps.setString(2, product.getProductCode().getProdCode());
            ps.setDouble(3, product.getPurchaseCost());
            ps.setInt(4, product.getQuantityOnHand());
            ps.setString(5, product.getDescription());
            ps.setBoolean(6, product.isAvailable());
            ps.setInt(7, product.getProductId());

            int result = ps.executeUpdate();

            LOG.debug("Total de registros afectados {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Product buscarCodigo(int codigo) {
        String sql = "SELECT P.PRODUCT_ID, P.DESCRIPTION, P.PURCHASE_COST, P.QUANTITY_ON_HAND, P.AVAILABLE, M.MANUFACTURER_ID, M.NAME, PC.PROD_CODE "
                + "FROM PRODUCT P "
                + "INNER JOIN MANUFACTURER M on P.MANUFACTURER_ID = M.MANUFACTURER_ID "
                + "INNER JOIN PRODUCT_CODE PC on P.PRODUCT_CODE = PC.PROD_CODE "
                + "WHERE PRODUCT_ID = ?";

        LOG.debug("[SQL] {}", sql);

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
        String sql = "SELECT P.PRODUCT_ID, P.DESCRIPTION, P.PURCHASE_COST, P.QUANTITY_ON_HAND, P.AVAILABLE, M.MANUFACTURER_ID, M.NAME, PC.PROD_CODE "
                + "FROM PRODUCT P "
                + "INNER JOIN MANUFACTURER M on P.MANUFACTURER_ID = M.MANUFACTURER_ID "
                + "INNER JOIN PRODUCT_CODE PC on P.PRODUCT_CODE = PC.PROD_CODE "
                + "WHERE UPPER(P.DESCRIPTION) LIKE ?";

        LOG.debug("[SQL] {}", sql);

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
    public boolean delete(Product product) {
        String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";

        LOG.debug("[SQL] {}", sql);

        try (Connection connection = ConnectionUtils.openConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, product.getProductId());

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
