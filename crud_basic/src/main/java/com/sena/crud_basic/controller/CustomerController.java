package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.CustomerDTO;
import com.sena.crud_basic.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        String response = customerService.save(customerDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCustomer(@PathVariable("id") int customerID) {
        Optional<CustomerDTO> customer = customerService.findById(customerID);
        if (!customer.isPresent()) {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable("id") int customerID, @RequestBody CustomerDTO customerDTO) {
        String response = customerService.updateCustomer(customerID, customerDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") int customerID) {
        String response = customerService.deleteCustomer(customerID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CustomerDTO>> filterCustomers(@RequestParam(required = false) String searchTerm) {
        List<CustomerDTO> customers = customerService.filterCustomers(searchTerm);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}