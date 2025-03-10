package com.sena.crud_basic.controller;

import com.sena.crud_basic.model.Employee;
import com.sena.crud_basic.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    /*
     * GET (Obtener todos los empleados)
     * GET (Obtener empleado por ID)
     * POST (Registrar un empleado)
     * PUT (Actualizar un empleado)
     * DELETE (Eliminar un empleado)
     */

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>("Register OK", HttpStatus.OK);
    }
}
