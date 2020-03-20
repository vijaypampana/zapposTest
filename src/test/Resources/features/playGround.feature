@zappos
Feature: This is a test feature to test the functionalities included

  Scenario: This test will open the Zappos and select a Shoe

    Given I start the application
    When I get the webpage "https://www.zappos.com"
    Then I wait for "Home" page to load
