package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.DTO.OrderDTO;
import com.sena.crud_basic.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    /*
     * GET (Obtener todo el inventario)
     * GET (Obtener un ítem por ID)
     * POST (Registrar un ítem)
     * PUT (Actualizar un ítem)
     * DELETE (Eliminar un ítem)
     */

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<Object> registerOrder(@RequestBody OrderDTO order) {
        responseDTO respuesta = orderService.save(order);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<Object> getAllOrder() {
        var listaOrder = orderService.findAll();
        return new ResponseEntity<>(listaOrder, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneOrder(@PathVariable int id) {
        var order = orderService.findById(id);
        if (!order.isPresent())
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable int id) {
        var message= orderService.deleteOrder(id);
        
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
