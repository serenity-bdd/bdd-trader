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