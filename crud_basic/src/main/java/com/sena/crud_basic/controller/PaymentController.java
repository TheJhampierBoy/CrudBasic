package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.PaymentDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    /*
     * GET (Obtener todos los pagos)
     * GET (Obtener un pago por ID)
     * POST (Registrar un pago)
     * PUT (Actualizar un pago)
     * DELETE (Eliminar un pago)
     */

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/")
    public ResponseEntity<Object> registerPayment(@RequestBody PaymentDTO payment) {
        responseDTO respuesta = paymentService.save(payment);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<Object> getAllPayment() {
        var listaPayment = paymentService.findAll();
        return new ResponseEntity<>(listaPayment, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOnePayment(@PathVariable int id) {
        var payment = paymentService.findById(id);
        if (!payment.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable int id) {
        var message = paymentService.deletePayment(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }   
}