package com.sena.crud_basic.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.recovery_requestDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.recovery_request;
import com.sena.crud_basic.model.user;
import com.sena.crud_basic.repository.Irecovery_request;

@Service
public class recovery_requestService {
  @Autowired
   private Irecovery_request data;
   @Autowired
   private userService userService;

   public List<recovery_request> findAll() {
      return data.findAll();
   }

   public Optional<recovery_request> findById(int id) {
      return data.findById(id);
   }

   public responseDTO delete(int id) {
      if (!findById(id).isPresent()) {
         responseDTO respuesta = new responseDTO(HttpStatus.OK,"The register does not exist");
         return respuesta;
   }
      data.deleteById(id);
        responseDTO respuesta = new responseDTO(
            HttpStatus.OK,
            "It was deleted correctly");
        return respuesta;
   }
   public responseDTO update(int id, recovery_requestDTO recovery_requestDTO) {
        Optional<recovery_request> existingrecovery_requestOpt = findById(id);
        if (!existingrecovery_requestOpt.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "El autor no existe");
        }
        Optional<user> userOptional = userService.findById(recovery_requestDTO.getIdUser());
        if (!userOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "El rol no existe");
        }
    
        
    
        recovery_request existingrecovery_request = existingrecovery_requestOpt.get();
        existingrecovery_request.setIdUser(userOptional.get());
        existingrecovery_request.setToken(recovery_requestDTO.getToken());
        existingrecovery_request.setUsed(recovery_requestDTO.isUsed());
        existingrecovery_request.setExpirationDate(recovery_requestDTO.getExpirationDate());
    
        data.save(existingrecovery_request);
    
        return new responseDTO(HttpStatus.OK, "Autor actualizado correctamente");
    }
       // register and update
    public responseDTO save(recovery_requestDTO recovery_requestDTO) {
        Optional<user> userOptional = userService.findById(recovery_requestDTO.getIdUser());
        if (!userOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, 
                "El país con ID " + recovery_requestDTO.getIdUser() + " no existe.");
        }
        
        recovery_request recovery_request = converToModel(recovery_requestDTO, userOptional.get());
        data.save(recovery_request);
        return new responseDTO(HttpStatus.OK, "Editorial guardada correctamente.");
    }
   public recovery_requestDTO convertToDTO(recovery_request recovery_request) {
      recovery_requestDTO recovery_requestDTO = new recovery_requestDTO(
         0,
         recovery_request.getIdRecoveryRequest(),
         recovery_request.getToken(),
         recovery_request.isUsed(),
         recovery_request.getExpirationDate(),
         recovery_request.getCreatedAt()
      );
      return recovery_requestDTO;
   }

   public recovery_request converToModel(recovery_requestDTO recovery_requestDTO, user user) {
      recovery_request recovery_request = new recovery_request(
         0,
            user,
            recovery_requestDTO.getToken(),
            recovery_requestDTO.isUsed(),
            recovery_requestDTO.getExpirationDate(),
            LocalDateTime.now()
      );
      return recovery_request;
   }
}