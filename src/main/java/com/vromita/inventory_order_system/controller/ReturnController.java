package com.vromita.inventory_order_system.controller;

import com.vromita.inventory_order_system.dto.ReturnRequest;
import com.vromita.inventory_order_system.dto.ReturnResponse;
import com.vromita.inventory_order_system.mapper.ReturnMapper;
import com.vromita.inventory_order_system.model.Return;
import com.vromita.inventory_order_system.service.ReturnService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/returns")
public class ReturnController {

    private final ReturnService returnService;
    private final ReturnMapper returnMapper;

    public ReturnController(ReturnService returnService, ReturnMapper returnMapper) {
        this.returnService = returnService;
        this.returnMapper = returnMapper;
    }

    @PostMapping
    public ResponseEntity<ReturnResponse> createReturn(@Valid @RequestBody ReturnRequest request) {
        Return returnEntity = returnService.createReturn(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnMapper.toResponse(returnEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnResponse> getReturnById(@PathVariable Long id){
        Return returnEntity = returnService.getReturnById(id);
        return ResponseEntity.status(HttpStatus.OK).body(returnMapper.toResponse(returnEntity));
    }

    @GetMapping
    public ResponseEntity<List<ReturnResponse>> getAllReturns(@RequestParam(required = false) String customerCode){
        List<Return> returnEntities = (customerCode !=null)
                ? returnService.getReturnsByCustomerCode(customerCode)
                : returnService.getAllReturns();


        return ResponseEntity.status(HttpStatus.OK).body(returnMapper.toResponseList(returnEntities));

    }





}
