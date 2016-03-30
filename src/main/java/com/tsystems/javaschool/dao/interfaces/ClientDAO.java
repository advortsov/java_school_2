package com.tsystems.javaschool.dao.interfaces;


import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.UserRole;
import com.tsystems.javaschool.dao.exeption.NotRegisteredUserException;

import java.util.Map;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 09.02.2016
 */
public interface ClientDAO extends AbstractJpaDAO<Client> {
    Client findByUserName(String name) throws NotRegisteredUserException;

    //admin features:

    Map<Client, Integer> getTopTenClients();

    UserRole getUserRoleByName(String userRole);
}
