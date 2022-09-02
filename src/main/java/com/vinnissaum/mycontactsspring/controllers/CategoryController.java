package com.vinnissaum.mycontactsspring.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinnissaum.mycontactsspring.entities.Category;
import com.vinnissaum.mycontactsspring.services.CategoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<Category>> index() {
        List<Category> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
}
