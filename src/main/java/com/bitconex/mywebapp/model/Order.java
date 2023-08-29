package com.bitconex.mywebapp.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * A class that represents an order and contains the selected  products and the customer.
 */

@Entity
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;
    @OneToMany
    private List<Product> products;

    public Order(Customer customer, List<Product> products) {
        this.customer = customer;
        this.products = products;
    }

    public Order() {
        //TODO?
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
