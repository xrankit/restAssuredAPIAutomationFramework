package com.ecommerce.api.basepage;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.testng.annotations.Test;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.anyOf;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.ecommerce.api.payloads.Payload;
import com.ecommerce.api.pojo.User;
import com.ecommerce.api.routes.Routes;


public class UserTests extends BasePage
{

	//1) Fetch all the users
	@Test
	public void testGetAllUsers()
	{
		given()
		.when()
			.get(Routes.GET_ALL_USERS)
		.then()
			.statusCode(200)
			.log().body()
			.contentType(ContentType.JSON)
			.body("size()",greaterThan(0));
		
	}
	
	//2)  Test to fetch a specific user by ID
	@Test
	public void testGetUserById()
	{
		int userId=configReader.getIntProperty("userId");
		given()
			.pathParam("id",userId)
		.when()
			.get(Routes.GET_USER_BY_ID)
		.then()
			.log().body()
			.statusCode(200);
	}
	
	
	//3) Test to fetch a limited number of users
	@Test
	public void testGetUsersWithLimit()
	{
		int limit=configReader.getIntProperty("limit");
		given()
			.pathParam("limit",limit)
		.when()
			.get(Routes.GET_USERS_WITH_LIMIT)
		.then()
			.statusCode(200)
			.log().body()
			.body("size()",equalTo(limit));
		
	}
	
	//4) Test to fetch users sorted in descending order
	@Test
	void testGetUsersSorted()
	{
		Response response=given()
			.pathParam("order", "desc")
		.when()
			.get(Routes.GET_USERS_SORTED)
		.then()
			.statusCode(200)
			.extract().response();
	
		List<Integer> userIds=response.jsonPath().getList("id", Integer.class);
		
		
		assertThat(isSortedDesceding(userIds), is(true));
	}
	
	//5) Test to fetch users sorted in ascending order
	@Test
	void testGetUsersSortedAsc()
	{
		Response response=given()
			.pathParam("order", "asc")
		.when()
			.get(Routes.GET_USERS_SORTED)
		.then()
			.statusCode(200)
			.extract().response();
	
		List<Integer> userIds=response.jsonPath().getList("id", Integer.class);
		
		
		assertThat(isSortedAsceding(userIds), is(true));
	}
	
	
	
	//6) Test to create a new user
	
	@Test
	public void testCreateUser()
	{
		User newUser=Payload.userPayload();
				
		int id=given()
			.contentType(ContentType.JSON)
			.body(newUser)
		.when()
			.post(Routes.CREATE_USER)
		.then() 
			.log().body()
			//.statusCode(200)
				.statusCode(anyOf(equalTo(200), equalTo(201)))
			.body("id", notNullValue())
			.extract().jsonPath().getInt("id");
		
		System.out.println("Generated UserID=====:"+ id);

	}
	
	//7) Test to update user
	
	@Test
	public void testUpdateUser()
	{
		int userId=configReader.getIntProperty("userId");
		
		User updateUser=Payload.userPayload();
				
		given()
			.contentType(ContentType.JSON)
			.pathParam("id", userId)
			.body(updateUser)
		.when()
			.put(Routes.UPDATE_USER)
		.then() 
			.log().body()
			.statusCode(200)
			.body("username",equalTo(updateUser.getUsername()));
				
	}
	
	//8) delete user
	
	@Test
	void testDeleteUser()
	{

		int userId=configReader.getIntProperty("userId");
		
		given()
			.pathParam("id", userId)
		.when()
			.delete(Routes.DELETE_USER)
		.then()
			.statusCode(200);
		}
	
	
	
	
}
