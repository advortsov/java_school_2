package com.tsystems.javaschool.view.controllers;

import com.tsystems.javaschool.services.interfaces.OrderManager;
import com.tsystems.javaschool.services.interfaces.ShoppingCartManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 17.02.2016
 */

@Controller
@RequestMapping("/order")
public class OrderListController {

    @Autowired
    OrderManager orderManager;

    @Autowired
    ShoppingCartManager shoppingCartManager;

    /**
     * Puts in the shopping cart content of the last order (additional task)
     */
    @RequestMapping(value = "/repeatOrder", method = RequestMethod.POST)
    public String repeatOrder(@RequestParam(value = "id", required = true) long id) {
        orderManager.repeatOrder(id);
        return "pages/cart";
    }
}
