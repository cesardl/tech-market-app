package pe.edu.unmsm.fisi.market.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.business.TemplateBusiness;
import pe.edu.unmsm.fisi.market.dao.impl.ProductDAO;
import pe.edu.unmsm.fisi.market.dao.impl.PurchaseOrderDAO;
import pe.edu.unmsm.fisi.market.model.PurchaseOrder;
import pe.edu.unmsm.fisi.market.util.Transaction;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Created on 17/06/2018.
 *
 * @author Cesardl
 */
public class PurchaseOrderBusiness extends TemplateBusiness<PurchaseOrder> {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseOrderBusiness.class);

    private static final PurchaseOrderBusiness INSTANCE = new PurchaseOrderBusiness();

    private final PurchaseOrderDAO purchaseOrderDAO;

    /**
     * The product's DAO rather than {@link ProductBusiness}: an order moves stock inside a
     * transaction, which needs the statement run on a given connection, and nothing in the
     * product's own business layer has a reason to expose that.
     */
    private final ProductDAO productDAO;

    //private constructor to avoid client applications to use constructor
    private PurchaseOrderBusiness() {
        purchaseOrderDAO = new PurchaseOrderDAO();
        setDao(purchaseOrderDAO);
        productDAO = new ProductDAO();
        setIdentifierSize(8);
    }

    public static PurchaseOrderBusiness getInstance() {
        return INSTANCE;
    }

    @Override
    public Collection<PurchaseOrder> all() {
        LOG.info("Getting all purchase orders");
        return dao.getAll();
    }

    @Override
    public boolean saveOrUpdate(final PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getOrderNum() == null) {
            LOG.info("Adding purchase order to customer {}", purchaseOrder.getCustomer().getName());

            Date date = new Date();
            purchaseOrder.setSalesDate(date);

            // Ship in 15 days unless the caller asked for another date: the order form
            // offers a date chooser, and stamping over it would discard what was typed.
            if (purchaseOrder.getShippingDate() == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 15);
                purchaseOrder.setShippingDate(calendar.getTime());
            }

            int identifier = generateIdentifier();
            purchaseOrder.setOrderNum(identifier);

            // An order takes its units out of stock, and may not be recorded unless it got
            // them: both statements commit together or neither does.
            boolean saved = Transaction.run(connection ->
                    productDAO.takeStock(connection, purchaseOrder.getProduct().getProductId(), purchaseOrder.getQuantity())
                            && purchaseOrderDAO.save(connection, purchaseOrder));

            if (!saved) {
                // Nothing was inserted, so leave the order looking new. Keeping the number
                // would send a second attempt down the update branch, to a row that is not
                // there, and the caller could never get the order in at all.
                purchaseOrder.setOrderNum(null);
            }
            return saved;
        } else {
            LOG.info("Updating purchase order to customer {}", purchaseOrder.getCustomer().getName());

            return Transaction.run(connection -> {
                PurchaseOrder stored = purchaseOrderDAO.findHeldStock(connection, purchaseOrder.getOrderNum());
                if (stored == null) {
                    return false;
                }

                // Give back everything the stored order held, then take what this one asks
                // for. The same product and quantity cancel out, a changed product moves
                // stock on both sides, and the held quantity is read inside the transaction
                // so two editors cannot spend the same units.
                return productDAO.takeStock(connection, stored.getProduct().getProductId(), -stored.getQuantity())
                        && productDAO.takeStock(connection, purchaseOrder.getProduct().getProductId(), purchaseOrder.getQuantity())
                        && purchaseOrderDAO.update(connection, purchaseOrder);
            });
        }
    }

    @Override
    public PurchaseOrder findById(final Integer identifier) {
        LOG.info("Searching purchase order by identifier: {}", identifier);
        return dao.findById(identifier);
    }

    @Override
    public boolean delete(final Integer identifier) {
        LOG.info("Deleting purchase order: {}", identifier);

        // Deleting the order gives its units back, or the stock it took would be lost.
        return Transaction.run(connection -> {
            PurchaseOrder stored = purchaseOrderDAO.findHeldStock(connection, identifier);
            if (stored == null) {
                return false;
            }

            return productDAO.takeStock(connection, stored.getProduct().getProductId(), -stored.getQuantity())
                    && purchaseOrderDAO.delete(connection, stored);
        });
    }
}
