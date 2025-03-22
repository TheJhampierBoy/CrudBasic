package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.CustomerDTO;
import com.sena.crud_basic.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<Object> registerCustomer(@RequestBody CustomerDTO customer) {
        responseDTO respuesta = customerService.save(customer);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping("/")
    public ResponseEntity<Object> getAllCustomer() {
        var listaCustomer = customerService.findAll();
        return new ResponseEntity<>(listaCustomer, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCustomer(@PathVariable int id) {
        var customer = customerService.findById(id);
        if (!customer.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDrug(@PathVariable int id) {
        var message= customerService.deleteCustomer(id);
        
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
