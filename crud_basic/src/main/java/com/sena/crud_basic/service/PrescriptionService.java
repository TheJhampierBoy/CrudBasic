package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.PrescriptionDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.Prescription;
import com.sena.crud_basic.repository.PrescriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<Prescription> findAll() {
        return prescriptionRepository.findAll();
    }

    public Optional<Prescription> findById(int id) {
        return prescriptionRepository.findById(id);
    }

    public responseDTO deletePrescription(int id) {
        if (!prescriptionRepository.existsById(id)) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "La receta no existe");
        }
        prescriptionRepository.deleteById(id);
        return new responseDTO(HttpStatus.OK.toString(), "Receta eliminada correctamente");
    }

    public responseDTO save(PrescriptionDTO dto) {
        if (dto.getDoctorName() == null || dto.getDoctorName().isEmpty()) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre del doctor no puede estar vacío");
        }

        Prescription prescription = convertToModel(dto);
        prescriptionRepository.save(prescription);
        return new responseDTO(HttpStatus.OK.toString(), "Receta guardada correctamente");
    }

    // ✅ Método para convertir DTO a modelo
    public Prescription convertToModel(PrescriptionDTO dto) {
        Prescription prescription = new Prescription();
        prescription.setPrescriptionID(dto.getPrescriptionID());
        prescription.setEmployeeID(dto.getEmployeeID());
        prescription.setCustomerID(dto.getCustomerID());
        prescription.setDoctorName(dto.getDoctorName());
        prescription.setPrescriptionDate(dto.getPrescriptionDate());
        prescription.setStatus(dto.getStatus());
        return prescription;
    }

    // ✅ Método para convertir modelo a DTO
    public PrescriptionDTO convertToDTO(Prescription model) {
        return new PrescriptionDTO(
            model.getPrescriptionID(),
            model.getEmployeeID(),
            model.getCustomerID(),
            model.getDoctorName(),
            model.getPrescriptionDate(),
            model.getStatus()
        );
    }
}
