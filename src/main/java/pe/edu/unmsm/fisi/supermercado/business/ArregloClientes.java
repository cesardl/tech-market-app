/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.fisi.supermercado.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.unmsm.fisi.supermercado.dao.impl.ClienteDatabaseDAO;
import pe.edu.unmsm.fisi.supermercado.model.Cliente;

import java.util.Collection;

/**
 * @author Cesardl
 */
public class ArregloClientes {

    private static final Logger LOG = LoggerFactory.getLogger(ArregloClientes.class);

    private static final ArregloClientes INSTANCE = new ArregloClientes();

    private final ClienteDatabaseDAO clienteDAO;

    //private constructor to avoid client applications to use constructor
    private ArregloClientes() {
        clienteDAO = new ClienteDatabaseDAO();
    }

    public static ArregloClientes getInstance() {
        return INSTANCE;
    }

    public Collection<Cliente> obtenerTodos() {
        LOG.info("Retornando todos los clientes");
        return clienteDAO.obtenerTodos();
    }
}
