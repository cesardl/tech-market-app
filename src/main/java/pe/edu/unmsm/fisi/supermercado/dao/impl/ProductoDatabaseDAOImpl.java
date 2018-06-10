package pe.edu.unmsm.fisi.supermercado.dao.impl;

import java.util.Collection;
import pe.edu.unmsm.fisi.supermercado.dao.ProductoDAO;
import pe.edu.unmsm.fisi.supermercado.model.Producto;

/**
 * Created on 10/06/2018.
 *
 * @author Cesardl
 */
public class ProductoDatabaseDAOImpl implements ProductoDAO {

    @Override
    public Collection<Producto> obtenerTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean aniadirProducto(Producto p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int numElementos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Producto buscarCodigo(int codigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Producto buscarNombre(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
