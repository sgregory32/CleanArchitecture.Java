package com.clean.architecture.api.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductCategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	
	@JsonIgnore
	private Long categoryId;
	private CategoryDTO category;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public CategoryDTO getCategory() {
		return category;
	}
	
	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

}
