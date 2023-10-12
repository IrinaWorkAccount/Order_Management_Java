package com.bitconex.mywebapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

/**
 * A class that represents a product in the product catalog, including name, sale price, availability dates and quantity
 */

@Entity
@Table(name = "products", schema = "public")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "sale_price")
    private double salePrice;
    @JsonIgnore
    @Column(name = "available_from")
    @Temporal(TemporalType.DATE)
    private Date availableFrom;
    @JsonIgnore
    @Column(name = "available_until")
    @Temporal(TemporalType.DATE)
    private Date availableUntil;
    @JsonIgnore
    @Column(name = "quantity")
    private int quantity;



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
    public void setId(Long id) {
        this.id = id;
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


}


