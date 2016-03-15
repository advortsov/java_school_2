package com.tsystems.javaschool.services.impl;

import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.interfaces.BookDAO;
import com.tsystems.javaschool.dao.interfaces.ClientDAO;
import com.tsystems.javaschool.dao.interfaces.OrderDAO;
import com.tsystems.javaschool.services.interfaces.AdminManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 11.02.2016
 */
@Service
public class AdminManagerImpl implements AdminManager {

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    BookDAO bookDAO;

    @Autowired
    ClientDAO clientDAO;

    public Map<Book, Integer> getTopTenBooks() {
        return bookDAO.getTopTenBooks();
    }

    public Map<Client, Integer> getTopTenClients() {
        return clientDAO.getTopTenClients();
    }

    public List<Order> getOrdersPerPeriod(Date periodStart, Date periodEnd) {
        return orderDAO.getOrdersPerPeriod(periodStart, periodEnd);
    }
}
