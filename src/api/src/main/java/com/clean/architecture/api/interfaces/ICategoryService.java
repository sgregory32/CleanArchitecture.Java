package com.clean.architecture.api.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.clean.architecture.api.models.CategoryProductDTO;

public interface ICategoryService {
	
	public ResponseEntity<List<CategoryProductDTO>> getCategories();
	public ResponseEntity<CategoryProductDTO> getCategory(Long id);
	public ResponseEntity<String> saveCategory(String jsonString);
	public ResponseEntity<String> updateCategory(String jsonString);
	public ResponseEntity<String> deleteCategory(Long id);
}
