package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.exeption.NotRegisteredUserException;
import com.tsystems.javaschool.services.ShoppingCart;
import com.tsystems.javaschool.services.impl.ClientManagerImpl;
import com.tsystems.javaschool.services.impl.ShoppingCartManagerImpl;
import com.tsystems.javaschool.services.interfaces.ClientManager;
import com.tsystems.javaschool.services.interfaces.ShoppingCartManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 21.02.2016
 */

@Controller
public class ClientController {

    @Autowired
    ClientManager clientManager;



    public Client actualizeClient(HttpServletRequest request, String userName) {
        System.out.println("userName = "  + userName);
        ClientManager clientManager = new ClientManagerImpl();
        HttpSession session = request.getSession();

        Client client = null;
//        try {
//            client = clientManager.findByUserName(userName);
//        } catch (NotRegisteredUserException ex) {
//            client = new Client();
//            client.setName("Guest");
//            userName = "Guest";
//            session.setAttribute("username", userName);
//        }

        client = new Client();
        client.setName("Guest");
        userName = "Guest";
        session.setAttribute("username", userName);

        session.setAttribute("currentClient", client);
        // теперь у нас в сессии есть наш клиент из базы или подложка для анонимуса

        return client;
    }


}
