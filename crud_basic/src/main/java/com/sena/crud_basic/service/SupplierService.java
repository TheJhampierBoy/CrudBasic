package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.SupplierDTO;
import com.sena.crud_basic.model.Supplier;
import com.sena.crud_basic.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    // Guardar proveedor con validaciones
    public SupplierDTO save(SupplierDTO supplierDTO) {
        if (supplierDTO.getName() == null || supplierDTO.getName().trim().isEmpty()) {
            return new SupplierDTO("400", "El nombre del proveedor no puede estar vacío");
        }

        if (supplierDTO.getContact() == null || supplierDTO.getContact().trim().isEmpty()) {
            return new SupplierDTO("400", "El contacto del proveedor no puede estar vacío");
        }

        if (supplierDTO.getAddress() == null || supplierDTO.getAddress().trim().isEmpty()) {
            return new SupplierDTO("400", "La dirección del proveedor no puede estar vacía");
        }

        Supplier supplier = convertToModel(supplierDTO);
        supplier = supplierRepository.save(supplier);
        return convertToDTO(supplier);
    }

    // Obtener todos los proveedores (activos por defecto si se usa status)
    public List<SupplierDTO> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public Optional<SupplierDTO> findById(int id) {
        return supplierRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Eliminación lógica (cambia status a false)
    public String deleteSupplier(int id) {
        if (!supplierRepository.existsById(id)) {
            return "Proveedor no encontrado";
        }
        supplierRepository.deleteById(id); // Borrado físico
        return "Proveedor eliminado permanentemente";
    }

    // Actualizar proveedor
    public SupplierDTO updateSupplier(int id, SupplierDTO supplierDTO) {
        Optional<Supplier> existing = supplierRepository.findById(id);
        if (existing.isEmpty()) {
            return new SupplierDTO("404", "Proveedor no encontrado");
        }

        // Validaciones
        if (supplierDTO.getName() == null || supplierDTO.getName().trim().isEmpty()) {
            return new SupplierDTO("400", "El nombre del proveedor no puede estar vacío");
        }

        Supplier supplier = existing.get();
        supplier.setName(supplierDTO.getName());
        supplier.setContact(supplierDTO.getContact());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setStatus(supplierDTO.getStatus() != null ? supplierDTO.getStatus() : true);

        supplier = supplierRepository.save(supplier);
        return convertToDTO(supplier);
    }

    // Filtrado avanzado (como en el modal de Supplier)
    public List<SupplierDTO> filterSuppliers(String name, String contact, String address, Boolean status) {
        return supplierRepository.filterSuppliers(
                name,
                contact,
                address,
                status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Conversiones
    private SupplierDTO convertToDTO(Supplier supplier) {
        return new SupplierDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getContact(),
                supplier.getAddress(),
                supplier.getStatus()
        );
    }

    private Supplier convertToModel(SupplierDTO dto) {
        return new Supplier(
                dto.getId(),
                dto.getName(),
                dto.getContact(),
                dto.getAddress(),
                dto.getStatus() != null ? dto.getStatus() : true // Default: activo
        );
    }
}
