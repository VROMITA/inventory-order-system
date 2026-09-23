package com.vromita.inventory_order_system.service;


import com.vromita.inventory_order_system.exception.InsufficientStockException;
import com.vromita.inventory_order_system.exception.ResourceNotFoundException;
import com.vromita.inventory_order_system.model.Stock;
import com.vromita.inventory_order_system.repository.StockRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private static final int MAX_RETRIES = 3;

    public StockService(StockRepository stockRepository){
        this.stockRepository=stockRepository;
    }


    public void decreaseStock(Long productId, Long warehouseId, int amount) {
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {

            try {
                Stock stock = stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                        .orElseThrow(() ->new ResourceNotFoundException("Stock", productId));

                if (stock.getQuantity() - amount < 0){
                    throw new InsufficientStockException(productId, warehouseId);
                    }

                   stock.setQuantity(stock.getQuantity() - amount);
                   stockRepository.saveAndFlush(stock);
                   return;

                } catch (OptimisticLockingFailureException e) {
            }
        }

        throw new IllegalStateException("Could not update stock for product " + productId + " after " + MAX_RETRIES + " attempts");
    }

    public void increaseStock(Long productId, Long warehouseId, int amount) {

        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {

            try {
                Stock stock = stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                        .orElseThrow(() -> new ResourceNotFoundException("Stock", productId));

                stock.setQuantity(stock.getQuantity() + amount);
                stockRepository.saveAndFlush(stock);
                return;


            } catch (OptimisticLockingFailureException e) {
            }
        }

        throw new IllegalStateException("Could not update stock for product " + productId + " after " + MAX_RETRIES + " attempts");


    }
}
