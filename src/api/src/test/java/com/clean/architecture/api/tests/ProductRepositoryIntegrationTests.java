package com.clean.architecture.api.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import com.clean.architecture.core.entities.Category;
import com.clean.architecture.core.entities.Product;
import com.clean.architecture.infrastructure.repositories.ProductRepository;

@DataJpaTest
class ProductRepositoryIntegrationTests {

	@Autowired
	ProductRepository repository;
	
	private Product product1 = new Product();
	private Product product2 = new Product();
	private Product product3 = new Product();
	private Product product4 = new Product();
	
	@BeforeEach
	void seedData() {

		product1.setId(1L);
		product1.setName("Product1 Name");
		product1.setDescription("Product1 Description");
		Category category1 = new Category();
		category1.setId(1L);
		category1.setName("Category1");
		product1.setCategory(category1);
		repository.save(product1);
		
		product2.setId(2L);
		product2.setName("Product2 Name");
		product2.setDescription("Product2 Description");
		Category category2 = new Category();
		category2.setId(2L);
		category2.setName("Category2");
		product2.setCategory(category2);
		repository.save(product2);
	}
	
    @Test
    public void contextNotNull() {
        assertNotNull(repository);
    }	

	@Test
	void findByIdProduct() {
		
		Optional<Product> result = repository.findById(1L);
		assertEquals(result.get().getId().longValue(), 1L);
		assertEquals(result.get().getName(), product1.getName());
		assertEquals(result.get().getDescription(), product1.getDescription());
		assertEquals(result.get().getCategory().getId().longValue(), product1.getCategory().getId());
	}
	
	@Test
	void findAllProducts() {
		
		List<Product> result = repository.findAll(Sort.by(Sort.Direction.ASC, "Id"));
		assertEquals(result.get(0).getId().longValue(), product1.getId());
		assertEquals(result.get(0).getName(), product1.getName());
		assertEquals(result.get(0).getDescription(), product1.getDescription());
		assertEquals(result.get(0).getCategory().getId().longValue(), product1.getCategory().getId());
		assertEquals(result.get(1).getId().longValue(), product2.getId());
		assertEquals(result.get(1).getName(), product2.getName());
		assertEquals(result.get(1).getDescription(), product2.getDescription());
		assertEquals(result.get(1).getCategory().getId().longValue(), product2.getCategory().getId());
	}
	
	@Test
	void saveProduct() {
		
		Product product = new Product();
		product.setName("Product Save Name");
		product.setDescription("Product Save Description");
		Category category = new Category();
		category.setId(1L);
		product.setCategory(category);
		
		Product savedProduct = repository.saveAndFlush(product);
		
		assertTrue(savedProduct.getId().longValue() > 0);
		assertEquals(savedProduct.getName(), product.getName());
		assertEquals(savedProduct.getDescription(), product.getDescription());
		assertEquals(savedProduct.getCategory().getId(), product.getCategory().getId());
	}
	
	@Test
	void updateProduct() {
		
		product3.setName("Product Update Name");
		product3.setDescription("Product Update Description");
		
		Category category = new Category();
		category.setId(1L);
		category.setName("Category Update");
		product3.setCategory(category);
		
		Product updatedProduct = repository.saveAndFlush(product3);
		
		assertEquals(updatedProduct.getId().longValue(), product3.getId().longValue());
		assertEquals(updatedProduct.getName(), product3.getName());
		assertEquals(updatedProduct.getDescription(), product3.getDescription());
		assertEquals(updatedProduct.getCategory().getId(), product3.getCategory().getId());
	}
	
	@Test
	void deleteProduct() {
		
		long productId = 2L;	
		Product product = repository.getOne(productId);
		assertEquals(product.getId().longValue(), productId);
		
		repository.delete(product);
		
		assertEquals(repository.findById(productId), Optional.empty());
	}
}
