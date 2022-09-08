package com.vinnissaum.mycontactsspring.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vinnissaum.mycontactsspring.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>{}
