package pe.edu.unmsm.fisi.market.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.market.dao.CompleteCrudDAO;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created on 07/07/2018.
 *
 * @param <T>
 * @author Cesardl
 */
public abstract class TemplateBusiness<T> {

    private static final Logger LOG = LoggerFactory.getLogger(TemplateBusiness.class);

    protected CompleteCrudDAO<T> dao;

    public void setDao(CompleteCrudDAO<T> dao) {
        this.dao = dao;
    }

    public abstract Collection<T> obtenerTodos();

    public abstract boolean saveOrUpdateProduct(final T element);

    public abstract T buscarCodigo(Integer identifier);

    public abstract boolean delete(Integer identifier);

    protected int generateIdentifier() {
        int id;
        do {
            id = ThreadLocalRandom.current().nextInt(100000, 1000000);
            LOG.debug("Generating random product identifier {}", id);
        } while (dao.buscarCodigo(id) != null);
        return id;
    }
}
