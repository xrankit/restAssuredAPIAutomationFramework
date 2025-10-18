@Product

Feature: Product API test cases different operations

  Scenario: Verify that user can get all the products successfully
    Given user prepared request to get all product data
    When user send a getRequest to get product endpoint with valid payload
    Then validate the complete API response for getAllProducts

  Scenario: Verify that user can retrieve a single product by valid ID
    Given user prepared request to get single product by id
    When user sends get request to get product by id endpoint
    Then response status code should be 200 with valid product details

  Scenario: Verify that user can retrieve a limited number of products
    Given user prepared request to get limited number of products
    When user sends get request to get products with limit
    Then validate the status code should be 200 with body size be 3
