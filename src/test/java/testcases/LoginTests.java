package testcases;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import payloads.Payload;
import pojo.Login;
import routes.Routes;

public class LoginTests extends BaseClass{

	@Test
	public void testInvalidUserLogin() {
		
		Login newLogin=Payload.loginPayload();
		
		given()
			.contentType(ContentType.JSON)
			.body(newLogin)
		.when()
			.post(Routes.AUTH_LOGIN)
		.then() 
			.log().body()
			.statusCode(401) // Expecting 401 for unauthorized access
			.body(equalTo("username or password is incorrect")); //validate the message in the response body
		
	}
	
	@Test
	public void testValidUserLogin() {
		
    	//Getting valid credentials from config.prperties file
    	String username = configReader.getProperty("username");
      	String password = configReader.getProperty("password");
		
      	Login newLogin=new Login(username,password);
		
		given()
			.contentType(ContentType.JSON)
			.body(newLogin)
		.when()
			.post(Routes.AUTH_LOGIN)
		.then() 
			.log().body()
			.statusCode(201) // Expecting 401 for unauthorized access
			.body("token", notNullValue()); // Validate the response token should be null
		
	}
}















