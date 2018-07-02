package pe.edu.unmsm.fisi.market.view;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import pe.edu.unmsm.fisi.market.DatabaseTestContext;
import pe.edu.unmsm.fisi.market.model.Product;

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

    @Test
    public void dialogAboutInitializedTest() {
        JDialogAbout dialog = new JDialogAbout(null);
        assertFalse(dialog.isVisible());
    }

    @Test
    public void dialogProductFormInitializedTest() {
        JDialogProductForm dialog = new JDialogProductForm(null, new Product());
        assertFalse(dialog.isVisible());
    }
}
