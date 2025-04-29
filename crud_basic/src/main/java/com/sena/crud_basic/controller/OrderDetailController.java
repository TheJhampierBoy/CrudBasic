package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.OrderDetailDTO;
import com.sena.crud_basic.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/")
    public ResponseEntity<String> registerOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        String response = orderDetailService.save(orderDetailDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        List<OrderDetailDTO> orderDetails = orderDetailService.findAll();
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneOrderDetail(@PathVariable int id) {
        Optional<OrderDetailDTO> orderDetail = orderDetailService.findById(id);
        if (!orderDetail.isPresent()) {
            return new ResponseEntity<>("Detalle de pedido no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderDetail.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrderDetail(@PathVariable int id, @RequestBody OrderDetailDTO orderDetailDTO) {
        String response = orderDetailService.updateOrderDetail(id, orderDetailDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable int id) {
        String response = orderDetailService.deleteOrder(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<OrderDetailDTO>> filterOrderDetails(
            @RequestParam(required = false) Integer orderId,
            @RequestParam(required = false) Integer drugId,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Double price) {
        List<OrderDetailDTO> orderDetails = orderDetailService.filterOrderDetails(
                orderId, drugId, quantity, price);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }
}