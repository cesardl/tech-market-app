package pe.edu.unmsm.fisi.market.business.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import pe.edu.unmsm.fisi.market.DatabaseTestContext;
import pe.edu.unmsm.fisi.market.PojoFake;
import pe.edu.unmsm.fisi.market.dao.impl.ProductDAO;
import pe.edu.unmsm.fisi.market.model.Product;
import pe.edu.unmsm.fisi.market.model.PurchaseOrder;
import pe.edu.unmsm.fisi.market.util.Transaction;

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

        Calendar expectedShippingDate = Calendar.getInstance();
        expectedShippingDate.setTime(salesDate);
        expectedShippingDate.add(Calendar.DAY_OF_MONTH, 15);

        // Comparing DAY_OF_MONTH + 15 only held while the sales day was below 16.
        assertEquals(day(expectedShippingDate.getTime()), day(shippingDate));
        // TODO Completar los campos

        boolean hasBeenDeleted = purchaseOrderBusiness.delete(fakePurchaseOrder.getOrderNum());
        assertTrue(hasBeenDeleted);

        result = purchaseOrderBusiness.all();
        assertEquals(1, result.size());
    }

    @Test
    public void saveKeepsTheShippingDateItWasGivenTest() {
        Calendar shippingDate = Calendar.getInstance();
        shippingDate.add(Calendar.DAY_OF_MONTH, 3);

        PurchaseOrder fakePurchaseOrder = PojoFake.purchaseOrder();
        fakePurchaseOrder.setShippingDate(shippingDate.getTime());

        assertTrue(purchaseOrderBusiness.saveOrUpdate(fakePurchaseOrder));
        try {
            PurchaseOrder saved = purchaseOrderBusiness.findById(fakePurchaseOrder.getOrderNum());
            assertEquals(day(shippingDate.getTime()), day(saved.getShippingDate()));
        } finally {
            purchaseOrderBusiness.delete(fakePurchaseOrder.getOrderNum());
        }
    }

    /**
     * Clearing the order form's date chooser sends a null shipping date through to the
     * update, where the DAO used to unbox it.
     */
    @Test
    public void updateAcceptsAnOrderWithNoShippingDateTest() {
        PurchaseOrder fakePurchaseOrder = PojoFake.purchaseOrder();
        assertTrue(purchaseOrderBusiness.saveOrUpdate(fakePurchaseOrder));
        try {
            PurchaseOrder saved = purchaseOrderBusiness.findById(fakePurchaseOrder.getOrderNum());
            assertNotNull(saved.getShippingDate());

            saved.setShippingDate(null);
            assertTrue(purchaseOrderBusiness.saveOrUpdate(saved));

            assertNull(purchaseOrderBusiness.findById(saved.getOrderNum()).getShippingDate());
        } finally {
            purchaseOrderBusiness.delete(fakePurchaseOrder.getOrderNum());
        }
    }

    @Test
    public void deletingAnAbsentPurchaseOrderReportsFailureTest() {
        int identifier = 1;
        assertNull(purchaseOrderBusiness.findById(identifier));
        assertFalse(purchaseOrderBusiness.delete(identifier));
    }

    @Test
    public void successSearchByIdAndUpdatePurchaseOrdersTest() {
        int identifier = 10398001;

        PurchaseOrder result = purchaseOrderBusiness.findById(identifier);
        assertNotNull(result.getOrderNum());
        assertNotNull(result.getCustomer());
        assertNotNull(result.getCustomer().getCustomerId());
        assertNotNull(result.getCustomer().getName());
        assertNotNull(result.getProduct());
        assertNotNull(result.getProduct().getProductId());
        assertNotNull(result.getProduct().getDescription());
        assertNotNull(result.getQuantity());
        assertNotNull(result.getShippingCost());
        assertNotNull(result.getSalesDate());
        assertNotNull(result.getShippingDate());
        assertNotNull(result.getFreightCompany());

        Integer originalQuantity = result.getQuantity();
        Integer tempQuantity = 1000;
        result.setQuantity(tempQuantity);
        boolean hasBeenUpdated = purchaseOrderBusiness.saveOrUpdate(result);

        assertTrue(hasBeenUpdated);
        result = purchaseOrderBusiness.findById(identifier);
        assertEquals(tempQuantity, result.getQuantity());

        result.setQuantity(originalQuantity);
        hasBeenUpdated = purchaseOrderBusiness.saveOrUpdate(result);

        assertTrue(hasBeenUpdated);
        result = purchaseOrderBusiness.findById(identifier);
        assertEquals(originalQuantity, result.getQuantity());
    }

    @Test
    public void savingAnOrderTakesItsUnitsOutOfStockTest() {
        PurchaseOrder fakePurchaseOrder = PojoFake.purchaseOrder();
        int productId = fakePurchaseOrder.getProduct().getProductId();
        int stockBefore = stockOf(productId);

        assertTrue(purchaseOrderBusiness.saveOrUpdate(fakePurchaseOrder));
        assertEquals(stockBefore - fakePurchaseOrder.getQuantity(), stockOf(productId));

        assertTrue(purchaseOrderBusiness.delete(fakePurchaseOrder.getOrderNum()));
        assertEquals(stockBefore, stockOf(productId));
    }

    /**
     * The order and the stock it takes commit together, so an order nobody has the units
     * for leaves neither a row nor a dent in the stock.
     */
    @Test
    public void anOrderBeyondTheStockOnHandIsRefusedTest() {
        PurchaseOrder fakePurchaseOrder = PojoFake.purchaseOrder();
        int productId = fakePurchaseOrder.getProduct().getProductId();
        int stockBefore = stockOf(productId);
        int ordersBefore = purchaseOrderBusiness.all().size();

        fakePurchaseOrder.setQuantity(stockBefore + 1);

        assertFalse(purchaseOrderBusiness.saveOrUpdate(fakePurchaseOrder));
        assertEquals(stockBefore, stockOf(productId));
        assertEquals(ordersBefore, purchaseOrderBusiness.all().size());

        // The refused order still looks new, so a second attempt for a quantity that fits
        // inserts it instead of trying to update a row that was never written.
        assertNull(fakePurchaseOrder.getOrderNum());

        fakePurchaseOrder.setQuantity(1);
        assertTrue(purchaseOrderBusiness.saveOrUpdate(fakePurchaseOrder));
        try {
            assertEquals(stockBefore - 1, stockOf(productId));
        } finally {
            purchaseOrderBusiness.delete(fakePurchaseOrder.getOrderNum());
        }
    }

    @Test
    public void changingAnOrdersQuantityMovesOnlyTheDifferenceTest() {
        PurchaseOrder fakePurchaseOrder = PojoFake.purchaseOrder();
        int productId = fakePurchaseOrder.getProduct().getProductId();
        int stockBefore = stockOf(productId);

        assertTrue(purchaseOrderBusiness.saveOrUpdate(fakePurchaseOrder));
        try {
            PurchaseOrder saved = purchaseOrderBusiness.findById(fakePurchaseOrder.getOrderNum());
            saved.setQuantity(saved.getQuantity() + 10);

            assertTrue(purchaseOrderBusiness.saveOrUpdate(saved));
            assertEquals(stockBefore - fakePurchaseOrder.getQuantity() - 10, stockOf(productId));

            // Raising it beyond what is left changes neither the order nor the stock.
            saved.setQuantity(stockBefore + 1);
            assertFalse(purchaseOrderBusiness.saveOrUpdate(saved));
            assertEquals(stockBefore - fakePurchaseOrder.getQuantity() - 10, stockOf(productId));
            assertEquals(Integer.valueOf(fakePurchaseOrder.getQuantity() + 10),
                    purchaseOrderBusiness.findById(fakePurchaseOrder.getOrderNum()).getQuantity());
        } finally {
            purchaseOrderBusiness.delete(fakePurchaseOrder.getOrderNum());
        }
        assertEquals(stockBefore, stockOf(productId));
    }

    /**
     * AVAILABLE is derived from the stock on hand, and the statement that moves the stock
     * has to keep it in step or a sold-out product stays on offer. The product combo and
     * both list screens read that flag, not the number.
     */
    @Test
    public void takingTheLastUnitMarksTheProductUnavailableTest() {
        PurchaseOrder fakePurchaseOrder = PojoFake.purchaseOrder();
        int productId = fakePurchaseOrder.getProduct().getProductId();
        int stockBefore = stockOf(productId);

        assertTrue(productOf(productId).isAvailable());

        fakePurchaseOrder.setQuantity(stockBefore);
        assertTrue(purchaseOrderBusiness.saveOrUpdate(fakePurchaseOrder));
        try {
            assertEquals(0, stockOf(productId));
            assertFalse(productOf(productId).isAvailable());
        } finally {
            purchaseOrderBusiness.delete(fakePurchaseOrder.getOrderNum());
        }

        assertEquals(stockBefore, stockOf(productId));
        assertTrue(productOf(productId).isAvailable());
    }

    /**
     * A unit of work that throws has to leave nothing behind, and must not come back as a
     * plain false: a null that slipped through is a bug, not short stock.
     */
    @Test
    public void aUnitOfWorkThatThrowsCommitsNothingTest() {
        int productId = PojoFake.purchaseOrder().getProduct().getProductId();
        int stockBefore = stockOf(productId);

        try {
            Transaction.run(connection -> {
                new ProductDAO().takeStock(connection, productId, 10);
                throw new IllegalStateException("as if a null had slipped through");
            });
            fail("the exception should have come back out");
        } catch (IllegalStateException expected) {
            assertEquals("as if a null had slipped through", expected.getMessage());
        }

        assertEquals(stockBefore, stockOf(productId));
    }

    private static Product productOf(final int productId) {
        return new ProductDAO().findById(productId);
    }

    /**
     * Read through the DAO rather than ProductBusiness: ProductInMemoryBusinessTest swaps
     * the DAO on that singleton and never puts it back, and it runs before this class.
     */
    private static int stockOf(final int productId) {
        return productOf(productId).getQuantityOnHand();
    }

    /**
     * SHIPPING_DATE is a DATE column, so only the day survives a round trip.
     */
    private static String day(final Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}