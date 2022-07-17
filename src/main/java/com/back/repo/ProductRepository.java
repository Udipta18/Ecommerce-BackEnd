package com.back.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.back.models.Category;
import com.back.models.Product;
import java.util.*;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	 Page<Product> findByCategory(Category category,Pageable pageable);
}
