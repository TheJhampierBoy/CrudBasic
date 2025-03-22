package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.EmployeeDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.CategoryDTO;
import com.sena.crud_basic.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    /*
     * GET
     * POST(REGISTER)
     * PUT
     * DELETE
     */
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/")
    public ResponseEntity<Object> registerEmployee(@RequestBody EmployeeDTO employee) {
        responseDTO respuesta = employeeService.save(employee); // Cambio aquí
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<Object> getAllEmployees() {
        var listaEmpleados = employeeService.findAll();
        return new ResponseEntity<>(listaEmpleados, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneEmployee(@PathVariable int id) {
        var empleado = employeeService.findById(id);
        if (!empleado.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(empleado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable int id) {
        var message = employeeService.deleteEmployee(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
