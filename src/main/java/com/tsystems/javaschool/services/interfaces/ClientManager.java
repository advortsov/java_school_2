package com.tsystems.javaschool.services.interfaces;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.entity.UserRole;
import com.tsystems.javaschool.dao.exeption.NotRegisteredUserException;

import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 17.02.2016
 */
public interface ClientManager {
    Client findByUserName(String name) throws NotRegisteredUserException;

    Client findById(long id) throws NotRegisteredUserException;

    void updateClient(Client client);

    List<Order> getClientOrders(Client client);

    UserRole getUserRoleByName(String userRole);
}
