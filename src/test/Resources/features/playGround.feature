@zappos
Feature: This is a test feature to test the functionalities included

  Scenario Outline: This test will open the Zappos and select a Shoe

    Given I start the application
    When I get the webpage "https://www.zappos.com"
    Then I wait for "Home" page to load
    Then I click on "searchBar"
    Then I enter "search Bar" as "<searchValue>"
    Then I click on "submitButton"
    Then I wait until the visibility of "mens Size"
    Then I click on "mens Size"
    Then I select shoe size as "<size>"
    Then I scroll down until the visibility of "color Selection"
    Then I scroll up until the visibility of "search Bar"



    Examples:
      | searchValue              | size |
      | Nike Air Zoom Pegasus 36 | "10"   |
