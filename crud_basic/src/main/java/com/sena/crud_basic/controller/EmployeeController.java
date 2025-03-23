package com.sena.crud_basic.controller;


import com.sena.crud_basic.DTO.EmployeeDTO;
import com.sena.crud_basic.DTO.responseDTO;
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
    public ResponseEntity<Object> registerEmployee(@RequestBody EmployeeDTO employee) {
        responseDTO respuesta = employeeService.save(employee);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllEmployee() {
        var listaEmployee = employeeService.findAll();
        return new ResponseEntity<>(listaEmployee, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneEmployee(@PathVariable int id) {
        var employee = employeeService.findById(id);
        if (!employee.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable int id) {
        var message= employeeService.deleteEmployee(id);
        
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
