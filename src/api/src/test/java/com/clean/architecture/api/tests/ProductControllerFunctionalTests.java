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

import com.clean.architecture.api.models.ProductCategoryDTO;
import com.clean.architecture.api.models.ProductDTO;
import com.clean.architecture.core.entities.Product;
import com.google.gson.Gson;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductControllerFunctionalTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private static final String API_URL = "/api/products";
	private static final Gson gson = new Gson();

	private Product product1 = TestHelper.getProduct1ComparisonData();
	private Product product2 = TestHelper.getProduct2ComparisonData();

	@Test
	public void getProduct_Returns_OK() {
		
		ResponseEntity<ProductCategoryDTO> response = this.restTemplate.getForEntity(API_URL + "/1", ProductCategoryDTO.class);
		
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertEquals(product1.getId().longValue(), response.getBody().getId().longValue());
		assertEquals(product1.getName(), response.getBody().getName());
		assertEquals(product1.getDescription(), response.getBody().getDescription());
		assertEquals(product1.getCategory().getId().longValue(), response.getBody().getCategory().getId().longValue());
	}

	@Test
	public void getProducts_Returns_OK() {
		
		ResponseEntity<ProductCategoryDTO[]> response = this.restTemplate.getForEntity(API_URL, ProductCategoryDTO[].class);
		
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertEquals(product1.getId().longValue(), response.getBody()[0].getId().longValue());
		assertEquals(product1.getName(), response.getBody()[0].getName());
		assertEquals(product1.getDescription(), response.getBody()[0].getDescription());
		assertEquals(product1.getCategory().getId().longValue(), response.getBody()[0].getCategory().getId().longValue());
		assertEquals(product2.getId().longValue(), response.getBody()[1].getId().longValue());
		assertEquals(product2.getName(), response.getBody()[1].getName());
		assertEquals(product2.getDescription(), response.getBody()[1].getDescription());
		assertEquals(product2.getCategory().getId().longValue(), response.getBody()[1].getCategory().getId().longValue());
	}

	@Test
	public void getProduct_Returns_NOT_FOUND() {
		
		ResponseEntity<String> response = restTemplate.getForEntity(API_URL + "/-1", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void getProduct_Returns_BAD_REQUEST() {
		
		ResponseEntity<String> response = restTemplate.getForEntity(API_URL + "/a", String.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void postProduct_Returns_CREATED() {

		ProductDTO product = new ProductDTO();
		product.setId(null);
		product.setName("Post Product");
		product.setDescription("Post Description");
		product.setCategoryId(1L);
		
		String json = gson.toJson(product);

		HttpHeaders headers = TestHelper.addHttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(API_URL, entity, String.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void putProduct_Returns_NO_CONTENT() {

		ProductDTO product = this.restTemplate.getForObject(API_URL + "/3", ProductDTO.class);

		product.setName("Product Update");
		product.setDescription("Put Description");
		product.setCategoryId(1L);
		
		String json = gson.toJson(product);

		HttpHeaders headers = TestHelper.addHttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(API_URL + "/3", HttpMethod.PUT, entity, String.class);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	public void deleteProduct_Returns_NO_CONTENT() {
		
		ResponseEntity<String> response = restTemplate.exchange(API_URL + "/5", HttpMethod.DELETE, null, String.class);
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
}
