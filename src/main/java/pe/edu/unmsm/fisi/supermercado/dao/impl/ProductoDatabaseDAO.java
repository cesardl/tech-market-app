package pe.edu.unmsm.fisi.supermercado.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.supermercado.dao.ProductoDAO;
import pe.edu.unmsm.fisi.supermercado.model.Producto;
import pe.edu.unmsm.fisi.supermercado.util.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created on 10/06/2018.
 *
 * @author Cesardl
 */
public class ProductoDatabaseDAO implements ProductoDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ProductoDatabaseDAO.class);

    @Override
    public Collection<Producto> obtenerTodos() {
        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute("SELECT PRODUCT_ID, DESCRIPTION, PURCHASE_COST, QUANTITY_ON_HAND FROM PRODUCT");

            try (ResultSet rs = s.getResultSet()) {
                Collection<Producto> vProductos = new ArrayList<>();

                while (rs.next()) {
                    Producto p = parseProducto(rs);

                    vProductos.add(p);
                }
                LOG.info("Se cargaron {} productos", vProductos.size());
                return vProductos;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean aniadirProducto(Producto p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int numElementos() {
        int total = 0;

        try (Connection conn = ConnectionUtils.openConnection();
             Statement s = conn.createStatement()) {
            s.execute("SELECT COUNT(1) FROM PRODUCT");

            try (ResultSet rs = s.getResultSet()) {

                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return total;
    }

    @Override
    public Producto buscarCodigo(int codigo) {
        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT PRODUCT_ID, DESCRIPTION, PURCHASE_COST, QUANTITY_ON_HAND FROM PRODUCT WHERE PRODUCT_ID = ?")) {
            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return parseProducto(rs);
                }
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Producto buscarNombre(String nombre) {
        try (Connection conn = ConnectionUtils.openConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT PRODUCT_ID, DESCRIPTION, PURCHASE_COST, QUANTITY_ON_HAND FROM PRODUCT WHERE DESCRIPTION = ?")) {
            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return parseProducto(rs);
                }
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

    private Producto parseProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setCodigo(rs.getInt("PRODUCT_ID"));
        p.setNombre(rs.getString("DESCRIPTION"));
        p.setPrecUnit(rs.getDouble("PURCHASE_COST"));
        p.setCantidad(rs.getInt("QUANTITY_ON_HAND"));
        return p;
    }
}
