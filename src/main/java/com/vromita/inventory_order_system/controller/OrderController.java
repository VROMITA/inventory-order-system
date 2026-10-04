package com.vromita.inventory_order_system.controller;

import com.vromita.inventory_order_system.dto.OrderItemResponse;
import com.vromita.inventory_order_system.dto.OrderRequest;
import com.vromita.inventory_order_system.dto.OrderResponse;
import com.vromita.inventory_order_system.mapper.OrderItemMapper;
import com.vromita.inventory_order_system.mapper.OrderMapper;
import com.vromita.inventory_order_system.model.Order;
import com.vromita.inventory_order_system.model.OrderItem;
import com.vromita.inventory_order_system.model.OrderStatus;
import com.vromita.inventory_order_system.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest){
        Order order = orderService.createOrder(orderRequest);
        List<OrderItem> items = orderService.getOrderItemsByOrderId(order.getId());

        OrderResponse base = orderMapper.toResponseWithoutItems(order);
        List<OrderItemResponse> itemsResponse = orderItemMapper.toResponseList(items);

        OrderResponse response = new OrderResponse(base.id(), base.customerCode(), base.status(), base.orderDate(), itemsResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id){
        Order order = orderService.getOrderById(id);
        List<OrderItem> items = orderService.getOrderItemsByOrderId(id);

        OrderResponse base = orderMapper.toResponseWithoutItems(order);
        List<OrderItemResponse> itemsResponse = orderItemMapper.toResponseList(items);

        OrderResponse response = new OrderResponse(base.id(), base.customerCode(), base.status(), base.orderDate(), itemsResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(@RequestParam(required = false) String customerCode){
        List<Order> orders = (customerCode != null)
                ? orderService.getOrdersByCustomerCode(customerCode)
                : orderService.getAllOrders();

        List<OrderResponse> responses = new ArrayList<>();

        for(Order order : orders){

            List<OrderItem> items=orderService.getOrderItemsByOrderId(order.getId());
            OrderResponse base= orderMapper.toResponseWithoutItems(order);
            List<OrderItemResponse> itemResponse = orderItemMapper.toResponseList(items);

            OrderResponse response = new OrderResponse(base.id(), base.customerCode(), base.status(), base.orderDate(), itemResponse);

            responses.add(response);
        }

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id){
        Order order = orderService.updateOrderStatus(id, OrderStatus.CANCELLED);
        List<OrderItem> items = orderService.getOrderItemsByOrderId(id);

        OrderResponse base = orderMapper.toResponseWithoutItems(order);
        List<OrderItemResponse> itemsResponse = orderItemMapper.toResponseList(items);

        OrderResponse response = new OrderResponse(
                base.id(), base.customerCode(), base.status(), base.orderDate(), itemsResponse);

        return ResponseEntity.ok(response);
    }
}

