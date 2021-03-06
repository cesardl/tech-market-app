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
import pe.edu.unmsm.fisi.market.model.DiscountCode;
import pe.edu.unmsm.fisi.market.model.MicroMarket;
import pe.edu.unmsm.fisi.market.util.ConnectionUtils;

import java.sql.*;
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
        String sql = "SELECT CUSTOMER_ID, NAME, CITY, PHONE, EMAIL FROM CUSTOMER ORDER BY NAME";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute(sql);

            try (ResultSet rs = s.getResultSet()) {
                Collection<Customer> customers = new ArrayList<>();

                while (rs.next()) {
                    Customer c = new Customer();
                    c.setCustomerId(rs.getInt("CUSTOMER_ID"));
                    c.setName(rs.getString("NAME"));
                    c.setCity(rs.getString("CITY"));
                    c.setPhone(rs.getString("PHONE"));
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
    public Customer findById(final int identifier) {
        String sql = "SELECT CUSTOMER_ID, DISCOUNT_CODE, ZIP, NAME, ADDRESSLINE1, ADDRESSLINE2, CITY, STATE, PHONE, FAX, EMAIL, CREDIT_LIMIT " +
                "FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, identifier);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    DiscountCode dc = new DiscountCode();
                    dc.setDiscountCode(rs.getString("DISCOUNT_CODE").charAt(0));

                    MicroMarket mm = new MicroMarket();
                    mm.setZipCode(rs.getString("ZIP"));

                    Customer c = new Customer();
                    c.setDiscountCode(dc);
                    c.setMicroMarket(mm);
                    c.setCustomerId(rs.getInt("CUSTOMER_ID"));
                    c.setName(rs.getString("NAME"));
                    c.setAddressLine1(rs.getString("ADDRESSLINE1"));
                    c.setAddressLine2(rs.getString("ADDRESSLINE2"));
                    c.setCity(rs.getString("CITY"));
                    c.setState(rs.getString("STATE"));
                    c.setPhone(rs.getString("PHONE"));
                    c.setFax(rs.getString("FAX"));
                    c.setEmail(rs.getString("EMAIL"));
                    c.setCreditLimit(rs.getLong("CREDIT_LIMIT"));
                    return c;
                }
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public boolean save(final Customer customer) {
        String sql = "INSERT INTO CUSTOMER(CUSTOMER_ID, DISCOUNT_CODE, ZIP, NAME, ADDRESSLINE1, ADDRESSLINE2, CITY, STATE, PHONE, FAX, EMAIL, CREDIT_LIMIT) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customer.getCustomerId());
            ps.setString(2, String.valueOf(customer.getDiscountCode().getDiscountCode()));
            ps.setString(3, customer.getMicroMarket().getZipCode());
            ps.setString(4, customer.getName());
            ps.setString(5, customer.getAddressLine1());
            ps.setString(6, customer.getAddressLine2());
            ps.setString(7, customer.getCity());
            ps.setString(8, customer.getState());
            ps.setString(9, customer.getPhone());
            ps.setString(10, customer.getFax());
            ps.setString(11, customer.getEmail());
            ps.setLong(12, customer.getCreditLimit());

            int result = ps.executeUpdate();

            LOG.debug("Number of affected rows {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean update(final Customer customer) {
        String sql = "UPDATE CUSTOMER SET DISCOUNT_CODE = ?, ZIP = ?, NAME = ?, ADDRESSLINE1 = ?, ADDRESSLINE2 = ?, CITY = ?, STATE = ?, PHONE = ?, FAX = ?, EMAIL = ?, CREDIT_LIMIT = ? " +
                "WHERE CUSTOMER_ID = ?";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (Connection connection = ConnectionUtils.openConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, String.valueOf(customer.getDiscountCode().getDiscountCode()));
            ps.setString(2, customer.getMicroMarket().getZipCode());
            ps.setString(3, customer.getName());
            ps.setString(4, customer.getAddressLine1());
            ps.setString(5, customer.getAddressLine2());
            ps.setString(6, customer.getCity());
            ps.setString(7, customer.getState());
            ps.setString(8, customer.getPhone());
            ps.setString(9, customer.getFax());
            ps.setString(10, customer.getEmail());
            ps.setLong(11, customer.getCreditLimit());
            ps.setInt(12, customer.getCustomerId());

            int result = ps.executeUpdate();

            LOG.debug("Number of affected rows {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Collection<Customer> buscarNombre(String description) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(final Customer customer) {
        String sql = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ?";

        LOG.debug(ConnectionUtils.SQL_LOG_TEMPLATE, sql);

        try (Connection connection = ConnectionUtils.openConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, customer.getCustomerId());

            int result = ps.executeUpdate();

            LOG.info("A customer has been deleted: {}", result);
            return true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }
}
