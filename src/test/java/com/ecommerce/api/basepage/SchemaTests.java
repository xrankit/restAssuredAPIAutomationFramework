package com.ecommerce.api.basepage;

import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;
import com.ecommerce.api.routes.Routes;

public class SchemaTests extends CommonPage {

	
	@Test
	public void testProductSchema()
	{
		int productId=configReader.getIntProperty("productId");
		
		given()
			.pathParam("id", productId)
		
		.when()
			.get(Routes.GET_PRODUCT_BY_ID)
		.then()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("test-spec/schema/productSchema.json"));
	}
	
	
	@Test
	 public void testCartSchema() {
    	int cartId = configReader.getIntProperty("cartId");
        given()
            .pathParam("id", cartId)
            .when()
                .get(Routes.GET_CART_BY_ID)
            .then()
            	.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("test-spec/schema/cartSchema.json"));
    }
	
	@Test
	public void testUserSchema()
	{
		int userId=configReader.getIntProperty("userId");
		given()
			.pathParam("id",userId)
		.when()
			.get(Routes.GET_USER_BY_ID)
		.then()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("test-spec/schema/userSchema.json"));
		
	}
	
	
	
}
