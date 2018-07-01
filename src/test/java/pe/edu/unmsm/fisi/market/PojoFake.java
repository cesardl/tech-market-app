package pe.edu.unmsm.fisi.market;

import pe.edu.unmsm.fisi.market.model.Manufacturer;
import pe.edu.unmsm.fisi.market.model.Product;
import pe.edu.unmsm.fisi.market.model.ProductCode;

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

        Product p = new Product(1, "CPU INTEL CORE I5-6500 6M 3.2 GHZ LGA 1151 SIXTH", 238.0, 100);
        p.setManufacturer(m);
        p.setProductCode(pc);
        return p;
    }
}
