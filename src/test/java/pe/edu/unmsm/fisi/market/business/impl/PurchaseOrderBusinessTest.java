package pe.edu.unmsm.fisi.market.business.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import pe.edu.unmsm.fisi.market.DatabaseTestContext;
import pe.edu.unmsm.fisi.market.model.PurchaseOrder;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created on 07/07/2018.
 *
 * @author Cesardl
 */
public class PurchaseOrderBusinessTest {

    private final PurchaseOrderBusiness purchaseOrderBusiness = PurchaseOrderBusiness.getInstance();

    @BeforeClass
    public static void initialize() throws InitializationError {
        DatabaseTestContext.init();
    }

    @Test
    public void listPurchaseOrdersTest() {
        Collection<PurchaseOrder> result = purchaseOrderBusiness.obtenerTodos();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        result.forEach(purchaseOrder -> {
            assertNotNull(purchaseOrder.getOrderNum());
            assertNotNull(purchaseOrder.getCustomer());
            assertNotNull(purchaseOrder.getProduct());
            assertNotNull(purchaseOrder.getSalesDate());
        });
    }
}