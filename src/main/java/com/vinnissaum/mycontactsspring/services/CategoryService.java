package com.vinnissaum.mycontactsspring.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.vinnissaum.mycontactsspring.entities.Category;
import com.vinnissaum.mycontactsspring.repositories.CategoryRepository;
import com.vinnissaum.mycontactsspring.services.errors.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    @Transactional
    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(UUID id) {
        Optional<Category> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
    }

}
