package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.CustomerDTO;
import com.sena.crud_basic.model.Customer;
import com.sena.crud_basic.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(int id) {
        return customerRepository.findById(id);
    }

    public responseDTO deleteCustomer(int id) {
        if (!findById(id).isPresent()) {
            return new responseDTO(HttpStatus.OK, "The register does not exist");
        }
        customerRepository.deleteById(id);
        return new responseDTO(HttpStatus.OK, "Se eliminó correctamente");
    }

    // Register and update
    public responseDTO save(CustomerDTO CustomerDTO) {
        // Validación longitud del nombre
        if (CustomerDTO.getNombre().length() < 1 || CustomerDTO.getNombre().length() > 50) {
            return new responseDTO(HttpStatus.BAD_REQUEST, "El nombre debe estar entre 1 y 50 caracteres");
        }

        Customer customerRegister = convertToModel(CustomerDTO);
        customerRepository.save(customerRegister);
        return new responseDTO(HttpStatus.OK, "Se guardó correctamente");
    }

    public CustomerDTO convertToDTO(Customer Customer) {
        return new CustomerDTO(
                Customer.getNombre(),
                Customer.getEmail(),
                Customer.getTelefono());
    }

    public Customer convertToModel(CustomerDTO CustomerDTO) {
        return new Customer(
                0,
                CustomerDTO.getNombre(),
                CustomerDTO.getEmail(),
                CustomerDTO.getTelefono(),
                LocalDateTime.now());
    }
}