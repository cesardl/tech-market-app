package pe.edu.unmsm.fisi.market.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.dao.impl.PurchaseOrderDAO;
import pe.edu.unmsm.fisi.market.model.PurchaseOrder;

import java.util.Collection;

/**
 * Created on 17/06/2018.
 *
 * @author Cesardl
 */
public class PurchaseOrderBusiness {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseOrderBusiness.class);

    private static final PurchaseOrderBusiness INSTANCE = new PurchaseOrderBusiness();

    private final PurchaseOrderDAO purchaseOrderDAO;

    //private constructor to avoid client applications to use constructor
    private PurchaseOrderBusiness() {
        purchaseOrderDAO = new PurchaseOrderDAO();
    }

    public static PurchaseOrderBusiness getInstance() {
        return INSTANCE;
    }

    public Collection<PurchaseOrder> all() {
        LOG.info("Getting all purchase orders");
        return purchaseOrderDAO.getAll();
    }
}
