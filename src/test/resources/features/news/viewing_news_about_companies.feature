Feature: View news articles about different companies

  Rule: Should be able to view the most recent articles in reverse chronological order
    Example: Articles are displayed in order
      Given Tracy has a trading account
      And the following articles have been published:
        | date       | time  | headline                                                   |
        | 2022-12-03 | 06:30 | Will Earnings Normalize Soon?                              |
        | 2022-12-03 | 11:30 | NYT: Apple signs with Volkswagen to make self-driving cars |
        | 2022-11-01 | 09:30 | Apple Watch shipments +35% in Q1                           |
        | 2022-12-05 | 09:30 | Famous Trading Mistakes                                    |
      When she views the latest news articles
      Then the articles should be presented as follows:
        | date       | time  | headline                                                   |
        | 2022-12-05 | 09:30 | Famous Trading Mistakes                                    |
        | 2022-12-03 | 11:30 | NYT: Apple signs with Volkswagen to make self-driving cars |
        | 2022-12-03 | 06:30 | Will Earnings Normalize Soon?                              |
        | 2022-11-01 | 09:30 | Apple Watch shipments +35% in Q1                           |

    Example: Two articles with the same timestamp are displayed in alphabetical order
      Given Tracy has a trading account
      And the following articles have been published:
        | date       | time     | headline                                                   |
        | 2022-12-03 | 06:30:01 | Will Earnings Normalize Soon?                              |
        | 2022-12-03 | 06:30:01 | NYT: Apple signs with Volkswagen to make self-driving cars |
      When she views the latest news articles
      Then the articles should be presented as follows:
        | date       | time     | headline                                                   |
        | 2022-12-03 | 06:30:01 | NYT: Apple signs with Volkswagen to make self-driving cars |
        | 2022-12-03 | 06:30:01 | Will Earnings Normalize Soon?                              |

  Rule: Should be able to view articles related to a specific company
    Example: Tracy only wants to see articles about Apple
      Given Tracy has a trading account
      And the following articles have been published:
        | date       | time  | headline                                                   | related      |
        | 2022-12-03 | 06:30 | Will Earnings Normalize Soon?                              | AAPL,EVIO,FB |
        | 2022-12-03 | 11:30 | NYT: Apple signs with Volkswagen to make self-driving cars | AAPL,BMWYY   |
        | 2022-12-05 | 09:30 | Famous Trading Mistakes                                    | IBM, TWT     |
      When she views the latest news articles about "AAPL"
      Then the articles should be presented as follows:
        | date       | time  | headline                                                   | related      |
        | 2022-12-03 | 06:30 | Will Earnings Normalize Soon?                              | AAPL,EVIO,FB |
        | 2022-12-03 | 11:30 | NYT: Apple signs with Volkswagen to make self-driving cars | AAPL,BMWYY   |


    Example: There aren't any related articles
      Given Tracy has a trading account
      And the following articles have been published:
        | date       | time  | headline                                                   | related      |
        | 2022-12-03 | 06:30 | Will Earnings Normalize Soon?                              | AAPL,EVIO,FB |
        | 2022-12-03 | 11:30 | NYT: Apple signs with Volkswagen to make self-driving cars | AAPL,BMWYY   |
        | 2022-12-05 | 09:30 | Famous Trading Mistakes                                    | IBM, TWT     |
      When she views the latest news articles about "GOOG"
      Then no articles should be presented
