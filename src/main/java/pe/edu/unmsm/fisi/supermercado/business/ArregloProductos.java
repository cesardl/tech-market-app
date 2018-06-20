/*
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.supermercado.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.supermercado.dao.impl.ProductCodeDAO;
import pe.edu.unmsm.fisi.supermercado.dao.impl.ProductoDAO;
import pe.edu.unmsm.fisi.supermercado.model.Product;
import pe.edu.unmsm.fisi.supermercado.model.ProductCode;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Eliana Zapata
 * @since 2009
 */
public class ArregloProductos {

    private static final Logger LOG = LoggerFactory.getLogger(ArregloProductos.class);

    private static final ArregloProductos INSTANCE = new ArregloProductos();

    private final ProductoDAO productoDAO;
    private final ProductCodeDAO productCodeDAO;

    //private constructor to avoid client applications to use constructor
    private ArregloProductos() {
        productoDAO = new ProductoDAO();
        productCodeDAO = new ProductCodeDAO();
    }

    public static ArregloProductos getInstance() {
        return INSTANCE;
    }

    public Collection<Product> obtenerTodosLosProductos() {
        LOG.info("Retornando todos los productos");
        return productoDAO.obtenerTodos();
    }

    /**
     * Obteniendo el total de productos.
     *
     * @return total de elementos
     */
    public Collection<ProductCode> obtenerTodosLosCodigosDeProducto() {
        LOG.info("Obteniendo el total de codigos de producto");
        return productCodeDAO.obtenerTodos();
    }

    public boolean aniadirProducto(Product product) {
        LOG.info("Agregando product {}", product);
        return productoDAO.aniadirProducto(product);
    }

    public Product buscarCodigo(int val) {
        LOG.info("Realizando busqueda por codigo: {}", val);
        return productoDAO.buscarCodigo(val);
    }

    public Collection<Product> buscarNombre(String str) {
        LOG.info("Realizando busqueda por nombre: {}", str);
        return productoDAO.buscarNombre(str);
    }
}
