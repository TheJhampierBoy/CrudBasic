package com.sena.crud_basic.controller;

import com.sena.crud_basic.DTO.CategoryDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.Category;
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
    public ResponseEntity<responseDTO> registerCategory(@RequestBody CategoryDTO categoryDTO) {
        responseDTO response = categoryService.saveCategory(categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Obtener todas las categorías
    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCategory(@PathVariable int id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.isPresent()
                ? new ResponseEntity<>(category.get(), HttpStatus.OK)
                : new ResponseEntity<>(new responseDTO("404", "Category not found"), HttpStatus.NOT_FOUND);
    }

    // Eliminar una categoría por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<responseDTO> deleteCategory(@PathVariable int id) {
        responseDTO response = categoryService.deleteCategory(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
