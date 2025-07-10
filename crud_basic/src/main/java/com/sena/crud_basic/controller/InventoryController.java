package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.InventoryDTO;
import com.sena.crud_basic.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    /*
     * GET (Obtener todo el inventario)
     * GET (Obtener un ítem por ID)
     * POST (Registrar un ítem)
     * PUT (Actualizar un ítem)
     * DELETE (Eliminar un ítem)
     */

    @Autowired
    private InventoryService inventoryService;


    @PostMapping("/")
    public ResponseEntity<Object> registerInventory(@RequestBody InventoryDTO inventory) {
        responseDTO respuesta = inventoryService.save(inventory);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllInventory() {
        var listaInventory = inventoryService.findAll();
        return new ResponseEntity<>(listaInventory, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneInventory(@PathVariable int id) {
        var inventory = inventoryService.findById(id);
        if (!inventory.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInventory(@PathVariable int id) {
        var message= inventoryService.deleteInventory(id);
        
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
