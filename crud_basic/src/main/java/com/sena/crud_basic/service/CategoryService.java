package com.sena.crud_basic.service;

import com.sena.crud_basic.DTO.CategoryDTO;
import com.sena.crud_basic.model.Category;
import com.sena.crud_basic.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Guardar una nueva categoría con validaciones
    public String save(CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
            return "El nombre de la categoría no puede estar vacío";
        }

        if (categoryDTO.getName().length() > 100) {
            return "El nombre de la categoría no puede exceder los 100 caracteres";
        }

        Category category = convertToModel(categoryDTO);
        categoryRepository.save(category);
        return "Categoría registrada exitosamente";
    }

    // Obtener todas las categorías
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar una categoría por ID
    public Optional<CategoryDTO> findById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(this::convertToDTO);
    }

    // Eliminar una categoría por ID con validación mejorada
    public String deleteCategory(int id) {
        if (!categoryRepository.existsById(id)) {
            return "La categoría con ID " + id + " no existe";
        }

        categoryRepository.deleteById(id);
        return "Categoría eliminada correctamente";
    }

    // Actualizar una categoría existente
    public String updateCategory(int id, CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (!existingCategory.isPresent()) {
            return "La categoría con ID " + id + " no existe";
        }

        Category category = existingCategory.get();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);

        return "Categoría actualizada correctamente";
    }

    // Filtrar categorías por nombre
    public List<CategoryDTO> filterCategories(String name) {
        return categoryRepository.filterCategories(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convertir DTO a modelo
    private Category convertToModel(CategoryDTO categoryDTO) {
        return new Category(
                categoryDTO.getId(),
                categoryDTO.getName(),
                categoryDTO.getDescription()
        );
    }

    // Convertir modelo a DTO
    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(
                category.getCategoryID(),
                category.getName(),
                category.getDescription()
        );
    }
}
