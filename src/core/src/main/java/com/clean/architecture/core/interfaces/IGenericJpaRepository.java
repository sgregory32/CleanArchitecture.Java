package com.clean.architecture.core.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IGenericJpaRepository<T, ID> extends JpaRepository<T, ID> {

}
