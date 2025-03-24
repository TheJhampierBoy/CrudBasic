package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.PrescriptionDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supplier")
public class PrescriptionController {

    /*
     * GET (Obtener todos los pagos)
     * GET (Obtener un pago por ID)
     * POST (Registrar un pago)
     * PUT (Actualizar un pago)
     * DELETE (Eliminar un pago)
     */

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/")
    public ResponseEntity<Object> registerPrescription(@RequestBody PrescriptionDTO prescription) {
        responseDTO respuesta = prescriptionService.save(prescription);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<Object> getAllPrescription() {
        var listaPrescription = prescriptionService.findAll();
        return new ResponseEntity<>(listaPrescription, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOnePrescription(@PathVariable int id) {
        var prescription = prescriptionService.findById(id);
        if (!prescription.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(prescription, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePrescription(@PathVariable int id) {
        var message = prescriptionService.deletePrescription(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }   
}