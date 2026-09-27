package com.vromita.inventory_order_system.service;

import com.vromita.inventory_order_system.dto.WarehouseRequest;
import com.vromita.inventory_order_system.exception.DuplicateIdentityCodeException;
import com.vromita.inventory_order_system.exception.ResourceNotFoundException;
import com.vromita.inventory_order_system.model.Warehouse;
import com.vromita.inventory_order_system.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository){
        this.warehouseRepository=warehouseRepository;
    }

    public Warehouse createWarehouse(WarehouseRequest request){

        if(warehouseRepository.findByIdentityCode(request.identityCode()).isPresent()){
            throw new DuplicateIdentityCodeException(request.identityCode());
        }

        Warehouse warehouse = new Warehouse();
        warehouse.setIdentityCode(request.identityCode());
        warehouse.setAddress(request.address());
        warehouse.setName(request.name());

        return warehouseRepository.save(warehouse);
    }

    public Warehouse getWarehouseById(Long id){
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", id));
    }

    public List<Warehouse> getAllWarehouses(){
        return warehouseRepository.findAll();
    }

    public Warehouse updateWarehouse(Long id, WarehouseRequest warehouseRequest){

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", id));

        Optional<Warehouse> existing = warehouseRepository.findByIdentityCode(warehouseRequest.identityCode());

        if(existing.isPresent() && !existing.get().getId().equals(id)){
            throw new DuplicateIdentityCodeException(warehouseRequest.identityCode());
        }

        warehouse.setName(warehouseRequest.name());
        warehouse.setAddress(warehouseRequest.address());
        warehouse.setIdentityCode(warehouseRequest.identityCode());

        return warehouseRepository.save(warehouse);
    }
}
