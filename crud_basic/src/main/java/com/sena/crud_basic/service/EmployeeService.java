package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.EmployeeDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.Employee;
import com.sena.crud_basic.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Obtener todos los empleados
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    // Buscar empleado por ID
    public Optional<Employee> findById(int id) {
        return employeeRepository.findById(id);
    }

    // Eliminar empleado por ID
    public responseDTO deleteEmployee(int id) {
        if (!employeeRepository.existsById(id)) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "El empleado no existe");
        }
        employeeRepository.deleteById(id);
        return new responseDTO(HttpStatus.OK.toString(), "Empleado eliminado correctamente");
    }

    // Guardar o actualizar empleado desde un DTO
    public responseDTO save(EmployeeDTO employeeDTO) {
        if (employeeDTO.getFirstName().length() < 1 || employeeDTO.getFirstName().length() > 50) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre debe tener entre 1 y 50 caracteres");
        }

        Employee employee = convertToModel(employeeDTO);
        employeeRepository.save(employee);
        return new responseDTO(HttpStatus.OK.toString(), "Empleado guardado correctamente");
    }

    // Convertir modelo a DTO
    public EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getEmployeeID(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getRole(),
                employee.getPhoneNumber(),
                employee.getEmail(),
                employee.getStatus()
        );
    }

    public Employee convertToModel(EmployeeDTO employeeDTO) {
        return new Employee(
                employeeDTO.getEmployeeID(),
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getRole(),
                employeeDTO.getPhoneNumber(),
                employeeDTO.getEmail(),
                employeeDTO.getStatus(),
                LocalDateTime.now()
        );
    }
}
