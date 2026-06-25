package org.groupf.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.groupf.annotation.PrefixedId;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @PrefixedId(prefix = "product_", sequenceName = "product_seq")
    @Column(name = "product_id", length = 20, nullable = false, updatable = false)
    private String productId;

    @Column(nullable = false)
    private String name;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    private String description;

    private String category;

    private Integer stock;

}
