package pe.edu.unmsm.fisi.market.business.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import pe.edu.unmsm.fisi.market.DatabaseTestContext;
import pe.edu.unmsm.fisi.market.business.impl.CustomerBusiness;
import pe.edu.unmsm.fisi.market.model.Customer;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created on 04/07/2018.
 *
 * @author Cesardl
 */
public class CustomerBusinessTest {

    private final CustomerBusiness customerBusiness = CustomerBusiness.getInstance();

    @BeforeClass
    public static void initialize() throws InitializationError {
        DatabaseTestContext.init();
    }

    @Test
    public void listCustomersTest() {
        Collection<Customer> result = customerBusiness.obtenerTodos();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        result.forEach(customer -> {
            assertNotNull(customer.getCustomerId());
            assertNotNull(customer.getName());
            assertNotNull(customer.getCity());
            assertNotNull(customer.getEmail());
        });
    }
}