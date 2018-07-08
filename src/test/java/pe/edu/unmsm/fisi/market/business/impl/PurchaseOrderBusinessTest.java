package pe.edu.unmsm.fisi.market.business.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import pe.edu.unmsm.fisi.market.DatabaseTestContext;
import pe.edu.unmsm.fisi.market.PojoFake;
import pe.edu.unmsm.fisi.market.model.PurchaseOrder;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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
    public void addReadAndDeletePurchaseOrderTest() {
        Collection<PurchaseOrder> result = purchaseOrderBusiness.all();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        result.forEach(purchaseOrder -> {
            assertNotNull(purchaseOrder.getOrderNum());
            assertNotNull(purchaseOrder.getCustomer());
            assertNotNull(purchaseOrder.getProduct());
            assertNotNull(purchaseOrder.getSalesDate());
            assertNotNull(purchaseOrder.getShippingDate());
        });

        PurchaseOrder fakePurchaseOrder = PojoFake.purchaseOrder();
        boolean hasBeenAdded = purchaseOrderBusiness.saveOrUpdate(fakePurchaseOrder);
        assertTrue(hasBeenAdded);

        PurchaseOrder purchaseOrder = purchaseOrderBusiness.findById(fakePurchaseOrder.getOrderNum());
        assertNotNull(purchaseOrder.getOrderNum());

        Date salesDate = purchaseOrder.getSalesDate();
        Date shippingDate = purchaseOrder.getShippingDate();
        assertNotNull(salesDate);
        assertNotNull(shippingDate);

        Calendar salesCalendar = Calendar.getInstance();
        salesCalendar.setTime(salesDate);

        Calendar shippingCalendar = Calendar.getInstance();
        shippingCalendar.setTime(shippingDate);

        assertEquals(salesCalendar.get(Calendar.DAY_OF_MONTH) + 15, shippingCalendar.get(Calendar.DAY_OF_MONTH));
        // TODO Completar los campos

        boolean hasBeenDeleted = purchaseOrderBusiness.delete(fakePurchaseOrder.getOrderNum());
        assertTrue(hasBeenDeleted);

        result = purchaseOrderBusiness.all();
        assertEquals(1, result.size());
    }
}