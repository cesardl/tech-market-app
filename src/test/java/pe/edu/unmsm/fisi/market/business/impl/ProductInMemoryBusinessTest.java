package pe.edu.unmsm.fisi.market.business.impl;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import pe.edu.unmsm.fisi.market.DatabaseTestContext;
import pe.edu.unmsm.fisi.market.PojoFake;
import pe.edu.unmsm.fisi.market.dao.impl.ProductInMemoryDAO;
import pe.edu.unmsm.fisi.market.model.Product;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created on 07/07/2018.
 *
 * @author Cesardl
 */
public class ProductInMemoryBusinessTest {

    private final ProductBusiness productBusiness = ProductBusiness.getInstance();

    @BeforeClass
    public static void initialize() throws InitializationError {
        DatabaseTestContext.init();
    }

    @Before
    public void setUp() {
        productBusiness.setDao(new ProductInMemoryDAO());
    }

    @Test
    public void addAndReadProductTest() {
        Collection<Product> result = productBusiness.all();
        assertEquals(13, result.size());
        result.forEach(product -> {
            assertNotNull(product.getProductId());
            assertNull(product.getProductCode());
            assertNotNull(product.getDescription());
            assertNotNull(product.getPurchaseCost());
            assertEquals(100, product.getQuantityOnHand().intValue());
            assertNull(product.getManufacturer());
            assertEquals(8.7, product.getMarkup(), 0);
            assertNull(product.isAvailable());
        });

        Product fakeProduct = PojoFake.getProduct();
        boolean hasBeenAdded = productBusiness.saveOrUpdate(fakeProduct);
        assertTrue(hasBeenAdded);

        Product product = productBusiness.findById(fakeProduct.getProductId());
        assertNotNull(product.getProductId());
        assertNotNull(product.getProductCode());
        assertNotNull(product.getProductCode().getProdCode());
        assertNotNull(product.getDescription());
        assertNotNull(product.getPurchaseCost());
        assertNotNull(product.getQuantityOnHand());
        assertNotEquals(0, product.getQuantityOnHand().intValue());
        assertNotEquals(0, product.getMarkup(), 0);
        assertTrue(product.isAvailable());
        assertNotNull(product.getManufacturer());
        assertNotNull(product.getManufacturer().getManufacturerId());
        assertNull(product.getManufacturer().getName());

        boolean hasBeenDeleted = productBusiness.delete(fakeProduct.getProductId());
        assertTrue(hasBeenDeleted);

        result = productBusiness.all();
        assertEquals(13, result.size());
    }

    @Test
    public void successSearchByIdAndUpdateProductTest() {
        Product result = productBusiness.findById(1);

        assertNotNull(result.getProductId());
        assertNull(result.getProductCode());
        assertNotNull(result.getDescription());
        assertNotNull(result.getPurchaseCost());
        assertNotNull(result.getQuantityOnHand());
        assertNotEquals(0, result.getQuantityOnHand().intValue());
        assertNotEquals(0, result.getMarkup(), 0);
        assertNull(result.isAvailable());
        assertNull(result.getManufacturer());

        Integer originalQuantityOnHand = result.getQuantityOnHand();
        result.setQuantityOnHand(0);
        boolean hasBeenUpdated = productBusiness.saveOrUpdate(result);

        assertTrue(hasBeenUpdated);
        assertFalse(result.isAvailable());
        assertEquals(0, result.getQuantityOnHand().intValue());

        result.setQuantityOnHand(originalQuantityOnHand);
        hasBeenUpdated = productBusiness.saveOrUpdate(result);

        assertTrue(hasBeenUpdated);
        assertTrue(result.isAvailable());
        assertEquals(originalQuantityOnHand, result.getQuantityOnHand());
    }

    @Test
    public void failSearchProductByIdTest() {
        Product result = productBusiness.findById(0);
        assertNull(result);
    }

    @Test
    public void successSearchProductByDescriptionTest() {
        Collection<Product> result = productBusiness.buscarNombre("monitor");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        result.forEach(product -> {
            assertNotNull(product.getProductId());
            assertNull(product.getProductCode());
            assertNotNull(product.getDescription());
            assertNotNull(product.getPurchaseCost());
            assertNotNull(product.getQuantityOnHand());
            assertNull(product.isAvailable());
            assertNull(product.getManufacturer());
        });
    }

    @Test
    public void failSearchProductByDescriptionTest() {
        Collection<Product> result = productBusiness.buscarNombre("xxx");
        assertTrue(result.isEmpty());
    }
}