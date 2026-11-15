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

    /**
     * The whole product graph: every PRODUCT column the POJO carries, plus the manufacturer
     * and the product code the row points at. {@link #parseProduct(ResultSet)} maps it.
     * Callers append their own WHERE clause.
     */
    private static final String SELECT_PRODUCT_GRAPH =
            "SELECT P.PRODUCT_ID, P.DESCRIPTION, P.PURCHASE_COST, P.QUANTITY_ON_HAND, P.MARKUP, P.AVAILABLE, M.MANUFACTURER_ID, M.NAME, PC.PROD_CODE "
                    + "FROM PRODUCT P "
                    + "INNER JOIN MANUFACTURER M on P.MANUFACTURER_ID = M.MANUFACTURER_ID "
                    + "INNER JOIN PRODUCT_CODE PC on P.PRODUCT_CODE = PC.PROD_CODE ";

    @Override
    public Collection<Product> getAll() {
        String sql = "SELECT PRODUCT_ID, DESCRIPTION FROM PRODUCT WHERE QUANTITY_ON_HAND <> 0 AND AVAILABLE = TRUE";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

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
    public boolean save(final Product product) {
        String sql = "INSERT INTO PRODUCT(PRODUCT_ID, MANUFACTURER_ID, PRODUCT_CODE, PURCHASE_COST, QUANTITY_ON_HAND, MARKUP, AVAILABLE, DESCRIPTION) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, product.getProductId());
            ps.setInt(2, product.getManufacturer().getManufacturerId());
            ps.setString(3, product.getProductCode().getProdCode());
            ps.setDouble(4, product.getPurchaseCost());
            ps.setInt(5, product.getQuantityOnHand());
            ps.setDouble(6, product.getMarkup());
            ps.setBoolean(7, product.isAvailable());
            ps.setString(8, product.getDescription());

            int result = ps.executeUpdate();

            LOG.debug("Number of affected rows {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean update(final Product product) {
        String sql = "UPDATE PRODUCT SET MANUFACTURER_ID = ?, PRODUCT_CODE = ?, PURCHASE_COST = ?, QUANTITY_ON_HAND = ?, MARKUP = ?, AVAILABLE = ?, DESCRIPTION = ? " +
                "WHERE PRODUCT_ID = ?";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (Connection connection = ConnectionUtils.openConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, product.getManufacturer().getManufacturerId());
            ps.setString(2, product.getProductCode().getProdCode());
            ps.setDouble(3, product.getPurchaseCost());
            ps.setInt(4, product.getQuantityOnHand());
            ps.setDouble(5, product.getMarkup());
            ps.setBoolean(6, product.isAvailable());
            ps.setString(7, product.getDescription());
            ps.setInt(8, product.getProductId());

            int result = ps.executeUpdate();

            LOG.debug("Number of affected rows {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Product findById(final int identifier) {
        String sql = SELECT_PRODUCT_GRAPH + "WHERE PRODUCT_ID = ?";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, identifier);

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
    public Collection<Product> buscarNombre(final String description) {
        String sql = SELECT_PRODUCT_GRAPH + "WHERE UPPER(P.DESCRIPTION) LIKE ?";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

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
    public boolean delete(final Product product) {
        String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (Connection connection = ConnectionUtils.openConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, product.getProductId());

            int result = ps.executeUpdate();

            LOG.info("A product has been deleted: {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    private Product parseProduct(final ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("PRODUCT_ID"));
        p.setDescription(rs.getString("DESCRIPTION"));
        p.setPurchaseCost(rs.getDouble("PURCHASE_COST"));
        p.setQuantityOnHand(rs.getInt("QUANTITY_ON_HAND"));
        p.setMarkup(rs.getDouble("MARKUP"));
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

    /**
     * Takes {@code quantity} units off a product's stock, or puts them back when it is
     * negative, and keeps AVAILABLE in step with what is left -- the same rule
     * {@code ProductBusiness.saveOrUpdate} applies, here as SQL because the row is never
     * read into a POJO.
     * <p>
     * One statement, so the check and the subtraction cannot be separated: no row is touched
     * when the stock on hand is below what was asked, and two orders cannot both be told
     * there is enough. Reading the stock first and updating it afterwards is what a
     * framework's optimistic locking would be protecting you from.
     *
     * @param connection the caller's transaction, so this commits with the order that caused it.
     * @return false when the product is gone or its stock is short, which is the caller's
     *         signal to roll the unit of work back.
     */
    public boolean takeStock(final Connection connection, final int productId, final int quantity) throws SQLException {
        String sql = "UPDATE PRODUCT SET QUANTITY_ON_HAND = QUANTITY_ON_HAND - ?, AVAILABLE = (QUANTITY_ON_HAND - ? <> 0) "
                + "WHERE PRODUCT_ID = ? AND QUANTITY_ON_HAND >= ?";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, quantity);
            ps.setInt(3, productId);
            ps.setInt(4, quantity);

            int result = ps.executeUpdate();

            LOG.info("Taking {} units of product {} out of stock: {} rows", quantity, productId, result);
            return result == 1;
        }
    }

    /**
     * Every product that can still be sold, optionally narrowed to one product code. It feeds
     * the product combo of the purchase order screen, so unlike {@link #getAll()} it returns
     * the full graph -- QUANTITY_ON_HAND included, which is what any stock check needs.
     *
     * @param prodCode the PRODUCT_CODE.PROD_CODE to filter by, or {@code null} for every
     *                 code. Rows out of stock or flagged unavailable are left out either
     *                 way, the same ones {@link #getAll()} hides.
     */
    public Collection<Product> findByProductCode(final String prodCode) {
        String sql = SELECT_PRODUCT_GRAPH
                + "WHERE P.QUANTITY_ON_HAND <> 0 AND P.AVAILABLE = TRUE "
                + (prodCode == null ? "" : "AND PC.PROD_CODE = ? ")
                + "ORDER BY P.DESCRIPTION";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (prodCode != null) {
                ps.setString(1, prodCode);
            }

            Collection<Product> products = new ArrayList<>();

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    products.add(parseProduct(rs));
                }
            }

            LOG.info("Loading {} products for product code {}", products.size(), prodCode);
            return products;
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }
}
