package com.back.service;

import java.util.List;

import com.back.payload.CategoryDto;
import com.back.payload.CategoryResponse;

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
		CategoryResponse get(int pageNo,int pageSize,String sortBy,String sortDir);
}
