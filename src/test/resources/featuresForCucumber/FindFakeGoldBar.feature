Feature: Find the fake gold bar

  @Test
  Scenario: Identify the fake gold bar

    Given I open the gold bar game website
    When I identify the fake gold bar
    Then I should see the correct alert message
    And I print the number of weighings and list of weighings