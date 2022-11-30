Feature: Create Client

  A new client need to be created for each portfolio.

  Rule: A new client must have a valid name and email
    @api
    Scenario Outline: Client needs to provide all the mandatory fields
      Given no client exists for with email of "<email>"
      When a new client is created with the following details:
        | email   | firstName   | lastName   |
        | <email> | <firstName> | <lastName> |
      Then the client should be created or not: <Client can be created>
      Examples:
        | email             | firstName | lastName | Client can be created |
        | homer@simpson.com | Homer     | Simpson  | Yes                   |
        | homer@simpson.com | Homer     |          | No                    |
        | homer@simpson.com |           | Simpson  | No                    |
        |                   | Homer     | Simpson  | No                    |
