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
public class ArregloProductosTest {

    private ArregloProductos productBusiness = ArregloProductos.getInstance();

    @BeforeClass
    public static void initialize() throws InitializationError {
        DatabaseTestContext.init();
    }

    @Test
    public void addAndReadProductTest() {
        Collection<Product> result = productBusiness.obtenerTodosLosProductos();
        assertTrue(result.isEmpty());

        Product product = PojoFake.getProduct();
        boolean hasBeenAdded = productBusiness.aniadirProducto(product);
        assertTrue(hasBeenAdded);

        result = productBusiness.obtenerTodosLosProductos();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    public void getProductCodesTest() {
        Collection<ProductCode> result = productBusiness.obtenerTodosLosCodigosDeProducto();
        assertTrue(result.isEmpty());
    }
}