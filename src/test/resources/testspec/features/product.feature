@Product

Feature: Product API test cases different operations

  Scenario: get all the products successfully
    Given user prepared request to get all product data
    When user send a getRequest to get product endpoint with valid payload
    Then response status code should be 200
    And response should contain a valid product id