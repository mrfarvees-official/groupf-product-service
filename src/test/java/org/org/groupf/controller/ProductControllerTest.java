package org.groupf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.groupf.dto.ProductCreateRequest;
import org.groupf.entity.Product;
import org.groupf.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductRepository productRepository;

    @Test
    void createProduct_shouldSaveAndReturnProduct() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest(
                "Laptop",
                250000.0,
                "Gaming laptop",
                "Electronics",
                5
        );

        Product savedProduct = new Product(
                "product_0001",
                "Laptop",
                250000.0,
                "Gaming laptop",
                "Electronics",
                5
        );

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("product_0001"))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.unitPrice").value(250000.0))
                .andExpect(jsonPath("$.description").value("Gaming laptop"))
                .andExpect(jsonPath("$.category").value("Electronics"))
                .andExpect(jsonPath("$.stock").value(5));

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_withInvalidRequest_shouldReturnBadRequest() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest(
                "",
                250000.0,
                "Gaming laptop",
                "Electronics",
                5
        );

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void getAllProducts_shouldReturnPagedProducts() throws Exception {
        Product product = new Product(
                "product_0001",
                "Laptop",
                250000.0,
                "Gaming laptop",
                "Electronics",
                5
        );

        when(productRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(List.of(product), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/products")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].productId").value("product_0001"))
                .andExpect(jsonPath("$.content[0].name").value("Laptop"))
                .andExpect(jsonPath("$.content[0].unitPrice").value(250000.0))
                .andExpect(jsonPath("$.content[0].category").value("Electronics"))
                .andExpect(jsonPath("$.content[0].stock").value(5));

        verify(productRepository, times(1))
                .findAll(PageRequest.of(0, 10));
    }

    @Test
    void getProductById_whenProductExists_shouldReturnProduct() throws Exception {
        Product product = new Product(
                "product_0001",
                "Laptop",
                250000.0,
                "Gaming laptop",
                "Electronics",
                5
        );

        when(productRepository.findById("product_0001"))
                .thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/product_0001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("product_0001"))
                .andExpect(jsonPath("$.name").value("Laptop"));

        verify(productRepository, times(1))
                .findById("product_0001");
    }

    @Test
    void deleteProductById_whenProductExists_shouldDeleteProduct() throws Exception {
        Product product = new Product(
                "product_0001",
                "Laptop",
                250000.0,
                "Gaming laptop",
                "Electronics",
                5
        );

        when(productRepository.findById("product_0001"))
                .thenReturn(Optional.of(product));

        mockMvc.perform(delete("/products/product_0001"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Product deleted successfully with id: product_0001"
                ));

        verify(productRepository, times(1))
                .findById("product_0001");

        verify(productRepository, times(1))
                .delete(product);
    }
}