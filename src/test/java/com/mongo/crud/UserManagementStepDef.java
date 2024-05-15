package com.mongo.crud;

import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mongo.crud.entities.AppUser;
import com.mongo.crud.services.IUserService;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest
public class UserManagementStepDef {

    @Autowired
    IUserService userService;

    AppUser user;
    AppUser createdUserToDelete;
    AppUser updateUser;

    String username;
    String dob;
    String userId;
    boolean isDeleted;

    //  Scenario: Create a new User
    @Given("I have the User details")
    @Order(1)
    public void i_have_the_User_details() {
        username = "John";
        dob = "1.04.1994";
    }

    @When("I create a new User with User name, DOB and User Id")
    public void i_create_a_new_User_with_User_name_DOB_and_User_Id() {
        createdUserToDelete = new AppUser();
        createdUserToDelete.setUsername(username);
        createdUserToDelete.setDob(dob);
        createdUserToDelete = userService.createUser(createdUserToDelete);
    }

    @Then("the User should be created successfully")
    public void the_User_should_be_created_successfully() {
        Assert.assertNotNull(createdUserToDelete);
    }


    //    Scenario: Read an existing User
    @Given("I have the User Id")
    public void i_have_the_User_Id() {
        userId ="664477e3d1c37240ec5ea8a9" ;
    }

    @When("I read the User with the User Id")
    public void i_read_the_User_with_the_User_Id() {
        user = userService.getUserById(userId);
    }

    @Then("the User details should be retrieved successfully")
    public void the_User_details_should_be_retrieved_successfully() {
        Assert.assertEquals(user.getUserId(),userId);
    }


    //  Scenario: Update an existing User
    @Given("I have the User Id and the new User details")
    public void i_have_the_User_Id_and_the_new_User_details() {
          userId = "664477e3d1c37240ec5ea8a9";
          updateUser = new AppUser();
          updateUser.setUsername("Bob");
          updateUser.setDob("23.01.1993");
    }

    @When("I update the User with the User Id")
    public void i_update_the_User_with_the_User_Id() {
         user = userService.updateUser(userId,updateUser);
    }

    @Then("the User details should be updated successfully")
    public void the_User_details_should_be_updated_successfully() {
        Assert.assertEquals(updateUser.getDob(),user.getDob());
    }

    //  Scenario: Delete an existing User
    @Given("I have the User Id to delete")
    public void i_have_the_userId_to_delete() {
        userId = "66447b6a671f12155438cff2";
    }

    @When("I delete the User with the User Id")
    public void i_delete_the_User_with_the_User_Id() {
       isDeleted = userService.deleteUser(userId);
    }

    @Then("the User should be deleted successfully")
    public void the_User_should_be_deleted_successfully() {
        Assert.assertEquals(true,isDeleted);

    }


}
