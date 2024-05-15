Feature: User Management

  Scenario: Create a new User
    Given I have the User details
    When I create a new User with User name, DOB and User Id
    Then the User should be created successfully

  Scenario: Read an existing User
    Given I have the User Id
    When I read the User with the User Id
    Then the User details should be retrieved successfully

  Scenario: Update an existing User
    Given I have the User Id and the new User details
    When I update the User with the User Id
    Then the User details should be updated successfully

  Scenario: Delete an existing User
    Given I have the User Id to delete
    When I delete the User with the User Id
    Then the User should be deleted successfully