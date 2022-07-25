package com.back.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.models.Cart;
import com.back.models.User;

public interface CartRepository extends JpaRepository<Cart, Integer>{
   Optional<Cart> findByUser(User user);
}
