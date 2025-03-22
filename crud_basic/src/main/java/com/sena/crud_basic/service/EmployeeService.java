package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.EmployeeDTO;
import com.sena.crud_basic.model.Employee;
import com.sena.crud_basic.repository.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    // Inyección de dependencia para acceder al repositorio
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
        if (!findById(id).isPresent()) {
            return new responseDTO(HttpStatus.OK.toString(), "The register does not exist");
        }
        employeeRepository.deleteById(id);
        return new responseDTO(HttpStatus.OK.toString(), "Se eliminó correctamente");
    }

    // Guardar o actualizar empleado
    public responseDTO save(EmployeeDTO employeeDTO) {
        // Validación de longitud del nombre
        if (employeeDTO.getFirstName().length() < 1 || employeeDTO.getFirstName().length() > 50) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre debe estar entre 1 y 50 caracteres");
        }
        Employee employeeRegister = convertToModel(employeeDTO);
        employeeRepository.save(employeeRegister);
        return new responseDTO(HttpStatus.OK.toString(), "Se guardó correctamente");
    }

    // Convertir de modelo a DTO
    public EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getEmployeeID(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getRole(),
                employee.getPhoneNumber(),
                employee.getEmail());
    }

    // Convertir de DTO a modelo
    public Employee convertToModel(EmployeeDTO employeeDTO) {
        return new Employee(
                employeeDTO.getEmployeeID(),
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getRole(),
                employeeDTO.getPhoneNumber(),
                employeeDTO.getEmail(),
                LocalDateTime.now()); // Agregar fecha y hora de creación
    }
}
