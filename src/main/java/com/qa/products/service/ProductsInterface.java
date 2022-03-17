package com.qa.products.service;

import java.util.List;

import com.qa.products.domain.Products;

public interface ProductsInterface{
	
	Products create(Products x);
	
	List<Products> readAll();
	
	Products readById(Long id);
	
	Products update(Long id, Products y);
	
	Boolean delete(Long id);
	
	Products remove(Long id);

}
