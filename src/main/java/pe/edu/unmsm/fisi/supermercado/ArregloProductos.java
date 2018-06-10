/*
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.supermercado;

import pe.edu.unmsm.fisi.supermercado.model.Producto;

/**
 *
 * @author Eliana Zapata
 * @since 2009
 */
public class ArregloProductos {

    private java.util.Vector<Producto> vProductos;

    public ArregloProductos() {
        vProductos = new java.util.Vector<Producto>();
    }

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

    public Producto getProducto(int index) {
        return vProductos.get(index);
    }

    public int numElementos() {
        return vProductos.size();
    }

    private void ordenar() {
        Producto aux = null;
        int j = 0;
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

    public int buscarCodigo(int codigo) {
        int inicio = 0;
        int fin = numElementos() - 1;
        int pos;
        while (inicio <= fin) {
            pos = (inicio + fin) / 2;
            if (vProductos.get(pos).getCodigo() == codigo) {
                return pos;
            } else if (vProductos.get(pos).getCodigo() < codigo) {
                inicio = pos + 1;
            } else {
                fin = pos - 1;
            }
        }
        return -1;
    }

    public int buscarNombre(String nombre) {
        int i = -1;
        for (Producto producto : vProductos) {
            if (nombre.equals(producto.getNombre())) {
                i = vProductos.indexOf(producto);
                break;
            }
        }
        return i;
    }
}
