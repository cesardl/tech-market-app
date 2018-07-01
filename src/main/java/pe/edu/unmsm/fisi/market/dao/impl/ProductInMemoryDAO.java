package pe.edu.unmsm.fisi.market.dao.impl;

import pe.edu.unmsm.fisi.market.dao.ProductoDAO;
import pe.edu.unmsm.fisi.market.model.Product;

import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created on 10/06/2018.
 *
 * @author Cesardl
 */
public class ProductInMemoryDAO implements ProductoDAO {

    private final Vector<Product> vProducts;

    public ProductInMemoryDAO() {
        this.vProducts = new Vector<>();

        AtomicInteger id = new AtomicInteger(1);

        this.vProducts.add(new Product(id.getAndIncrement(), "CPU INTEL CORE I5-6500 6M 3.2 GHZ LGA 1151 SEXTA GEN", 238.0, 100));
        this.vProducts.add(new Product(id.getAndIncrement(), "PLACA MSI Z17A GAMING PRO DD4", 230.0, 100));
        this.vProducts.add(new Product(id.getAndIncrement(), "MEMORIA 8GB DD4 BUS 2133", 60.0, 100));
        this.vProducts.add(new Product(id.getAndIncrement(), "DISCO D 1TB SATA III SEAGATE 64 MB 72RPM", 52.0, 100));
        this.vProducts.add(new Product(id.getAndIncrement(), "GRABADOR DVD 24X ASUS NEGRO", 17.0, 100));
        this.vProducts.add(new Product(id.getAndIncrement(), "VIDEO MSI NVIDIA GTX970 4GB GDDR5 256BITS", 430.0, 100));
        this.vProducts.add(new Product(id.getAndIncrement(), "VIDEO MSI NVIDIA GTX980 4GB GDDR5 256BITS", 504.0, 100));
        this.vProducts.add(new Product(id.getAndIncrement(), "CASE CORSAIR 500R MT BLACK", 125.0, 100));
        this.vProducts.add(new Product(id.getAndIncrement(), "FUENTE 750W CORSAIR CX750W 80+BRONZ", 99.0, 100));
        this.vProducts.add(new Product(id.getAndIncrement(), "TECLADO Y MOUSE LOGITECH MK120 USB SP", 10.0, 100));
        this.vProducts.add(new Product(id.getAndIncrement(), "ESTABILIZADOR FORZA 8 AVR 220V FVR-1202", 13.0, 100));
        this.vProducts.add(new Product(id.getAndIncrement(), "MONITOR 27 LED ASUS IPS MX279H FH 5M FULL HD", 320.0, 100));
        this.vProducts.add(new Product(id.getAndIncrement(), "DISCO SOLIDO SSD 240GB A-DATA SP550 2.5 SATA 6GB", 96.0, 100));
    }

    @Override
    public Collection<Product> getAll() {
        return vProducts;
    }

    @Override
    public boolean aniadirProducto(Product p) {
        if (numElementos() == 0) {
            vProducts.addElement(p);
            return true;
        } else {
            int i = 0;
            for (; i < numElementos(); i++) {
                if (vProducts.get(i).equals(p)) {
                    break;
                }
            }
            if (i == numElementos()) {
                vProducts.addElement(p);
                ordenar();
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public int numElementos() {
        return vProducts.size();
    }

    private void ordenar() {
        Product aux;
        int j;
        for (int i = 1; i < numElementos(); i++) {
            aux = vProducts.get(i);
            j = i - 1;
            while (j >= 0 && vProducts.get(j).getProductId() > aux.getProductId()) {
                vProducts.set(j + 1, vProducts.get(j));
                j--;
            }
            vProducts.set(j + 1, aux);
        }
    }

    @Override
    public Product buscarCodigo(int codigo) {
        ordenar();

        int inicio = 0;
        int fin = numElementos() - 1;
        int pos;
        while (inicio <= fin) {
            pos = (inicio + fin) / 2;
            if (vProducts.get(pos).getProductId() == codigo) {
                return vProducts.get(pos);
            } else if (vProducts.get(pos).getProductId() < codigo) {
                inicio = pos + 1;
            } else {
                fin = pos - 1;
            }
        }
        return null;
    }

    @Override
    public Collection<Product> buscarNombre(String description) {
        return vProducts.stream()
                .filter(p -> p.getDescription().contains(description))
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(int productId) {
        return false;
    }
}
