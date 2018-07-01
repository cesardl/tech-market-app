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

    public Object[][] all() {
        LOG.info("Getting all purchase orders");
        Collection<PurchaseOrder> purchaseOrders = purchaseOrderDAO.getAll();

        Object[][] data = new Object[purchaseOrders.size()][5];

        int index = 0;
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            data[index][0] = purchaseOrder.getOrderNum();
            data[index][1] = purchaseOrder.getCustomer().getName();
            data[index][2] = purchaseOrder.getProduct().getDescription();
            data[index][3] = purchaseOrder.getQuantity();
            data[index][4] = purchaseOrder.getSalesDate();
            index++;
        }

        return data;
    }
}
