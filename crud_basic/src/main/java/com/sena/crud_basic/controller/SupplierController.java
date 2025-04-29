package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.SupplierDTO;
import com.sena.crud_basic.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // Registrar nuevo proveedor
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody SupplierDTO supplierDTO) {
        SupplierDTO result = supplierService.save(supplierDTO);
        if (result.getStatusCode() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // Obtener todos los proveedores
    @GetMapping("/")
    public ResponseEntity<List<SupplierDTO>> getAll() {
        return ResponseEntity.ok(supplierService.findAll());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<SupplierDTO> result = supplierService.findById(id);
        return result.isPresent() 
                ? ResponseEntity.ok(result.get())
                : ResponseEntity.notFound().build();
    }

    // Actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO result = supplierService.updateSupplier(id, supplierDTO);
        if ("404".equals(result.getStatusCode())) {
            return ResponseEntity.notFound().build();
        } else if ("400".equals(result.getStatusCode())) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    // Eliminar (desactivar) proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        String result = supplierService.deleteSupplier(id);
        return ResponseEntity.ok(result);
    }
    // Filtrar proveedores (como en el modal)
    @GetMapping("/filter")
    public ResponseEntity<List<SupplierDTO>> filter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String contact,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Boolean status) {
        
        return ResponseEntity.ok(
            supplierService.filterSuppliers(name, contact, address, status)
        );
    }
}
