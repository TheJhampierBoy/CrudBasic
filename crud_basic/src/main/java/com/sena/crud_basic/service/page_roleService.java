package com.sena.crud_basic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.page_roleDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.page;
import com.sena.crud_basic.model.page_role;
import com.sena.crud_basic.model.roles;
import com.sena.crud_basic.repository.Ipage_role;


@Service
public class page_roleService {
   @Autowired
    private Ipage_role data;

    @Autowired
    private rolesService roleService;
    @Autowired
    private pageService pageService;


    public List<page_role> findAll() {
        return data.findAll();
    }

    
    public Optional<page_role> findById(int id) {
        return data.findById(id);
    }

    public responseDTO delete(int id) {
        Optional<page_role> page_role = findById(id);
        if (!page_role.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "La factura de préstamo no existe.");
        }
        data.deleteById(id);
        return new responseDTO(HttpStatus.OK, "Factura de préstamo eliminada correctamente.");
    }

    public responseDTO update(int id, page_roleDTO page_roleDTO) {
        Optional<page_role> existingLoanOpt = findById(id);

        if (!existingLoanOpt.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "La factura de préstamo no existe.");
        }

        Optional<roles> roleOptional = roleService.findById(page_roleDTO.getIdRole());
        if (!roleOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "El usuario no existe.");
        }
        Optional<page> pageOptional = pageService.findById(page_roleDTO.getIdPage());
        if (!pageOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "La página no existe.");
        }


        try {
            page_role existingLoan = existingLoanOpt.get();
            existingLoan.setIdRole(roleOptional.get());
            existingLoan.setIdPage(pageOptional.get());
            data.save(existingLoan);

            return new responseDTO(HttpStatus.OK, "Factura de préstamo actualizada correctamente.");
        } catch (Exception e) {
            return new responseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar: " + e.getMessage());
        }
    }

    public responseDTO save(page_roleDTO page_roleDTO) {
        Optional<roles> roleOptional = roleService.findById(page_roleDTO.getIdRole());
        if (!roleOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "The page_role with ID " + page_roleDTO.getIdRole() + " does not exist.");
        }
        Optional<page> pageOptional = pageService.findById(page_roleDTO.getIdPage());
        if (!pageOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "The page with ID " + page_roleDTO.getIdPage() + " does not exist.");
        }


        page_role record = convertToModel(page_roleDTO, roleOptional.get(), pageOptional.get());

        page_role savedLoan = data.save(record);
        page_roleDTO responseLoan = convertToDTO(savedLoan);

        return new responseDTO(HttpStatus.OK, "page_role saved correctly.", responseLoan);
    }

    public page_roleDTO convertToDTO(page_role page_role) {
        return new page_roleDTO(
            page_role.getIdPageRole(),
            page_role.getIdPage().getIdPage(),
            page_role.getIdRole().getid_roles()
        );
    }

    public page_role convertToModel(page_roleDTO page_roleDTO, roles role, page page) {
        return new page_role(
            0,
            page,
            role
        );
    }
}