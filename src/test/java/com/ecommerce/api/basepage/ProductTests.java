package com.ecommerce.api.basepage;

import com.ecommerce.api.pojo.Product;
import com.ecommerce.api.routes.Routes;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.ecommerce.api.payloads.Payload;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;



public class ProductTests extends CommonPage {

	
	//1) Test to retrieve all products
	@Test
	public void testGetAllProducts()
	{
		given()
		
		.when()
			.get(Routes.GET_ALL_PRODUCTS)
		.then()
			.statusCode(200)
			.body("size()",greaterThan(0));
			
	}
	
	//2) Test to retrieve a single product by ID
	@Test
	public void testGetSingleProductById()
	{
		int productId=configReader.getIntProperty("productId");
		
		given()
			.pathParam("id", productId)
		
		.when()
			.get(Routes.GET_PRODUCT_BY_ID)
		.then()
			.statusCode(200)
			.log().body();
	}
	
	
	//3) Test to retrive a limited number of products
	@Test
	public void testGetLimitedProducts()
	{
		given()
			.pathParam("limit",3)
				
		.when()
			.get(Routes.GET_PRODUCTS_WITH_LIMIT)
		.then()
			.statusCode(200)
			.log().body()
			.body("size()",equalTo(3));
	}

	//4) Test to retreive products sorted in descending order
	@Test
	public void testGetSortedProducts()
	{
		Response response=given()
			.pathParam("order", "desc")
		.when()
			.get(Routes.GET_PRODUCTS_SORTED)
		.then()
			.statusCode(200)
			.extract().response();
		
		List<Integer> productIds=response.jsonPath().getList("id", Integer.class);
		 assertThat(isSortedDescending(productIds), is(true));
	}
	
	//5) Test to retreive products sorted in Ascending order
		@Test
		public void testGetSortedProductsAsc()
		{
			Response response=given()
				.pathParam("order", "asc")
			.when()
				.get(Routes.GET_PRODUCTS_SORTED)
			.then()
				.statusCode(200)
				.extract().response();
			
			List<Integer> productIds=response.jsonPath().getList("id", Integer.class);
			 assertThat(isSortedAscending(productIds), is(true));
		}
	
	//6) Test to get all product categories
		@Test
		public void testGetAllCategories()
		{
			given()
				
			.when()
				.get(Routes.GET_ALL_CATEGORIES)
			.then()
				.statusCode(200)
				.body("size()",greaterThan(0));
				
		}
	
	
	//7) Test to get products by category
		
		@Test
		public void testGetProductsByCategory()
		{
			given()
				.pathParam("category", "electronics")
			
			.when()
				.get(Routes.GET_PRODUCTS_BY_CATEGORY)
			.then()
				.statusCode(200)
				.body("size()",greaterThan(0))
				.body("category", everyItem(notNullValue()))
				.body("category", everyItem(equalTo("electronics")))
				.log().body();
				
		}
		

	//8) Test to add a new product
		@Test
		public void testAddNewProduct()
		{
			Product newProduct=Payload.productPayload();
			
			
			int productId=given()
				.contentType(ContentType.JSON)
				.body(newProduct)
				
			.when()
				.post(Routes.CREATE_PRODUCT)
			.then()
				.log().body()
				.statusCode(201)
				.body("id", notNullValue())
				.body("title", equalTo(newProduct.getTitle()))
				.extract().jsonPath().getInt("id"); //Extracting Id from response body
			
			System.out.println(productId);
			
		}
		
		//9) Test to update an existing product
		@Test
		public void testUpdateProduct()
		{
			int productId=configReader.getIntProperty("productId");
			
			Product updatedPayload=Payload.productPayload();
			
			given()
				.contentType(ContentType.JSON)
				.body(updatedPayload)
				.pathParam("id", productId)
				
			.when()
				.put(Routes.UPDATE_PRODUCT)
			.then()
				.log().body()
				.statusCode(200)
				.body("title", equalTo(updatedPayload.getTitle()));
				
		}
		
		//10) test to delete a product
		@Test
		public void testDeleteProduct()
		{
			int productId=configReader.getIntProperty("productId");
			
			given()
				.pathParam("id",productId)
			.when()
				.delete(Routes.DELETE_PRODUCT)
			.then()
				.statusCode(200);
		}
		
}











