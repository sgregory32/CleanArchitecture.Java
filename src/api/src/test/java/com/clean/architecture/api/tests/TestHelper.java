package com.clean.architecture.api.tests;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.clean.architecture.core.entities.Category;
import com.clean.architecture.core.entities.Product;

public class TestHelper {
	
	public static HttpHeaders addHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
	public static Category getCategory1ComparisonData() {
		Category category = new Category();
		category.setId(1L);
		category.setName("Category1");
		return category;
	}
	
	public static Category getCategory2ComparisonData() {
		Category category = new Category();
		category.setId(2L);
		category.setName("Category2");
		return category;
	}
	
	public static Product getProduct1ComparisonData() {
		Product product = new Product();
		product.setId(1L);
		product.setName("Product1");
		product.setDescription("Product1 Description");
		Category category = new Category();
		category.setId(1L);
		category.setName("Category1");
		product.setCategory(category);
		return product;
	}
	
	public static Product getProduct2ComparisonData() {
		Product product = new Product();
		product.setId(2L);
		product.setName("Product2");
		product.setDescription("Product2 Description");
		Category category = new Category();
		category.setId(2L);
		category.setName("Category2");
		product.setCategory(category);
		return product;
	}
}
