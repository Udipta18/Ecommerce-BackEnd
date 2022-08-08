package com.back.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.payload.ApiResponse;
import com.back.payload.OrderDto;
import com.back.payload.OrderRequest;
import com.back.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	/* static String username="sksubhgo@gmail.com"; */
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/")
	public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest order,Principal principal){
		OrderDto createOrder = this.orderService.createOrder(order, principal.getName());
		return new ResponseEntity<OrderDto>(createOrder,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable int orderId){
		this.orderService.deleteOrder(orderId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("successfully deleted", false),HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<OrderDto>> getAllOrder(){
		List<OrderDto> all = this.orderService.getAll();
		return new ResponseEntity<List<OrderDto>>(all,HttpStatus.OK);
	}
	
	@PutMapping("/update/{orderId}")
	public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto,@PathVariable int orderId ){
		OrderDto updateOrder = this.orderService.updateOrder(orderDto, orderId);
		return new ResponseEntity<OrderDto>(updateOrder,HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDto> getOrderById(@PathVariable int orderId){
		OrderDto orderDto = this.orderService.get(orderId);
		return new ResponseEntity<OrderDto>(orderDto,HttpStatus.OK);
	}
 }
