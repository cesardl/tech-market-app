/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.dao.CompleteCrudDAO;
import pe.edu.unmsm.fisi.market.model.Customer;
import pe.edu.unmsm.fisi.market.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Cesardl
 */
public class CustomerDAO implements CompleteCrudDAO<Customer> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerDAO.class);

    @Override
    public Collection<Customer> getAll() {
        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute("SELECT CUSTOMER_ID, NAME, CITY, EMAIL FROM CUSTOMER");

            try (ResultSet rs = s.getResultSet()) {
                Collection<Customer> customers = new ArrayList<>();

                while (rs.next()) {
                    Customer c = new Customer();
                    c.setCustomerId(rs.getInt("CUSTOMER_ID"));
                    c.setName(rs.getString("NAME"));
                    c.setCity(rs.getString("CITY"));
                    c.setEmail(rs.getString("EMAIL"));

                    customers.add(c);
                }
                LOG.info("Loading {} customers", customers.size());
                return customers;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }

    @Override
    public Customer buscarCodigo(int codigo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean save(Customer t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Customer t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Customer> buscarNombre(String description) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Customer t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
