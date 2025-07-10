package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.OrderDetailDTO;
import com.sena.crud_basic.model.OrderDetail;
import com.sena.crud_basic.model.Drug;
import com.sena.crud_basic.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public responseDTO save(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = new OrderDetail();

        // Si estás enviando solo el ID del drug desde el DTO
        Drug drug = new Drug();
        drug.setDrugID(orderDetailDTO.getDrugId());
        orderDetail.setDrug(drug);

        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setPrice(orderDetailDTO.getPrice());

        orderDetailRepository.save(orderDetail);
        return new responseDTO(HttpStatus.OK, "OrderDetail registered successfully");
    }

    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    public Optional<OrderDetail> findById(Integer id) {
        return orderDetailRepository.findById(id);
    }

    public responseDTO deleteOrder(Integer id) {
        if (orderDetailRepository.existsById(id)) {
            orderDetailRepository.deleteById(id);
            return new responseDTO(HttpStatus.OK, "OrderDetail deleted successfully");
        } else {
            return new responseDTO(HttpStatus.NOT_FOUND, "OrderDetail not found");
        }
    }
}