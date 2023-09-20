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

    @OneToMany( cascade = CascadeType.ALL)//mappedBy = "order",(wird fehler angezeigt
    private List<Product> product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Column(name = "status")
    private String status;

    public Order() {
        //Default constructor
    }
    public Order(Customer customer, List<Product> products, String status) {
        this.customer = customer;
        this.status = status;
        for (Product product : products) {
            product.setOrder(this);
        }
    }

        public Long getId() {
        return id;
    }
        public void setId(Long id) {
            this.id = id;
        }

        public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
