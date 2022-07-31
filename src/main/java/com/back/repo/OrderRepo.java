package com.back.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.models.Order;
import com.back.models.User;

public interface OrderRepo extends JpaRepository<Order, Integer> {
       
	  List<Order> findByUser(User user);
}
