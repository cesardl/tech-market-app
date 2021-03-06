/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.business.TemplateBusiness;
import pe.edu.unmsm.fisi.market.dao.impl.CustomerDAO;
import pe.edu.unmsm.fisi.market.dao.impl.DiscountCodeDAO;
import pe.edu.unmsm.fisi.market.dao.impl.MicroMarketDAO;
import pe.edu.unmsm.fisi.market.model.Customer;
import pe.edu.unmsm.fisi.market.model.DiscountCode;
import pe.edu.unmsm.fisi.market.model.MicroMarket;

import java.util.Collection;

/**
 * @author Cesardl
 */
public class CustomerBusiness extends TemplateBusiness<Customer> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerBusiness.class);

    private static final CustomerBusiness INSTANCE = new CustomerBusiness();

    private final DiscountCodeDAO discountCodeDAO;
    private final MicroMarketDAO microMarketDAO;

    //private constructor to avoid client applications to use constructor
    private CustomerBusiness() {
        setDao(new CustomerDAO());
        discountCodeDAO = new DiscountCodeDAO();
        microMarketDAO = new MicroMarketDAO();
    }

    public static CustomerBusiness getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<Customer> all() {
        LOG.info("Getting all customers");
        return dao.getAll();
    }

    public Collection<DiscountCode> getDiscountCodes() {
        LOG.info("Getting all discount codes");
        return discountCodeDAO.getAll();
    }

    public Collection<MicroMarket> getMicroMarkets() {
        LOG.info("Getting all micro markets");
        return microMarketDAO.getAll();
    }

    @Override
    public boolean saveOrUpdate(final Customer customer) {
        if (customer.getCustomerId() == null) {
            LOG.info("Adding customer: {}", customer);

            int identifier = generateIdentifier();
            customer.setCustomerId(identifier);
            return dao.save(customer);
        } else {
            LOG.info("Updating customer: {}", customer);
            return dao.update(customer);
        }
    }

    @Override
    public Customer findById(final Integer identifier) {
        LOG.info("Searching customer by identifier: {}", identifier);
        return dao.findById(identifier);
    }

    @Override
    public boolean delete(final Integer identifier) {
        LOG.info("Deleting customer: {}", identifier);
        Customer product = new Customer();
        product.setCustomerId(identifier);
        return dao.delete(product);
    }
}
