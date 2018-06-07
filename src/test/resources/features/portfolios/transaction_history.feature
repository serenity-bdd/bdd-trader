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
      | CASH         | Sell    | 50000  | 1            | 50000.00     |
      | SNAP         | Buy     | 5      | 10000        | 50000        |

