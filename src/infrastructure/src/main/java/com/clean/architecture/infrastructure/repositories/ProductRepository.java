package com.clean.architecture.infrastructure.repositories;

import org.springframework.stereotype.Repository;

import com.clean.architecture.core.entities.Product;
import com.clean.architecture.core.interfaces.IGenericJpaRepository;

@Repository
public interface ProductRepository extends IGenericJpaRepository<Product, Long>  {

}