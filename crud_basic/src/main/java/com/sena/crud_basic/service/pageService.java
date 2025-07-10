package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sena.crud_basic.DTO.pageDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.page;
import com.sena.crud_basic.repository.Ipage;

import java.util.List;
import java.util.Optional;

@Service
public class pageService {
@Autowired
private Ipage IPage;

public List<page> findAll() {
    return IPage.findAll();
}
public Optional<page> findById(int id) {
    return IPage.findById(id);
}
public responseDTO delete(int id) {
    Optional<page> page=findById(id);
    if (!page.isPresent()) {
        responseDTO respuesta = new responseDTO(
                HttpStatus.OK,
                "The register does not exist");
            return respuesta;
        }

        IPage.deleteById(id);
        
        responseDTO respuesta = new responseDTO(
                HttpStatus.OK,
                "Se eliminó correctamente");
        return respuesta;
    }
    public responseDTO update(int id, pageDTO pageDTO) {
        Optional<page> userOptional = findById(id);
        
        if (!userOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "Registro no existe");
        }
    
        try {
            page existingUser = userOptional.get();
            existingUser.setpage(pageDTO.getPage());
    
            IPage.save(existingUser);
    
            return new responseDTO(HttpStatus.OK, "Usuario actualizado correctamente");
    
        } catch (Exception e) {
            return new responseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar: " + e.getMessage());
        }
    }
public responseDTO save(pageDTO pageDTO) {
         // validación longitud del nombre
         if (pageDTO.getPage().length() < 1 ||
                 pageDTO.getPage().length() > 50) {
             responseDTO respuesta = new responseDTO(
                     HttpStatus.BAD_REQUEST,
                     "El nombre debe estar entre 1 y 50 caracteres");
             return respuesta;
         }
         // otras condiciones
         // n
         page page_Registro = converToModel(pageDTO);
         IPage.save(page_Registro);
         responseDTO respuesta = new responseDTO(
                 HttpStatus.OK,
                 "Se guardó correctamente");
         return respuesta;
     }
public page converToModel(pageDTO pageDTO) {
    page page_Registro = new page();
    page_Registro.setIdpage(pageDTO.getIdPage());
    page_Registro.setpage(pageDTO.getPage());
    return page_Registro;
    }
public pageDTO converToDTO(page page) {
    pageDTO pageDTO = new pageDTO();
    pageDTO.setIdPage(page.getIdPage());
    pageDTO.setPage(page.getpage());
    return pageDTO;
    }
}