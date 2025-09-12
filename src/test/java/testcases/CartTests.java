package testcases;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;
import pojo.Cart;
import routes.Routes;

public class CartTests extends BaseClass{

	 	@Test
	    public void testGetAllCarts() {
	        given()
	            .when()
	                .get(Routes.GET_ALL_CARTS)
	            .then()
	                .statusCode(200)
	                .body("size()", greaterThan(0)); // Validate that the response is not empty
	    }
	
	 	@Test
	 	 public void testGetCartById() {
	     	int cartId = configReader.getIntProperty("cartId");
	         given()
	             .pathParam("id", cartId)
	             .when()
	                 .get(Routes.GET_CART_BY_ID)
	             .then()
	                 .statusCode(200)
	                 .log().body()
	                 .body("id", equalTo(cartId)); // Validate that the response contains the correct cart ID
	     }
	 	 
	 	@Test
	    public void testGetCartsByDateRange() {
	     
	    	 String startDate = configReader.getProperty("startdate");
	    	 String endDate = configReader.getProperty("enddate");
	    	    
	        Response response=given()
	            .pathParam("startdate", startDate)
	            .pathParam("enddate", endDate)
	            .when()
	                .get(Routes.GET_CARTS_BY_DATE_RANGE)
	            .then()
	                .statusCode(200)
	                .body("size()", greaterThan(0)) // Validate that the response is not empty
	                .extract().response();
	        
	     // Extract the list of cart dates
	        List<String> cartDates = response.jsonPath().getList("date");

	        // Validate cart dates
	        //validateCartDatesWithinRange(cartDates, startDate, endDate);
	        
	        assertThat(validateCartDatesWithinRange(cartDates, startDate, endDate), is(true));
	        
	    }
	    
	   @Test
	    public void testGetUserCart() {
	        int userId = configReader.getIntProperty("userId");
	        
	        given()
	            .pathParam("userId", userId)
	            .when()
	                .get(Routes.GET_USER_CART)
	            .then()
	                .statusCode(200)
	                .body("userId", everyItem(equalTo(userId))); // Validate that the response contains the correct user ID
	    }
	    
	    
	    @Test
	    public void testGetCartsWithLimit() {
	        int limit = configReader.getIntProperty("limit");
	        given()
	            .pathParam("limit", limit)
	            .when()
	                .get(Routes.GET_CARTS_WITH_LIMIT)
	            .then()
	                .statusCode(200)
	                .body("size()", lessThanOrEqualTo(limit)); // Validate that the response size is within the limit
	    }

	   @Test
	    public void testGetCartsSorted() {
	    	Response response = given()
	            .pathParam("order", "desc")
	            .when()
	                .get(Routes.GET_CARTS_SORTED)
	            .then()
	                .statusCode(200)
	                .body("size()", greaterThan(0)) // Validate that the response is not empty
	                .extract().response();
	         
	         // Parse response to get product IDs
	         List<Integer> cartIds = response.jsonPath().getList("id", Integer.class);

	         // Validate IDs are sorted in ascending  order(calling helper method)
	         assertThat(isSortedDesceding(cartIds), is(true));
	    }  
	    
	   @Test
	    public void testGetCartsSortedAsc() {
	    	Response response = given()
	            .pathParam("order", "asc")
	            .when()
	                .get(Routes.GET_CARTS_SORTED)
	            .then()
	                .statusCode(200)
	                .body("size()", greaterThan(0)) // Validate that the response is not empty
	                .extract().response();
	         
	         // Parse response to get product IDs
	         List<Integer> cartIds = response.jsonPath().getList("id", Integer.class);

	         // Validate IDs are sorted in ascending  order(calling helper method)
	         assertThat(isSortedAsceding(cartIds), is(true));
	    }  
	    
	    @Test
	    public void testCreateCart() {
	        
	    	//Cart newCart=Payload.cartPayload(1); //Passing userId is 1
	    	
	    	int userId = configReader.getIntProperty("userId");
	    	Cart newCart=Payload.cartPayload(userId); //Passing userId is 1
	    	
	    	
	    	given()
	            .contentType(ContentType.JSON)
	            .body(newCart)
	            .when()
	                .post(Routes.CREATE_CART)
	            .then()
	                .statusCode(201)
	                .log().body()
	                .body("id", notNullValue()) // Validate that the cart ID in response is not null
	                .body("userId", notNullValue()) // Validate that the user ID in response is not null
	    			.body("products.size()", greaterThan(0));
	          }

	   @Test
	    public void testUpdateCart() {
	        
	    	int userId = configReader.getIntProperty("userId");
	    	int cartId = configReader.getIntProperty("cartId");
	    	
	    	Cart updateCart=Payload.cartPayload(userId); //userId passing
	    	given()
	            .pathParam("id", cartId)
	            .contentType(ContentType.JSON)
	            .body(updateCart)
	            .when()
	                .put(Routes.UPDATE_CART)
	            .then()
	                .statusCode(200)
	                .body("id", equalTo(cartId)) // Validate that the response contains the correct cart ID
	                .body("userId", notNullValue())
	    			.body("products.size()", equalTo(1));
	    }
	    
	   @Test
	    public void testDeleteCart() {
	    	int cartId = configReader.getIntProperty("cartId");
	        given()
	            .pathParam("id", cartId)
	            .when()
	                .delete(Routes.DELETE_CART)
	            .then()
	                .statusCode(200); // Validate that the response status code is 204 (No Content)
	    }
	 	 	
}



