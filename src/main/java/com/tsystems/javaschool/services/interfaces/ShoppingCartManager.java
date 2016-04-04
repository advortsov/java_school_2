package com.tsystems.javaschool.services.interfaces;


import com.tsystems.javaschool.services.ShoppingCart;

/**
 * Provides methods to interaction with ShoppingCart entity
 *
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 09.02.2016
 */
public interface ShoppingCartManager {

    /**
     * Removes order line from shopping cart
     *
     * @param id the book id in the deleting line
     */
    void removeLine(long id);

    /**
     * Removes all order lines from the cart
     */
    void clearCart();

    /**
     * returns the actual shopping cart
     */
    ShoppingCart getShoppingCart();

    /**
     * sets the shopping cart to ShoppingCartManager
     *
     * @param shoppingCart shoppingCart for injection in the manager
     */
    void setShoppingCart(ShoppingCart shoppingCart);
}
