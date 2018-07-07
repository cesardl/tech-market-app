package pe.edu.unmsm.fisi.market.business;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import pe.edu.unmsm.fisi.market.DatabaseTestContext;
import pe.edu.unmsm.fisi.market.PojoFake;
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
    public void addAndReadProductTest() {
        Collection<Product> result = productBusiness.getAllProductsToSell();
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
        boolean hasBeenAdded = productBusiness.saveOrUpdateProduct(fakeProduct);
        assertTrue(hasBeenAdded);

        Product product = productBusiness.buscarCodigo(fakeProduct.getProductId());
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
        assertNotNull(product.getManufacturer().getName());

        boolean hasBeenDeleted = productBusiness.deleteProduct(fakeProduct.getProductId());
        assertTrue(hasBeenDeleted);

        result = productBusiness.getAllProductsToSell();
        assertEquals(2, result.size());
    }

    @Test
    public void successSearchByIdAndUpdateProductTest() {
        Product result = productBusiness.buscarCodigo(980005);

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

        result.setQuantityOnHand(0);
        boolean hasBeenUpdated = productBusiness.saveOrUpdateProduct(result);

        assertTrue(hasBeenUpdated);
        assertFalse(result.isAvailable());
        assertEquals(0, result.getQuantityOnHand().intValue());
    }

    @Test
    public void failSearchProductByIdTest() {
        Product result = productBusiness.buscarCodigo(0);
        assertNull(result);
    }

    @Test
    public void getProductCodesTest() {
        Collection<ProductCode> result = productBusiness.getProductCodes();
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
