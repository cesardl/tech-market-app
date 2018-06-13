package pe.edu.unmsm.fisi.supermercado.dao;

import java.util.Collection;

/**
 * Created on 13/06/2018.
 *
 * @author Cesardl
 * @param <T> entity to define
 */
public interface crudSimpleDAO<T> {

    Collection<T> obtenerTodos();
}
