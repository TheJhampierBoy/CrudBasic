package com.sena.crud_basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.pageDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.page;
import com.sena.crud_basic.service.pageService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/page")
public class pageController {
    
    @Autowired
    private pageService pageService;

    @PostMapping("/")
    public ResponseEntity<responseDTO> register(@RequestBody pageDTO page) {
        responseDTO respuesta = pageService.save(page);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll() {
        var lista = pageService.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

@GetMapping("/{id}")
public ResponseEntity<Object> getOnePage(@PathVariable int id) {
    Optional<page> page = pageService.findById(id); // Cambiado a Optional<page>
    if (!page.isPresent())
        return new ResponseEntity<>(
            new responseDTO(HttpStatus.NOT_FOUND, "Page not found"), 
            HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(page.get(), HttpStatus.OK);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<responseDTO> delete(@PathVariable int id) {
        responseDTO response = pageService.delete(id); 
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<responseDTO> updatePage(@PathVariable int id, @RequestBody pageDTO pageDTO) {
        responseDTO respuesta = pageService.update(id, pageDTO);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }
}