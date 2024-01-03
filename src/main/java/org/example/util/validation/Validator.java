package org.example.util.validation;

public interface Validator<T> {

	boolean isValidParamsForCreate(T entity);

	boolean isValidParamsForUpdate(T entity);

}
