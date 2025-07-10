package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.SupplierDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.Supplier;
import com.sena.crud_basic.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    // Guardar un nuevo proveedor
    public responseDTO save(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setContact(supplierDTO.getContact());
        supplier.setAddress(supplierDTO.getAddress());

        supplierRepository.save(supplier);
        return new responseDTO(HttpStatus.CREATED, "Proveedor registrado correctamente");
    }

    // Obtener todos los proveedores
    public List<SupplierDTO> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar proveedor por ID
    public Optional<SupplierDTO> findById(int id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        return supplier.map(this::convertToDTO);
    }

    // Eliminar proveedor por ID
    public responseDTO deleteSupplier(int id) {
        if (!supplierRepository.existsById(id)) {
            return new responseDTO(HttpStatus.NOT_FOUND, "El proveedor no existe");
        }
        supplierRepository.deleteById(id);
        return new responseDTO(HttpStatus.OK, "Proveedor eliminado correctamente");
    }

    // Convertir entidad a DTO
    private SupplierDTO convertToDTO(Supplier supplier) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setContact(supplier.getContact());
        dto.setAddress(supplier.getAddress());
        return dto;
    }
}