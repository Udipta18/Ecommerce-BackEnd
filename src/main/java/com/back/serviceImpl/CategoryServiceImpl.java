package com.back.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.back.exception.ResourceNotFoundException;
import com.back.models.Category;
import com.back.payload.CategoryDto;
import com.back.payload.CategoryResponse;
import com.back.repo.CategoryRepository;
import com.back.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto create(CategoryDto dto) {
		Category cat = this.mapper.map(dto, Category.class);
		Category savedCategory = this.categoryRepository.save(cat);
		return this.mapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto update(CategoryDto dto, int categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(
				() -> new ResourceNotFoundException("Category with " + categoryId + " not found on server !!"));
		category.setTitle(dto.getTitle());
		Category updatedCat = this.categoryRepository.save(category);
		return this.mapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void delete(int categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(
				() -> new ResourceNotFoundException("Category with " + categoryId + " not found on server !!"));
		this.categoryRepository.delete(category);
	}

	@Override
	public CategoryDto get(int categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(
				() -> new ResourceNotFoundException("Category with " + categoryId + " not found on server !!"));
		return this.mapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryResponse get(int pageNo,int pageSize,String sortBy,String sortDir) {
		
		Sort sr=null;
		if(sortDir.trim().toLowerCase().equals("asc")) {
			sr=Sort.by(sortBy).ascending();
		}
		else{
			sr=Sort.by(sortBy).descending();
		}
		
		Pageable pageable=PageRequest.of(pageNo, pageSize,sr);
		 Page<Category> page = this.categoryRepository.findAll(pageable);
		List<Category> all = page.getContent();
			
		List<CategoryDto> dtos = all.stream().map((cat) -> this.mapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		
		CategoryResponse response=new CategoryResponse();
		response.setContent(dtos);
		response.setPageNumber(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalElements(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setLastPage(page.isLast());
		
		return response;

	}

	@Override
	public List<CategoryDto> get() {
		List<Category> all = this.categoryRepository.findAll();
		List<CategoryDto> dtos = all.stream().map((cat) -> this.mapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		return dtos;
	}
}
