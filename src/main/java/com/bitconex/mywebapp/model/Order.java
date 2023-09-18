package com.bitconex.mywebapp.model;

import jakarta.persistence.*;

import java.util.List;
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
    private Set<Product> product;

    @Id
    @ManyToOne
    @JoinColumn
    private Product products;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String status;

    public Order() {
        //Default constructor
    }

    public Order(User user, String status, Set<Product> product) {
        this.user = user;
        this.status = status;
        for(Product op : product) op.setOrder(this);
        this.product = product;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Set<Product> getProduct() {
        return product;
    }

    public void setProduct(Set<Product> product) {
        this.product = product;
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
