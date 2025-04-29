package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.CategoryDTO;
import com.sena.crud_basic.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Registrar una nueva categoría
    @PostMapping("/")
    public ResponseEntity<String> registerCategory(@RequestBody CategoryDTO categoryDTO) {
        String response = categoryService.save(categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Obtener todas las categorías
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCategory(@PathVariable int id) {
        Optional<CategoryDTO> category = categoryService.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category.get(), HttpStatus.OK);
    }

    // Actualizar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO) {
        String response = categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Eliminar una categoría por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        String response = categoryService.deleteCategory(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Filtrar categorías por nombre
    @GetMapping("/filter")
    public ResponseEntity<List<CategoryDTO>> filterCategories(@RequestParam(required = false) String name) {
        List<CategoryDTO> categories = categoryService.filterCategories(name);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}