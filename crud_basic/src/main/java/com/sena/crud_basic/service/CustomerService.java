package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.CustomerDTO;
import com.sena.crud_basic.model.Customer;
import com.sena.crud_basic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public String save(CustomerDTO customerDTO) {
        if (customerDTO.getName() == null || customerDTO.getName().trim().isEmpty()) {
            return "El nombre del cliente no puede estar vacío";
        }

        if (customerDTO.getName().length() > 100) {
            return "El nombre del cliente no puede exceder los 100 caracteres";
        }

        if (customerDTO.getEmail() == null || customerDTO.getEmail().trim().isEmpty()) {
            return "El email del cliente no puede estar vacío";
        }

        if (!isValidEmail(customerDTO.getEmail())) {
            return "El formato del email no es válido";
        }

        Customer customer = convertToModel(customerDTO);
        customerRepository.save(customer);
        return "Cliente registrado exitosamente";
    }

    public List<CustomerDTO> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CustomerDTO> findById(int customerID) {
        return customerRepository.findById(customerID)
                .map(this::convertToDTO);
    }

    public String deleteCustomer(int customerID) {
        Optional<Customer> customer = customerRepository.findById(customerID);
        if (!customer.isPresent()) {
            throw new RuntimeException("Cliente no encontrado con ID: " + customerID);
        }
        try {
            customerRepository.deleteById(customerID);
            return "Cliente eliminado correctamente";
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el cliente. Verifica las relaciones en la base de datos.");
        }
    }
    
    public String updateCustomer(int customerID, CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerRepository.findById(customerID);
        if (!existingCustomer.isPresent()) {
            return "El cliente con ID " + customerID + " no existe";
        }
    
        Customer customer = existingCustomer.get();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customerRepository.save(customer);
    
        return "Cliente actualizado correctamente";
    }

    public List<CustomerDTO> filterCustomers(String searchTerm) {
        return customerRepository.filterCustomers(searchTerm == null ? "" : searchTerm)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Customer convertToModel(CustomerDTO customerDTO) {
        return new Customer(
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getPhone(),
                LocalDateTime.now()
        );
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO(
            customer.getName(),
            customer.getEmail(),
            customer.getPhone()
        );
        dto.setCustomerID(customer.getCustomerID());
        return dto;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}