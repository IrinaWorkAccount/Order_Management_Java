package com.bitconex.mywebapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

/**
 * The Product class represents a product in the product catalog. It includes properties such as the product name, sale price, availability dates, and quantity.
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
   // @Min(value = 0, message = "Sale price must be greater than or equal to 0")
    private double salePrice;
    @JsonIgnore
    @Column(name = "available_from")
    @Temporal(TemporalType.DATE)
    private Date availableFrom;
    @JsonIgnore
    @Column(name = "available_until")
    @Temporal(TemporalType.DATE)
    //@Future(message = "Available from date must be in the future")
    private Date availableUntil;
    @JsonIgnore
    @Column(name = "quantity")
    //@Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private int quantity;

    /**
     * Constructs a new product with the specified properties.
     *
     * @param productName    The name of the product.
     * @param salePrice      The sale price of the product.
     * @param availableFrom  The availability date from which the product can be purchased.
     * @param availableUntil The availability date until which the product can be purchased.
     * @param quantity       The quantity of the product in stock.
     */
    public Product(String productName, double salePrice, Date availableFrom, Date availableUntil, int quantity) {
        this.productName = productName;
        this.salePrice = salePrice;
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
        this.quantity = quantity;
    }

    /**
     * Default constructor for the `Product` class.
     */
    public Product() {
        //Default constructor
    }

    // Getters and setters for class properties
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


