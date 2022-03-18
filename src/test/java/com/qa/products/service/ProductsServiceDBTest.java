package com.qa.products.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.products.domain.Products;
import com.qa.products.repo.ProductsRepo;

@SpringBootTest
@ActiveProfiles("test")
public class ProductsServiceDBTest {

	@Autowired
	private ProductsServiceDB service;

	@MockBean
	private ProductsRepo repo;
	
	private Products a1;
	private Optional<Products> a1o;

	private List<Products> returnList;
	private Products a1r;
	private Products a2r;
	
	@BeforeEach
	void setUp() {
		a1 = new Products("Apple", "Fruits", 200, 1.75);

		a1r = new Products(1L, "Apple", "Fruits", 200, 1.75);
		a2r = new Products(2L, "Grapes", "Fruits", 200, 2.50);
		returnList = List.of(a1r, a2r);

		a1o = Optional.of(a1r);
	}

	@Test
	void testCreate() {
		Mockito.when(this.repo.save(this.a1)).thenReturn(this.a1r);
		assertThat(this.service.create(this.a1)).isEqualTo(this.a1r);
		Mockito.verify(this.repo, Mockito.times(1)).save(this.a1);
	}

	@Test
	void testReadById() {
		final Long id = 1L;
		Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(this.a1r));
		assertThat(this.service.readById(id)).isEqualTo(this.a1r);
		Mockito.verify(this.repo, Mockito.times(1)).findById(id);
	}

	@Test
	void testReadAll() {
		Mockito.when(this.repo.findAll()).thenReturn(this.returnList);
		assertThat(this.service.readAll()).isEqualTo(this.returnList);
		Mockito.verify(this.repo, Mockito.times(1)).findAll();
	}

	@Test
	void testUpdate() {
		final Long id = 1L;
		Products updated = new Products(id, this.a1.getName(), this.a1.getCategory(), this.a1.getQuantity(),
				this.a1.getPrice());

		Mockito.when(this.repo.findById(id)).thenReturn(a1o);
		Mockito.when(this.repo.save(updated)).thenReturn(updated);
		assertThat(this.service.update(id, this.a1)).isEqualTo(updated);
		Mockito.verify(this.repo, Mockito.times(1)).findById(id);
		Mockito.verify(this.repo, Mockito.times(1)).save(updated);
	}

	@Test
	void testDelete() {
		final Long id = 1L;
		final boolean deleted = true;

		Mockito.when(this.repo.findById(id)).thenReturn(a1o);
		Mockito.when(this.repo.existsById(id)).thenReturn(!deleted);

		assertThat(this.service.delete(id)).isEqualTo(deleted);
		Mockito.verify(this.repo, Mockito.times(1)).findById(id);
		Mockito.verify(this.repo, Mockito.times(1)).deleteById(id);
	}

	@Test
	void testRemove() {
		final Long id = 1L;
		Mockito.when(this.repo.findById(id)).thenReturn(a1o);

		assertThat(this.service.remove(id)).isEqualTo(a1r);
		Mockito.verify(this.repo, Mockito.times(1)).findById(id);
		Mockito.verify(this.repo, Mockito.times(1)).deleteById(id);
	}

}
