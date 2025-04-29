package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.InventoryDTO;
import com.sena.crud_basic.model.Drug;
import com.sena.crud_basic.model.Inventory;
import com.sena.crud_basic.repository.DrugRepository;
import com.sena.crud_basic.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private DrugRepository drugRepository;

    public String save(InventoryDTO inventoryDTO) {
        if (inventoryDTO.getProductName() == null || inventoryDTO.getProductName().trim().isEmpty()) {
            return "El nombre del producto no puede estar vacío";
        }
        if (inventoryDTO.getStockReceived() <= 0) {
            return "El stock recibido debe ser mayor que cero";
        }
        if (inventoryDTO.getStockRemaining() < 0) {
            return "El stock restante no puede ser negativo";
        }

        Inventory inventory = convertToModel(inventoryDTO);
        inventoryRepository.save(inventory);
        return "Ítem de inventario registrado exitosamente";
    }

    public List<InventoryDTO> findAll() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<InventoryDTO> findById(int id) {
        return inventoryRepository.findById(id)
                .map(this::convertToDTO);
    }

    public String deleteInventory(int id) {
        if (!inventoryRepository.existsById(id)) {
            return "El ítem con ID " + id + " no existe";
        }
        inventoryRepository.deleteById(id);
        return "Ítem eliminado correctamente";
    }

    public String updateInventory(int id, InventoryDTO inventoryDTO) {
        Optional<Inventory> existingInventory = inventoryRepository.findById(id);
        if (!existingInventory.isPresent()) {
            return "El ítem con ID " + id + " no existe";
        }

        Inventory inventory = existingInventory.get();
        inventory.setProductName(inventoryDTO.getProductName());
        inventory.setStockReceived(inventoryDTO.getStockReceived());
        inventory.setStockRemaining(inventoryDTO.getStockRemaining());
        
        inventoryRepository.save(inventory);
        return "Ítem actualizado correctamente";
    }

    public List<InventoryDTO> advancedFilter(String productName, String drugName, 
                                           Integer minReceived, Integer maxReceived,
                                           Integer minRemaining, Integer maxRemaining) {
        return inventoryRepository.advancedFilter(
                productName != null ? productName.toLowerCase() : "",
                drugName != null ? drugName : "",
                minReceived,
                maxReceived,
                minRemaining,
                maxRemaining
        ).stream()
         .map(this::convertToDTO)
         .collect(Collectors.toList());
    }

    private Inventory convertToModel(InventoryDTO dto) {
        Inventory inventory = new Inventory();
        inventory.setProductName(dto.getProductName());
        inventory.setStockReceived(dto.getStockReceived());
        inventory.setStockRemaining(dto.getStockRemaining());

        Optional<Drug> drug = drugRepository.findById(dto.getDrugID());
        if (drug.isPresent()) {
            inventory.setDrug(drug.get());
        } else {
            throw new IllegalArgumentException("El DrugID proporcionado no existe: " + dto.getDrugID());
        }

        return inventory;
    }

    private InventoryDTO convertToDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setInventoryID(inventory.getInventoryID());
        dto.setProductName(inventory.getProductName());
        dto.setStockReceived(inventory.getStockReceived());
        dto.setStockRemaining(inventory.getStockRemaining());
    
        if (inventory.getDrug() != null) {
            dto.setDrugName(inventory.getDrug().getName());
            dto.setDrugID(inventory.getDrug().getDrugID());
        }
        return dto;
    }
}