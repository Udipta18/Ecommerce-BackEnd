package com.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.payload.CartDto;
import com.back.payload.ItemRequest;
import com.back.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	String userName="sksubhgo@gmail.com";
     
	@Autowired
	private CartService cartService;
	
	
	@PostMapping("/")
	public ResponseEntity<CartDto> addItemToCart(@RequestBody ItemRequest req){
         CartDto addItem = this.cartService.addItem(req, userName);
         return new ResponseEntity<CartDto>(addItem,HttpStatus.OK);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<CartDto> getCart(){
		CartDto cartDto = this.cartService.get(userName);
		return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
	}
	
	
	@PutMapping("/{productId}")
	public ResponseEntity<CartDto> removeItemFromCart(@PathVariable int productId){
		CartDto removeItem = this.cartService.removeItem(userName, productId);
		return new ResponseEntity<CartDto>(removeItem,HttpStatus.OK);
	}
}
