Feature: Registering a new client

  New clients are given a portfolio with $1000 to start with.

  Name and email is mandartory, e.g:

  {Examples} New clients need to provide their full name and email address

  Scenario: New clients are given a portfolio with $1000 to start with
    Given a trader with the following details:
      | firstName | lastName | email          |
      | Jake      | Smith    | jack@smith.com |
    When the trader registers with BDD Trader
    Then the trader should have a portfolio with $1000 in cash

  Scenario Outline: New clients need to provide their full name and email address
    Given a trader with the following details:
      | firstName   | lastName   | email   |
      | <firstName> | <lastName> | <email> |
    When the trader attempts to register with BDD Trader
    Then the registration should be rejected with the message '<message>'
    Examples:
      | firstName | lastName | email         | message                                        |
      | Joe       |          | joe@smith.com | Missing mandatory fields for client: lastName  |
      |           | Smith    | joe@smith.com | Missing mandatory fields for client: firstName |
      | Joe       | Smith    |               | Missing mandatory fields for client: email     |