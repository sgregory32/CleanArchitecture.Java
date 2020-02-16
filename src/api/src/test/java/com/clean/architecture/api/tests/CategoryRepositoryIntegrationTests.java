package com.clean.architecture.api.tests;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.clean.architecture.core.entities.Category;
import com.clean.architecture.infrastructure.repositories.CategoryRepository;

@DataJpaTest
class CategoryRepositoryIntegrationTests {
	
	@Autowired
	CategoryRepository repository;
	
    @Test
    public void contextNotNull() {
        Assertions.assertNotNull(repository);
    }	

	@Test
	void findByIdCategory() {
		
		Optional<Category> result = repository.findById(1L);
		Assertions.assertEquals(result.get().getId().longValue(), 1L);
		Assertions.assertEquals(result.get().getName(), "Category1");
	}
	
	@Test
	void findAllCategories() {
		
		List<Category> categories = repository.findAll();
		Assertions.assertEquals(categories.get(0).getId().longValue(), 1L);
		Assertions.assertEquals(categories.get(0).getName(), "Category1");
		Assertions.assertEquals(categories.get(1).getId().longValue(), 2L);
		Assertions.assertEquals(categories.get(1).getName(), "Category2");		
	}
	
	@Test
	void saveCategory() {
		Category category = new Category();
		category.setName("Category Save Name");
		Category savedCategory = repository.saveAndFlush(category);
		
		Assertions.assertTrue(savedCategory.getId().longValue() > 0);
		Assertions.assertEquals(savedCategory.getName(), category.getName());
	}
	
	@Test
	void updateCategory() {
		Category category = new Category();
		category.setName("Category Name");
		Category savedCategory = repository.saveAndFlush(category);
		
		savedCategory.setName("Category Name Update");
		Category updatedCategory = repository.saveAndFlush(savedCategory);
		
		Assertions.assertEquals(updatedCategory.getId().longValue(), savedCategory.getId().longValue());
		Assertions.assertEquals(updatedCategory.getName(), savedCategory.getName());
	}
	
	@Test
	void deleteCategory() {
		Category category = new Category();
		category.setName("Category Save Name");
		Category savedCategory = repository.saveAndFlush(category);
		long categoryId = savedCategory.getId();
		
		repository.delete(savedCategory);
		
		Assertions.assertEquals(repository.findById(categoryId), Optional.empty());
	}

}

