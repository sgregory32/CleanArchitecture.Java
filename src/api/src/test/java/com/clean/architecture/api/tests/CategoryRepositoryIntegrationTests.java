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

import com.clean.architecture.core.entities.Category;
import com.clean.architecture.infrastructure.repositories.CategoryRepository;

@DataJpaTest
class CategoryRepositoryIntegrationTests {
	
	@Autowired
	CategoryRepository repository;
	
	Category category1 = new Category();
	Category category2 = new Category();
	
	@BeforeEach
	void seedData() {

		category1.setId(1L);
		category1.setName("Category1");
		repository.save(category1);
		
		category2.setId(2L);
		category2.setName("Category2");
		repository.save(category2);
	}
	
    @Test
    public void contextNotNull() {
        assertNotNull(repository);
    }	

	@Test
	void findByIdCategory() {
		
		Optional<Category> result = repository.findById(1L);
		assertEquals(result.get().getId().longValue(), 1L);
		assertEquals(result.get().getName(), "Category1");
	}
	
	@Test
	void findAllCategories() {
		
		List<Category> categories = repository.findAll();
		assertEquals(categories.get(0).getId().longValue(), 1L);
		assertEquals(categories.get(0).getName(), "Category1");
		assertEquals(categories.get(1).getId().longValue(), 2L);
		assertEquals(categories.get(1).getName(), "Category2");		
	}
	
	@Test
	void saveCategory() {
		
		Category category = new Category();
		category.setName("Category Save Name");
		Category savedCategory = repository.saveAndFlush(category);
		
		assertTrue(savedCategory.getId().longValue() > 0);
		assertEquals(savedCategory.getName(), category.getName());
	}
	
	@Test
	void updateCategory() {
		
		long categoryId = 1L;
		Category category = repository.getOne(categoryId);
		assertEquals(category.getId().longValue(), categoryId);
		
		category.setName("Category Update Name");
		Category updatedCategory = repository.saveAndFlush(category);
		
		assertEquals(updatedCategory.getId().longValue(), category.getId().longValue());
		assertEquals(updatedCategory.getName(), category.getName());
	}
	
	@Test
	void deleteCategory() {

		long categoryId = 2L;
		Category category = repository.getOne(categoryId);
		assertEquals(category.getId().longValue(), categoryId);
		
		repository.delete(category);
		
		assertEquals(repository.findById(categoryId), Optional.empty());
	}
}

