package com.qa.products.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.products.domain.Products;

@Repository
public interface ProductsRepo extends JpaRepository<Products,Long>{
	
	

}