package com.vromita.inventory_order_system.mapper;

import com.vromita.inventory_order_system.dto.ProductResponse;
import com.vromita.inventory_order_system.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel ="spring")
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);
}
