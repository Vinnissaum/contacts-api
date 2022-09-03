package com.vinnissaum.mycontactsspring.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vinnissaum.mycontactsspring.entities.Category;
import com.vinnissaum.mycontactsspring.services.CategoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<Category>> index() {
        List<Category> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> show(@PathVariable UUID id) {
        Category category = service.findById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Category> store(@RequestBody Category cat) {
        Category category = service.create(cat);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(cat.getId()).toUri();

        return ResponseEntity.created(location).body(category);
    }
}
