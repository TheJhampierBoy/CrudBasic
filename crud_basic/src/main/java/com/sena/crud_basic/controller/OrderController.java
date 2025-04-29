package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.OrderDTO;
import com.sena.crud_basic.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        String response = orderService.save(orderDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable int id) {
        Optional<OrderDTO> order = orderService.findById(id);
        if (!order.isPresent()) {
            return new ResponseEntity<>("Pedido no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable int id, @RequestBody OrderDTO orderDTO) {
        String response = orderService.updateOrder(id, orderDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) {
        String response = orderService.deleteOrder(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<OrderDTO>> filterOrders(@RequestParam(required = false) String searchTerm) {
        List<OrderDTO> orders = orderService.filterOrders(searchTerm);
        orders.forEach(order -> System.out.println("Order ID: " + order.getOrderId())); // Agregar este log
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/advanced-filter")
    public ResponseEntity<List<OrderDTO>> advancedFilter(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer minAmount,
            @RequestParam(required = false) Integer maxAmount,
            @RequestParam(required = false) String status) {
        
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : null;
        
        List<OrderDTO> orders = orderService.advancedFilterOrders(
                start, end, minAmount, maxAmount, status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
