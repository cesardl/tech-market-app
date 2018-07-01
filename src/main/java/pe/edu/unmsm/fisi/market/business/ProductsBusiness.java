/*
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.dao.impl.ManufacturerDAO;
import pe.edu.unmsm.fisi.market.dao.impl.ProductCodeDAO;
import pe.edu.unmsm.fisi.market.dao.impl.ProductDAO;
import pe.edu.unmsm.fisi.market.model.Manufacturer;
import pe.edu.unmsm.fisi.market.model.Product;
import pe.edu.unmsm.fisi.market.model.ProductCode;

import java.util.Collection;

/**
 * @author Eliana Zapata
 * @since 2009
 */
public class ProductsBusiness {

    private static final Logger LOG = LoggerFactory.getLogger(ProductsBusiness.class);

    private static final ProductsBusiness INSTANCE = new ProductsBusiness();

    private final ProductDAO productDAO;
    private final ProductCodeDAO productCodeDAO;
    private final ManufacturerDAO manufacturerDAO;

    //private constructor to avoid client applications to use constructor
    private ProductsBusiness() {
        productDAO = new ProductDAO();
        productCodeDAO = new ProductCodeDAO();
        manufacturerDAO = new ManufacturerDAO();
    }

    public static ProductsBusiness getInstance() {
        return INSTANCE;
    }

    public Collection<Product> obtenerTodosLosProductos() {
        LOG.info("Getting all products");
        return productDAO.getAll();
    }

    /**
     * Obteniendo el total de productos.
     *
     * @return total de elementos
     */
    public Collection<ProductCode> getProductCodes() {
        LOG.info("Getting all product codes");
        return productCodeDAO.getAll();
    }

    public Collection<Manufacturer> getManufacturers() {
        LOG.info("Getting all manufacturers");
        return manufacturerDAO.getAll();
    }

    public boolean aniadirProducto(Product product) {
        LOG.info("Adding product: {}", product);
        return productDAO.aniadirProducto(product);
    }

    public Product buscarCodigo(int val) {
        LOG.info("Searching product by product identifier: {}", val);
        return productDAO.buscarCodigo(val);
    }

    public Collection<Product> buscarNombre(String str) {
        LOG.info("Searching product by description: {}", str);
        return productDAO.buscarNombre(str);
    }

    public boolean deleteProduct(Product product) {
        LOG.info("Deleting product: {}", product.getDescription());
        return productDAO.delete(product.getProductId());
    }
}
