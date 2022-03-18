package com.qa.products.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.products.domain.Products;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts= {"classpath:products-schema.sql","classpath:products-data.sql"},executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class ProductsControllerTest {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private ObjectMapper map;
	
	@Test
	void testCreate() throws Exception {
		Products create=new Products("Banana","Fruits",100,1.50);
		String createJSON=this.map.writeValueAsString(create);
		RequestBuilder mockRequest=post("/create").contentType(MediaType.APPLICATION_JSON).content(createJSON);
		Products saved=new Products(3L,"Banana","Fruits",100,1.50);
		String savedJSON=this.map.writeValueAsString(saved);
		ResultMatcher matchStatus=status().isCreated();
		ResultMatcher matchBody=content().json(savedJSON);
		this.mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
	}
	
	@Test
	void testReadById() throws Exception {
		Long id=1L;
		RequestBuilder mockRequest=get("/readById/"+id);
		Products read=new Products(1L,"Apple","Fruits",200,1.75);
		String savedJSON=this.map.writeValueAsString(read);
		ResultMatcher matchStatus=status().isOk();
		ResultMatcher matchBody=content().json(savedJSON);
		this.mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
		
	}
	
	@Test
	void testRealAll() throws Exception {
		RequestBuilder mockRequest=get("/readAll");
		
		Products a1=new Products(1L,"Apple","Fruits",200,1.75);
		Products a2=new Products(2L,"Grapes","Fruits",200,2.50);
		List<Products> read=List.of(a1,a2);
		
		String savedJSON=this.map.writeValueAsString(read);
		ResultMatcher matchStatus=status().isOk();
		ResultMatcher matchBody=content().json(savedJSON);
		this.mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
		
	}
	
	@Test
	void testUpdate() throws Exception {
		Long id=2L;
		Products update=new Products("Watermelon","Fruits",50,3.50);
		String updateJSON=this.map.writeValueAsString(update);
		
		RequestBuilder mockRequest=put("/update/"+id).contentType(MediaType.APPLICATION_JSON).content(updateJSON);
		Products saved=new Products(2L,"Watermelon","Fruits",50,3.50);
		String savedJSON=this.map.writeValueAsString(saved);
		ResultMatcher matchStatus=status().isFound();
		ResultMatcher matchBody=content().json(savedJSON);
		this.mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
	}
	
	@Test
	void testDelete() throws Exception{
		Long id=1L;
		RequestBuilder mockRequest=delete("/delete/?id="+id);
		ResultMatcher matchStatus=status().isNoContent();
		ResultMatcher matchBody=content().string("true");
		this.mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
	}
	
	@Test
	void testRemove() throws Exception{
		Long id=2L;
		
		RequestBuilder mockRequest=delete("/remove/?id="+id);
		
		Products removed=new Products(2L,"Grapes","Fruits",200,2.50);
		String removedJSON=this.map.writeValueAsString(removed);
		ResultMatcher matchStatus=status().isAccepted();
		ResultMatcher matchBody=content().json(removedJSON);
		this.mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
	}

}
