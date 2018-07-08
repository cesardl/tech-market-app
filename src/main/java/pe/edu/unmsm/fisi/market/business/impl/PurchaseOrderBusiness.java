package pe.edu.unmsm.fisi.market.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.business.TemplateBusiness;
import pe.edu.unmsm.fisi.market.dao.impl.PurchaseOrderDAO;
import pe.edu.unmsm.fisi.market.model.PurchaseOrder;

import java.util.Collection;

/**
 * Created on 17/06/2018.
 *
 * @author Cesardl
 */
public class PurchaseOrderBusiness extends TemplateBusiness<PurchaseOrder> {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseOrderBusiness.class);

    private static final PurchaseOrderBusiness INSTANCE = new PurchaseOrderBusiness();

    //private constructor to avoid client applications to use constructor
    private PurchaseOrderBusiness() {
        setDao(new PurchaseOrderDAO());
    }

    public static PurchaseOrderBusiness getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<PurchaseOrder> obtenerTodos() {
        LOG.info("Getting all purchase orders");
        return dao.getAll();
    }
}
