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
public class ArregloVentas {

    private static final Logger LOG = LoggerFactory.getLogger(ArregloVentas.class);

    private static final ArregloVentas INSTANCE = new ArregloVentas();

    private final PurchaseOrderDAO purchaseOrderDAO;

    //private constructor to avoid client applications to use constructor
    private ArregloVentas() {
        purchaseOrderDAO = new PurchaseOrderDAO();
    }

    public static ArregloVentas getInstance() {
        return INSTANCE;
    }

    public Object[][] all() {
        LOG.info("Retornando todas las ordenes de compra");
        Collection<PurchaseOrder> purchaseOrders = purchaseOrderDAO.obtenerTodos();

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
