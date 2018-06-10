package pe.edu.unmsm.fisi.supermercado.dao.impl;

import pe.edu.unmsm.fisi.supermercado.dao.ProductoDAO;
import pe.edu.unmsm.fisi.supermercado.model.Producto;

import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 10/06/2018.
 *
 * @author Cesardl
 */
public class ProductoInMemoryDAO implements ProductoDAO {

    private final Vector<Producto> vProductos;

    public ProductoInMemoryDAO() {
        this.vProductos = new Vector<>();

        AtomicInteger id = new AtomicInteger(1);

        this.vProductos.add(new Producto(id.getAndIncrement(), "CPU INTEL CORE I5-6500 6M 3.2 GHZ LGA 1151 SEXTA GEN", 238, 100));
        this.vProductos.add(new Producto(id.getAndIncrement(), "PLACA MSI Z17A GAMING PRO DD4", 230, 100));
        this.vProductos.add(new Producto(id.getAndIncrement(), "MEMORIA 8GB DD4 BUS 2133", 60, 100));
        this.vProductos.add(new Producto(id.getAndIncrement(), "DISCO D 1TB SATA III SEAGATE 64 MB 72RPM", 52, 100));
        this.vProductos.add(new Producto(id.getAndIncrement(), "GRABADOR DVD 24X ASUS NEGRO", 17, 100));
        this.vProductos.add(new Producto(id.getAndIncrement(), "VIDEO MSI NVIDIA GTX970 4GB GDDR5 256BITS", 430, 100));
        this.vProductos.add(new Producto(id.getAndIncrement(), "VIDEO MSI NVIDIA GTX980 4GB GDDR5 256BITS", 504, 100));
        this.vProductos.add(new Producto(id.getAndIncrement(), "CASE CORSAIR 500R MT BLACK", 125, 100));
        this.vProductos.add(new Producto(id.getAndIncrement(), "FUENTE 750W CORSAIR CX750W 80+BRONZ", 99, 100));
        this.vProductos.add(new Producto(id.getAndIncrement(), "TECLADO Y MOUSE LOGITECH MK120 USB SP", 10, 100));
        this.vProductos.add(new Producto(id.getAndIncrement(), "ESTABILIZADOR FORZA 8 AVR 220V FVR-1202", 13, 100));
        this.vProductos.add(new Producto(id.getAndIncrement(), "MONITOR 27 LED ASUS IPS MX279H FH 5M FULL HD", 320, 100));
        this.vProductos.add(new Producto(id.getAndIncrement(), "DISCO SOLIDO SSD 240GB A-DATA SP550 2.5 SATA 6GB", 96, 100));
    }

    @Override
    public Collection<Producto> obtenerTodos() {
        return vProductos;
    }

    @Override
    public boolean aniadirProducto(Producto p) {
        if (numElementos() == 0) {
            vProductos.addElement(p);
            return true;
        } else {
            int i = 0;
            for (; i < numElementos(); i++) {
                if (vProductos.get(i).equals(p)) {
                    break;
                }
            }
            if (i == numElementos()) {
                vProductos.addElement(p);
                ordenar();
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public int numElementos() {
        return vProductos.size();
    }

    private void ordenar() {
        Producto aux;
        int j;
        for (int i = 1; i < numElementos(); i++) {
            aux = vProductos.get(i);
            j = i - 1;
            while (j >= 0 && vProductos.get(j).getCodigo() > aux.getCodigo()) {
                vProductos.set(j + 1, vProductos.get(j));
                j--;
            }
            vProductos.set(j + 1, aux);
        }
    }

    @Override
    public Producto buscarCodigo(int codigo) {
        ordenar();

        int inicio = 0;
        int fin = numElementos() - 1;
        int pos;
        while (inicio <= fin) {
            pos = (inicio + fin) / 2;
            if (vProductos.get(pos).getCodigo() == codigo) {
                return vProductos.get(pos);
            } else if (vProductos.get(pos).getCodigo() < codigo) {
                inicio = pos + 1;
            } else {
                fin = pos - 1;
            }
        }
        return null;
    }

    @Override
    public Producto buscarNombre(String nombre) {
        return vProductos.stream()
                .filter(producto -> producto.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }
}
