package com.sena.crud_basic.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_basic.DTO.page_roleDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.service.page_roleService;

@RestController
@RequestMapping("/api/v1/page_role")
public class page_roleController {
  @Autowired
    private page_roleService page_roleService;

    @PostMapping("/")
    public ResponseEntity<Object> register(@RequestBody page_roleDTO page_role) {
        responseDTO respuesta = page_roleService.save(page_role);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll() {
        var lista = page_roleService.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable int id) {
        var page_role = page_roleService.findById(id);
        if (!page_role.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(page_role, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        var message = page_roleService.delete(id); 
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<responseDTO> update(@PathVariable int id, @RequestBody page_roleDTO page_roleDTO) {
        responseDTO respuesta = page_roleService.update(id, page_roleDTO);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }
}
