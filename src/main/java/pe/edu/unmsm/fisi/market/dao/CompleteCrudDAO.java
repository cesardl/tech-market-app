package pe.edu.unmsm.fisi.market.dao;

import pe.edu.unmsm.fisi.market.model.Product;

import java.util.Collection;

/**
 * Created on 01/07/2018.
 *
 * @param <T> entity to define
 * @author Cesardl
 */
public interface CompleteCrudDAO<T> extends SimpleCrudDAO<T> {

    /**
     * Save an element.
     *
     * @param t the element
     * @return true if was saved
     */
    boolean save(T t);

    /**
     * Updating an element.
     *
     * @param t the element
     * @return true if was updated
     */
    boolean update(T t);

    Product buscarCodigo(int codigo);

    Collection<T> buscarNombre(String description);

    /**
     * Deleting an element.
     *
     * @param t the element
     * @return true if was deleted
     */
    boolean delete(T t);
}
