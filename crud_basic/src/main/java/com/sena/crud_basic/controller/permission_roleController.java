package com.sena.crud_basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.permission_roleDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.permission_role;
import com.sena.crud_basic.service.permission_roleService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/permission_role")
public class permission_roleController {
    
    @Autowired
    private permission_roleService permission_roleService;

    @PostMapping("/")
    public ResponseEntity<responseDTO> register(@RequestBody permission_roleDTO permission_role) {
        responseDTO respuesta = permission_roleService.save(permission_role);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll() {
        var lista = permission_roleService.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable int id) {
        Optional<permission_role> permission_role = permission_roleService.findById(id);
        if (permission_role.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new responseDTO(HttpStatus.NOT_FOUND, "Permission role not found"));
        }
        return ResponseEntity.ok(permission_role.get());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<responseDTO> delete(@PathVariable int id) {
        responseDTO response = permission_roleService.delete(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<responseDTO> update(@PathVariable int id, @RequestBody permission_roleDTO permission_roleDTO) {
        responseDTO respuesta = permission_roleService.update(id, permission_roleDTO);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }
}