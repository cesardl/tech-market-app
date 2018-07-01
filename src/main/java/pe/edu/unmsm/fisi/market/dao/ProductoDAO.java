package pe.edu.unmsm.fisi.market.dao;

import pe.edu.unmsm.fisi.market.model.Product;

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

    Collection<Product> buscarNombre(String description);

    boolean delete(int productId);
}
