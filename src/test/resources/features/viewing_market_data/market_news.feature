Feature: Market news

  In order to make sensible trading decisions
  As a trader
  I want to be informed of relevant news about shares I am interested in

  Scenario: Viewing news about a particular share
    Given Tim is interested in Apple
    When Tim views the news about APPL
    Then Tim should only see articles related to APPL
