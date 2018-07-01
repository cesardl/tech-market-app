package pe.edu.unmsm.fisi.market;

import pe.edu.unmsm.fisi.market.model.Product;

/**
 * Created on 30/06/2018.
 *
 * @author Cesardl
 */
public class PojoFake {

    public static Product getProduct() {
        return new Product(1, "CPU INTEL CORE I5-6500 6M 3.2 GHZ LGA 1151 SEXTA GEN", 238, 100);
    }
}
