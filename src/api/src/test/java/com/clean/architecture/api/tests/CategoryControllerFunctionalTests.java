package com.clean.architecture.api.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.clean.architecture.api.models.CategoryDTO;
import com.google.gson.Gson;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoryControllerFunctionalTests {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	private static final String API_URL  =  "/categories";
	private static final Gson gson = new Gson();
	
//    @Test
//    void optionsForAllow() {
//    	Set<HttpMethod> httpMethods = restTemplate.optionsForAllow(API_URL + "/1");
//    	List<HttpMethod> expectedHttpMethods = new ArrayList<HttpMethod>();
//    	expectedHttpMethods.add(HttpMethod.GET);
//    	expectedHttpMethods.add(HttpMethod.PUT);
//    	expectedHttpMethods.add(HttpMethod.DELETE);
//    	Assertions.assertTrue(httpMethods.containsAll(expectedHttpMethods));
//    }
	
    @Test
    public void getCategory_Returns_OK() {
   	 ResponseEntity<CategoryDTO> response = this.restTemplate.getForEntity(API_URL + "/1",	CategoryDTO.class);
   	 Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());
   	 Assertions.assertEquals(response.getBody().getId().longValue(), 1L);
   	 Assertions.assertEquals(response.getBody().getName(), "Category1");
    }
    
    @Test
    public void getCategories_Returns_OK() {
   	 	ResponseEntity<CategoryDTO[]> response = this.restTemplate.getForEntity(API_URL, CategoryDTO[].class);
   	 	Assertions.assertEquals(response.getStatusCodeValue(),  HttpStatus.OK.value());
   	 	Assertions.assertEquals(response.getBody()[0].getId().longValue(), 1L);
   	 	Assertions.assertEquals(response.getBody()[0].getName(), "Category1");
   	 	Assertions.assertEquals(response.getBody()[1].getId().longValue(), 2L);
   	 	Assertions.assertEquals(response.getBody()[1].getName(), "Category2");
    }
	
	@Test
	public void getCategory_Returns_NOT_FOUND() {
		ResponseEntity<String> response = restTemplate.getForEntity(API_URL + "/-1", String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void getCategory_Returns_BAD_REQUEST() {
		ResponseEntity<String> response = restTemplate.getForEntity(API_URL + "/a", String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
    
    @Test
    public void postCategory_Returns_CREATED() {
    	
    	CategoryDTO category = new CategoryDTO();
    	category.setId(null);
    	category.setName("Post Category");
    	String json = gson.toJson(category);
    	
    	HttpHeaders headers = addHttpHeader();

    	HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	
    	ResponseEntity<String> response = restTemplate.postForEntity(API_URL, entity, String.class);
    	
    	Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }
    
    @Test
    public void putCategory_Returns_NO_CONTENT() {
    	
    	CategoryDTO category = this.restTemplate.getForObject(API_URL + "/3",	CategoryDTO.class);

    	category.setName("Category Update");
    	String json = gson.toJson(category);
    	
    	HttpHeaders headers = addHttpHeader();
    	HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	ResponseEntity<String> response = restTemplate.exchange(API_URL + "/3", HttpMethod.PUT, entity, String.class);

    	Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }
    
    @Test
    public void deleteCategory_Returns_NO_CONTENT() {
    	ResponseEntity<String> response = restTemplate.exchange(API_URL + "/4", HttpMethod.DELETE, null, String.class);
    	Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }
    
	private HttpHeaders addHttpHeader() {
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
