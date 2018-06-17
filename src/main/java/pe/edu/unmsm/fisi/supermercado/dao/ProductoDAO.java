package pe.edu.unmsm.fisi.supermercado.dao;

import pe.edu.unmsm.fisi.supermercado.model.Product;

import java.util.Collection;

/**
 * Created on 10/06/2018.
 *
 * @author Cesardl
 */
public interface ProductoDAO extends CrudSimpleDAO<Product> {

    boolean aniadirProducto(Product p);

    int numElementos();

    Product buscarCodigo(int codigo);

    Collection<Product> buscarNombre(String nombre);
}
