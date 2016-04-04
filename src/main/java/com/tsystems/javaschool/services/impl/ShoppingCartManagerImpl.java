package com.tsystems.javaschool.services.impl;


import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.services.ShoppingCart;
import com.tsystems.javaschool.services.interfaces.BookManager;
import com.tsystems.javaschool.services.interfaces.ShoppingCartManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 09.02.2016
 */
@Service
public class ShoppingCartManagerImpl implements ShoppingCartManager {

    @Autowired
    private ShoppingCart shoppingCart;

    public ShoppingCartManagerImpl() {
    }

    public ShoppingCartManagerImpl(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void removeLine(long id) {
        List<OrderLine> currentLines = shoppingCart.getItems();
        Iterator<OrderLine> i = currentLines.iterator();
        while (i.hasNext()) {
            OrderLine orderLine = i.next();
            if (orderLine.getBook().getId() == id) {
                i.remove();
            }
        }
    }

    @Override
    public void clearCart() {
        List<OrderLine> items = shoppingCart.getItems();
        items.clear();
        shoppingCart.setItems(items);
    }


}
