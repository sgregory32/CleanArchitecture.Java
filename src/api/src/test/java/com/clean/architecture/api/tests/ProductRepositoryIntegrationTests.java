package com.clean.architecture.api.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

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
	
	private Product product1 = TestHelper.getProduct1ComparisonData();
	private Product product2 = TestHelper.getProduct2ComparisonData();
	
    @Test
    public void contextNotNull() {
        assertNotNull(repository);
    }	

	@Test
	void findByIdProduct() {
		
		Optional<Product> result = repository.findById(1L);
		
		assertEquals(product1.getId().longValue(), result.get().getId().longValue());
		assertEquals(product1.getName(), result.get().getName());
		assertEquals(product1.getDescription(), result.get().getDescription());
		assertEquals(product1.getCategory().getId().longValue(), result.get().getCategory().getId().longValue());
	}
	
	@Test
	void getOneProduct() {
		
		long productId = 1L;
		Product result = repository.getOne(productId);
		
		assertEquals(product1.getId().longValue(), result.getId().longValue());
		assertEquals(product1.getName(), result.getName());
		assertEquals(product1.getDescription(), result.getDescription());
		assertEquals(product1.getCategory().getId().longValue(), result.getCategory().getId().longValue());
	}
	
	@Test
	void findAllProducts() {
		
		List<Product> result = repository.findAll(Sort.by(Sort.Direction.ASC, "Id"));
		assertEquals(product1.getId(), result.get(0).getId().longValue());
		assertEquals(product1.getName(), result.get(0).getName());
		assertEquals(product1.getDescription(), result.get(0).getDescription());
		assertEquals(product1.getCategory().getId(), result.get(0).getCategory().getId().longValue());
		assertEquals(product2.getId(), result.get(1).getId().longValue());
		assertEquals(product2.getName(), result.get(1).getName());
		assertEquals(product2.getDescription(), result.get(1).getDescription());
		assertEquals(product2.getCategory().getId(), result.get(1).getCategory().getId().longValue());
	}
	
	@Test
	void saveProduct() {
		
		Product product = new Product();
		product.setName("Product Save Name");
		product.setDescription("Product Save Description");
		Category category = new Category();
		category.setId(1L);
		product.setCategory(category);
		
		Product result = repository.saveAndFlush(product);
		
		assertTrue(result.getId().longValue() > 0);
		assertEquals(product.getName(), result.getName());
		assertEquals(product.getDescription(), result.getDescription());
		assertEquals(product.getCategory().getId(), result.getCategory().getId());
	}
	
	@Test
	void updateProduct() {
		
		long productId = 3L;
		
		Product product = repository.getOne(productId);	
		product.setName("Product Update Name");
		product.setDescription("Product Update Description");
		
		Category category = new Category();
		category.setId(1L);
		category.setName("Category Update");
		product.setCategory(category);
		
		Product result = repository.saveAndFlush(product);
		
		assertEquals(product.getId().longValue(), result.getId().longValue());
		assertEquals(product.getName(), result.getName());
		assertEquals(product.getDescription(), result.getDescription());
		assertEquals(product.getCategory().getId(), result.getCategory().getId());
	}
	
	@Test
	void deleteProduct() {
		
		long productId = 4L;	
		Product product = repository.getOne(productId);
		assertEquals(productId, product.getId().longValue());
		
		repository.delete(product);
		
		assertEquals(Optional.empty(), repository.findById(productId));
	}
}
