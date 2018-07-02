/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.dao.SimpleCrudDAO;
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
public class CustomerDAO implements SimpleCrudDAO<Customer> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerDAO.class);

    @Override
    public Collection<Customer> getAll() {
        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute("SELECT CUSTOMER_ID, NAME FROM CUSTOMER");

            try (ResultSet rs = s.getResultSet()) {
                Collection<Customer> customers = new ArrayList<>();

                while (rs.next()) {
                    Customer c = new Customer();
                    c.setId(rs.getInt("CUSTOMER_ID"));
                    c.setName(rs.getString("NAME"));

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
}