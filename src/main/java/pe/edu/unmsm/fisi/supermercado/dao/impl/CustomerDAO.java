/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.supermercado.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.supermercado.dao.CrudSimpleDAO;
import pe.edu.unmsm.fisi.supermercado.model.Customer;
import pe.edu.unmsm.fisi.supermercado.util.ConnectionUtils;

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
public class CustomerDAO implements CrudSimpleDAO<Customer> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerDAO.class);

    @Override
    public Collection<Customer> obtenerTodos() {
        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute("SELECT CUSTOMER_ID, NAME FROM CUSTOMER");

            try (ResultSet rs = s.getResultSet()) {
                Collection<Customer> vCustomers = new ArrayList<>();

                while (rs.next()) {
                    Customer c = new Customer();
                    c.setId(rs.getInt("CUSTOMER_ID"));
                    c.setName(rs.getString("NAME"));

                    vCustomers.add(c);
                }
                LOG.info("Se cargaron {} clientes", vCustomers.size());
                return vCustomers;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }
}
