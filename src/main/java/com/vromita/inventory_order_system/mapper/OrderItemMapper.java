package com.vromita.inventory_order_system.mapper;

import com.vromita.inventory_order_system.dto.OrderItemResponse;
import com.vromita.inventory_order_system.model.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses={ProductMapper.class, WarehouseMapper.class})
public interface OrderItemMapper {
    OrderItemResponse toResponse(OrderItem orderItem);
    List<OrderItemResponse> toResponseList(List<OrderItem> orderitemList);
}
