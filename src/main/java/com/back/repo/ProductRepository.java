package com.back.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
