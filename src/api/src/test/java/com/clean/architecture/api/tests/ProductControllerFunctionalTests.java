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

import com.clean.architecture.api.models.ProductDTO;
import com.google.gson.Gson;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerFunctionalTests {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	private static final String API_URL  =  "/api/products";
	private static final Gson gson = new Gson();
	
    @Test
    public void getProduct_Returns_OK() {
   	 ResponseEntity<ProductDTO> response = this.restTemplate.getForEntity(API_URL + "/1",	ProductDTO.class);
   	 Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());
   	 Assertions.assertEquals(response.getBody().getId().longValue(), 1L);
   	 Assertions.assertEquals(response.getBody().getName(), "Product1");
    }
    
    @Test
    public void getProducts_Returns_OK() {
   	 	ResponseEntity<ProductDTO[]> response = this.restTemplate.getForEntity(API_URL, ProductDTO[].class);
   	 	Assertions.assertEquals(response.getStatusCodeValue(),  HttpStatus.OK.value());
   	 	Assertions.assertEquals(response.getBody()[0].getId().longValue(), 1L);
   	 	Assertions.assertEquals(response.getBody()[0].getName(), "Product1");
   	 	Assertions.assertEquals(response.getBody()[1].getId().longValue(), 2L);
   	 	Assertions.assertEquals(response.getBody()[1].getName(), "Product2");
    }
	
	@Test
	public void getProduct_Returns_NOT_FOUND() {
		ResponseEntity<String> response = restTemplate.getForEntity(API_URL + "/-1", String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void getProduct_Returns_BAD_REQUEST() {
		ResponseEntity<String> response = restTemplate.getForEntity(API_URL + "/a", String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
    
    @Test
    public void postProduct_Returns_CREATED() {
    	
    	ProductDTO product = new ProductDTO();
    	product.setId(null);
    	product.setName("Post Product");
    	String json = gson.toJson(product);
    	
    	HttpHeaders headers = addHttpHeader();

    	HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	
    	ResponseEntity<String> response = restTemplate.postForEntity(API_URL, entity, String.class);
    	
    	Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }
    
    @Test
    public void putProduct_Returns_NO_CONTENT() {
    	
    	
    	ProductDTO product = this.restTemplate.getForObject(API_URL + "/3",	ProductDTO.class);

    	product.setName("Product Update");
    	String json = gson.toJson(product);
    	
    	HttpHeaders headers = addHttpHeader();
    	HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    	ResponseEntity<String> response = restTemplate.exchange(API_URL + "/3", HttpMethod.PUT, entity, String.class);

    	Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);    	
    }
    
    @Test
    public void deleteProduct_Returns_NO_CONTENT() {
    	ResponseEntity<String> response = restTemplate.exchange(API_URL + "/5", HttpMethod.DELETE, null, String.class);
    	Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }
    
	private HttpHeaders addHttpHeader() {
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}

