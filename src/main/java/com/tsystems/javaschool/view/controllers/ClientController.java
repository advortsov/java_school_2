package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.exeption.NotRegisteredUserException;
import com.tsystems.javaschool.services.interfaces.ClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 21.02.2016
 */

@Controller
@RequestMapping("/profile")
public class ClientController {

    @Autowired
    private ClientManager clientManager;

//    private Client client;

    @ModelAttribute(value = "clientOrdersList")
    public List<Order> createClientOrdersList(HttpSession session) {
        return clientManager.getClientOrders((Client) session.getAttribute("client"));
    }

    public Client actualizeClient(HttpServletRequest request, String userName) {
        Client client = null;
        try {
            client = clientManager.findByUserName(userName);
        } catch (NotRegisteredUserException ex) {
            client = new Client();
            client.setName("Guest");
            //userName = "Guest";
            //session.setAttribute("username", userName);
        }

        request.getSession().setAttribute("currentClient", client);
        // теперь у нас в сессии есть наш клиент из базы или подложка для анонимуса

        return client;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String mainPage() {
        return "user_pages/profile.jsp";
    }

//    /**
//     * Retrieves the edit page
//     *
//     * @return the name of the JSP page
//     */
//    @RequestMapping(value = "/edit", method = RequestMethod.GET)
//    public String getEdit(@RequestParam(value = "id", required = true) Integer id,
//                          Model model) {
//        logger.debug("Received request to show book edit page");
//        model.addAttribute("book", bookManager.findBookById(id));
//        return "admin_pages/edit";
//    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public String saveEditClient(@ModelAttribute("client") Client client,
                                 @RequestParam("client_name") String name,
                                 @RequestParam("client_surname") String surname,
                                 @RequestParam("client_address") String address,
                                 @RequestParam("client_bday") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date bday,
                                 @RequestParam("client_email") String email) {

//        logger.debug("Received request to show edit page");

        System.out.println("saveEditClient");
        client.setName(name);
        client.setSurname(surname);
        client.setAddress(address);
        client.setBirthday(new java.sql.Date(bday.getTime()));
        client.setEmail(email);

        clientManager.updateClient(client);

        return "pages/books.jsp";
    }


}
