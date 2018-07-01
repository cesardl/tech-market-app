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
public class ProductsBusinessTest {

    private ProductsBusiness productBusiness = ProductsBusiness.getInstance();

    @BeforeClass
    public static void initialize() throws InitializationError {
        DatabaseTestContext.init();
    }

    @Test
    public void addAndReadProductTest() {
        Collection<Product> result = productBusiness.obtenerTodosLosProductos();
        assertEquals(2, result.size());

        Product fakeProduct = PojoFake.getProduct();
        boolean hasBeenAdded = productBusiness.aniadirProducto(fakeProduct);
        assertTrue(hasBeenAdded);

        result = productBusiness.obtenerTodosLosProductos();
        assertEquals(3, result.size());
        result.forEach(product -> {
            assertNotNull(product.getProductId());
            assertNull(product.getProductCode());
            assertNotNull(product.getDescription());
            assertNull(product.getPurchaseCost());
            assertNull(product.getQuantityOnHand());
            assertNull(product.getManufacturer());
        });

        boolean hasBeenDeleted = productBusiness.deleteProduct(fakeProduct);
        assertTrue(hasBeenDeleted);

        result = productBusiness.obtenerTodosLosProductos();
        assertEquals(2, result.size());
    }

    @Test
    public void getProductCodesTest() {
        Collection<ProductCode> result = productBusiness.getProductCodes();
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    public void successSearchProductByDescriptionTest() {
        Collection<Product> result = productBusiness.buscarNombre("server");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        result.forEach(product -> {
            assertNotNull(product.getProductId());
            assertNotNull(product.getProductCode());
            assertNotNull(product.getProductCode().getProdCode());
            assertNotNull(product.getDescription());
            assertNotNull(product.getPurchaseCost());
            assertNotNull(product.getQuantityOnHand());
            assertTrue(product.isAvailable());
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

    @Test
    public void successSearchProductByIdTest() {
        Product result = productBusiness.buscarCodigo(980001);

        assertNotNull(result.getProductId());
        assertNotNull(result.getProductCode());
        assertNotNull(result.getProductCode().getProdCode());
        assertNotNull(result.getDescription());
        assertNotNull(result.getPurchaseCost());
        assertNotNull(result.getQuantityOnHand());
        assertTrue(result.isAvailable());
        assertNotNull(result.getManufacturer());
        assertNotNull(result.getManufacturer().getManufacturerId());
        assertNotNull(result.getManufacturer().getName());
    }

    @Test
    public void failSearchProductByIdTest() {
        Product result = productBusiness.buscarCodigo(0);
        assertNull(result);
    }
}