package pe.edu.unmsm.fisi.market.business.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import pe.edu.unmsm.fisi.market.DatabaseTestContext;
import pe.edu.unmsm.fisi.market.PojoFake;
import pe.edu.unmsm.fisi.market.model.Manufacturer;
import pe.edu.unmsm.fisi.market.model.Product;
import pe.edu.unmsm.fisi.market.model.ProductCode;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created on 30/06/2018.
 *
 * @author Cesardl
 */
public class ProductBusinessTest {

    private final ProductBusiness productBusiness = ProductBusiness.getInstance();

    @BeforeClass
    public static void initialize() throws InitializationError {
        DatabaseTestContext.init();
    }

    @Test
    public void addReadAndDeleteProductTest() {
        Collection<Product> result = productBusiness.all();
        assertEquals(2, result.size());
        result.forEach(product -> {
            assertNotNull(product.getProductId());
            assertNull(product.getProductCode());
            assertNotNull(product.getDescription());
            assertNull(product.getPurchaseCost());
            assertEquals(0, product.getQuantityOnHand().intValue());
            assertNull(product.getManufacturer());
            assertEquals(0, product.getMarkup(), 0);
            assertFalse(product.isAvailable());
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
        assertNotNull(product.getMarkup());
        assertEquals(fakeProduct.getQuantityOnHand(), product.getQuantityOnHand());
        assertEquals(fakeProduct.getMarkup(), product.getMarkup(), 0);
        assertTrue(product.isAvailable());
        assertNotNull(product.getManufacturer());
        assertNotNull(product.getManufacturer().getManufacturerId());
        assertNotNull(product.getManufacturer().getName());

        boolean hasBeenDeleted = productBusiness.delete(fakeProduct.getProductId());
        assertTrue(hasBeenDeleted);

        result = productBusiness.all();
        assertEquals(2, result.size());
    }

    @Test
    public void successSearchByIdAndUpdateProductTest() {
        Product result = productBusiness.findById(980005);

        assertNotNull(result.getProductId());
        assertNotNull(result.getProductCode());
        assertNotNull(result.getProductCode().getProdCode());
        assertNotNull(result.getDescription());
        assertNotNull(result.getPurchaseCost());
        assertNotNull(result.getQuantityOnHand());
        assertNotEquals(0, result.getQuantityOnHand().intValue());
        assertNotEquals(0, result.getMarkup(), 0);
        assertTrue(result.isAvailable());
        assertNotNull(result.getManufacturer());
        assertNotNull(result.getManufacturer().getManufacturerId());
        assertNotNull(result.getManufacturer().getName());

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
    public void getProductCodesTest() {
        Collection<ProductCode> result = productBusiness.getProductCodes();
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    public void getManufacturersTest() {
        Collection<Manufacturer> result = productBusiness.getManufacturers();
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    public void successSearchProductByDescriptionTest() {
        Collection<Product> result = productBusiness.buscarNombre("computer");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        result.forEach(product -> {
            assertNotNull(product.getProductId());
            assertNotNull(product.getProductCode());
            assertNotNull(product.getProductCode().getProdCode());
            assertNotNull(product.getDescription());
            assertNotNull(product.getPurchaseCost());
            assertNotNull(product.getQuantityOnHand());
            assertFalse(product.isAvailable());
            assertNotNull(product.getManufacturer());
            assertNotNull(product.getManufacturer().getManufacturerId());
            assertNotNull(product.getManufacturer().getName());
        });
    }

    @Test
    public void failSearchProductByDescriptionTest() {
        Collection<Product> result = productBusiness.buscarNombre("xxx");
        assertTrue(result.isEmpty());
    }
}
