package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.OrderDetailDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orderDetail")
public class OrderDetailController {

    /*
     * GET (Obtener todos los detalles de pedidos)
     * GET (Obtener detalle de pedido por ID)
     * POST (Registrar un detalle de pedido)
     * PUT (Actualizar un detalle de pedido)
     * DELETE (Eliminar un detalle de pedido)
     */

    @Autowired
    private OrderDetailService orderDetailService;
                        
     @PostMapping("/")
    public ResponseEntity<Object> registerOrderDetail(@RequestBody OrderDetailDTO orderDetail) {
        responseDTO respuesta = orderDetailService.save(orderDetail);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<Object> getAllOrderDetail() {
        var listaOrderDetail = orderDetailService.findAll();
        return new ResponseEntity<>(listaOrderDetail, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneOrderDetail(@PathVariable int id) {
        var orderDetail = orderDetailService.findById(id);
        if (!orderDetail.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrderDetail(@PathVariable int id) {
        var message = orderDetailService.deleteOrder(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    
}