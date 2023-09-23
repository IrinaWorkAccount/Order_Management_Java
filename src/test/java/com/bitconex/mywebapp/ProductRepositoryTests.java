package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Calendar;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository pr;

    @Test
    public void testAddNewProduct(){
        Product product = new Product();
        product.setProductName("SomeProduct1");
        product.setProductSalePrice(1290.78);
        Calendar today = Calendar.getInstance();
        product.setProductAvailableFrom(today.getTime());

        Product savedProduct = pr.save(product);

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testListProducts(){
        Iterable<com.bitconex.mywebapp.model.Product> products = pr.findAll();
        Assertions.assertThat(products).hasSizeGreaterThan(0);

        for (Product product: products){
            System.out.println(product);
        }
    }

    @Test
    public void testUpdateProduct(){
        Long productID= 2L;
        Optional<Product> optionalProduct= pr.findById(productID);
        Product product= optionalProduct.orElse(null);
        assert product != null;
        if (product.getProductName() != null) {
            product.setProductQuantity(265);}
        pr.save(product);

        Product updatedProduct= pr.findById(productID).orElse(null);
        assert updatedProduct != null;
        if (updatedProduct.getProductName() != null) {
            Assertions.assertThat(updatedProduct.getProductQuantity()).isEqualTo(265); }
    }

    @Test
    public void testGet(){
        Long productID  = 2L;
        Optional<Product> optionalProduct= pr.findById(productID);

        Assertions.assertThat(optionalProduct).isPresent();
        System.out.println(optionalProduct.get());
    }

    @Test
    public void testDelete(){
        Long productID = 4L;
        pr.deleteById(productID);

        Optional<Product>optionalProduct = pr.findById(productID);
        Assertions.assertThat(optionalProduct).isNotPresent();
    }

}
