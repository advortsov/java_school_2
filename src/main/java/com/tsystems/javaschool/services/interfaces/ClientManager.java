package com.tsystems.javaschool.services.interfaces;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.entity.UserRole;
import com.tsystems.javaschool.dao.exeption.NotRegisteredUserException;

import java.util.List;

/**
 * Provides methods to interaction with Client entity
 *
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 17.02.2016
 */
public interface ClientManager {

    /**
     * Returns client entity by it user's name
     *
     * @param name the user name
     * @return Client
     */
    Client findByUserName(String name) throws NotRegisteredUserException;

    /**
     * Returns client entity by it id
     *
     * @param id the client id
     * @return Client
     */
    Client findById(long id) throws NotRegisteredUserException;

    /**
     * Updates some changed client entity fields in db
     *
     * @param client the client to updating
     */
    void updateClient(Client client);

    /**
     * Returns all orders found by client
     *
     * @param client the Client entity
     * @return List<Order>
     */
    List<Order> getClientOrders(Client client);

    /**
     * Returns UserRole entity for this client
     *
     * @param userRole the user role name
     * @return UserRole
     */
    UserRole getUserRoleByName(String userRole);
}
