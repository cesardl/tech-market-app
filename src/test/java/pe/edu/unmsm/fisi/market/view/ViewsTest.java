package pe.edu.unmsm.fisi.market.view;

import org.junit.Test;
import pe.edu.unmsm.fisi.market.model.Customer;
import pe.edu.unmsm.fisi.market.model.Product;
import pe.edu.unmsm.fisi.market.model.PurchaseOrder;

import static org.junit.Assert.assertFalse;

/**
 * Unit test for simple TechMarketApp.
 *
 * @author Cesardl
 */
public class ViewsTest {

    @Test
    public void frameMainInitializedTest() {
        JFrameMain frame = new JFrameMain();
        assertFalse(frame.isVisible());
    }

    @Test
    public void internalFrameCustomersInitializedTest() {
        JInternalFrameCustomers frame = new JInternalFrameCustomers();
        assertFalse(frame.isVisible());
    }

    @Test
    public void internalFrameProductsInitializedTest() {
        JInternalFrameProducts frame = new JInternalFrameProducts();
        assertFalse(frame.isVisible());
    }

    @Test
    public void internalFramePurchaseOrdersInitializedTest() {
        JInternalFramePurchaseOrders frame = new JInternalFramePurchaseOrders();
        assertFalse(frame.isVisible());
    }

    @Test
    public void dialogAboutInitializedTest() {
        JDialogAbout dialog = new JDialogAbout(null);
        assertFalse(dialog.isVisible());
    }

    @Test
    public void dialogCustomerFormInitializedTest() {
        JDialogCustomerForm dialog = new JDialogCustomerForm(null, new Customer());
        assertFalse(dialog.isVisible());
    }

    @Test
    public void dialogProductFormInitializedTest() {
        JDialogProductForm dialog = new JDialogProductForm(null, new Product());
        assertFalse(dialog.isVisible());
    }

    @Test
    public void dialogPurchaseOrderFormInitializedTest() {
        JDialogPurchaseOrderForm dialog = new JDialogPurchaseOrderForm(null, new PurchaseOrder());
        assertFalse(dialog.isVisible());
    }
}
