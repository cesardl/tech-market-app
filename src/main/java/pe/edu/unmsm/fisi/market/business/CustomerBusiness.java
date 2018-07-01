/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.dao.impl.CustomerDAO;
import pe.edu.unmsm.fisi.market.model.Customer;

import java.util.Collection;

/**
 * @author Cesardl
 */
public class CustomerBusiness {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerBusiness.class);

    private static final CustomerBusiness INSTANCE = new CustomerBusiness();

    private final CustomerDAO customerDAO;

    //private constructor to avoid client applications to use constructor
    private CustomerBusiness() {
        customerDAO = new CustomerDAO();
    }

    public static CustomerBusiness getInstance() {
        return INSTANCE;
    }

    public Collection<Customer> obtenerTodos() {
        LOG.info("Retornando todos los clientes");
        return customerDAO.getAll();
    }
}
