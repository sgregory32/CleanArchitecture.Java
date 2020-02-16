package com.clean.architecture.api.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.clean.architecture.api.models.CategoryProductDTO;
import com.clean.architecture.api.services.CategoryService;

@RestController
@RequestMapping(value = {"/categories"}, produces = "application/json;charset=UTF-8")
public class CategoryController {

	private final CategoryService categoryService;
	final static Logger logger = LogManager.getLogger(CategoryController.class);

	@Autowired	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<CategoryProductDTO>> getCategories() {
		return categoryService.getAll();
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CategoryProductDTO> getCategory(@PathVariable Long id) {
		return categoryService.getById(id);
	}	
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> postCategory(@RequestBody String jsonString) {
		return categoryService.save(jsonString);
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json;charset=UTF-8")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> putCategory(@PathVariable("id") final Long id, @RequestBody String jsonString) {
		return categoryService.update(jsonString);
	}	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<String> deleteCategory(@PathVariable("id") final Long id) {
		return categoryService.delete(id);
	}
}
