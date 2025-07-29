package api.tests;

import api.endpoints.UserEndpoints;
import api.endpoints.UserEndpointsPropertiesFile;
import api.payload.User;
import api.utilities.RetryAnalyzer;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import api.payload.User;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Slf4j
public class UserTests {

    Faker faker;
    User userPayload;

    @BeforeClass
    public void setup(){

        faker = new Faker();
        userPayload = new User();

        /*
        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setUsername(faker.name().username());
        userPayload.setPassword(faker.internet().password(5, 10));
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPhone(faker.phoneNumber().phoneNumber());
        */

        userPayload.setId(12345);
        userPayload.setFirstName("John");
        userPayload.setLastName("Doe");
        userPayload.setUsername("apitestuser123");
        userPayload.setPassword("Test@123");
        userPayload.setEmail("john.doe@example.com");
        userPayload.setPhone("1234567890");
        userPayload.setUserStatus(1); // Set a default userStatus

        log.info("Setup completed for UserTests");
    }

    @Test(priority = 1)
    public void test_createUser(){
        log.info("Starting test: test_createUser");
        log.info("Creating user with username: " + userPayload.getUsername());

        Response response = UserEndpoints.createUser(userPayload);
        log.debug("Response: " + response.then().extract().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "User creation failed");
        log.info("User created successfully: " + userPayload.getUsername());
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
    public void test_getUserByName() {
        log.info("Starting test: test_getUserByName");
        log.info("Fetching user with username: " + userPayload.getUsername());

        Response response = UserEndpoints.getUser(this.userPayload.getUsername());
        log.debug("Response: " + response.then().extract().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "Failed to fetch user");
        log.info("User fetched successfully: " + userPayload.getUsername());
    }

    @Test(priority = 3)
    public void test_modifyUser(){
        log.info("Starting test: test_modifyUser");
        log.info("Modifying user with username: " + userPayload.getUsername());

        userPayload.setFirstName(faker.gameOfThrones().character());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndpoints.updateUser(userPayload.getUsername(), userPayload);
        log.debug("Response: " + response.then().extract().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "User modification failed");
        log.info("User modified successfully: " + userPayload.getUsername());
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class)
    public void test_deleteUserByName(){
        log.info("Starting test: test_deleteUserByName");
        log.info("Deleting user with username: " + userPayload.getUsername());

        Response response = UserEndpoints.deleteUser(this.userPayload.getUsername());
        log.debug("Response: " + response.then().extract().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "User deletion failed");
        log.info("User deleted successfully: " + userPayload.getUsername());
    }

    @Test(priority = 5)
    public void test_createUser_propertiesFile(){
        log.info("Starting test: test_createUser");
        log.info("Creating user with username: " + userPayload.getUsername());

        Response response = UserEndpointsPropertiesFile.createUser(userPayload);
        log.debug("Response: " + response.then().extract().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "User creation failed");
        log.info("User created successfully: " + userPayload.getUsername());
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class)
    public void test_getUserByName_propertiesFile() {
        log.info("Starting test: test_getUserByName");
        log.info("Fetching user with username: " + userPayload.getUsername());

        Response response = UserEndpointsPropertiesFile.getUser(this.userPayload.getUsername());
        log.debug("Response: " + response.then().extract().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "Failed to fetch user");
        log.info("User fetched successfully: " + userPayload.getUsername());
    }


    @Test(priority = 7)
    public void test_modifyUser_propertiesFile(){
        log.info("Starting test: test_modifyUser");
        log.info("Modifying user with username: " + userPayload.getUsername());

        userPayload.setFirstName(faker.gameOfThrones().character());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndpointsPropertiesFile.updateUser(userPayload.getUsername(), userPayload);
        log.debug("Response: " + response.then().extract().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "User modification failed");
        log.info("User modified successfully: " + userPayload.getUsername());
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class)
    public void test_deleteUserByName_propertiesFile(){
        log.info("Starting test: test_deleteUserByName");
        log.info("Deleting user with username: " + userPayload.getUsername());

        Response response = UserEndpointsPropertiesFile.deleteUser(this.userPayload.getUsername());
        log.debug("Response: " + response.then().extract().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "User deletion failed");
        log.info("User deleted successfully: " + userPayload.getUsername());
    }

}
