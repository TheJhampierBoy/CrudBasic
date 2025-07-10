package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.PaymentDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.Payment;
import com.sena.crud_basic.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Guardar un nuevo pago
    public responseDTO save(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setDate(paymentDTO.getDate());
        payment.setMethod(paymentDTO.getMethod());
        payment.setDescription(paymentDTO.getDescription());

        paymentRepository.save(payment);
        return new responseDTO(HttpStatus.OK,"Pago registrado correctamente");
    }

    // Listar todos los pagos
    public List<PaymentDTO> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar un pago por ID
    public Optional<PaymentDTO> findById(int id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.map(this::convertToDTO);
    }

    // Eliminar un pago por ID
    public responseDTO deletePayment(int id) {
        if (!paymentRepository.existsById(id)) {
            return new responseDTO(HttpStatus.NOT_FOUND,"El pago no existe");
        }
        paymentRepository.deleteById(id);
        return new responseDTO(HttpStatus.OK,"Pago eliminado correctamente");
    }

    // Método para convertir entidad a DTO
    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setDate(payment.getDate());
        dto.setMethod(payment.getMethod());
        dto.setDescription(payment.getDescription());
        return dto;
    }
}
