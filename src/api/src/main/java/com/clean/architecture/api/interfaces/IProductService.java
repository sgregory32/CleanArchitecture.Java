package com.clean.architecture.api.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.clean.architecture.api.models.ProductCategoryDTO;

public interface IProductService {
	
	public ResponseEntity<List<ProductCategoryDTO>> getProducts();
	public ResponseEntity<ProductCategoryDTO> getProduct(Long id);
	public ResponseEntity<String> saveProduct(String jsonString);
	public ResponseEntity<String> updateProduct(String jsonString);
	public ResponseEntity<String> deleteProduct(Long id);
}
