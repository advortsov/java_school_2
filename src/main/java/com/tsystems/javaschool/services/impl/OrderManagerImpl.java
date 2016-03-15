package com.tsystems.javaschool.services.impl;

import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.dao.interfaces.BookDAO;
import com.tsystems.javaschool.dao.interfaces.OrderDAO;
import com.tsystems.javaschool.services.interfaces.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 17.02.2016
 */
@Service
public class OrderManagerImpl implements OrderManager {

    @Autowired
    BookDAO bookDAO;
    @Autowired
    OrderDAO orderDAO;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Order findByGenreName(String name) {
        return null;
    }

    @Override
    public List<Order> loadAllOrders() {
        return orderDAO.findAll(Order.class);
    }

    @Override
    public void saveNewOrder(Order order) {
        orderDAO.save(order);
    }


    @Override
    public Order findOrderById(long id) {
        return orderDAO.findByID(Order.class, id);
    }

    @Override
    public void deleteOrder(Order order) {
        throw new UnsupportedOperationException();
    }

    public int orderTotalSumm(Order order) {
//        List<OrderLine> orderLines = order.getOrderLines();
//        int totalSumm = 0;
//        for (OrderLine orderLine : orderLines) {
//            totalSumm += (orderLine.getBook().getPrice() * orderLine.getQuantity());
//        }
//        return totalSumm;
        return -1;
    }

    @Override
    public void updateOrder(Order order) {
        orderDAO.merge(order);
    }

}
