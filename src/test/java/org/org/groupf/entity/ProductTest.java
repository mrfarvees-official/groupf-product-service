package org.groupf.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void product_shouldSetAndGetFieldsCorrectly() {
        Product product = new Product();

        product.setProductId("product_0001");
        product.setName("Laptop");
        product.setUnitPrice(250000.0);
        product.setDescription("Gaming laptop");
        product.setCategory("Electronics");
        product.setStock(5);

        assertEquals("product_0001", product.getProductId());
        assertEquals("Laptop", product.getName());
        assertEquals(250000.0, product.getUnitPrice());
        assertEquals("Gaming laptop", product.getDescription());
        assertEquals("Electronics", product.getCategory());
        assertEquals(5, product.getStock());
    }

    @Test
    void allArgsConstructor_shouldCreateProductCorrectly() {
        Product product = new Product(
                "product_0001",
                "Laptop",
                250000.0,
                "Gaming laptop",
                "Electronics",
                5
        );

        assertEquals("product_0001", product.getProductId());
        assertEquals("Laptop", product.getName());
        assertEquals(250000.0, product.getUnitPrice());
        assertEquals("Gaming laptop", product.getDescription());
        assertEquals("Electronics", product.getCategory());
        assertEquals(5, product.getStock());
    }
}