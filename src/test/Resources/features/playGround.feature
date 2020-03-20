@zappos
Feature: This is a test feature to test the functionalities included

  Scenario Outline: This test will open the Zappos and select a Shoe

    Given I start the application
    When I get the webpage "https://www.zappos.com"
    Then I wait for "Home" page to load
    Then I click on "searchBar"
    Then I enter "search Bar" as "<searchValue>"
    Then I click on "submitButton"
    Then I wait for "Home" page to load


    Examples:
      | searchValue              |
      | Nike Air Zoom Pegasus 36 |
