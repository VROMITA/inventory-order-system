package com.vromita.inventory_order_system.service;

import com.vromita.inventory_order_system.dto.OrderItemRequest;
import com.vromita.inventory_order_system.dto.OrderRequest;
import com.vromita.inventory_order_system.exception.InsufficientStockException;
import com.vromita.inventory_order_system.exception.InvalidOrderStatusTransitionException;
import com.vromita.inventory_order_system.exception.ResourceNotFoundException;
import com.vromita.inventory_order_system.model.*;
import com.vromita.inventory_order_system.repository.OrderRepository;
import com.vromita.inventory_order_system.repository.OrderItemRepository;
import com.vromita.inventory_order_system.repository.ProductRepository;
import com.vromita.inventory_order_system.repository.StockRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OrderService {

    private static final Map<OrderStatus, Set<OrderStatus>> ALLOWED_TRANSITIONS = Map.of(
            OrderStatus.ORDERED, Set.of(OrderStatus.PACKAGING, OrderStatus.CANCELLED),
            OrderStatus.PACKAGING, Set.of(OrderStatus.IN_TRANSPORT, OrderStatus.CANCELLED),
            OrderStatus.IN_TRANSPORT, Set.of(OrderStatus.DELIVERED),
            OrderStatus.DELIVERED, Set.of(),
            OrderStatus.CANCELLED, Set.of()
    );

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final StockRepository stockRepository;
    private final StockService stockService;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        OrderItemRepository orderItemRepository,
                        StockRepository stockRepository,
                        StockService stockService){

        this.orderRepository=orderRepository;
        this.productRepository=productRepository;
        this.orderItemRepository=orderItemRepository;
        this.stockRepository=stockRepository;
        this.stockService=stockService;
    }

    @Transactional
    public Order createOrder(OrderRequest request){

        Order order = new Order();
        order.setCustomerCode(request.customerCode());
        order.setStatus(OrderStatus.ORDERED);

        Order savedOrder = orderRepository.save(order);

        for(OrderItemRequest itemRequest : request.items()){

            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", itemRequest.productId()));
            Stock stock=stockRepository.findFirstByProductIdAndQuantityGreaterThanEqual(itemRequest.productId(), itemRequest.quantity())
                    .orElseThrow(() -> new InsufficientStockException(itemRequest.productId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setQuantity(itemRequest.quantity());
            orderItem.setProduct(product);
            orderItem.setPriceAtOrder(product.getPrice());

            orderItemRepository.save(orderItem);

            stockService.decreaseStock(product.getId(), stock.getWarehouse().getId(), itemRequest.quantity());
        }

        return savedOrder;
    }


    public void updateOrderStatus(Long orderId, OrderStatus newStatus){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        Set<OrderStatus> allowedNext = ALLOWED_TRANSITIONS.get(order.getStatus());

        if(!allowedNext.contains(newStatus)){
            throw new InvalidOrderStatusTransitionException(order.getStatus(), newStatus);
        }

        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
    }

    public List<Order> getOrdersByCustomerCode(String customerCode) {
        return orderRepository.findByCustomerCode(customerCode);
    }
}
