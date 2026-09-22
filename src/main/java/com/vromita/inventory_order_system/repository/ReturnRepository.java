package com.vromita.inventory_order_system.repository;

import com.vromita.inventory_order_system.model.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Long> {

    List<Return> findByOrderItemId(Long orderItemId);

}
