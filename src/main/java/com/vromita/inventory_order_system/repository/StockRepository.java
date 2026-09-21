package com.vromita.inventory_order_system.repository;

import com.vromita.inventory_order_system.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProductIdAndWarehouseId(Long productId, Long warehouseId);

    @Modifying
    @Query("UPDATE Stock s SET s.quantity = s.quantity - :amount " +
           "WHERE s.id = :id AND s.quantity >= :amount")
    int decreaseQuantity(@Param("id") Long id, @Param("amount") int amount);

    @Modifying
    @Query("UPDATE Stock s SET s.quantity = s.quantity + :amount WHERE s.id = :id")

    int increaseQuantity(@Param("id") Long id, @Param("amount") int amount);


}
