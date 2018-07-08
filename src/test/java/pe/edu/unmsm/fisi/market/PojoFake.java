package pe.edu.unmsm.fisi.market;

import pe.edu.unmsm.fisi.market.model.*;

/**
 * Created on 30/06/2018.
 *
 * @author Cesardl
 */
public class PojoFake {

    public static Product getProduct() {
        Manufacturer m = new Manufacturer();
        m.setManufacturerId(19985678);

        ProductCode pc = new ProductCode();
        pc.setProdCode("HW");

        Product p = new Product();
        p.setDescription("CPU INTEL CORE I5-6500 6M 3.2 GHZ LGA 1151 SIXTH");
        p.setPurchaseCost(238.0);
        p.setQuantityOnHand(100);
        p.setMarkup(8.8);
        p.setManufacturer(m);
        p.setProductCode(pc);
        return p;
    }

    public static PurchaseOrder purchaseOrder() {
        Customer customer = new Customer();
        customer.setCustomerId(2);

        Product product = new Product();
        product.setProductId(980005);

        PurchaseOrder po = new PurchaseOrder();
        po.setCustomer(customer);
        po.setProduct(product);
        po.setQuantity(100);
        return po;
    }
}
