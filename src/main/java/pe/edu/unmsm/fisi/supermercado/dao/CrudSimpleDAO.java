package pe.edu.unmsm.fisi.supermercado.dao;

import java.util.Collection;

/**
 * Created on 13/06/2018.
 *
 * @param <T> entity to define
 * @author Cesardl
 */
public interface CrudSimpleDAO<T> {

    Collection<T> obtenerTodos();
}
