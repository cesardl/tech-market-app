package pe.edu.unmsm.fisi.market.view;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import pe.edu.unmsm.fisi.market.DatabaseTestContext;

import static org.junit.Assert.assertFalse;

/**
 * Unit test for simple App.
 *
 * @author Cesardl
 */
public class ViewsTest {

    @BeforeClass
    public static void initialize() throws InitializationError {
        DatabaseTestContext.init();
    }

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
}
