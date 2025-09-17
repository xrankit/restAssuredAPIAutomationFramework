package testcases.day2;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.annotations.Test;
import java.util.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HttpRequestJsonLibrary {

    private int cartId;

    @Test(priority = 1)
    void testGetHttpRequestJsonLibrary(){
        given()
                .contentType("application/json")

        .when()
                .get("http://localhost:8765/carts")

                .then()
                .statusCode(200)
                .log().all();
    }

    @Test(priority = 2)
    void testPostHttpRequestJsonLibrary(){
/*
        {
            "userId": 1,
                "products": [{
                "id": 1
            }]
        }
 */

        JsonObject data = new JsonObject();
        data.addProperty("userId","1");

        JsonObject product = new JsonObject();
        product.addProperty("id",1);
        JsonArray productArray = new JsonArray();
        productArray.add(product);
        data.add("products",productArray);

        cartId = given()
                .contentType("application/json")
                .body(data.toString())

                .when()
                .post("http://localhost:8765/carts")
                .jsonPath().getInt("id");

//                .then()
//                .statusCode(201)
//                .header("Content-Type","application/json charset=utf-8")
//                .body("id",equalTo(1))
//                .body("userId",equalTo(1))
//                .body("product[0].id",equalTo(1))
//                .body("products[1].title",equalTo("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops"))
//                .log().all();
    }

    @Test(priority = 3, dependsOnMethods = {"testPostHttpRequestJsonLibrary"})
    void testPutHttpRequestJsonLibrary(){
          /*
        {
            "userId": 1,
                "products": [{
                "id": 1
            }]
        }
 */

        JsonObject data = new JsonObject();
        data.addProperty("userId",2);

        JsonObject product = new JsonObject();
        product.addProperty("id",2);
        JsonArray productArray = new JsonArray();
        productArray.add(product);
        data.add("products",productArray);


        given()
                .contentType("application/json")
                .body(data.toString())

                .when()
                .post("http://localhost:8765/carts/"+cartId)

                .then()
                .statusCode(200)
                .header("Content-Type","application/json charset=utf-8")
                .body("id",equalTo(cartId))
                .body("userId",equalTo(2))
                .body("product[0].id",equalTo(2))
                .body("products[1].title",equalTo("Mens Casual Premium Slim Fit T-Shirts"))
                .log().all();
    }

    @Test(priority = 4,dependsOnMethods = {"testPostHttpRequestJsonLibrary"})
    void testDeleteHttpRequestHashMap(){

        given()
                .contentType("application/json")

                .when()
                .delete("http://localhost:8765/carts/"+cartId)

                .then()
                .statusCode(200);

    }
}
