package com.bitconex.mywebapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

/**
 * A class that represents an order and contains the selected products and the customer.
 */

@Entity
@Table(name = "orders", schema = "public")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    //@JoinColumn
    private Product product;

    //@ManyToMany
    // private List<Product> products;

    @ManyToOne//(fetch = FetchType.LAZY)
    //Multiple orders can belong to one customer. This means that a customer can place several orders.
    private User user;

    @ManyToOne
    @JsonBackReference
  //  @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "status")
    private String status;

    public Order() {
        //Default constructor
    }

    public Order(User user, String status, Product product, int quantity) {
        this.user = user;
        this.status = status;
        this.product = product;
        this.quantity = quantity;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    //public List<Product> getProducts() {
    //    return products;
    //}
    //public void setProducts(List<Product> products) {
    //    this.products = products;
    //}
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
