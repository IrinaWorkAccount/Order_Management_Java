package com.bitconex.mywebapp.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * A class that represents an order and contains the selected products and the customer.
 */

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
//mappedBy = "order", (An error is displayed) In one order, multiple OrderItems can be included.
    private List<OrderItem> orderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    //Multiple orders can belong to one customer. This means that a customer can place several orders.
    private User user;

    @Column(name = "status")
    private String status;

    public Order() {
        //Default constructor
    }

    public Order(User user, List<OrderItem> orderItem, String status) {
        this.user = user;
        this.status = status;
        this.orderItem = orderItem;
        for (OrderItem oi : orderItem) oi.setOrder(this);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
