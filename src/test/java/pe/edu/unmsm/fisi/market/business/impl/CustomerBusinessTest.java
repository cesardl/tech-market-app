package pe.edu.unmsm.fisi.market.business.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import pe.edu.unmsm.fisi.market.DatabaseTestContext;
import pe.edu.unmsm.fisi.market.PojoFake;
import pe.edu.unmsm.fisi.market.model.Customer;
import pe.edu.unmsm.fisi.market.model.DiscountCode;
import pe.edu.unmsm.fisi.market.model.MicroMarket;

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
    public void addReadAndDeleteCustomersTest() {
        Collection<Customer> result = customerBusiness.all();

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        result.forEach(customer -> {
            assertNotNull(customer.getCustomerId());
            assertNotNull(customer.getName());
            assertNotNull(customer.getCity());
            assertNotNull(customer.getPhone());
            assertNotNull(customer.getEmail());
        });

        Customer fakeCustomer = PojoFake.getCustomer();
        boolean hasBeenAdded = customerBusiness.saveOrUpdate(fakeCustomer);
        assertTrue(hasBeenAdded);

        Customer customer = customerBusiness.findById(fakeCustomer.getCustomerId());
        assertNotNull(customer.getCustomerId());
        assertNotNull(customer.getDiscountCode());
        assertNotNull(customer.getDiscountCode().getDiscountCode());
        assertNotNull(customer.getMicroMarket());
        assertNotNull(customer.getMicroMarket().getZipCode());
        assertNotNull(customer.getName());
        assertNotNull(customer.getAddressLine1());
        assertNotNull(customer.getAddressLine2());
        assertNotNull(customer.getCity());
        assertNotNull(customer.getState());
        assertNotNull(customer.getPhone());
        assertNotNull(customer.getFax());
        assertNotNull(customer.getEmail());
        assertNotNull(customer.getCreditLimit());

        boolean hasBeenDeleted = customerBusiness.delete(fakeCustomer.getCustomerId());
        assertTrue(hasBeenDeleted);

        result = customerBusiness.all();
        assertEquals(2, result.size());
    }

    @Test
    public void successSearchByIdAndUpdateCustomersTest() {
        Customer result = customerBusiness.findById(2);
        assertNotNull(result.getCustomerId());
        assertNotNull(result.getDiscountCode());
        assertNotNull(result.getDiscountCode().getDiscountCode());
        assertNotNull(result.getMicroMarket());
        assertNotNull(result.getMicroMarket().getZipCode());
        assertNotNull(result.getName());
        assertNotNull(result.getAddressLine1());
        assertNotNull(result.getAddressLine2());
        assertNotNull(result.getCity());
        assertNotNull(result.getState());
        assertNotNull(result.getPhone());
        assertNotNull(result.getFax());
        assertNotNull(result.getEmail());
        assertNotNull(result.getCreditLimit());

        String originalEmail = result.getEmail();
        result.setEmail(null);
        boolean hasBeenUpdated = customerBusiness.saveOrUpdate(result);

        assertTrue(hasBeenUpdated);

        result.setEmail(originalEmail);
        hasBeenUpdated = customerBusiness.saveOrUpdate(result);

        assertTrue(hasBeenUpdated);
    }

    @Test
    public void getDiscountCodesTest() {
        Collection<DiscountCode> result = customerBusiness.getDiscountCodes();
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    public void getMicroMarketsTest() {
        Collection<MicroMarket> result = customerBusiness.getMicroMarkets();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}