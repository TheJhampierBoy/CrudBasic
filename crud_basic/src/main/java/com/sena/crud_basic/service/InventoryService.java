package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.InventoryDTO;
import com.sena.crud_basic.model.Inventory;
import com.sena.crud_basic.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public responseDTO save(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        inventory.setDrug(inventoryDTO.getDrug());
        inventory.setStockReceived(inventoryDTO.getStockReceived());
        inventory.setStockRemaining(inventoryDTO.getStockRemaining());
        
        inventoryRepository.save(inventory);
        return new responseDTO(HttpStatus.OK, "Item registrado exitosamente");
    }

    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> findById(Integer id) {
        return inventoryRepository.findById(id);
    }

    public responseDTO deleteInventory(Integer id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if (inventory.isPresent()) {
            inventoryRepository.deleteById(id);
            return new responseDTO(HttpStatus.OK, "Item eliminado exitosamente");
        } else {
            return new responseDTO(HttpStatus.NOT_FOUND, "Item no encontrado");
        }
    }
}