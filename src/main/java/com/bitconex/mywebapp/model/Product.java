package com.bitconex.mywebapp.model;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;

/**
 * A class that represents a product in the product catalog, including name, sale price, availability dates and quantity
 */

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    public Integer getId() {
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

    public Date getProductAvailableFrom(Calendar today) {
        if (availableFrom != null && availableFrom.after(today.getTime())) {
            return availableFrom;
        } else {
            return today.getTime();
        }
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


