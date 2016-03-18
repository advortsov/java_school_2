package com.tsystems.javaschool.dao.entity;

import com.tsystems.javaschool.services.enums.OrderStatus;
import com.tsystems.javaschool.services.enums.PaymentStatus;
import com.tsystems.javaschool.services.enums.PaymentType;
import com.tsystems.javaschool.services.enums.ShippingType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 09.02.2016
 */

@Entity
@Table(name = "buy")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "orderStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "paymentStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "paymentType", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "shippingType", nullable = false)
    @Enumerated(EnumType.STRING)
    private ShippingType shippingType;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;

    public Order() {
    }

    public Order(Client client, OrderStatus orderStatus, PaymentStatus paymentStatus, PaymentType paymentType, ShippingType shippingType, List<OrderLine> orderLines) {
        this.client = client;
        this.orderStatus = orderStatus;//
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
        this.shippingType = shippingType;
        this.orderLines = orderLines;
    }

    public int getTotalSumm() {
        int totalSumm = 0;
        for (OrderLine orderLine : orderLines) {
            totalSumm += (orderLine.getBookActualPrice() * orderLine.getQuantity());
        }
        return totalSumm;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public ShippingType getShippingType() {
        return shippingType;
    }

    public void setShippingType(ShippingType shippingType) {
        this.shippingType = shippingType;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "Order{" +
                ", orderStatus=" + orderStatus +
                ", paymentStatus=" + paymentStatus +
                ", paymentType=" + paymentType +
                ", shippingType=" + shippingType +
                ", date=" + date +
                ", orderLines=" + orderLines +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (!client.equals(order.client)) return false;
        if (orderStatus != order.orderStatus) return false;
        if (paymentStatus != order.paymentStatus) return false;
        if (paymentType != order.paymentType) return false;
        if (shippingType != order.shippingType) return false;
        return date.equals(order.date);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + client.hashCode();
        result = 31 * result + orderStatus.hashCode();
        result = 31 * result + paymentStatus.hashCode();
        result = 31 * result + paymentType.hashCode();
        result = 31 * result + shippingType.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
