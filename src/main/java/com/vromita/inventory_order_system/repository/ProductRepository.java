package com.vromita.inventory_order_system.repository;

import com.vromita.inventory_order_system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Optional<Product> findBySerial(String serial);
}
