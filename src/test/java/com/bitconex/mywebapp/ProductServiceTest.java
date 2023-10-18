package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Customer;
import com.bitconex.mywebapp.model.CustomerAddress;
import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.repository.ProductRepository;
import com.bitconex.mywebapp.security.Role;
import com.bitconex.mywebapp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() {
        Product product = new Product();
        product.setProductName("productName");
        product.setProductSalePrice(129.78);
        Calendar today = Calendar.getInstance();
        product.setProductAvailableFrom(today.getTime());
        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.YEAR, 1);
        product.setProductAvailableUntil(futureDate.getTime());
        product.setProductQuantity(10);

        when(productRepository.save(product)).thenReturn(product);

        Product addedProduct = productService.add(product);

        when(productService.listAll()).thenReturn(List.of(product));
        var result = productService.listAll();

        assertNotNull(addedProduct);
        assertEquals("productName", addedProduct.getProductName());
        assertEquals(129.78, addedProduct.getProductSalePrice());
        assertEquals(today.getTime(), addedProduct.getProductAvailableFrom());
        assertEquals(futureDate.getTime(), addedProduct.getProductAvailableUntil());
        assertEquals(10, addedProduct.getProductQuantity());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testListAllProducts() {
        Product product1 = new Product();
        product1.setProductName("productName_1");
        product1.setProductSalePrice(129.78);
        Calendar today1 = Calendar.getInstance();
        product1.setProductAvailableFrom(today1.getTime());
        Calendar futureDate1 = Calendar.getInstance();
        futureDate1.add(Calendar.YEAR, 1);
        product1.setProductAvailableUntil(futureDate1.getTime());
        product1.setProductQuantity(10);

        Product product2 = new Product();
        product2.setProductName("productName_2");
        product2.setProductSalePrice(34.87);
        Calendar today2 = Calendar.getInstance();
        product2.setProductAvailableFrom(today2.getTime());
        Calendar futureDate2 = Calendar.getInstance();
        futureDate2.add(Calendar.YEAR, 2);
        product2.setProductAvailableUntil(futureDate2.getTime());
        product2.setProductQuantity(77);

        when(productService.listAll()).thenReturn(List.of(product1, product2));
        var result = productService.listAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));
    }
    @Test
    public void testDeleteProduct() {
        Long productId = 1L;
        Product mockProduct = new Product();

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        productService.delete(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testDeleteProductNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.delete(productId);
        });

        assertEquals("Product with ID 1 not found.", exception.getMessage());

        verify(productRepository, never()).deleteById(productId);
    }
}