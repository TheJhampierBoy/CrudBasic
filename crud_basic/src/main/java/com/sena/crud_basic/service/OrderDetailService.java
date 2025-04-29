package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.OrderDetailDTO;
import com.sena.crud_basic.model.OrderDetail;
import com.sena.crud_basic.model.Drug;
import com.sena.crud_basic.model.Order;
import com.sena.crud_basic.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public String save(OrderDetailDTO orderDetailDTO) {
        if (orderDetailDTO.getOrderID() == null) {
            return "El ID del pedido no puede estar vacío";
        }

        if (orderDetailDTO.getDrugId() == null) {
            return "El ID del medicamento no puede estar vacío";
        }

        if (orderDetailDTO.getQuantity() <= 0) {
            return "La cantidad debe ser mayor que cero";
        }

        if (orderDetailDTO.getPrice() <= 0) {
            return "El precio debe ser mayor que cero";
        }

        OrderDetail orderDetail = convertToModel(orderDetailDTO);
        orderDetailRepository.save(orderDetail);
        return "Detalle de pedido registrado exitosamente";
    }

    public List<OrderDetailDTO> findAll() {
        return orderDetailRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrderDetailDTO> findById(Integer id) {
        return orderDetailRepository.findById(id)
                .map(this::convertToDTO);
    }

    public String deleteOrder(Integer id) {
        if (!orderDetailRepository.existsById(id)) {
            return "El detalle de pedido con ID " + id + " no existe";
        }

        orderDetailRepository.deleteById(id);
        return "Detalle de pedido eliminado correctamente";
    }

    public String updateOrderDetail(Integer id, OrderDetailDTO orderDetailDTO) {
        Optional<OrderDetail> existingOrderDetail = orderDetailRepository.findById(id);
        if (!existingOrderDetail.isPresent()) {
            return "El detalle de pedido con ID " + id + " no existe";
        }

        // Validations
        if (orderDetailDTO.getOrderID() == null) {
            return "El ID del pedido no puede estar vacío";
        }
        if (orderDetailDTO.getDrugId() == null) {
            return "El ID del medicamento no puede estar vacío";
        }
        if (orderDetailDTO.getQuantity() <= 0) {
            return "La cantidad debe ser mayor que cero";
        }
        if (orderDetailDTO.getPrice() <= 0) {
            return "El precio debe ser mayor que cero";
        }

        OrderDetail orderDetail = existingOrderDetail.get();
        Order order = new Order();
        order.setOrderID(orderDetailDTO.getOrderID());
        orderDetail.setOrder(order);
        
        Drug drug = new Drug();
        drug.setDrugID(orderDetailDTO.getDrugId());
        orderDetail.setDrug(drug);
        
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setPrice(orderDetailDTO.getPrice());

        orderDetailRepository.save(orderDetail);
        return "Detalle de pedido actualizado correctamente";
    }

    public List<OrderDetailDTO> filterOrderDetails(Integer orderId, Integer drugId, Integer quantity, Double price) {
        return orderDetailRepository.filterOrderDetails(
                orderId, 
                drugId, 
                quantity, 
                price)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderDetail convertToModel(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = new OrderDetail();
        
        Order order = new Order();
        order.setOrderID(orderDetailDTO.getOrderID());
        orderDetail.setOrder(order);
        
        Drug drug = new Drug();
        drug.setDrugID(orderDetailDTO.getDrugId());
        orderDetail.setDrug(drug);
        
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setPrice(orderDetailDTO.getPrice());
        
        return orderDetail;
    }

    private OrderDetailDTO convertToDTO(OrderDetail orderDetail) {
        OrderDetailDTO dto = new OrderDetailDTO(
            orderDetail.getOrder().getOrderID(),
            orderDetail.getDrug().getDrugID(),
            orderDetail.getQuantity(),
            orderDetail.getPrice()
        );
        dto.setOrderDetailID(orderDetail.getOrderDetailID());
        return dto;
    }
}
