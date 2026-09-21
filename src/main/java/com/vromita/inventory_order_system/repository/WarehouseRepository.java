package com.vromita.inventory_order_system.repository;

import com.vromita.inventory_order_system.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findByIdentityCode(String identityCode);
}
