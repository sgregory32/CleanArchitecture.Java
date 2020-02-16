package com.clean.architecture.core.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface IGenericService<T> {
	public ResponseEntity<List<T>> getAll();
	public ResponseEntity<T> getById(Long id);
	public ResponseEntity<String> save(String json);
	public ResponseEntity<String> update(String json);
	public ResponseEntity<String> delete(Long id);
}
