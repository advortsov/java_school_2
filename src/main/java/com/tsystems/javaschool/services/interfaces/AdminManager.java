package com.tsystems.javaschool.services.interfaces;

import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dto.BookDTO;
import com.tsystems.javaschool.dto.ClientDTO;
import com.tsystems.javaschool.dto.OrderDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 11.02.2016
 */
public interface AdminManager {

    Map<Book, Integer> getTopTenBooks();

    Map<Client, Integer> getTopTenClients();

    List<Order> getOrdersPerPeriod(Date periodStart, Date periodEnd);

    List<OrderDTO> getOrdersDTOPerPeriod(Date periodStart, Date periodEnd);

    List<ClientDTO> getClientsDTOPerPeriod(Date startDate, Date endDate);

    List<BookDTO> getBooksDTOPerPeriod(Date startDate, Date endDate);

}
