package pe.edu.unmsm.fisi.market.view;

import org.junit.Test;
import pe.edu.unmsm.fisi.market.model.Customer;
import pe.edu.unmsm.fisi.market.model.Product;
import pe.edu.unmsm.fisi.market.model.PurchaseOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

    /**
     * A new order carries quantity 0 and shipping cost 0 from PurchaseOrder's own
     * constructor, and @Min(1) rejects both. The dialog used to show them and then
     * demand a shipping cost in a field it had made read-only, so "Comprar" could
     * never be completed. The quantity now starts where it validates, and the cost is
     * still demanded, but it can be typed.
     */
    @Test
    public void dialogPurchaseOrderFormStartsANewOrderWhereItValidatesTest() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCustomer(new Customer());
        purchaseOrder.setProduct(new Product());
        // A bare Product holds no stock, and the unit the spinner starts at has to come
        // out of somewhere.
        purchaseOrder.getProduct().setQuantityOnHand(1);

        JDialogPurchaseOrderForm dialog = new JDialogPurchaseOrderForm(null, purchaseOrder);
        dialog.showPurchaseOrder();

        Map<String, String> errors = dialog.catchingData();

        assertEquals(Integer.valueOf(1), purchaseOrder.getQuantity());
        assertFalse(errors.containsKey("quantity"));
        assertTrue(errors.containsKey("shippingCost"));
        assertFalse(dialog.isVisible());
    }

    /**
     * The form has to read back every field it shows. Shipping cost and shipping date
     * were displayed, validated and then dropped, so the dialog reported a save that
     * had silently discarded them.
     */
    @Test
    public void dialogPurchaseOrderFormReadsBackEveryFieldItShowsTest() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCustomer(new Customer());
        purchaseOrder.setProduct(new Product());
        purchaseOrder.getProduct().setQuantityOnHand(7);
        purchaseOrder.setQuantity(7);
        purchaseOrder.setShippingCost(new BigDecimal("150.50"));
        purchaseOrder.setShippingDate(new Date());
        purchaseOrder.setFreightCompany("FR Express");

        JDialogPurchaseOrderForm dialog = new JDialogPurchaseOrderForm(null, purchaseOrder);
        dialog.showPurchaseOrder();

        // The widgets now hold the order. Wipe the order and let the dialog refill it.
        purchaseOrder.setQuantity(null);
        purchaseOrder.setShippingCost(null);
        purchaseOrder.setShippingDate(null);
        purchaseOrder.setFreightCompany(null);

        assertTrue(dialog.catchingData().isEmpty());

        assertEquals(Integer.valueOf(7), purchaseOrder.getQuantity());
        assertEquals(new BigDecimal("150.50"), purchaseOrder.getShippingCost());
        assertEquals("FR Express", purchaseOrder.getFreightCompany());
        assertNotNull(purchaseOrder.getShippingDate());
    }

    /**
     * Shipping cost is the field the form demands and the one it let through anyway: an
     * empty box answers null, which @Min and @Digits both skip, so the order saved with no
     * shipping cost at all.
     */
    @Test
    public void dialogPurchaseOrderFormDemandsAShippingCostTest() {
        PurchaseOrder purchaseOrder = completeOrder();
        purchaseOrder.setShippingCost(null);

        JDialogPurchaseOrderForm dialog = new JDialogPurchaseOrderForm(null, purchaseOrder);
        dialog.showPurchaseOrder();

        Map<String, String> errors = dialog.catchingData();

        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("shippingCost"));
        assertNull(purchaseOrder.getShippingCost());
    }

    /**
     * SHIPPING_COST is DECIMAL(12, 2), which holds ten digits before the point. A wider
     * cost used to pass validation and fail in the database, where the DAO logs the error
     * and the form can only report that the order could not be saved.
     */
    @Test
    public void dialogPurchaseOrderFormRejectsACostWiderThanItsColumnTest() {
        PurchaseOrder purchaseOrder = completeOrder();
        purchaseOrder.setShippingCost(new BigDecimal("999999999999.99"));

        JDialogPurchaseOrderForm dialog = new JDialogPurchaseOrderForm(null, purchaseOrder);
        dialog.showPurchaseOrder();
        assertTrue(dialog.catchingData().containsKey("shippingCost"));

        // The widest cost the column does hold.
        purchaseOrder.setShippingCost(new BigDecimal("9999999999.99"));
        dialog.showPurchaseOrder();
        assertTrue(dialog.catchingData().isEmpty());
    }

    /**
     * The database refuses an order it has no units for, but all it can answer is no. The
     * form is where the number gets named.
     */
    @Test
    public void dialogPurchaseOrderFormReportsShortStockTest() {
        PurchaseOrder purchaseOrder = completeOrder();
        purchaseOrder.getProduct().setQuantityOnHand(3);

        // A new order holds nothing yet, so all four units have to come out of stock.
        JDialogPurchaseOrderForm buying = new JDialogPurchaseOrderForm(null, purchaseOrder);
        purchaseOrder.setQuantity(4);
        buying.showPurchaseOrder();

        Map<String, String> errors = buying.catchingData();
        assertTrue(errors.containsKey("quantity"));
        assertTrue(errors.get("quantity").contains("3"));

        // The same four units on an order that already holds them need no stock at all.
        JDialogPurchaseOrderForm editing = new JDialogPurchaseOrderForm(null, purchaseOrder);
        editing.showPurchaseOrder();
        assertTrue(editing.catchingData().isEmpty());
    }

    /**
     * An order that validates, apart from whichever field the test is about. Quantity is
     * left at what PurchaseOrder's constructor gives it, so the dialog counts the order as
     * holding no stock yet.
     */
    private static PurchaseOrder completeOrder() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCustomer(new Customer());
        purchaseOrder.setProduct(new Product());
        purchaseOrder.getProduct().setQuantityOnHand(100);
        purchaseOrder.setShippingCost(new BigDecimal("150.50"));
        purchaseOrder.setShippingDate(new Date());
        purchaseOrder.setFreightCompany("FR Express");
        return purchaseOrder;
    }
}
