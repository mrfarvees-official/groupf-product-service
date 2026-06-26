package org.groupf.repository;

import jakarta.persistence.EntityManager;
import org.groupf.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void createSequence() {
        entityManager
                .createNativeQuery("""
                        CREATE SEQUENCE IF NOT EXISTS product_seq
                        START WITH 1
                        INCREMENT BY 1
                        """)
                .executeUpdate();
    }

    @Test
    void saveProduct_shouldGeneratePrefixedId() {
        Product product = new Product();

        product.setName("Laptop");
        product.setUnitPrice(250000.0);
        product.setDescription("Gaming laptop");
        product.setCategory("Electronics");
        product.setStock(5);

        Product savedProduct = productRepository.saveAndFlush(product);

        assertNotNull(savedProduct.getProductId());
        assertTrue(savedProduct.getProductId().startsWith("product_"));
    }

    @Test
    void findById_shouldReturnSavedProduct() {
        Product product = new Product();

        product.setName("Mouse");
        product.setUnitPrice(3500.0);
        product.setDescription("Wireless mouse");
        product.setCategory("Accessories");
        product.setStock(10);

        Product savedProduct = productRepository.saveAndFlush(product);

        Product foundProduct = productRepository
                .findById(savedProduct.getProductId())
                .orElseThrow();

        assertEquals(savedProduct.getProductId(), foundProduct.getProductId());
        assertEquals("Mouse", foundProduct.getName());
        assertEquals(3500.0, foundProduct.getUnitPrice());
        assertEquals("Accessories", foundProduct.getCategory());
        assertEquals(10, foundProduct.getStock());
    }

    @Test
    void deleteProduct_shouldRemoveProduct() {
        Product product = new Product();

        product.setName("Keyboard");
        product.setUnitPrice(12000.0);
        product.setDescription("Mechanical keyboard");
        product.setCategory("Accessories");
        product.setStock(3);

        Product savedProduct = productRepository.saveAndFlush(product);

        productRepository.delete(savedProduct);
        productRepository.flush();

        assertFalse(productRepository.findById(savedProduct.getProductId()).isPresent());
    }
}