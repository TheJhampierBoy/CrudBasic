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

    @PostMapping("/")
    public ResponseEntity<responseDTO> registerCategory(@RequestBody CategoryDTO categoryDTO) {
        responseDTO response = categoryService.saveCategory(categoryDTO);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCategory(@PathVariable int id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.isPresent()
                ? new ResponseEntity<>(category.get(), HttpStatus.OK)
                : new ResponseEntity<>(
                    new responseDTO(HttpStatus.NOT_FOUND, "Category not found"), 
                    HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<responseDTO> deleteCategory(@PathVariable int id) {
        responseDTO response = categoryService.deleteCategory(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}