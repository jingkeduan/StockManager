package com.qa.products.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.qa.products.domain.Products;
import com.qa.products.repo.ProductsRepo;

@Service
public class ProductsServiceDB implements ProductsInterface {

	private ProductsRepo repo;

	public ProductsServiceDB(ProductsRepo repo) {
		super();
		this.repo = repo;
	}

	@Override
	public Products create(Products x) {
		return this.repo.save(x);

	}

	@Override
	public List<Products> readAll() {
		return this.repo.findAll();
	}

	@Override
	public Products readById(Long id) {
		return this.repo.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Products update(Long id, Products y) {
		Optional<Products> a = this.repo.findById(id);
		Products newItem = a.get();
		newItem.setCategory(y.getCategory());
		newItem.setId(id);
		newItem.setName(y.getName());
		newItem.setPrice(y.getPrice());
		newItem.setQuantity(y.getQuantity());
		return this.repo.save(newItem);

	}

	@Override
	public Boolean delete(Long id) {
		Optional<Products> toDelete = this.repo.findById(id);
		if (toDelete.isPresent()) {
			this.repo.deleteById(id);
			if (!this.repo.existsById(id)) {
				return true;
			} else {
				return false;
			}
		}
		return true; // to indicate possibility of an object already been deleted before this
						// operation

	}

	@Override
	public Products remove(Long id) {
		Optional<Products> toDelete = this.repo.findById(id);
		this.repo.deleteById(id);
		return toDelete.orElse(null);
	}

}
