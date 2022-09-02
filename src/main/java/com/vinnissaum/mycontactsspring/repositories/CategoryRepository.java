package com.vinnissaum.mycontactsspring.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinnissaum.mycontactsspring.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID>{}
