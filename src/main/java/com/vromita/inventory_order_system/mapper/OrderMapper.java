package com.vromita.inventory_order_system.mapper;


import com.vromita.inventory_order_system.dto.OrderResponse;
import com.vromita.inventory_order_system.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target="items", ignore = true)
    OrderResponse toResponseWithoutItems(Order order);
}
