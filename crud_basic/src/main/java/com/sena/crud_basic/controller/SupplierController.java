    package com.sena.crud_basic.controller;

    import com.sena.crud_basic.DTO.SupplierDTO;
    import com.sena.crud_basic.DTO.responseDTO;
    import com.sena.crud_basic.service.SupplierService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    
    @RestController
    @RequestMapping("/api/v1/supplier")
    public class SupplierController {
    
        /*
         * GET (Obtener todos los pagos)
         * GET (Obtener un pago por ID)
         * POST (Registrar un pago)
         * PUT (Actualizar un pago)
         * DELETE (Eliminar un pago)
         */
    
        @Autowired
        private SupplierService supplierService;
    
        @PostMapping("/")
        public ResponseEntity<Object> registerSupplier(@RequestBody SupplierDTO supplier) {
            responseDTO respuesta = supplierService.save(supplier);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }
        @GetMapping("/")
        public ResponseEntity<Object> getAllSupplier() {
            var listaSupplier = supplierService.findAll();
            return new ResponseEntity<>(listaSupplier, HttpStatus.OK);
        }
    
        @GetMapping("/{id}")
        public ResponseEntity<Object> getOneSupplier(@PathVariable int id) {
            var supplier = supplierService.findById(id);
            if (!supplier.isPresent())
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(supplier, HttpStatus.OK);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Object> deleteSupplier(@PathVariable int id) {
            var message = supplierService.deleteSupplier(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }   
    }