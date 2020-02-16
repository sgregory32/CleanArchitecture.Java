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

import com.clean.architecture.api.models.ProductCategoryDTO;
import com.clean.architecture.api.utilities.JsonUtilities;
import com.clean.architecture.core.entities.Product;
import com.clean.architecture.core.interfaces.IGenericService;
import com.clean.architecture.infrastructure.repositories.ProductRepository;
import com.google.gson.Gson;


@Service
public class ProductService implements IGenericService<ProductCategoryDTO> {
	
	private final ProductRepository productRepository;
	private final ModelMapper mapper;
	private static final Gson gson = new Gson();
	final static Logger logger = LogManager.getLogger(CategoryService.class);

	@Autowired
	public ProductService(ProductRepository productRepository, ModelMapper mapper) {
		this.productRepository = productRepository;
		this.mapper = mapper;
	}

	@Override
	public ResponseEntity<List<ProductCategoryDTO>> getAll() {
		Type listType = new TypeToken<List<ProductCategoryDTO>>() {}.getType();
		List<ProductCategoryDTO> products = mapper.map(productRepository.findAll(), listType);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ProductCategoryDTO> getById(Long id) {
		try {
			return new ResponseEntity<>(mapper.map(productRepository.getOne(id), ProductCategoryDTO.class), HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("An HTTP 404 error occurred: " + ex.getMessage());
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<String> save(String jsonString) {	
		try {
			JsonUtilities.validateJson(jsonString);
		} catch (Exception ex) {
			logger.error("An HTTP 400 error occurred: " + ex.getMessage());
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		productRepository.saveAndFlush(gson.fromJson(jsonString, Product.class));
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
			//Check if product exists
			productRepository.getOne(gson.fromJson(jsonString, Product.class).getId());
			productRepository.saveAndFlush(gson.fromJson(jsonString, Product.class));
		} catch (Exception ex) {
			logger.error("An HTTP 404 error occurred: " + ex.getMessage());
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}	
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<String> delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (Exception ex) {
			logger.error("An HTTP 404 error occurred: " + ex.getMessage());
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);		
	}
}
