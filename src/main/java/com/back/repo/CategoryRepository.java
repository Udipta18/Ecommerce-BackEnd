package com.back.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
