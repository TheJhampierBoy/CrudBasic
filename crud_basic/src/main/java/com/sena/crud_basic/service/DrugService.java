package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.DrugDTO;
import com.sena.crud_basic.model.Drug;
import com.sena.crud_basic.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DrugService {

    @Autowired
    private DrugRepository drugRepository;

    public String save(DrugDTO drugDTO) {
        if (drugDTO.getName() == null || drugDTO.getName().trim().isEmpty()) {
            return "El nombre del medicamento no puede estar vacío";
        }
        
        if (drugDTO.getName().length() < 3 || drugDTO.getName().length() > 150) {
            return "El nombre del medicamento debe tener entre 3 y 150 caracteres";
        }
        if (drugDTO.getPrice() <= 0) {
            return "El precio debe ser mayor que cero";
        }
        if (drugDTO.getStockQuantity() < 0) {
            return "La cantidad en stock no puede ser negativa";
        }
        if (drugDTO.getExpirationDate() == null || drugDTO.getExpirationDate().isBefore(LocalDate.now())) {
            return "La fecha de expiración debe ser válida y futura";
        }
        if (drugDTO.getCategoryID() <= 0 || drugDTO.getSupplierID() <= 0) {
            return "Debe especificar una categoría y un proveedor válidos";
        }

        Drug drug = convertToModel(drugDTO);
        drugRepository.save(drug);
        return "Medicamento registrado exitosamente";
    }

    public List<DrugDTO> findAll() {
        return drugRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DrugDTO> findById(Integer id) {
        return drugRepository.findById(id)
                .map(this::convertToDTO);
    }

    public String deleteDrug(Integer id) {
        if (!drugRepository.existsById(id)) {
            return "El medicamento con ID " + id + " no existe";
        }
        drugRepository.deleteById(id);
        return "Medicamento eliminado correctamente";
    }

    public String updateDrug(Integer id, DrugDTO drugDTO) {
        Optional<Drug> drugOpt = drugRepository.findById(id);
        if (!drugOpt.isPresent()) {
            return "El medicamento con ID " + id + " no existe";
        }

        if (drugDTO.getName() == null || drugDTO.getName().trim().isEmpty()) {
            return "El nombre del medicamento no puede estar vacío";
        }
        if (drugDTO.getName().length() < 3 || drugDTO.getName().length() > 150) {
            return "El nombre del medicamento debe tener entre 3 y 150 caracteres";
        }
        if (drugDTO.getPrice() <= 0) {
            return "El precio debe ser mayor que cero";
        }
        if (drugDTO.getStockQuantity() < 0) {
            return "La cantidad en stock no puede ser negativa";
        }
        if (drugDTO.getExpirationDate() == null || drugDTO.getExpirationDate().isBefore(LocalDate.now())) {
            return "La fecha de expiración debe ser válida y futura";
        }
        if (drugDTO.getCategoryID() <= 0 || drugDTO.getSupplierID() <= 0) {
            return "Debe especificar una categoría y un proveedor válidos";
        }

        Drug existingDrug = drugOpt.get();
        existingDrug.setName(drugDTO.getName());
        existingDrug.setDescription(drugDTO.getDescription());
        existingDrug.setPrice(drugDTO.getPrice());
        existingDrug.setStockQuantity(drugDTO.getStockQuantity());
        existingDrug.setExpirationDate(drugDTO.getExpirationDate());
        existingDrug.setCategoryID(drugDTO.getCategoryID());
        existingDrug.setSupplierID(drugDTO.getSupplierID());

        drugRepository.save(existingDrug);
        return "Medicamento actualizado correctamente";
    }

    public List<DrugDTO> advancedFilterDrugs(String name, String description, double minPrice, double maxPrice, int categoryId) {
        return drugRepository.advancedFilterDrugs(name, description, minPrice, maxPrice, categoryId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Drug convertToModel(DrugDTO drugDTO) {
        return new Drug(
                0,
                drugDTO.getName(),
                drugDTO.getDescription(),
                drugDTO.getPrice(),
                drugDTO.getStockQuantity(),
                drugDTO.getExpirationDate(),
                drugDTO.getCategoryID(),
                drugDTO.getSupplierID()
        );
    }

    private DrugDTO convertToDTO(Drug drug) {
        return new DrugDTO(
                drug.getDrugID(),
                drug.getName(),
                drug.getDescription(),
                drug.getPrice(),
                drug.getStockQuantity(),
                drug.getExpirationDate(),
                drug.getCategoryID(),
                drug.getSupplierID()
        );
    }
}