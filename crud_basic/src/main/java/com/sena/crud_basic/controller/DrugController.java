package com.sena.crud_basic.controller;


import com.sena.crud_basic.DTO.DrugDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drug")
public class DrugController{

    /*
     * GET (Obtener todas las categorías)
     * GET (Obtener categoría por ID)
     * POST (Registrar una categoría)
     * PUT (Actualizar una categoría)
     * DELETE (Eliminar una categoría)
     */

    @Autowired
    private DrugService drugService;

        @PostMapping("/")
    public ResponseEntity<Object> registerDrug(@RequestBody DrugDTO drug) {
        responseDTO respuesta = drugService.save(drug);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping("/")
    public ResponseEntity<Object> getAllDrug() {
        var listaDrug = drugService.findAll();
        return new ResponseEntity<>(listaDrug, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneDrug(@PathVariable int id) {
        var drug = drugService.findById(id);
        if (!drug.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(drug, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDrug(@PathVariable int id) {
        var message= drugService.deleteDrug(id);
        
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
