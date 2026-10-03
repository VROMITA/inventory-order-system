package com.vromita.inventory_order_system.service;


import com.vromita.inventory_order_system.dto.StockRequest;
import com.vromita.inventory_order_system.exception.DuplicateStockException;
import com.vromita.inventory_order_system.exception.InsufficientStockException;
import com.vromita.inventory_order_system.exception.ResourceNotFoundException;
import com.vromita.inventory_order_system.model.Product;
import com.vromita.inventory_order_system.model.Stock;
import com.vromita.inventory_order_system.model.Warehouse;
import com.vromita.inventory_order_system.repository.ProductRepository;
import com.vromita.inventory_order_system.repository.StockRepository;
import com.vromita.inventory_order_system.repository.WarehouseRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private static final int MAX_RETRIES = 3;

    public StockService(StockRepository stockRepository,
                        WarehouseRepository warehouseRepository,
                        ProductRepository productRepository){
        this.stockRepository=stockRepository;
        this.productRepository=productRepository;
        this.warehouseRepository=warehouseRepository;
    }


    public Stock createStock(StockRequest stockRequest){

        if(stockRepository.findByProductIdAndWarehouseId(stockRequest.productId(), stockRequest.warehouseId()).isPresent()){
            throw new DuplicateStockException(stockRequest.productId(), stockRequest.warehouseId());
        }

        Product product = productRepository.findById(stockRequest.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", stockRequest.productId()));

        Warehouse warehouse = warehouseRepository.findById(stockRequest.warehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", stockRequest.warehouseId()));

       Stock stock = new Stock();
       stock.setQuantity(stockRequest.quantity());
       stock.setProduct(product);
       stock.setWarehouse(warehouse);

        return stockRepository.save(stock);
    }

    public Stock getStockById(Long id){

       return stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock", id));

    }

    public List<Stock> getAllStocks(){

        return stockRepository.findAll();
    }


    public Stock decreaseStock(Long productId, Long warehouseId, int amount) {
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {

            try {
                Stock stock = stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                        .orElseThrow(() ->new ResourceNotFoundException("Stock", productId));

                if (stock.getQuantity() - amount < 0){
                    throw new InsufficientStockException(productId, warehouseId);
                    }

                   stock.setQuantity(stock.getQuantity() - amount);
                   stockRepository.saveAndFlush(stock);
                   return stock;

                } catch (OptimisticLockingFailureException e) {
            }
        }

        throw new IllegalStateException("Could not update stock for product " + productId + " after " + MAX_RETRIES + " attempts");
    }

    public Stock increaseStock(Long productId, Long warehouseId, int amount) {

        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {

            try {
                Stock stock = stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                        .orElseThrow(() -> new ResourceNotFoundException("Stock", productId));

                stock.setQuantity(stock.getQuantity() + amount);
                stockRepository.saveAndFlush(stock);
                return stock;


            } catch (OptimisticLockingFailureException e) {
            }
        }

        throw new IllegalStateException("Could not update stock for product " + productId + " after " + MAX_RETRIES + " attempts");

    }
}
