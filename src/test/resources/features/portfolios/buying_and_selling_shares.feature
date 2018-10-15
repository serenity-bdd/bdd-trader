Feature: Buying and selling shares

  In order to make my investments grow
  As a trader
  I want to be able to buy and sell shares to make a profit

  All traders start with $1000 in cash in their portfolio
  CASH amounts are recorded in cents, so 50000 represents $500

  Key capabilities include the ability to buy and sell shares, e.g.

  {Scenario} Buying and selling shares

  Scenario: Buying shares

    Given Tom Smith is a registered trader
    When he purchases 5 AAPL shares at $100 each
    Then he should have the following positions:
      | securityCode | amount |
      | CASH         | 50000  |
      | AAPL         | 5      |

  Scenario: Buying and selling shares
    Given Tom Smith is a registered trader
    When he purchases 5 AAPL shares at $100 each
    And he sells 3 AAPL shares for $150 each
    Then he should have the following positions:
      | securityCode | amount |
      | CASH         | 95000  |
      | AAPL         | 2      |
