package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.EmployeeDTO;
import com.sena.crud_basic.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/")
    public ResponseEntity<String> registerEmployee(@RequestBody EmployeeDTO employeeDTO) {
        String response = employeeService.save(employeeDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneEmployee(@PathVariable("id") int employeeID) {
        Optional<EmployeeDTO> employee = employeeService.findById(employeeID);
        if (!employee.isPresent()) {
            return new ResponseEntity<>("Empleado no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable("id") int employeeID, @RequestBody EmployeeDTO employeeDTO) {
        String response = employeeService.updateEmployee(employeeID, employeeDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") int employeeID) {
        String response = employeeService.deleteEmployee(employeeID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EmployeeDTO>> filterEmployees(@RequestParam(required = false) String searchTerm) {
        List<EmployeeDTO> employees = employeeService.filterEmployees(searchTerm);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}