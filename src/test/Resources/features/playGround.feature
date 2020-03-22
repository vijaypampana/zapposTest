@zappos
Feature: This is a test feature to test the functionalities included

  Scenario Outline: This test will open the Zappos and select a Shoe

    Given I start the application
    When I get the webpage "https://www.zappos.com"
    Then I wait for "Home" page to load
    Then I click on "searchBar"
    Then I enter "search Bar" as "<searchValue>"
    Then I click on "submitButton"
    Then I handle subWindow
    Then I wait until the visibility of "mens Size"
    Then I click on "mens Size"
    Then I select shoe size as "<size>"
    Then I scroll down until the visibility of "color Selection"
    Then I select the color using below table
      | Black |
      | Gray  |
      | White |
    Then I scroll up until the visibility of "search Bar"
    Then I click first result element
    Then I wait for "Result.Home" page to load
    Then I handle subWindow
    Then I select "color Select" as "<colorSelect>"
    Then I select "size Select" as "<sizeSelect>"
    Then I select "width Select" as "<widthSelect>"
    Then I scroll down until the visibility of "addToCart"
    Then I click on "addToCart"
    Then I wait until the visibility of "checkOut"
    Then I click on "view Cart Link"
    Then I wait for "Result.Cart" page to load
    Then I verify "color" is displayed as "<expectedColor>"
    Then I verify "size" is displayed as "<sizeSelect>"
    Then I verify "width" is displayed as "<widthSelect>"


    Examples:
      | searchValue              | size | colorSelect              | sizeSelect | widthSelect | expectedColor |
      | Nike Air Zoom Pegasus 36 | "10" | Black/White/Thunder Grey | 10         | EE - Wide   | colorSelect   |
