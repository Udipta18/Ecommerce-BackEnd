package com.back.service;

import java.util.List;

import com.back.payload.OrderDto;
import com.back.payload.OrderRequest;

public interface OrderService {
 
	// create order

		OrderDto createOrder(OrderRequest request, String username);

		// update order
		OrderDto updateOrder(OrderDto orderDto, int orderId);

		// delete order
		void deleteOrder(int orderId);

		// get all orders
		List<OrderDto> getAll();

		// get single order
		OrderDto get(int orderId);

}
