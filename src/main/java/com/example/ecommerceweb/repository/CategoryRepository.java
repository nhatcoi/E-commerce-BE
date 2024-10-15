package com.example.ecommerceweb.repository;

import com.example.ecommerceweb.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
