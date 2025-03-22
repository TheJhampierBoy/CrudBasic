package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.CategoryDTO;
import com.sena.crud_basic.DTO.responseDTO;
import com.sena.crud_basic.model.Category;
import com.sena.crud_basic.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Guardar una nueva categoría
    public responseDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return new responseDTO("200", "Category registered successfully");
    }

    // Obtener todas las categorías
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Obtener una categoría por ID
    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    // Eliminar una categoría por ID
    public responseDTO deleteCategory(int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return new responseDTO("200", "Category deleted successfully");
        } else {
            return new responseDTO("404", "Category not found");
        }
    }
}
