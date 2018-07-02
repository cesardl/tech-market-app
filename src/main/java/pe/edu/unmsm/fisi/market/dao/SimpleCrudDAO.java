package pe.edu.unmsm.fisi.market.dao;

import java.util.Collection;

/**
 * Created on 13/06/2018.
 *
 * @param <T> entity to define
 * @author Cesardl
 */
public interface SimpleCrudDAO<T> {

    /**
     * Obtain all elements.
     *
     * @return a collections of elements
     */
    Collection<T> getAll();
}
