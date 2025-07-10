package com.sena.crud_basic.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sena.crud_basic.DTO.permission_roleDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.page_role;
import com.sena.crud_basic.model.permission_role;
import com.sena.crud_basic.repository.IPermission_role;



@Service
public class permission_roleService {
   @Autowired
    private IPermission_role data;

    @Autowired
    private page_roleService page_roleService;





    public List<permission_role> findAll() {
        return data.findAll();
    }

    
    public Optional<permission_role> findById(int id) {
        return data.findById(id);
    }

    public responseDTO delete(int id) {
        Optional<permission_role> permission_role = findById(id);
        if (!permission_role.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "La factura de préstamo no existe.");
        }
        data.deleteById(id);
        return new responseDTO(HttpStatus.OK, "Factura de préstamo eliminada correctamente.");
    }

    public responseDTO update(int id, permission_roleDTO permission_roleDTO) {
        Optional<permission_role> existingLoanOpt = findById(id);

        if (!existingLoanOpt.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "La factura de préstamo no existe.");
        }

        Optional<page_role> pageRoleOptional = page_roleService.findById(permission_roleDTO.getIdPageRole());
        if (!pageRoleOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "El usuario no existe.");
        }



        try {
            permission_role existingLoan = existingLoanOpt.get();
            existingLoan.setPermissionType(permission_roleDTO.getPermissionType());
            existingLoan.setIdPageRole(pageRoleOptional.get());

            data.save(existingLoan);

            return new responseDTO(HttpStatus.OK, "Factura de préstamo actualizada correctamente.");
        } catch (Exception e) {
            return new responseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar: " + e.getMessage());
        }
    }

    public responseDTO save(permission_roleDTO permission_roleDTO) {
        Optional<page_role> pageRoleOptional = page_roleService.findById(permission_roleDTO.getIdPageRole());
        if (!pageRoleOptional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND, "The permission_role with ID " + permission_roleDTO.getIdPageRole() + " does not exist.");
        }


        permission_role record = convertToModel(permission_roleDTO, pageRoleOptional.get());

        permission_role savedLoan = data.save(record);
        permission_roleDTO responseLoan = convertToDTO(savedLoan);

        return new responseDTO(HttpStatus.OK, "permission_role saved correctly.", responseLoan);
    }

    public permission_roleDTO convertToDTO(permission_role permission_role) {
        return new permission_roleDTO(
            permission_role.getIdPermissionRole(),
            permission_role.getPermissionType(),
            permission_role.getIdPageRole().getIdPageRole()
        );
    }

    public permission_role convertToModel(permission_roleDTO permission_roleDTO, page_role page_role) {
        return new permission_role(
            0,
            permission_roleDTO.getPermissionType(),
            page_role
        );
    }
}