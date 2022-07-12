package com.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.back.service.ProductService;


/**
 * This class contains all the urls to Create,Update,Fetch and Delete Products;
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
 * @since 1.0
 * @param product
 * @return Product
 * @see first.Rest.Services.ProductServiceImpl
 */
    @PostMapping("/products")
	public Product createProduct(@RequestBody Product p) {
		Product createdProduct=service.createProduct(p);
		return createdProduct;
		
	}
	
    
    /**
     * This method provide url to get all product
     * @since 1.0
     * @return List of Product
     * @see first.Rest.Services.ProductServiceImpl
     */

// @RequestMapping(value = "/all-product",method = RequestMethod.GET)
    @GetMapping("/products")
    public List<Product> getAllProduct(){
		return service.getAllProduct();
	}
	
    
    
    
    /**
     * This method provide url to get single product to given product id
     * @since 1.0
     * @param productId
     * @return Product
     * @see first.Rest.Services.ProductServiceImpl
     */
@GetMapping("/products/{productId}")
 public Product getProduct(@PathVariable int productId) {
  Product product=service.getProduct(productId);
  return product;
	 
 }

 


/**
 * This method provide url to delete product 
 * @since 1.0
 * @param productId
 * @return message in the form of String
 * @see first.Rest.Services.ProductServiceImpl
 */

@DeleteMapping("/products/{productId}") 
public String deleteProduct(@PathVariable int productId) {
	service.deleteProduct(productId);
	return "product deleted successfully";
}
 
/**
 * This method provide url to update product
 * @since 1.0
 * @param productId
 * @param Product
 * @return Product
 * @see first.Rest.Services.ProductServiceImpl
 */

@PutMapping("/products/{productId}") 
public Product updateProduct(@PathVariable("productId") int pid,@RequestBody Product newProduct) {
	Product updatedProduct=service.updateProduct(newProduct, pid);
	return updatedProduct;
}
 
	
}
