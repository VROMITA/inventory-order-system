package com.vromita.inventory_order_system.mapper;

import com.vromita.inventory_order_system.dto.StockResponse;
import com.vromita.inventory_order_system.model.Stock;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel="spring", uses={ProductMapper.class, WarehouseMapper.class})
public interface StockMapper {
    StockResponse toResponse(Stock stock);
    List<StockResponse> toResponseList(List<Stock> stocks);

}
