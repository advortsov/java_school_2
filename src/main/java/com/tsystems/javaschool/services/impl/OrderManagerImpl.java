package com.tsystems.javaschool.services.impl;

import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.dao.interfaces.BookDAO;
import com.tsystems.javaschool.dao.interfaces.OrderDAO;
import com.tsystems.javaschool.services.interfaces.OrderManager;
import com.tsystems.javaschool.services.interfaces.ShoppingCartManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    ShoppingCartManager shoppingCartManager;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private OrderDAO orderDAO;
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
        orderDAO.saveOrder(order);
    }


    @Override
    public Order findOrderById(long id) {
        return orderDAO.findByID(Order.class, id);
    }

    @Override
    public void deleteOrder(Order order) {
        throw new UnsupportedOperationException();
    }

//    public int orderTotalSumm(Order order) {
////        List<OrderLine> orderLines = order.getOrderLines();
////        int totalSumm = 0;
////        for (OrderLine orderLine : orderLines) {
////            totalSumm += (orderLine.getBook().getPrice() * orderLine.getQuantity());
////        }
////        return totalSumm;
//        return -1;
//    }

    @Override
    public void updateOrder(Order order) {
        orderDAO.merge(order);
    }

    @Override
    public void repeatOrder(long orderId) {
        Order order = findOrderById(orderId);
        System.out.println("order.getId() = " + order.getId());
        shoppingCartManager.clearCart();
        List<OrderLine> prevLines = order.getOrderLines();
        List<OrderLine> currLines = new ArrayList<>();

        for (OrderLine line : prevLines){
            OrderLine newLine = new OrderLine();
            newLine.setBook(line.getBook());
            newLine.setQuantity(line.getQuantity());
            newLine.setBookActualPrice(line.getBookActualPrice());
            newLine.setBookActualPrice(line.getBook().getPrice());
            currLines.add(newLine);
        }

        shoppingCartManager.getShoppingCart().setItems(currLines);
        System.out.println("shoppingCartManager.getShoppingCart().getItems().size() = "
                + shoppingCartManager.getShoppingCart().getItems().size());

    }


}
