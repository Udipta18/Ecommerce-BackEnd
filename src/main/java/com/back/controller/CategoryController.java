package com.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.back.config.AppConstants;
import com.back.payload.ApiResponse;
import com.back.payload.CategoryDto;
import com.back.payload.CategoryResponse;
import com.back.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// create

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCat(@RequestBody CategoryDto caDto) {
		CategoryDto create = this.categoryService.create(caDto);
		return new ResponseEntity<CategoryDto>(create, HttpStatus.CREATED);
	}

	// update
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCat(@RequestBody CategoryDto caDto, @PathVariable int catId) {
		CategoryDto update = this.categoryService.update(caDto, catId);
		return new ResponseEntity<CategoryDto>(update, HttpStatus.OK);
	}

	// delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleleCat(@PathVariable int catId) {
		this.categoryService.delete(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true), HttpStatus.OK);
	}

	// get

	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCat(@PathVariable int catId) {
		CategoryDto categoryDto = this.categoryService.get(catId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}

	// getall

	@GetMapping("/pagination")
	public ResponseEntity<CategoryResponse> get(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER_STRING, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE_STRING, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_CATEGORY_ID, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_STRING, required = false) String sortDir
			
			) {
		 CategoryResponse response = this.categoryService.get(pageNo,pageSize,sortBy,sortDir);
		return new ResponseEntity<CategoryResponse>(response, HttpStatus.OK);
	}
}
