package com.vinnissaum.mycontactsspring.dto;

import java.util.UUID;

import com.vinnissaum.mycontactsspring.entities.Category;

import lombok.Data;

@Data
public class CategoryDTO {

    private UUID id;

    private String name;

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
