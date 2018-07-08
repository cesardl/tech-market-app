package pe.edu.unmsm.fisi.market.dao;

import java.util.Collection;

/**
 * Created on 07/07/2018.
 *
 * @author Cesardl
 */
public interface MasterCrudDAO<T> {

    /**
     * Obtain all elements.
     *
     * @return a collections of elements
     */
    Collection<T> getAll();
}
