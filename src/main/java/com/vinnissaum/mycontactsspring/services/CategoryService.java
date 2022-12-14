package com.vinnissaum.mycontactsspring.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.vinnissaum.mycontactsspring.dto.CategoryDTO;
import com.vinnissaum.mycontactsspring.entities.Category;
import com.vinnissaum.mycontactsspring.repositories.CategoryRepository;
import com.vinnissaum.mycontactsspring.services.errors.DatabaseException;
import com.vinnissaum.mycontactsspring.services.errors.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    private static final String NOT_FOUND = "Category not found by id: ";

    @Transactional
    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();

        return list.stream().map(CategoryDTO::new).toList();
    }

    @Transactional
    public CategoryDTO findById(UUID id) {
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(
            () -> new ResourceNotFoundException(NOT_FOUND + id));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO create(CategoryDTO dto) {
        nameIsNull(dto.getName());

        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);

        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(UUID id, CategoryDTO dto) {
        nameIsNull(dto.getName());

        try {
            Category entity = repository.getReferenceById(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);

            return new CategoryDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(NOT_FOUND + id);
        }
    }

    public void delete(UUID id) {
        try {
            Category entity = repository.getReferenceById(id);
            repository.delete(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(NOT_FOUND + id);
        }
    }

    private void nameIsNull(String name) {
        if (name == null) {
            throw new DatabaseException("Name is required");
        }
    }
}
