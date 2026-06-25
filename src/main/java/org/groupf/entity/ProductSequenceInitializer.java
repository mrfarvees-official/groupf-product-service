package org.groupf.entity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductSequenceInitializer implements CommandLineRunner {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) {
        entityManager
                .createNativeQuery("""
                        CREATE SEQUENCE IF NOT EXISTS product_seq
                        START WITH 1
                        INCREMENT BY 1
                        """)
                .executeUpdate();
    }
}
