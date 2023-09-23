package com.bitconex.mywebapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Entity
public class OrderItem implements Serializable {
    private int orderItemQuantity;

    @Id
    @ManyToOne
    //@JoinColumn
    private Order order;
    @Id
    @ManyToOne
    //@JoinColumn
    private Product product;


    public OrderItem(Product product, int orderItemQuantity) {
        super();
        this.product = product;
        this.orderItemQuantity = orderItemQuantity;
    }

    public OrderItem() {

    }

    public int getOrderItemQuantity() {
        return orderItemQuantity;
    }

    public void setOrderItemQuantity(int orderItemQuantity) {
        this.orderItemQuantity = orderItemQuantity;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
