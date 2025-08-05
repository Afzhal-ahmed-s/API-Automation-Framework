package api.tests;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class DDTests {

    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void test_createUser(String userId, String userName, String firstName, String lastName, String email, String password, String phone){

        User userPayload = new User(Integer.parseInt(userId), userName, firstName,lastName,email, password, phone, 1);

        Response response = UserEndpoints.createUser(userPayload);

        log.info(response.then().extract().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void test_deleteUser(String userName) {

        log.debug("Attempting to delete user: " + userName);

        // Retry logic for transient backend issues
        int retryCount = 5;
        boolean userExists = false;

        Response getUserResponse = null;

        for (int i = 0; i < retryCount; i++) {
            getUserResponse = UserEndpoints.getUser(userName);
            if (getUserResponse.getStatusCode() == 200) {
                userExists = true;
                break;
            }
            log.warn("Retrying to check user existence: Attempt " + (i + 1));
            try {
                Thread.sleep(1000); // Wait for 1 second before retrying
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (!userExists) {
            log.error("User does not exist after retries: " + userName);
            Assert.fail("Cannot delete user as it does not exist: " + userName);
        }

        // Log the getUser response for debugging
        log.info("getUser Response: " + getUserResponse.then().extract().asPrettyString());

        // Proceed to delete the user
        // Log the deleteUser request details
        log.debug("Sending delete request for user: " + userName);

        // Retry logic for delete operation
        int deleteRetryCount = 5;
        boolean deleteSuccess = false;

        for (int i = 0; i < deleteRetryCount; i++) {
            Response deleteResponse = UserEndpoints.deleteUser(userName);
            log.info("Delete Response: " + deleteResponse.then().extract().asPrettyString());

            if (deleteResponse.getStatusCode() == 200) {
                deleteSuccess = true;
                break;
            }

            log.warn("Retrying delete operation: Attempt " + (i + 1));
            try {
                Thread.sleep(1000); // Wait for 1 second before retrying
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (!deleteSuccess) {
            log.error("Failed to delete user after retries: " + userName);
            Assert.fail("Failed to delete user: " + userName);
        }
    }


}