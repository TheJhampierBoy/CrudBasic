package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.DrugDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.Drug;
import com.sena.crud_basic.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrugService {

    @Autowired
    private DrugRepository drugRepository;

    public responseDTO save(DrugDTO drugDTO) {
        Drug drug = new Drug();
        drug.setName(drugDTO.getName());
        drug.setDescription(drugDTO.getDescription());
        drug.setPrice((int) drugDTO.getPrice()); // Conversión de double a int
        drugRepository.save(drug);
        return new responseDTO("success", "Drug registered successfully");
    }

    public List<Drug> findAll() {
        return drugRepository.findAll();
    }

    public Optional<Drug> findById(Integer id) {
        return drugRepository.findById(id);
    }

    public responseDTO deleteDrug(Integer id) {
        if (drugRepository.existsById(id)) {
            drugRepository.deleteById(id);
            return new responseDTO("success", "Drug deleted successfully");
        } else {
            return new responseDTO("error", "Drug not found");
        }
    }
}