package com.back.serviceImpl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.back.exception.ResourceNotFoundException;
import com.back.models.Category;
import com.back.models.Product;
import com.back.payload.ProductDto;
import com.back.payload.ProductResponse;
import com.back.repo.CategoryRepository;
import com.back.repo.ProductRepository;
import com.back.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDto createProduct(ProductDto productDto, int categoryId) {
		// TODO Auto-generated method stub
		Category cat = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category ID not found" + categoryId));
		Product product = modelMapper.map(productDto, Product.class);
		product.setCategory(cat);
		Product createdProudct = productRepository.save(product);
		return modelMapper.map(createdProudct, ProductDto.class);
	}

	@Override
	public ProductResponse getAllProduct(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Product> page = this.productRepository.findAll(pageable);
		List<Product> all = page.getContent();
		List<ProductDto> dtos = all.stream().map(product -> this.modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());

		ProductResponse response = new ProductResponse();

		response.setContent(dtos);
		response.setPageNumber(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalElements(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setLastPage(page.isLast());

		return response;
	}

	@Override
	public ProductDto getProduct(int productId) {
		// TODO Auto-generated method stub
		Product productById = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("product id not found " + productId));
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

	@Override
	public ProductResponse getProductByCategory(int categoryId, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Category cat = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category ID not found" + categoryId));
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Product> page = productRepository.findByCategory(cat, pageable);

		List<Product> all = page.getContent();
		List<ProductDto> dtos = all.stream().map((product) -> this.modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());

		ProductResponse response = new ProductResponse();

		response.setContent(dtos);
		response.setPageNumber(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalElements(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setLastPage(page.isLast());

		return response;
	}

}
