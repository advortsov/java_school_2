package com.tsystems.javaschool.services.interfaces;

import com.tsystems.javaschool.dao.entity.Order;

import java.util.List;

/**
 *
 * Provides methods to interaction with Order entity
 *
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 17.02.2016
 */
public interface OrderManager {

    /**
     * Returns all order's entities from db
     *
     * @return List<Order>
     */
    List<Order> loadAllOrders();

    /**
     * Save new genre to database
     *
     * @param order the order entity
     */
    void saveNewOrder(Order order);

    /**
     * Returns genre entity by it id
     *
     * @param id the order id
     * @return Order
     */
    Order findOrderById(long id);

    /**
     * Updates some changed order entity fields in db
     *
     * @param order the order to updating
     */
    void updateOrder(Order order);

    /**
     * Clears the shopping cart and puts there the order
     * lines from order, found by id
     *
     * @param orderId the order id
     */
    void repeatOrder(long orderId);

}
