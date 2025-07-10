package com.sena.crud_basic.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.rolesDTO;
import com.sena.crud_basic.model.roles;
import com.sena.crud_basic.service.rolesService;

@RestController
@RequestMapping("/api/v1/roles")
public class rolesController {
    
    @Autowired
    private rolesService rolesService;

    @PostMapping("/")
    public ResponseEntity<responseDTO> registerroles(@RequestBody rolesDTO roles) {
        responseDTO respuesta = rolesService.save(roles);
        return new ResponseEntity<>(respuesta, respuesta.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneroles(@PathVariable int id) {
        var roles = rolesService.findById(id);
        if (!roles.isPresent())
            return new ResponseEntity<>(
                new responseDTO(HttpStatus.NOT_FOUND, "Role not found"), 
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(roles.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<responseDTO> deleteroles(@PathVariable int id) {
        responseDTO response = rolesService.deleteroles(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

// En tu rolesController.java
@GetMapping("/Listroles")  // << Asegúrate que sea GET
public ResponseEntity<List<roles>> getListroles() {
    return ResponseEntity.ok(rolesService.getListroles());
}

}