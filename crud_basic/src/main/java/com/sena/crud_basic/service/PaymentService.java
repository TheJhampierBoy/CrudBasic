package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.PaymentDTO;
import com.sena.crud_basic.model.Payment;
import com.sena.crud_basic.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public String save(PaymentDTO paymentDTO) {
        if (paymentDTO.getAmount() <= 0) {
            return "El monto debe ser mayor que cero";
        }
        if (paymentDTO.getMethod() == null || paymentDTO.getMethod().trim().isEmpty()) {
            return "El método de pago no puede estar vacío";
        }

        Payment payment = convertToModel(paymentDTO);
        paymentRepository.save(payment);
        return "Pago registrado correctamente";
    }

    public List<PaymentDTO> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PaymentDTO> findById(int id) {
        return paymentRepository.findById(id)
                .map(this::convertToDTO);
    }

    public String deletePayment(int id) {
        if (!paymentRepository.existsById(id)) {
            return "El pago no existe";
        }
        paymentRepository.deleteById(id);
        return "Pago eliminado correctamente";
    }

    public String updatePayment(int id, PaymentDTO paymentDTO) {
        Optional<Payment> paymentOpt = paymentRepository.findById(id);
        if (!paymentOpt.isPresent()) {
            return "El pago con ID " + id + " no existe";
        }

        Payment existingPayment = paymentOpt.get();
        existingPayment.setAmount(paymentDTO.getAmount());
        existingPayment.setDate(paymentDTO.getDate());
        existingPayment.setMethod(paymentDTO.getMethod());
        existingPayment.setDescription(paymentDTO.getDescription());
        existingPayment.setStatus(paymentDTO.getStatus()); // Cambiado a String

        paymentRepository.save(existingPayment);
        return "Pago actualizado correctamente";
    }

    public List<PaymentDTO> filterPayments(String method, String description,
                                           Double minAmount, Double maxAmount,
                                           LocalDateTime startDate, LocalDateTime endDate,
                                           String status) { // Cambiado a String
        return paymentRepository.filterPayments(
                method, description,
                minAmount, maxAmount,
                startDate, endDate,
                status) // Cambiado a String
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Payment convertToModel(PaymentDTO paymentDTO) {
        return new Payment(
                paymentDTO.getId(),
                paymentDTO.getAmount(),
                paymentDTO.getDate(),
                paymentDTO.getMethod(),
                paymentDTO.getDescription(),
                paymentDTO.getStatus() // Cambiado a String
        );
    }

    private PaymentDTO convertToDTO(Payment payment) {
        return new PaymentDTO(
                payment.getId(),
                payment.getAmount(),
                payment.getDate(),
                payment.getMethod(),
                payment.getDescription(),
                payment.getStatus() // Cambiado a String
        );
    }
}
