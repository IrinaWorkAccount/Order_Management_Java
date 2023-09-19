package com.bitconex.mywebapp.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * A class that represents a product in the product catalog, including name, sale price, availability dates and quantity
 */

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "sale_price")
    private double salePrice;
    @Column(name = "available_from")
    @Temporal(TemporalType.DATE)
    private Date availableFrom;
    @Column(name = "available_until")
    @Temporal(TemporalType.DATE)

    private Date availableUntil;

    @Column(name = "quantity")
    private int quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Product> orderProduct;

    @Id
    @ManyToOne
    @JoinColumn
    private Order order;



    public Product(String productName, double salePrice, Date availableFrom, Date availableUntil, int quantity) {
        this.productName = productName;
        this.salePrice = salePrice;
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
        this.quantity = quantity;
    }

    public Product() {
        //Default constructor
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public double getProductSalePrice() {
        return salePrice;
    }

    public void setProductSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public Date getProductAvailableFrom() {
        return availableFrom;
    }

    public void setProductAvailableFrom(Date availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Date getProductAvailableUntil() {
        return availableUntil;
    }

    public void setProductAvailableUntil(Date availableUntil) {
        this.availableUntil = availableUntil;
    }

    public int getProductQuantity() {
        return quantity;
    }

    public void setProductQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<Product> getProduct() {
        return orderProduct;
    }

    public void setProduct(Set<Product> product) {
        this.orderProduct = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}


