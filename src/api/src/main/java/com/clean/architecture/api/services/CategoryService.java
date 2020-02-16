package com.clean.architecture.api.services;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clean.architecture.api.models.CategoryProductDTO;
import com.clean.architecture.api.utilities.JsonUtilities;
import com.clean.architecture.core.entities.Category;
import com.clean.architecture.core.interfaces.IGenericService;
import com.clean.architecture.infrastructure.repositories.CategoryRepository;
import com.google.gson.Gson;

@Service
public class CategoryService implements IGenericService<CategoryProductDTO> {

	private final CategoryRepository categoryRepository;
	private final ModelMapper mapper;
	private static final Gson gson = new Gson();
	final static Logger logger = LogManager.getLogger(CategoryService.class);
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository , ModelMapper mapper) {
		this.categoryRepository = categoryRepository;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<List<CategoryProductDTO>> getAll() {
		Type listType = new TypeToken<List<CategoryProductDTO>>() {}.getType();
		List<CategoryProductDTO> categories = mapper.map(categoryRepository.findAll(), listType);
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CategoryProductDTO> getById(Long id) {
		try {
			return new ResponseEntity<>(mapper.map(categoryRepository.getOne(id), CategoryProductDTO.class), HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("An HTTP 404 error occured: " + ex.getMessage());
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<String> save(String jsonString) {	
		try {
			JsonUtilities.validateJson(jsonString);
		} catch (Exception ex) {
			logger.error("An HTTP 400 error occured: " + ex.getMessage());
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		categoryRepository.saveAndFlush(gson.fromJson(jsonString, Category.class));
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> update(String jsonString) {	
		try {
			JsonUtilities.validateJson(jsonString);
		} catch (Exception ex) {
			logger.error("An HTTP 400 error occurred: " + ex.getMessage());
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		try {
			//Check if category exists
			categoryRepository.getOne(gson.fromJson(jsonString, Category.class).getId());
			categoryRepository.saveAndFlush(gson.fromJson(jsonString, Category.class));
		} catch (Exception ex) {
			logger.error("An HTTP 404 error occurred: " + ex.getMessage());
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}	
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<String> delete(Long id) {
		try {
			categoryRepository.deleteById(id);
		} catch (Exception ex) {
			logger.error("An HTTP 404 error occurred: " + ex.getMessage());
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}
