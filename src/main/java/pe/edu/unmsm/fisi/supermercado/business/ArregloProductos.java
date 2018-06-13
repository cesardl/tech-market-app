/*
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.supermercado.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.supermercado.dao.ProductoDAO;
import pe.edu.unmsm.fisi.supermercado.dao.impl.ProductoDatabaseDAO;
import pe.edu.unmsm.fisi.supermercado.model.Producto;

import java.util.Collection;

/**
 * @author Eliana Zapata
 * @since 2009
 */
public class ArregloProductos {

    private static final Logger LOG = LoggerFactory.getLogger(ArregloProductos.class);

    private static final ArregloProductos INSTANCE = new ArregloProductos();

    private final ProductoDAO productoDAO;

    //private constructor to avoid client applications to use constructor
    private ArregloProductos() {
        productoDAO = new ProductoDatabaseDAO();
    }

    public static ArregloProductos getInstance() {
        return INSTANCE;
    }

    public Collection<Producto> obtenerTodos() {
        LOG.info("Retornando todos los productos");
        return productoDAO.obtenerTodos();
    }

    /**
     * Obteniendo el total de productos.
     *
     * @return total de elementos
     */
    public int numElementos() {
        LOG.info("Obteniendo el total de productos");
        return productoDAO.numElementos();
    }

    public boolean aniadirProducto(Producto producto) {
        LOG.info("Agregando producto {}", producto);
        return productoDAO.aniadirProducto(producto);
    }

    public Producto buscarCodigo(int val) {
        LOG.info("Realizando busqueda por codigo: {}", val);
        return productoDAO.buscarCodigo(val);
    }

    public Producto buscarNombre(String str) {
        LOG.info("Realizando busqueda por nombre: {}", str);
        return productoDAO.buscarNombre(str);
    }

}
