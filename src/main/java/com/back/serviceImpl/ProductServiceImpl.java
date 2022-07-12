package com.back.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.models.Product;
import com.back.repo.ProductRepository;
import com.back.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	@Override
	public Product createProduct(Product product) {
		// TODO Auto-generated method stub
		Product save = productRepository.save(product);
		return save;
	}

	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		 List<Product> list = productRepository.findAll();
		return list;
	}

	@Override
	public Product getProduct(int productId) {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(productId).get();
		return product;
	}

	@Override
	public void deleteProduct(int productId) {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(productId).get();
		productRepository.delete(product);
	}

	@Override
	public Product updateProduct(Product newProduct, int productId) {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(productId).get();
		product.setProductName(newProduct.getProductName());
		product.setProductDesc(newProduct.getProductDesc());
		product.setProductPrice(newProduct.getProductPrice());
		product.setStock(newProduct.isStock());
		Product save = productRepository.save(product);
		return save;
	}

}
