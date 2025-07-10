package com.sena.crud_basic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.crud_basic.DTO.user_rolDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.user_rol;
import com.sena.crud_basic.model.roles;
import com.sena.crud_basic.model.user;
import com.sena.crud_basic.repository.Iuser_rol;

@Service
public class user_rolService {
   
   @Autowired
   private Iuser_rol data;
   
   @Autowired
   private userService userService;
   
   @Autowired
   private rolesService rolesService;


    public List<user_rol> findAll() {
        return data.findAll();
    }
   public Optional<user_rol> findById(int id) {
        return data.findById(id);
   }

    public List<user_rol> getUserByUserId(int id_user) {
        return data.getUserByUserId(id_user);
    }
   
    @Transactional
    public responseDTO delete(int id) {
        Optional<user_rol> user_rolOptional = findById(id);
        if (!user_rolOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "The register does not exist");
        }
        data.deleteById(id);
        return new responseDTO(HttpStatus.OK, "It was deleted correctly");
    }
    @Transactional
    public responseDTO update(int id, user_rolDTO user_rolDTO) {
        Optional<user_rol> existingUserRolOpt = findById(id);

        if (!existingUserRolOpt.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "El registro no existe");
        }
        Optional<roles> rolesOptional = rolesService.findById(user_rolDTO.get_id_rol());
        if (!rolesOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "El rol no existe");
        }
        Optional<user> userOptional = userService.findById(user_rolDTO.getUser());
        if (!userOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "El usuario no existe");
        }
        try {
            user_rol existingUserRol = existingUserRolOpt.get();
            existingUserRol.setUser(userOptional.get());
            existingUserRol.set_id_rol(rolesOptional.get());

            data.save(existingUserRol);

            return new responseDTO(HttpStatus.OK, "Rol de usuario actualizado correctamente");
        } catch (Exception e) {
            return new responseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar: " + e.getMessage());
        }
    }
    @Transactional
    public responseDTO save(user_rolDTO user_rolDTO) {
        Optional<roles> rolesOptional = rolesService.findById(user_rolDTO.get_id_rol());
        if (!rolesOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "The role with ID " + user_rolDTO.get_id_rol() + " does not exist.");
        }
        Optional<user> userOptional = userService.findById(user_rolDTO.getUser());
        if (!userOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "The role with ID " + user_rolDTO.getUser() + " does not exist.");
        }

        // Convertimos el DTO en un modelo de user_rol
        user_rol userRolRecord = convertToModel(user_rolDTO,userOptional.get(), rolesOptional.get());

        // Guardar en la base de datos
        data.save(userRolRecord);
        return new responseDTO(HttpStatus.OK, "User role saved correctly.");
    }

    public user_rolDTO convertToDTO(user_rol user_rol) {
        return new user_rolDTO(
            user_rol.get_id_user_rol(),
            user_rol.getUser().getId_user(),
            user_rol.get_id_rol().getid_roles()
        );
    }

    public user_rol convertToModel(user_rolDTO user_rolDTO,user user, roles role) {
        return new user_rol(
            0, 
            user,
            role
        );
    }
}
