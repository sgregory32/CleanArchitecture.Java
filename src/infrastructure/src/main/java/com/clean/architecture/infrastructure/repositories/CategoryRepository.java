package com.clean.architecture.infrastructure.repositories;

import org.springframework.stereotype.Repository;

import com.clean.architecture.core.entities.Category;
import com.clean.architecture.core.interfaces.IGenericJpaRepository;

@Repository
public interface CategoryRepository extends IGenericJpaRepository<Category, Long> {

}