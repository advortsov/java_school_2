package com.tsystems.javaschool.services.impl;

import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dao.entity.OrderLine;
import com.tsystems.javaschool.dao.interfaces.BookDAO;
import com.tsystems.javaschool.dao.interfaces.ClientDAO;
import com.tsystems.javaschool.dao.interfaces.OrderDAO;
import com.tsystems.javaschool.dto.BookDTO;
import com.tsystems.javaschool.dto.ClientDTO;
import com.tsystems.javaschool.dto.OrderDTO;
import com.tsystems.javaschool.dto.OrderLineDTO;
import com.tsystems.javaschool.services.interfaces.AdminManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 11.02.2016
 */
@Service
@Transactional
public class AdminManagerImpl implements AdminManager {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private ClientDAO clientDAO;

    public Map<Book, Integer> getTopTenBooks() {
        return bookDAO.getTopTenBooks();
    }

    public Map<Client, Integer> getTopTenClients() {
        return clientDAO.getTopTenClients();
    }

    public List<Order> getOrdersPerPeriod(Date periodStart, Date periodEnd) {
        return orderDAO.getOrdersPerPeriod(periodStart, periodEnd);
    }

    public List<OrderDTO> getOrdersDTOPerPeriod(Date periodStart, Date periodEnd) {
        List<Order> orders = orderDAO.getOrdersPerPeriod(periodStart, periodEnd);
        List<OrderDTO> ordersDTO = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setDate(order.getDate().toString());
            orderDTO.setClientName(order.getClient().getName());
            orderDTO.setClientSurname(order.getClient().getSurname());
            orderDTO.setClientUserName(order.getClient().getUser().getUserName());
            orderDTO.setClientEmail(order.getClient().getEmail());
            orderDTO.setTotalSumm(order.getTotalSumm());

            List<OrderLineDTO> orderLineDTOs = new ArrayList<>();
            for (OrderLine orderLine : order.getOrderLines()) {
                BookDTO bookDTO = new BookDTO();
                bookDTO.setName(orderLine.getBook().getName());
                bookDTO.setActualPrice(orderLine.getBook().getPrice());
                bookDTO.setIsbn(orderLine.getBook().getIsbn());
                bookDTO.setStoreCount(orderLine.getBook().getQuantity());
                OrderLineDTO orderLineDTO = new OrderLineDTO();
                orderLineDTO.setBookDTO(bookDTO);
                orderLineDTO.setCount(orderLine.getQuantity());
                orderLineDTOs.add(orderLineDTO);
            }
            orderDTO.setItems(orderLineDTOs);
            ordersDTO.add(orderDTO);
        }

        return ordersDTO;
    }
}
