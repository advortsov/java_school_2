package com.tsystems.javaschool.services.impl;

import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.dao.interfaces.OrderDAO;
import com.tsystems.javaschool.services.interfaces.OrderManager;
import com.tsystems.javaschool.services.interfaces.ShoppingCartManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 17.02.2016
 */
@Service
@Transactional
public class OrderManagerImpl implements OrderManager {

    @Autowired
    private ShoppingCartManager shoppingCartManager;

    @Autowired
    private OrderDAO orderDAO;


    @Override
    public List<Order> loadAllOrders() {
        return orderDAO.findAll(Order.class);
    }

    @Override
    public void saveNewOrder(Order order) {
        orderDAO.saveOrder(order);
    }

    @Override
    public Order findOrderById(long id) {
        return orderDAO.findByID(Order.class, id);
    }

    @Override
    public void updateOrder(Order order) {
        orderDAO.merge(order);
    }

    @Override
    public void repeatOrder(long orderId) {
        Order order = findOrderById(orderId);
        shoppingCartManager.clearCart();
        List<OrderLine> prevLines = order.getOrderLines();
        List<OrderLine> currLines = new ArrayList<>();

        for (OrderLine line : prevLines) {
            OrderLine newLine = new OrderLine();
            newLine.setBook(line.getBook());
            newLine.setQuantity(line.getQuantity());
            newLine.setBookActualPrice(line.getBookActualPrice());
            newLine.setBookActualPrice(line.getBook().getPrice());
            currLines.add(newLine);
        }

        shoppingCartManager.getShoppingCart().setItems(currLines);
    }


}
