 package com.clean.architecture.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name="Id", nullable=false)
	private Long id;
    
    @Column(name="Name", length=50, nullable=false, unique=false)    
	private String name;
	
    @Column(name="Description", length=50, nullable=true, unique=false)	
	private String description;
     
    @ManyToOne
    @JoinColumn(name = "CategoryId")
    private Category category;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
