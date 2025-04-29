package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.PrescriptionDTO;
import com.sena.crud_basic.model.Prescription;
import com.sena.crud_basic.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    // Método para guardar prescripción
    public PrescriptionDTO save(PrescriptionDTO prescriptionDTO) {
        Prescription prescription = convertToModel(prescriptionDTO);
        prescription = prescriptionRepository.save(prescription);
        return convertToDTO(prescription);
    }

    // Obtener todas las prescripciones
    public List<PrescriptionDTO> findAll() {
        return prescriptionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public Optional<PrescriptionDTO> findById(int id) {
        return prescriptionRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Eliminar prescripción
    public String deletePrescription(int id) {
        if (!prescriptionRepository.existsById(id)) {
            return "Prescripción no encontrada";
        }
        prescriptionRepository.deleteById(id);
        return "Prescripción eliminada correctamente";
    }

    // Actualizar prescripción
    public Optional<PrescriptionDTO> updatePrescription(int id, PrescriptionDTO prescriptionDTO) {
        return prescriptionRepository.findById(id)
                .map(existingPrescription -> {
                    existingPrescription.setEmployeeID(prescriptionDTO.getEmployeeID());
                    existingPrescription.setCustomerID(prescriptionDTO.getCustomerID());
                    existingPrescription.setDoctorName(prescriptionDTO.getDoctorName());
                    existingPrescription.setPrescriptionDate(prescriptionDTO.getPrescriptionDate());
                    Prescription updated = prescriptionRepository.save(existingPrescription);
                    return convertToDTO(updated);
                });
    }

    // Filtrado avanzado
    public List<PrescriptionDTO> filterPrescriptions(
            String doctorName,
            Integer employeeId,
            Integer customerId,
            Date startDate,
            Date endDate) {
        
        if (startDate != null && endDate != null && startDate.after(endDate)) {
            throw new IllegalArgumentException("Fecha de inicio no puede ser posterior a fecha final");
        }

        return prescriptionRepository.filterPrescriptions(
                doctorName,
                employeeId,
                customerId,
                startDate,
                endDate
        ).stream()
         .map(this::convertToDTO)
         .collect(Collectors.toList());
    }

    // Conversiones
    private PrescriptionDTO convertToDTO(Prescription prescription) {
        return new PrescriptionDTO(
                prescription.getPrescriptionID(),
                prescription.getEmployeeID(),
                prescription.getCustomerID(),
                prescription.getDoctorName(),
                prescription.getPrescriptionDate()
        );
    }

    private Prescription convertToModel(PrescriptionDTO dto) {
        Prescription prescription = new Prescription();
        prescription.setPrescriptionID(dto.getPrescriptionID());
        prescription.setEmployeeID(dto.getEmployeeID());
        prescription.setCustomerID(dto.getCustomerID());
        prescription.setDoctorName(dto.getDoctorName());
        prescription.setPrescriptionDate(dto.getPrescriptionDate());
        return prescription;
    }
}