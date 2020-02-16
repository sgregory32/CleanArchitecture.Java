package com.clean.architecture.api.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.clean.architecture.api.models.ProductCategoryDTO;
import com.clean.architecture.api.services.ProductService;

@RestController
@RequestMapping(value = {"/products"}, produces = "application/json;charset=UTF-8")
public class ProductController {
	
	private final ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<ProductCategoryDTO>> getProducts() {
		return productService.getAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ProductCategoryDTO> getProduct(@PathVariable Long id) {
		return productService.getById(id); 
	}	
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
	public ResponseEntity<String> postProduct(@RequestBody String jsonString) {
		return productService.save(jsonString);
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json;charset=UTF-8")
	public ResponseEntity<String> putProduct(@PathVariable("id") final Long id,	@RequestBody String jsonString) {		
		return productService.update(jsonString);
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> putProduct(@PathVariable("id") final Long id) {
		return productService.delete(id);
	}
}
