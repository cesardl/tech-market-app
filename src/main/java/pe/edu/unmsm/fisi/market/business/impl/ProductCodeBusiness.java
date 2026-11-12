/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.unmsm.fisi.market.business.impl;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.business.TemplateBusiness;
import pe.edu.unmsm.fisi.market.dao.impl.ProductCodeDAO;
import pe.edu.unmsm.fisi.market.model.ProductCode;

/**
 *
 * @author flaqu
 */
public class ProductCodeBusiness extends TemplateBusiness<ProductCode> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCodeBusiness.class);

    private static final ProductCodeBusiness INSTANCE = new ProductCodeBusiness();

    private ProductCodeBusiness() {
        setDao(new ProductCodeDAO());
    }

    public static ProductCodeBusiness getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<ProductCode> all() {
        LOG.info("Getting all product codes");
        return dao.getAll();
    }

    @Override
    public boolean saveOrUpdate(ProductCode element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ProductCode findById(Integer identifier) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(Integer identifier) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
