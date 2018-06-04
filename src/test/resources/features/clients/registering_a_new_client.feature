Feature: Registering a new client

  New clients are given a portfolio with $1000 to start with.

  Scenario Outline: New traders need to provide their full name and email address
    Given a trader with the following details:
      | firstName   | lastName   | email   |
      | <firstName> | <lastName> | <email> |
    When the trader attempts to register with BDD Trader
    Then the registration should be rejected with the message <message>
    Examples:
      | firstName | lastName | email         | message                                        |
      | Joe       |          | joe@smith.com | Missing mandatory fields for client: lastName  |
      |           | Smith    | joe@smith.com | Missing mandatory fields for client: firstName |
      | Joe       | Smith    |               | Missing mandatory fields for client: email     |