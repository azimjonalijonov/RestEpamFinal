package org.example.interfaces;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BaseDAO<T> {

	List<T> readAll();

	T readById(Long id);

	T createOrUpdate(T entity);

	boolean deleteById(Long id);

	boolean existById(Long id);

}
