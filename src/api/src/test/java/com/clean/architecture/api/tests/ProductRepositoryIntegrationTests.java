package com.clean.architecture.api.tests;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.clean.architecture.core.entities.Category;
import com.clean.architecture.core.entities.Product;
import com.clean.architecture.infrastructure.repositories.ProductRepository;

@DataJpaTest
public class ProductRepositoryIntegrationTests {
	
	@Autowired
	ProductRepository repository;
	
    @Test
    public void contextNotNull() {
        Assertions.assertNotNull(repository);
    }	

	@Test
	void findByIdProduct() {
		
		Optional<Product> result = repository.findById(1L);
		Assertions.assertEquals(result.get().getId().longValue(), 1L);
		Assertions.assertEquals(result.get().getName(), "Product1");
		Assertions.assertEquals(result.get().getDescription(), "Product1 Description");
		Assertions.assertEquals(result.get().getCategory().getId().longValue(), 1L);
	}
	
	@Test
	void findAllProducts() {
		
		List<Product> products = repository.findAll();
		Assertions.assertEquals(products.get(0).getId().longValue(), 1L);
		Assertions.assertEquals(products.get(0).getName(), "Product1");
		Assertions.assertEquals(products.get(0).getDescription(), "Product1 Description");
		Assertions.assertEquals(products.get(0).getCategory().getId().longValue(), 1L);
		Assertions.assertEquals(products.get(1).getId().longValue(), 2L);
		Assertions.assertEquals(products.get(1).getName(), "Product2");		
		Assertions.assertEquals(products.get(1).getDescription(), "Product2 Description");
		Assertions.assertEquals(products.get(1).getCategory().getId().longValue(), 2L);
	}
	
	@Test
	void saveProduct() {
		Product product = new Product();
		product.setName("Product Save Name");
		Product savedProduct = repository.saveAndFlush(product);
		
		Assertions.assertTrue(savedProduct.getId().longValue() > 0);
		Assertions.assertEquals(savedProduct.getName(), product.getName());
	}
	
	@Test
	void updateProduct() {
		
		Product product = new Product();
		product.setName("Product Name");
		product.setDescription("Product Description");
		
		Category category = new Category();
		category.setId(1L);
		category.setName("Category1");
		product.setCategory(category);
		
		Product savedProduct = repository.saveAndFlush(product);
		
		savedProduct.setName("Product Name Update");
		savedProduct.setDescription("Product Description Update");
		
		category.setId(2L);
		category.setName("Category2");
		
		savedProduct.setCategory(category);
		
		Product updatedProduct = repository.saveAndFlush(savedProduct);
		
		Assertions.assertEquals(updatedProduct.getId().longValue(), savedProduct.getId().longValue());
		Assertions.assertEquals(updatedProduct.getName(), savedProduct.getName());
		Assertions.assertEquals(updatedProduct.getDescription(), savedProduct.getDescription());
		Assertions.assertEquals(updatedProduct.getCategory().getId(), savedProduct.getCategory().getId());
	}
	
	@Test
	void deleteProduct() {
		
		Product product = new Product();
		product.setName("Product Save Name");
		Product savedProduct = repository.saveAndFlush(product);
		long productId = savedProduct.getId();
		
		repository.delete(savedProduct);
		
		Assertions.assertEquals(repository.findById(productId), Optional.empty());
	}

}

