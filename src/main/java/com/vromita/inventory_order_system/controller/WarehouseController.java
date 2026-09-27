package com.vromita.inventory_order_system.controller;


import com.vromita.inventory_order_system.dto.WarehouseRequest;
import com.vromita.inventory_order_system.dto.WarehouseResponse;
import com.vromita.inventory_order_system.mapper.WarehouseMapper;
import com.vromita.inventory_order_system.model.Warehouse;
import com.vromita.inventory_order_system.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;


    public WarehouseController(WarehouseService warehouseService, WarehouseMapper warehouseMapper){
        this.warehouseService=warehouseService;
        this.warehouseMapper=warehouseMapper;
    }

    @PostMapping
    public ResponseEntity<WarehouseResponse> createWarehouse(@Valid @RequestBody WarehouseRequest request){
        Warehouse warehouse = warehouseService.createWarehouse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouseMapper.toResponse(warehouse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponse> getWarehouseById(@PathVariable Long id){
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        return ResponseEntity.status(HttpStatus.OK).body(warehouseMapper.toResponse(warehouse));
    }

    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAllWarehouses(){
        List<Warehouse> warehouses = warehouseService.getAllWarehouses();
        return ResponseEntity.status(HttpStatus.OK).body(warehouseMapper.toResponseList(warehouses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponse> updateWarehouse(@PathVariable Long id, @Valid @RequestBody WarehouseRequest request){
        Warehouse warehouse = warehouseService.updateWarehouse(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(warehouseMapper.toResponse(warehouse));
    }

}
