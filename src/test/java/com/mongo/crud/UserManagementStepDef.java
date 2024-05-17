package com.mongo.crud;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import com.mongo.crud.entities.AppUser;
import com.mongo.crud.repositories.MongoUserRepository;
import com.mongo.crud.services.IUserService;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest
@ActiveProfiles("prod")
public class UserManagementStepDef {
    @Autowired
    Environment environment;

    String[] activeProfiles;

    @Autowired
    IUserService userService;

    @Autowired
    private MongoUserRepository mockUserRepository;

    String userId;
    AppUser user;
    String username;
    String dob;
    AppUser savedUser;
    AppUser recivedUserById;

    AppUser recivedUserAfterUpdate;

    boolean isDeleted;

    AppUser preparedUser;



    //  Scenario: Create a new User
    @Given("I have the User details")
    @Order(1)
    public void i_have_the_User_details() {
        username = "John";
        dob = "1.04.1994";
    }

    @When("I create a new User with User name, DOB and User Id")
    public void i_create_a_new_User_with_User_name_DOB_and_User_Id() {
        activeProfiles = environment.getActiveProfiles();
        if (Arrays.asList(activeProfiles).contains("test")) {
            setUpMock(username,dob);
        }else{
            prepareMongoUser(username,dob);
        }
        savedUser = userService.createUser(user);
    }

    @Then("the User should be created successfully")
    public void the_User_should_be_created_successfully() {
        Assert.assertEquals(user, savedUser);
    }


     //    Scenario: Read an existing User
    @Order(2)
    @Given("I have the User Id")
    public void i_have_the_User_Id() {
        activeProfiles = environment.getActiveProfiles();
        if (Arrays.asList(activeProfiles).contains("test")) {
            setUpMock("Max","12.07.1996");
        }else{
            prepareMongoUserAndSaveToDB("Max","12.07.1996");
        }

    }

    @When("I read the User with the User Id")
    public void i_read_the_User_with_the_User_Id() {
        recivedUserById = userService.getUserById(userId);
    }



    @Then("the User details should be retrieved successfully")
    public void the_User_details_should_be_retrieved_successfully() {
        Assert.assertEquals(recivedUserById,user);
    }

    //  Scenario: Update an existing User
    @Order(3)
    @Given("I have the User Id and the new User details")
    public void i_have_the_User_Id_and_the_new_User_details() {

        activeProfiles = environment.getActiveProfiles();
        if (Arrays.asList(activeProfiles).contains("test")) {
            setUpMock("Max","12.07.1996");
        }else{
            prepareMongoUserAndSaveToDB("Max","12.07.1996");
        }

        user = new AppUser();
        user.setId(userId);
        user.setUsername("Max2");
        user.setDob("23.01.1993");
    }

    @When("I update the User with the User Id")
    public void i_update_the_User_with_the_User_Id() {
         recivedUserAfterUpdate = userService.updateUser(userId,user);
    }

    @Then("the User details should be updated successfully")
    public void the_User_details_should_be_updated_successfully() {
        Assert.assertEquals(user,recivedUserAfterUpdate);
    }

    //  Scenario: Delete an existing User
    @Order(4)
    @Given("I have the User Id to delete")
    public void i_have_the_userId_to_delete() {
        activeProfiles = environment.getActiveProfiles();
        if (Arrays.asList(activeProfiles).contains("test")) {
            setUpMock("Julia","12.07.1987");
        }else{
            prepareMongoUserAndSaveToDB("Julia","12.07.1987");
        }
    }

    @When("I delete the User with the User Id")
    public void i_delete_the_User_with_the_User_Id() {
       isDeleted = userService.deleteUser(userId);
    }

    @Then("the User should be deleted successfully")
    public void the_User_should_be_deleted_successfully() {
        Assert.assertEquals(true,isDeleted);
    }



    private void setUpMock(String username, String dob) {
            userId = "123";

            prepareMockUser(userId, username, dob);

            when(mockUserRepository.save(user)).thenReturn(user);

            when(mockUserRepository.findById(userId)).thenReturn(Optional.ofNullable(user));

            doNothing().when(mockUserRepository).deleteById(userId);
    }


    private void prepareMockUser(String userId, String username, String dob) {
        user = new AppUser();
        user.setId(userId);
        user.setUsername(username);
        user.setDob(dob);
    }

    private void prepareMongoUser(String username, String dob) {
        user = new AppUser();
        user.setUsername(username);
        user.setDob(dob);
    }

    private void prepareMongoUserAndSaveToDB(String username, String dob) {
        preparedUser = new AppUser();
        preparedUser.setUsername(username);
        preparedUser.setDob(dob);
        user = userService.createUser(preparedUser);
        userId= user.getUserId();
    }
}
