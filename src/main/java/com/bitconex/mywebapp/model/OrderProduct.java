package com.bitconex.mywebapp.model;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

public class OrderProduct implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    private Order order;

    @Id@ManyToOne
    @JoinColumn
    private Product product;
    private int buyqty;

    public OrderProduct() {

    }

    public OrderProduct(Product product, int buyqty) {
        super();
        this.product = product;
        this.buyqty = buyqty;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getBuyqty() {
        return buyqty;
    }

    public void setBuyqty(int buyqty) {
        this.buyqty = buyqty;
    }

}
