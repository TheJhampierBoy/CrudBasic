package com.sena.crud_basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.user_rolDTO;
import com.sena.crud_basic.service.user_rolService;

@RestController
@RequestMapping("/api/v1/user_rol")
public class user_rolController {
    
    @Autowired
    private user_rolService user_rolService;

    @PostMapping("/")
    public ResponseEntity<responseDTO> registerUser_rol(@RequestBody user_rolDTO user_rol) {
        responseDTO respuesta = user_rolService.save(user_rol);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllUser() {
        var listaUsuario = user_rolService.findAll();
        return new ResponseEntity<>(listaUsuario, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneuser_rol(@PathVariable int id) {
        var user_rol = user_rolService.findById(id);
        if (!user_rol.isPresent())
            return new ResponseEntity<>(
                new responseDTO(HttpStatus.NOT_FOUND, "User role not found"),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user_rol.get(), HttpStatus.OK);
    }

    @GetMapping("/filter/{id_user}")
    public ResponseEntity<Object> getUserByUserId(@PathVariable int id_user) {
        var userList = user_rolService.getUserByUserId(id_user);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<responseDTO> delete(@PathVariable int id) {
        responseDTO response = user_rolService.delete(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<responseDTO> updateuser_rol(@PathVariable int id, @RequestBody user_rolDTO user_rolDTO) {
        responseDTO respuesta = user_rolService.update(id, user_rolDTO);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }
}