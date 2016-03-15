package com.tsystems.javaschool.dao.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Alexander Dvortsov
 * @version 1.0
 * @since 09.02.2016
 */

@Entity
@Table(name = "order_line")//
@NamedQuery(name = "OrderLine.getAll", query = "SELECT b from OrderLine b")
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public OrderLine() {
    }

    public OrderLine(int quantity, Book book) {
        this.quantity = quantity;
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @OneToOne
    @JoinColumn(name = "book_id")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderLine orderLine = (OrderLine) o;

        if (id != orderLine.id) return false;
        if (quantity != orderLine.quantity) return false;
        if (!order.equals(orderLine.order)) return false;
        return book.equals(orderLine.book);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + order.hashCode();
        result = 31 * result + quantity;
        result = 31 * result + book.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                ", quantity=" + quantity +
                ", book=" + book.getName() +
                '}';
    }
}
