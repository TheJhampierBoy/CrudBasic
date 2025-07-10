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

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(int id) {
        return employeeRepository.findById(id);
    }

    public responseDTO deleteEmployee(int id) {
        if (!employeeRepository.existsById(id)) {
            return new responseDTO(HttpStatus.NOT_FOUND, "El empleado no existe");
        }
        employeeRepository.deleteById(id);
        return new responseDTO(HttpStatus.OK, "Empleado eliminado correctamente");
    }

    public responseDTO save(EmployeeDTO employeeDTO) {
        if (employeeDTO.getFirstName().length() < 1 || employeeDTO.getFirstName().length() > 50) {
            return new responseDTO(HttpStatus.BAD_REQUEST, "El nombre debe tener entre 1 y 50 caracteres");
        }

        Employee employee = convertToModel(employeeDTO);
        employeeRepository.save(employee);
        return new responseDTO(HttpStatus.OK, "Empleado guardado correctamente");
    }

    public EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getEmployeeID(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getRole(),
                employee.getPhoneNumber(),
                employee.getEmail()
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
                LocalDateTime.now()
        );
    }
}