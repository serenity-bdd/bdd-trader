Feature: Transaction history
  In order to understand why I have no money left
  As a trader
  I want to see a historyu of all my transactions

  Scenario: All transactions are recorded in the transaction history
    Given Tim Trady is a registered trader
    When Tim has purchased 5 SNAP shares at $100 each
    Then his transaction history should be the following:
      | securityCode | type    | amount | priceInCents | totalInCents |
      | CASH         | Deposit | 100000 | 1            | 100000       |
      | CASH         | Sell    | 50000  | 1            | 50000        |
      | SNAP         | Buy     | 5      | 10000        | 50000        |

  Scenario: A transaction history with buys and sells
    Given Tim Trady is a registered trader
    When he purchases 5 SNAP shares at $100 each
    And he purchases 10 IBM shares at $50 each
    And he sells 2 SNAP shares for $110 each
    Then his transaction history should be the following:
      | securityCode | type    | amount | priceInCents | totalInCents |
      | CASH         | Deposit | 100000 | 1            | 100000       |
      | CASH         | Sell    | 50000  | 1            | 50000        |
      | SNAP         | Buy     | 5      | 10000        | 50000        |
      | CASH         | Sell    | 50000  | 1            | 50000        |
      | SNAP         | Buy     | 5      | 10000        | 50000        |
      | CASH         | Buy     | 22000  | 1            | 22000        |
      | SNAP         | Sell    | 2      | 11000        | 22000        |

