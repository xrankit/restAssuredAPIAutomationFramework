package com.ecommerce.api.driver.stepdefinition;

import com.ecommerce.api.driver.implementation.ProductPage;
import io.cucumber.java.en.*;

public class ProductStepDefPage {

    ProductPage productPage = new ProductPage();

    @Given("^user prepared request to get product data$")
    public void userPreparedRequestToGetProductData() {
        
    }

    @When("user send a getRequest to get product endpoint with valid payload")
    public void userSendAGetRequestToGetProductEndpointWithValidPayload() {
    }

    @Then("response status code should be {int}")
    public void responseStatusCodeShouldBe(int statusCode) {

    }

    @And("response should contain a valid product id")
    public void responseShouldContainAValidProductId() {
        
    }
}
