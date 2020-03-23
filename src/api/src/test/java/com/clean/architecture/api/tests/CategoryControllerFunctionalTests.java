package com.clean.architecture.api.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.clean.architecture.api.models.CategoryDTO;
import com.clean.architecture.core.entities.Category;
import com.google.gson.Gson;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CategoryControllerFunctionalTests {

	@Autowired
	private TestRestTemplate restTemplate;
	
	private static final String API_URL  =  "/api/categories";
	private static final Gson gson = new Gson();
	
	Category category1 = TestHelper.getCategory1ComparisonData();
	Category category2 = TestHelper.getCategory2ComparisonData();
	
    @Test
    public void getCategory_Returns_OK() {
    	
   	 ResponseEntity<CategoryDTO> response = this.restTemplate.getForEntity(API_URL + "/1",	CategoryDTO.class);
   	 
   	 assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
   	 assertEquals(category1.getId().longValue(), response.getBody().getId().longValue());
   	 assertEquals(category1.getName(), response.getBody().getName());
    }
    
    @Test
    public void getCategories_Returns_OK() {
    	
   	 	ResponseEntity<CategoryDTO[]> response = this.restTemplate.getForEntity(API_URL, CategoryDTO[].class);
   	 	
   	 	assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
   	 	assertEquals(category1.getId().longValue(), response.getBody()[0].getId().longValue());
   	 	assertEquals(category1.getName(), response.getBody()[0].getName());
   	 	assertEquals(category2.getId().longValue(), response.getBody()[1].getId().longValue());
   	 	assertEquals(category2.getName(), response.getBody()[1].getName());
    }
	
	@Test
	public void getCategory_Returns_NOT_FOUND() {
		
		ResponseEntity<String> response = restTemplate.getForEntity(API_URL + "/-1", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void getCategory_Returns_BAD_REQUEST() {
		
		ResponseEntity<String> response = restTemplate.getForEntity(API_URL + "/a", String.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
    
    @Test
    public void postCategory_Returns_CREATED() {
    	
    	CategoryDTO category = new CategoryDTO();
    	category.setId(null);
    	category.setName("Post Category");
    	
    	String json = gson.toJson(category);
    	
    	HttpHeaders headers =  TestHelper.addHttpHeaders();

    	HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	
    	ResponseEntity<String> response = restTemplate.postForEntity(API_URL, entity, String.class);
    	
    	assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    
    @Test
    public void putCategory_Returns_NO_CONTENT() {
    	
    	CategoryDTO category = this.restTemplate.getForObject(API_URL + "/3",	CategoryDTO.class);
    	category.setName("Category Update");
    	
    	String json = gson.toJson(category);
    	
    	HttpHeaders headers = TestHelper.addHttpHeaders();
    	HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	ResponseEntity<String> response = restTemplate.exchange(API_URL + "/3", HttpMethod.PUT, entity, String.class);

    	assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    
    @Test
    public void deleteCategory_Returns_NO_CONTENT() {
    	
    	ResponseEntity<String> response = restTemplate.exchange(API_URL + "/4", HttpMethod.DELETE, null, String.class);
    	
    	assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
