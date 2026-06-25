package org.groupf.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.groupf.dto.ProductCreateRequest;
import org.groupf.entity.Product;
import org.groupf.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @PostMapping
    public Product createProduct(@Valid @RequestBody ProductCreateRequest request) {
        Product product = new Product();

        product.setName(request.name());
        product.setUnitPrice(request.unitPrice());
        product.setDescription(request.description());
        product.setCategory(request.category());
        product.setStock(request.stock());

        return productRepository.save(product);
    }

    @GetMapping
    public Page<Product> getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

    @DeleteMapping("/{productId}")
    public String deleteProductById(@PathVariable String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        productRepository.delete(product);

        return "Product deleted successfully with id: " + productId;
    }
}
