package com.tsystems.javaschool.services.interfaces;

import com.tsystems.javaschool.dao.entity.Order;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 17.02.2016
 */
public interface OrderManager {
    Order findByGenreName(String name);

    List<Order> loadAllOrders();

    void saveNewOrder(Order order);

    Order findOrderById(long id);

    void deleteOrder(Order order);

    int orderTotalSumm(Order order);

    void updateOrder(Order order);

    void repeatOrder(long orderId);
}
