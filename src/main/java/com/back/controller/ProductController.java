package com.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.back.models.Product;
import com.back.payload.ApiResponse;
import com.back.payload.ProductDto;
import com.back.service.ProductService;

/**
 * This class contains all the urls to Create,Update,Fetch and Delete Products;
 * 
 * @since 1.0
 * @author admin
 *
 */
@RestController
public class ProductController {
	@Autowired
	private ProductService service;

	/**
	 * This method provide url to create product
	 * 
	 * @since 1.0
	 * @param product
	 * @return Product
	 * @see first.Rest.Services.ProductServiceImpl
	 */
	@PostMapping("/products")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto p) {
		ProductDto createProduct = service.createProduct(p);
		return new ResponseEntity<ProductDto>(createProduct, HttpStatus.CREATED);

	}

	/**
	 * This method provide url to get all product
	 * 
	 * @since 1.0
	 * @return List of Product
	 * @see first.Rest.Services.ProductServiceImpl
	 */

// @RequestMapping(value = "/all-product",method = RequestMethod.GET)
	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getAllProduct() {
		List<ProductDto> allProduct = service.getAllProduct();
		return new ResponseEntity<List<ProductDto>>(allProduct, HttpStatus.OK);
	}

	/**
	 * This method provide url to get single product to given product id
	 * 
	 * @since 1.0
	 * @param productId
	 * @return Product
	 * @see first.Rest.Services.ProductServiceImpl
	 */
	@GetMapping("/products/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable int productId) {
		ProductDto productDto = service.getProduct(productId);
		return new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);

	}

	/**
	 * This method provide url to delete product
	 * 
	 * @since 1.0
	 * @param productId
	 * @return message in the form of String
	 * @see first.Rest.Services.ProductServiceImpl
	 */

	@DeleteMapping("/products/{productId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable int productId) {
		service.deleteProduct(productId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted successfullt", true), HttpStatus.OK);
	}

	/**
	 * This method provide url to update product
	 * 
	 * @since 1.0
	 * @param productId
	 * @param Product
	 * @return Product
	 * @see first.Rest.Services.ProductServiceImpl
	 */

	@PutMapping("/products/{productId}")
	public  ResponseEntity<ProductDto>  updateProduct(@PathVariable("productId") int pid, @RequestBody ProductDto newProduct) {
		 ProductDto updateProduct = service.updateProduct(newProduct, pid);
		return new ResponseEntity<ProductDto>(updateProduct, HttpStatus.OK);
	}

}
