package org.groupf.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductCreateRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validRequest_shouldHaveNoValidationErrors() {
        ProductCreateRequest request = new ProductCreateRequest(
                "Laptop",
                250000.0,
                "Gaming laptop",
                "Electronics",
                5
        );

        Set<ConstraintViolation<ProductCreateRequest>> violations =
                validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void blankName_shouldFailValidation() {
        ProductCreateRequest request = new ProductCreateRequest(
                "",
                250000.0,
                "Gaming laptop",
                "Electronics",
                5
        );

        Set<ConstraintViolation<ProductCreateRequest>> violations =
                validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getMessage().equals("Product name is required"))
        );
    }

    @Test
    void nullUnitPrice_shouldFailValidation() {
        ProductCreateRequest request = new ProductCreateRequest(
                "Laptop",
                null,
                "Gaming laptop",
                "Electronics",
                5
        );

        Set<ConstraintViolation<ProductCreateRequest>> violations =
                validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getMessage().equals("Unit price is required"))
        );
    }

    @Test
    void negativeStock_shouldFailValidation() {
        ProductCreateRequest request = new ProductCreateRequest(
                "Laptop",
                250000.0,
                "Gaming laptop",
                "Electronics",
                -1
        );

        Set<ConstraintViolation<ProductCreateRequest>> violations =
                validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getMessage().equals("Stock cannot be negative"))
        );
    }
}
