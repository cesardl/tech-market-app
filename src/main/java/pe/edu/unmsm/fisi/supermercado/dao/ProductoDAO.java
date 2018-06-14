package pe.edu.unmsm.fisi.supermercado.dao;

import pe.edu.unmsm.fisi.supermercado.model.Producto;

/**
 * Created on 10/06/2018.
 *
 * @author Cesardl
 */
public interface ProductoDAO extends CrudSimpleDAO<Producto> {

    boolean aniadirProducto(Producto p);

    int numElementos();

    Producto buscarCodigo(int codigo);

    Producto buscarNombre(String nombre);
}
