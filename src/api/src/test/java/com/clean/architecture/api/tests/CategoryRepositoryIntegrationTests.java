package com.clean.architecture.api.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.clean.architecture.core.entities.Category;
import com.clean.architecture.infrastructure.repositories.CategoryRepository;

@DataJpaTest
class CategoryRepositoryIntegrationTests {
	
	@Autowired
	CategoryRepository repository;
	
	Category category1 = TestHelper.getCategory1ComparisonData();
	Category category2 = TestHelper.getCategory2ComparisonData();
	
    @Test
    public void contextNotNull() {
        assertNotNull(repository);
    }	

	@Test
	void findByIdCategory() {
		
		Optional<Category> result = repository.findById(1L);
		assertEquals(category1.getId().longValue(), result.get().getId().longValue());
		assertEquals(category1.getName(), result.get().getName());
	}
	
	@Test
	void getOneCategory() {
		
		long categoryId = 1L;
		Category result = repository.getOne(categoryId);
		assertEquals(category1.getId().longValue(), result.getId().longValue());
		assertEquals(category1.getName(), result.getName());
	}
	
	@Test
	void findAllCategories() {
		
		List<Category> categories = repository.findAll();
		assertEquals(category1.getId().longValue(), categories.get(0).getId().longValue());
		assertEquals(category1.getName(), categories.get(0).getName());
		assertEquals(category2.getId().longValue(), categories.get(1).getId().longValue());
		assertEquals(category2.getName(), categories.get(1).getName());		
	}
	
	@Test
	void saveCategory() {
		
		Category category = new Category();
		category.setName("Category Save Name");
		Category savedCategory = repository.saveAndFlush(category);
		
		assertTrue(savedCategory.getId().longValue() > 0);
		assertEquals(category.getName(), savedCategory.getName());
	}
	
	@Test
	void updateCategory() {
		
		long categoryId = 3L;
		Category category = repository.getOne(categoryId);
		assertEquals(category.getId().longValue(), categoryId);
		
		category.setName("Category Update Name");
		Category updatedCategory = repository.saveAndFlush(category);
		
		assertEquals(category.getId().longValue(), updatedCategory.getId().longValue());
		assertEquals(category.getName(), updatedCategory.getName());
	}
	
	@Test
	void deleteCategory() {

		long categoryId = 4L;
		Category category = repository.getOne(categoryId);
		assertEquals(categoryId, category.getId().longValue());
		
		repository.delete(category);
		
		assertEquals(Optional.empty(), repository.findById(categoryId));
	}
}

