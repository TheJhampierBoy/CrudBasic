package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.PaymentDTO;
import com.sena.crud_basic.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/")
    public ResponseEntity<String> registerPayment(@RequestBody PaymentDTO paymentDTO) {
        String response = paymentService.save(paymentDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentService.findAll();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable int id) {
        return paymentService.findById(id)
                .map(payment -> new ResponseEntity<>(payment, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePayment(@PathVariable int id, @RequestBody PaymentDTO paymentDTO) {
        String response = paymentService.updatePayment(id, paymentDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable int id) {
        String response = paymentService.deletePayment(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<PaymentDTO>> filterPayments(
            @RequestParam(required = false) String method,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) String status) { // Cambiado a String
        
        List<PaymentDTO> payments = paymentService.filterPayments(
                method, description,
                minAmount, maxAmount,
                startDate, endDate,
                status); // Cambiado a String
        
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}