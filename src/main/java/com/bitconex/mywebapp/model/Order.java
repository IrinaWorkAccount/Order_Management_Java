package com.bitconex.mywebapp.model;

import jakarta.persistence.*;
import java.util.Set;


/**
 * A class that represents an order and contains the selected  products and the customer.
 */

@Entity
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProduct;

   @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String status;

    public Order() {
        //Default constructor
    }

    public Order(User user, String status, Set<OrderProduct> orderProduct) {
        this.user = user;
        this.status = status;
        for(OrderProduct op : orderProduct) op.setOrder(this);
        this.orderProduct = orderProduct;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Set<OrderProduct> getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(Set<OrderProduct> orderProduct) {
        this.orderProduct = orderProduct;
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
