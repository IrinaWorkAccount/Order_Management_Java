package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Product;
import com.bitconex.mywebapp.repository.ProductRepository;
import com.bitconex.mywebapp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddProduct() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductSalePrice(50.0);
        Calendar today = Calendar.getInstance();
        product.setProductAvailableFrom(today.getTime());
        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.YEAR, 1);
        product.setProductAvailableUntil(futureDate.getTime());
        product.setProductQuantity(10);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product addedProduct = productService.add(product);

        assertNotNull(addedProduct);
        assertEquals("Test Product", addedProduct.getProductName());
        assertEquals(50.0, addedProduct.getProductSalePrice());
        assertEquals("2023-09-28", addedProduct.getProductAvailableFrom());
        assertEquals("2024-09-24", addedProduct.getProductAvailableUntil());
        assertEquals(10, addedProduct.getProductQuantity());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testListAllProducts() {
        List<Product> mockProductList = new ArrayList<>();
        mockProductList.add(new Product());
        mockProductList.add(new Product());

        when(productRepository.findAll()).thenReturn(mockProductList);

        List<Product> productList = productService.listAll();

        assertNotNull(productList);
        assertEquals(2, productList.size());

        verify(productRepository, times(1)).findAll();
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