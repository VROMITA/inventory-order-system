package com.vromita.inventory_order_system.service;


import com.vromita.inventory_order_system.dto.ProductRequest;
import com.vromita.inventory_order_system.exception.DuplicateSerialException;
import com.vromita.inventory_order_system.exception.ResourceNotFoundException;
import com.vromita.inventory_order_system.model.Product;
import com.vromita.inventory_order_system.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }


    public Product createProduct(ProductRequest request){

        if(productRepository.findBySerial(request.serial()).isPresent()){
            throw new DuplicateSerialException(request.serial());
        }

        Product product = new Product();
        product.setSerial(request.serial());
        product.setName(request.name());
        product.setPrice(request.price());

        return productRepository.save(product);
    }

    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
}
