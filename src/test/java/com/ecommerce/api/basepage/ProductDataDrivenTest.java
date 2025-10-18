package com.ecommerce.api.basepage;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.anyOf;


import java.util.Map;

import com.ecommerce.api.utils.DataProviders;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import com.ecommerce.api.pojo.Product;
import com.ecommerce.api.routes.Routes;

public class ProductDataDrivenTest extends CommonPage
{
	
	@Test(dataProvider="jsonDataProvider", dataProviderClass= DataProviders.class)
	public void testAddNewProduct(Map<String,String> data)
	{
		
		String title=data.get("title");
		double price=Double.parseDouble(data.get("price"));
		String category=data.get("category");
		String description=data.get("description");
		String image=data.get("image");
		
		//String title, double price, String description, String image, String category
		Product newProduct=new Product(title,price,description,image,category);
		
		
		int productId=given()
			.contentType(ContentType.JSON)
			.body(newProduct)
			
		.when()
			.post(Routes.CREATE_PRODUCT)
		.then()
			.log().body()
			.statusCode(anyOf(equalTo(200), equalTo(201)))
			.body("id", notNullValue())
			.body("title", equalTo(newProduct.getTitle()))
			.extract().jsonPath().getInt("id"); //Extracting Id from response body
		
		System.out.println("Product ID======> "+ productId);
		
		//Delete product
		given()
			.pathParam("id",productId)
		.when()
			.delete(Routes.DELETE_PRODUCT)
		.then()
			.statusCode(anyOf(equalTo(200), equalTo(201)));
		
		System.out.println("Deleted Product ID======> "+ productId);
	}

}