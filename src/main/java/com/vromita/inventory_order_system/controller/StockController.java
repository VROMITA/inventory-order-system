package com.vromita.inventory_order_system.controller;


import com.vromita.inventory_order_system.dto.StockRequest;
import com.vromita.inventory_order_system.dto.StockResponse;
import com.vromita.inventory_order_system.mapper.StockMapper;
import com.vromita.inventory_order_system.model.Stock;
import com.vromita.inventory_order_system.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;
    private final StockMapper stockMapper;

    public StockController(StockService stockService, StockMapper stockMapper){
        this.stockService=stockService;
        this.stockMapper=stockMapper;

    }

    @PostMapping
    public ResponseEntity<StockResponse> createStock(@Valid @RequestBody StockRequest stockRequest){
        Stock stock= stockService.createStock(stockRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockMapper.toResponse(stock));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponse> getStockById(@PathVariable Long id){
        Stock stock= stockService.getStockById(id);
        return ResponseEntity.status(HttpStatus.OK).body(stockMapper.toResponse(stock));
    }

    @GetMapping
    public ResponseEntity<List<StockResponse>> getAllStock(){
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.status(HttpStatus.OK).body(stockMapper.toResponseList(stocks));
    }

    @PostMapping("/receive")
    public ResponseEntity<StockResponse> receiveStock(@Valid @RequestBody StockRequest stockRequest){
        Stock stock=stockService.increaseStock(stockRequest.productId(), stockRequest.warehouseId(), stockRequest.quantity());
        return ResponseEntity.status(HttpStatus.OK).body(stockMapper.toResponse(stock));
    }

    @PostMapping("/block")
    public ResponseEntity<StockResponse> blockStock(@Valid @RequestBody StockRequest stockRequest){
        Stock stock=stockService.decreaseStock(stockRequest.productId(), stockRequest.warehouseId(), stockRequest.quantity());
        return ResponseEntity.status(HttpStatus.OK).body(stockMapper.toResponse(stock));
    }
}
