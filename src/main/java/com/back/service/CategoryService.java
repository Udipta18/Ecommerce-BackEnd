package com.back.service;

import java.util.List;

import com.back.payload.CategoryDto;

public interface CategoryService  {
	// create

		CategoryDto create(CategoryDto dto);

		// update
		CategoryDto update(CategoryDto dto, int categoryId);

		// delete

		void delete(int categoryId);

		//get single

		CategoryDto get(int categoryId);

		//get all
		List<CategoryDto> get();
}
