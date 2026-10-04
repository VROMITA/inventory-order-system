package com.vromita.inventory_order_system.mapper;


import com.vromita.inventory_order_system.dto.ReturnResponse;
import com.vromita.inventory_order_system.model.Return;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface ReturnMapper {

    ReturnResponse toResponse(Return returnEntity );
    List<ReturnResponse> toResponseList(List<Return> returns);
}
