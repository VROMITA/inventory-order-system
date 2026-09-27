package com.vromita.inventory_order_system.mapper;

import com.vromita.inventory_order_system.dto.WarehouseResponse;
import com.vromita.inventory_order_system.model.Warehouse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    WarehouseResponse toResponse(Warehouse warehouse);
    List<WarehouseResponse> toResponseList(List<Warehouse> warehouses);
}
