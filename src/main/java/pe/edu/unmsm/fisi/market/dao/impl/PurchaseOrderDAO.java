package pe.edu.unmsm.fisi.market.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.dao.CrudSimpleDAO;
import pe.edu.unmsm.fisi.market.model.Customer;
import pe.edu.unmsm.fisi.market.model.Product;
import pe.edu.unmsm.fisi.market.model.PurchaseOrder;
import pe.edu.unmsm.fisi.market.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created on 17/06/2018.
 *
 * @author Cesardl
 */
public class PurchaseOrderDAO implements CrudSimpleDAO<PurchaseOrder> {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseOrderDAO.class);

    @Override
    public Collection<PurchaseOrder> obtenerTodos() {
        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute("SELECT PO.ORDER_NUM, C.NAME, P.DESCRIPTION, PO.QUANTITY, PO.SALES_DATE FROM PURCHASE_ORDER PO " +
                    "INNER JOIN CUSTOMER C on PO.CUSTOMER_ID = C.CUSTOMER_ID " +
                    "INNER JOIN PRODUCT P on PO.PRODUCT_ID = P.PRODUCT_ID " +
                    "ORDER BY PO.SALES_DATE DESC");

            try (ResultSet rs = s.getResultSet()) {
                Collection<PurchaseOrder> vPurchaseOrders = new ArrayList<>();

                while (rs.next()) {
                    PurchaseOrder purchaseOrder = new PurchaseOrder();
                    purchaseOrder.setOrderNum(rs.getInt(1));

                    Customer customer = new Customer();
                    customer.setName(rs.getString(2));
                    purchaseOrder.setCustomer(customer);

                    Product product = new Product();
                    product.setDescription(rs.getString(3));
                    purchaseOrder.setProduct(product);

                    purchaseOrder.setQuantity(rs.getInt(4));
                    purchaseOrder.setSalesDate(rs.getDate(5));

                    vPurchaseOrders.add(purchaseOrder);
                }
                LOG.info("Se cargaron {} ordenes de compra", vPurchaseOrders.size());
                return vPurchaseOrders;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }
}
