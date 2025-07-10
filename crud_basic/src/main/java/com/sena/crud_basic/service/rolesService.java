package com.sena.crud_basic.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.rolesDTO;
import com.sena.crud_basic.model.roles;
import com.sena.crud_basic.repository.Iroles;

@Service
public class rolesService {
    @Autowired
    private Iroles data;

    public List<roles> findAll() {
        return data.findAll();
    }

    public Optional<roles> findById(int id) {
        return data.findById(id);
    }

    public List<roles> getListroles() {
        if(data.count() == 0) {
            roles admin = new roles(1, "Administrador");
            roles farma = new roles(2, "Farmacéutico");
            data.saveAll(List.of(admin, farma));
        }
        return data.findAll();
    }

    public responseDTO deleteroles(int id) {
        if (!findById(id).isPresent()) {
            return new responseDTO(HttpStatus.OK, "El registro no existe");
        }
        data.deleteById(id);
        return new responseDTO(HttpStatus.OK, "Eliminado correctamente");
    }

    public responseDTO save(rolesDTO rolesDTO) {
        if (rolesDTO.getName_rol() == null || rolesDTO.getName_rol().length() < 1 || rolesDTO.getName_rol().length() > 50) {
            return new responseDTO(HttpStatus.BAD_REQUEST, "El nombre debe estar entre 1 y 50 caracteres");
        }
        
        roles roles_Registro = convertToModel(rolesDTO);
        data.save(roles_Registro);
        return new responseDTO(HttpStatus.OK, "Guardado correctamente");
    }

    public rolesDTO convertToDTO(roles roles) {
        return new rolesDTO(roles.getid_roles(), roles.get_name_rol());
    }

    public roles convertToModel(rolesDTO rolesDTO) {    
        return new roles(0, rolesDTO.getName_rol());
    }
}