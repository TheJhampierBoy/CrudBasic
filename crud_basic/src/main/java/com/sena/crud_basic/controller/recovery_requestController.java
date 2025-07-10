package com.sena.crud_basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.recovery_requestDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.recovery_request;
import com.sena.crud_basic.service.recovery_requestService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/recovery_request")
public class recovery_requestController {
    
    @Autowired
    private recovery_requestService recovery_requestService;

    @PostMapping("/")
    public ResponseEntity<responseDTO> register(@RequestBody recovery_requestDTO recovery_request) {
        responseDTO respuesta = recovery_requestService.save(recovery_request);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll() {
        var lista = recovery_requestService.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

@GetMapping("/{id}")
public ResponseEntity<Object> getOne(@PathVariable int id) {
    Optional<recovery_request> recovery_request = recovery_requestService.findById(id);
    if (recovery_request.isEmpty()) {
        return new ResponseEntity<>(
            new responseDTO(HttpStatus.NOT_FOUND, "Recovery request not found"),
            HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(recovery_request.get(), HttpStatus.OK);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<responseDTO> delete(@PathVariable int id) {
        responseDTO response = recovery_requestService.delete(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<responseDTO> update(@PathVariable int id, @RequestBody recovery_requestDTO recovery_requestDTO) {
        responseDTO respuesta = recovery_requestService.update(id, recovery_requestDTO);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }
}