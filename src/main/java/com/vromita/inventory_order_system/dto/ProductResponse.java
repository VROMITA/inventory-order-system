package com.vromita.inventory_order_system.dto;

import java.math.BigDecimal;

public record ProductResponse(

        Long id,
        String serial,
        String name,
        BigDecimal price

) {
}
