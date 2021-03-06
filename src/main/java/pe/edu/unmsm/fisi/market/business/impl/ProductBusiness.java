/*
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.market.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.business.TemplateBusiness;
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
public class ProductBusiness extends TemplateBusiness<Product> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBusiness.class);

    private static final ProductBusiness INSTANCE = new ProductBusiness();

    private final ProductCodeDAO productCodeDAO;
    private final ManufacturerDAO manufacturerDAO;

    //private constructor to avoid client applications to use constructor
    private ProductBusiness() {
        setDao(new ProductDAO());
        productCodeDAO = new ProductCodeDAO();
        manufacturerDAO = new ManufacturerDAO();
        setIdentifierSize(6);
    }

    public static ProductBusiness getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<Product> all() {
        LOG.info("Getting all products");
        return dao.getAll();
    }

    public Collection<ProductCode> getProductCodes() {
        LOG.info("Getting all product codes");
        return productCodeDAO.getAll();
    }

    public Collection<Manufacturer> getManufacturers() {
        LOG.info("Getting all manufacturers");
        return manufacturerDAO.getAll();
    }

    @Override
    public boolean saveOrUpdate(final Product product) {
        product.setAvailable(product.getQuantityOnHand() != 0);

        if (product.getProductId() == null) {
            LOG.info("Adding product: {}", product);

            int identifier = generateIdentifier();
            product.setProductId(identifier);
            return dao.save(product);
        } else {
            LOG.info("Updating product: {}", product);
            return dao.update(product);
        }
    }

    @Override
    public Product findById(final Integer identifier) {
        LOG.info("Searching product by identifier: {}", identifier);
        return dao.findById(identifier);
    }

    public Collection<Product> buscarNombre(String str) {
        LOG.info("Searching product by description: {}", str);
        return dao.buscarNombre(str);
    }

    @Override
    public boolean delete(final Integer identifier) {
        LOG.info("Deleting product: {}", identifier);
        Product product = new Product();
        product.setProductId(identifier);
        return dao.delete(product);
    }
}
