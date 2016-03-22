package com.tsystems.javaschool.services.impl;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.exeption.NotRegisteredUserException;
import com.tsystems.javaschool.dao.interfaces.ClientDAO;
import com.tsystems.javaschool.dao.interfaces.OrderDAO;
import com.tsystems.javaschool.services.interfaces.ClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 17.02.2016
 */

@Service
@Transactional
public class ClientManagerImpl implements ClientManager {

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    OrderDAO orderDAO;

    @PersistenceContext
    EntityManager em;

    public ClientManagerImpl(){

    }

    @Override
    public Client findByUserName(String name) throws NotRegisteredUserException {
        return clientDAO.findByUserName(name);
    }

    @Override
    public Client findById(long id) throws NotRegisteredUserException {
        return clientDAO.findByID(Client.class, id);
    }

    @Override
    public void updateClient(Client client) {
        clientDAO.merge(client);
    }

    @Override
    public List<Order> getClientOrders(Client currClient) {
        List<Order> orders = null;
        String sql = "SELECT o FROM Order o WHERE o.client = :currClient";
        Query query = em.createQuery(sql).
                setParameter("currClient", currClient);
        orders = orderDAO.findMany(query);
        return orders;
    }
}
