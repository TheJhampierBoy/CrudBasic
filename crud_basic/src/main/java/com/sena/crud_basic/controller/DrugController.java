package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.DrugDTO;
import com.sena.crud_basic.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/drug")
public class DrugController {

    @Autowired
    private DrugService drugService;

    @PostMapping("/")
    public ResponseEntity<String> registerDrug(@RequestBody DrugDTO drugDTO) {
        String response = drugService.save(drugDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<DrugDTO>> getAllDrugs() {
        List<DrugDTO> drugs = drugService.findAll();
        return new ResponseEntity<>(drugs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneDrug(@PathVariable int id) {
        Optional<DrugDTO> drug = drugService.findById(id);
        if (!drug.isPresent()) {
            return new ResponseEntity<>("Medicamento no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(drug.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDrug(@PathVariable int id, @RequestBody DrugDTO drugDTO) {
        String response = drugService.updateDrug(id, drugDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDrug(@PathVariable int id) {
        String response = drugService.deleteDrug(id);
        if (response.contains("no existe")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<DrugDTO>> filterDrugs(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "0") Double minPrice,
            @RequestParam(required = false, defaultValue = "1000000") Double maxPrice,
            @RequestParam(required = false, defaultValue = "0") Integer categoryId) {

        List<DrugDTO> drugs = drugService.advancedFilterDrugs(name, description, minPrice, maxPrice, categoryId);
        return new ResponseEntity<>(drugs, HttpStatus.OK);
    }
}