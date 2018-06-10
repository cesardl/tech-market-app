package pe.edu.unmsm.fisi.supermercado.dao;

import java.util.Collection;
import pe.edu.unmsm.fisi.supermercado.model.Producto;

/**
 * Created on 10/06/2018.
 *
 * @author Cesardl
 */
public interface ProductoDAO {

    Collection<Producto> obtenerTodos();

    boolean aniadirProducto(Producto p);

    int numElementos();

    Producto buscarCodigo(int codigo);

    Producto buscarNombre(String nombre);
}
