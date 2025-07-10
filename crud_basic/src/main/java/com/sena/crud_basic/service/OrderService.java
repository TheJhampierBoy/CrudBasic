package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.OrderDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.Order;
import com.sena.crud_basic.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public responseDTO save(OrderDTO orderDTO) {
        Order order = new Order();
        order.setProduct(orderDTO.getProduct());
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(orderDTO.getPrice());
        orderRepository.save(order);
        return new responseDTO(HttpStatus.CREATED, "Order registered successfully");
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    public responseDTO deleteOrder(Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return new responseDTO(HttpStatus.OK, "Order deleted successfully");
        } else {
            return new responseDTO(HttpStatus.NOT_FOUND, "Order not found");
        }
    }
}