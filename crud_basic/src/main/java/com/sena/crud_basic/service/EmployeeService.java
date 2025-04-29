package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.EmployeeDTO;
import com.sena.crud_basic.model.Employee;
import com.sena.crud_basic.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public String save(EmployeeDTO employeeDTO) {
        if (employeeDTO.getFirstName() == null || employeeDTO.getFirstName().trim().isEmpty()) {
            return "Error: El nombre es obligatorio";
        }
        if (employeeDTO.getLastName() == null || employeeDTO.getLastName().trim().isEmpty()) {
            return "Error: El apellido es obligatorio";
        }
        if (employeeDTO.getRole() == null || employeeDTO.getRole().trim().isEmpty()) {
            return "Error: El rol es obligatorio";
        }
        if (employeeDTO.getPhoneNumber() == null || employeeDTO.getPhoneNumber().trim().isEmpty()) {
            return "Error: El teléfono es obligatorio";
        }
        if (employeeDTO.getEmail() == null || employeeDTO.getEmail().trim().isEmpty()) {
            return "Error: El email es obligatorio";
        }
        if (!isValidEmail(employeeDTO.getEmail())) {
            return "Error: Email inválido";
        }
    
        try {
            Employee employee = convertToModel(employeeDTO);
            employeeRepository.save(employee);
            return "Empleado registrado exitosamente";
        } catch (Exception e) {
            return "Error interno al guardar: " + e.getMessage();
        }
    }

    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> findById(int employeeID) {
        return employeeRepository.findById(employeeID)
                .map(this::convertToDTO);
    }

    public String deleteEmployee(int employeeID) {
        Optional<Employee> employee = employeeRepository.findById(employeeID);
        if (!employee.isPresent()) {
            throw new RuntimeException("Empleado no encontrado con ID: " + employeeID);
        }
        try {
            employeeRepository.deleteById(employeeID);
            return "Empleado eliminado correctamente";
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el empleado. Verifica las relaciones en la base de datos.");
        }
    }

    public String updateEmployee(int employeeID, EmployeeDTO employeeDTO) {
        Optional<Employee> existingEmployee = employeeRepository.findById(employeeID);
        if (!existingEmployee.isPresent()) {
            return "El empleado con ID " + employeeID + " no existe";
        }
    
        Employee employee = existingEmployee.get();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setRole(employeeDTO.getRole());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setEmail(employeeDTO.getEmail());
        employeeRepository.save(employee);
    
        return "Empleado actualizado correctamente";
    }

    public List<EmployeeDTO> filterEmployees(String searchTerm) {
        return employeeRepository.filterEmployees(searchTerm == null ? "" : searchTerm)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Employee convertToModel(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setRole(employeeDTO.getRole());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setEmail(employeeDTO.getEmail());
        return employee;
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeID(employee.getEmployeeID());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setRole(employee.getRole());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setEmail(employee.getEmail());
        return dto;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}