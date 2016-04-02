package com.tsystems.javaschool.services.interfaces;

import com.tsystems.javaschool.dao.entity.Book;
import com.tsystems.javaschool.dao.entity.Client;
import com.tsystems.javaschool.dao.entity.Order;
import com.tsystems.javaschool.dto.OrderDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Provides admin tools
 *
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 11.02.2016
 */
public interface AdminManager {

    /**
     * Returns the top books on the number of copies sold
     *
     * @return top ten books of whole orders history
     */
    Map<Book, Integer> getTopTenBooks();

    /**
     * Returns the top clients on revenue
     *
     * @return top ten clients of whole orders history
     */
    Map<Client, Integer> getTopTenClients();

    /**
     * Returns all orders between periodStart and periodEnd
     *
     * @param periodStart the beginning of period
     * @param periodEnd   the end of the period
     * @return list of orders
     */
    List<Order> getOrdersPerPeriod(Date periodStart, Date periodEnd);

    /**
     * Returns all orders between periodStart and periodEnd at OrderDTO entities for REST services
     *
     * @param periodStart the beginning of period
     * @param periodEnd   the end of the period
     * @return list of orderDTOs
     */
    List<OrderDTO> getOrdersDTOPerPeriod(Date periodStart, Date periodEnd);

}
