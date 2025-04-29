package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.InventoryDTO;
import com.sena.crud_basic.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody InventoryDTO inventoryDTO) {
        String result = inventoryService.save(inventoryDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<InventoryDTO>> getAll() {
        return new ResponseEntity<>(inventoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<InventoryDTO> inventory = inventoryService.findById(id);
        return inventory.<ResponseEntity<?>>map(dto -> 
            new ResponseEntity<>(dto, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>("Ítem no encontrado", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody InventoryDTO inventoryDTO) {
        return new ResponseEntity<>(inventoryService.updateInventory(id, inventoryDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return new ResponseEntity<>(inventoryService.deleteInventory(id), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<InventoryDTO>> advancedFilter(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String drugName,
            @RequestParam(required = false, defaultValue = "0") Integer minReceived,
            @RequestParam(required = false, defaultValue = "1000000") Integer maxReceived,
            @RequestParam(required = false, defaultValue = "0") Integer minRemaining,
            @RequestParam(required = false, defaultValue = "1000000") Integer maxRemaining) {
        
        List<InventoryDTO> result = inventoryService.advancedFilter(
                productName, drugName,
                minReceived, maxReceived,
                minRemaining, maxRemaining);
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}