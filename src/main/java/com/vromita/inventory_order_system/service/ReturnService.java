package com.vromita.inventory_order_system.service;

import com.vromita.inventory_order_system.dto.ReturnRequest;
import com.vromita.inventory_order_system.exception.InsufficientReturnException;
import com.vromita.inventory_order_system.exception.InvalidReturnStatusException;
import com.vromita.inventory_order_system.exception.ResourceNotFoundException;
import com.vromita.inventory_order_system.model.OrderItem;
import com.vromita.inventory_order_system.model.OrderStatus;
import com.vromita.inventory_order_system.model.Return;
import com.vromita.inventory_order_system.repository.OrderItemRepository;
import com.vromita.inventory_order_system.repository.ReturnRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReturnService {

    private final ReturnRepository returnRepository;
    private final OrderItemRepository orderItemRepository;
    private final StockService stockService;

    public ReturnService(ReturnRepository returnRepository,
                         OrderItemRepository orderItemRepository,
                         StockService stockService){

        this.returnRepository=returnRepository;
        this.orderItemRepository=orderItemRepository;
        this.stockService=stockService;
    }

    @Transactional
    public Return createReturn(ReturnRequest request){

        OrderItem orderItem = orderItemRepository.findById(request.orderItemId())
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", request.orderItemId()));

        if(orderItem.getOrder().getStatus() != OrderStatus.DELIVERED ){
            throw new InvalidReturnStatusException(orderItem.getOrder().getStatus());
        }

        List<Return> existingReturns = returnRepository.findByOrderItemId(orderItem.getId());
        int alreadyReturned=existingReturns.stream()
                .mapToInt(Return::getQuantity)
                .sum();

        if(alreadyReturned + request.quantity() > orderItem.getQuantity()){
            throw new InsufficientReturnException(orderItem.getId());
        }

        Return returnEntity= new Return();
        returnEntity.setOrderItem(orderItem);
        returnEntity.setQuantity(request.quantity());
        returnEntity.setReason(request.reason());

        Return savedReturn = returnRepository.save(returnEntity);

        stockService.increaseStock(
                     orderItem.getProduct().getId(),
                     orderItem.getWarehouse().getId(),
                     request.quantity());

        return savedReturn;

    }


    public Return getReturnById(Long id) {
        return returnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Return", id));
    }

    public List<Return> getReturnsByOrderItemId(Long orderItemId) {

        return returnRepository.findByOrderItemId(orderItemId);
    }
}
