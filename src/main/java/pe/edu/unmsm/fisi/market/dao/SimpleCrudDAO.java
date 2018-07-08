package pe.edu.unmsm.fisi.market.dao;

/**
 * Created on 13/06/2018.
 *
 * @param <T> entity to define
 * @author Cesardl
 */
public interface SimpleCrudDAO<T> extends MasterCrudDAO<T> {

    /**
     * Obtain specific element.
     *
     * @param codigo element identifier
     * @return an element with code
     */
    T buscarCodigo(int codigo);
}
