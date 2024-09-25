package com.erichiroshi.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erichiroshi.dscatalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}