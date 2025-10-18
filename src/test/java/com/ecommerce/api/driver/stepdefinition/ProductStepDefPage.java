package com.ecommerce.api.driver.stepdefinition;

import com.ecommerce.api.driver.implementation.ProductPage;
import io.cucumber.java.en.*;

public class ProductStepDefPage {

    ProductPage productPage = new ProductPage();

    @Given("user prepared request to get all product data")
    public void userPreparedRequestToGetAllProductData() {
        productPage.prepareRequestToGetAllProductData();
    }

    @When("user send a getRequest to get product endpoint with valid payload")
    public void userSendAGetRequestToGetProductEndpointWithValidPayload() {
        productPage.sendGetRequestToGetAllProductEndpoint();
    }

    @Then("validate the complete API response for getAllProducts")
    public void validateCompleteApiResponse() {
        productPage.validateResponseEndToEnd();
    }
}
