Feature: Sample Test for Spring Boot API

  Background:
    * url 'http://localhost:5000'

  Scenario: Health check endpoint
    Given path '/health'
    When method get
    Then status 200
    And match response == 'I am healthy'

  Scenario: Fetch all orders
    Given path '/orders'
    When method get
    Then status 200
    And match response contains [{"id":809,"name":"headset","quantity":1,"price":1799},{"id":58,"name":"Book","quantity":4,"price":2000},{"id":101,"name":"Mobile","quantity":1,"price":30000},{"id":205,"name":"Laptop","quantity":1,"price":150000}]
