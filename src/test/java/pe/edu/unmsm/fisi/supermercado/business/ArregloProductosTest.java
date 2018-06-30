package pe.edu.unmsm.fisi.supermercado.business;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import pe.edu.unmsm.fisi.DatabaseTestContext;
import pe.edu.unmsm.fisi.supermercado.model.Product;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

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
    }

}