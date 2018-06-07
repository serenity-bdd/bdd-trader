Feature: Viewing positions
  In order to understand how my investments are doing
  As a trader
  I want to be able to see the profits and losses for my investments

  All traders start with $1000 in cash in their portfolio
  CASH amounts are recorded in cents, so 50000 represents $500

  Background:
    Given the following market prices:
      | securityCode | price |
      | SNAP         | 200   |
      | IBM          | 60    |

  Scenario: Making a profit on a single share
    Given Sarah Smith is a registered trader
    When Sarah has purchased 5 SNAP shares at $100 each
    Then she should have the following positions:
      | securityCode | amount | totalValueInDollars | profit |
      | CASH         | 50000  | 500.00              | 0.00   |
      | SNAP         | 5      | 1000.00             | 500.00 |

  Scenario: Making profits on multiple shares
    Given Sarah Smith is a registered trader
    When Sarah has purchased 5 SNAP shares at $100 each
    And Sarah has purchased 10 IBM shares at $50 each
    Then she should have the following positions:
      | securityCode | amount | totalValueInDollars | profit |
      | CASH         | 0      | 0.00                | 0.00   |
      | SNAP         | 5      | 1000.00             | 500.00 |
      | IBM          | 10     | 600.00              | 100.00 |
    And the overall profit should be $600

  Scenario: Making losses a single share
    Given Sarah Smith is a registered trader
    When Sarah has purchased 2 SNAP shares at $300 each
    Then she should have the following positions:
      | securityCode | amount | totalValueInDollars | profit  |
      | CASH         | 40000  | 400.00              | 0.00    |
      | SNAP         | 2      | 400.00              | -200.00 |

  Scenario: Making profits and losses across multiple shares
    Given Sarah Smith is a registered trader
    When Sarah has purchased 2 SNAP shares at $300 each
    And she has purchased 5 IBM shares at $50 each
    Then she should have the following positions:
      | securityCode | amount | totalValueInDollars | profit  |
      | CASH         | 15000  | 150.00              | 0.00    |
      | SNAP         | 2      | 400.00              | -200.00 |
      | IBM          | 5      | 300.00              | 50.00  |
    And the overall profit should be $-150.00
