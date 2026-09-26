package com.vromita.inventory_order_system.controller;

import com.vromita.inventory_order_system.dto.ProductRequest;
import com.vromita.inventory_order_system.dto.ProductResponse;
import com.vromita.inventory_order_system.mapper.ProductMapper;
import com.vromita.inventory_order_system.model.Product;
import com.vromita.inventory_order_system.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper){
        this.productService= productService;
        this.productMapper=productMapper;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest){

        Product product = productService.createProduct(productRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toResponse(product));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){

        Product product = productService.getProductById(id);

        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toResponse(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {

        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productMapper.toResponseList(productList));
    }

}
