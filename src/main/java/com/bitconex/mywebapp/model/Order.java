package com.bitconex.mywebapp.model;

import jakarta.persistence.*;

/**
 * The Order class represents an order and contains information about the selected products, the customer, and the order status.
 */
@Entity
@Table(name = "orders", schema = "public")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @ManyToOne
    @JoinColumn
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER)//es werden alle user abgerufen, wenn man Order anruft
    private User user;

    @Column(name = "status")
    private String status;

    /**
     * Constructs a new order with the specified properties.
     *
     * @param user     The user who placed the order.
     * @param status   The status of the order (e.g., "In Progress" or "Confirmed").
     * @param product  The product included in the order.
     * @param quantity The quantity of the product in the order.
     */
    public Order(User user, String status, Product product, int quantity) {
        this.user = user;
        this.status = status;
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Default constructor for the `Order` class.
     */
    public Order() {
        //Default constructor
    }

    // Getters and setters for class properties
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
