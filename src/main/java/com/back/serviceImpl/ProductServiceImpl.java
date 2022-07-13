package com.back.serviceImpl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.exception.ResourceNotFoundException;
import com.back.models.Product;
import com.back.payload.ProductDto;
import com.back.repo.ProductRepository;
import com.back.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDto createProduct(ProductDto productDto) {
		// TODO Auto-generated method stub
		Product map = modelMapper.map(productDto, Product.class);
		Product createdProudct = productRepository.save(map);
		return modelMapper.map(createdProudct, ProductDto.class);
	}

	@Override
	public List<ProductDto> getAllProduct() {
		// TODO Auto-generated method stub
		List<Product> all = this.productRepository.findAll();
		List<ProductDto> dtos = all.stream().map(product -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());

				
		return dtos;
	}

	@Override
	public ProductDto getProduct(int productId) {
		// TODO Auto-generated method stub
		Product productById = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product id not found "+productId));
		ProductDto productDto = modelMapper.map(productById, ProductDto.class);
		return productDto;
	}

	@Override
	public void deleteProduct(int productId) {
		// TODO Auto-generated method stub

		Product product = this.productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("Product with " + productId + " not found on server !!"));
		this.productRepository.delete(product);
	}

	@Override
	public ProductDto updateProduct(ProductDto newProduct, int productId) {
		// TODO Auto-generated method stub
		Product product = this.productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("Product with " + productId + " not found on server !!"));
		product.setProductName(newProduct.getProductName());
		product.setProductDesc(newProduct.getProductDesc());
		product.setProductPrice(newProduct.getProductPrice());
		product.setLive(newProduct.isLive());
		product.setStock(newProduct.isStock());
		product.setImageName(newProduct.getImageName());
		Product updatedProduct = this.productRepository.save(product);
		return this.modelMapper.map(updatedProduct, ProductDto.class);
	}
	

}
