package api.endpoints;


import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

@Slf4j
public class UserEndpointsPropertiesFile {

    static ResourceBundle getUrl(){
        ResourceBundle routes= ResourceBundle.getBundle("routes"); // Load properties file
        return routes;
    }

    public static Response createUser(User payload){

        String createUserUrl= getUrl().getString("createUser_postUrl");

        Response response =
                given()
                .contentType("application/json")
                .accept("application/json")
                .body(payload)

                .when()
                .post(createUserUrl);


        return response;
    }

    public static Response getUser(String username){

        String getUserUrl= getUrl().getString("getUser_getUrl");

        Response response =
                given()
                        .pathParam("username", username)

                        .when()
                        .get(getUserUrl);


        return response;
    }

    public static Response updateUser(String username, User payload){

        String modifyUserUrl =getUrl().getString("modifyUser_putUrl");

        Response response =
                given()
                        .contentType("application/json")
                        .accept(ContentType.JSON) // .accept("application/json") also correct
                        .pathParam("username", username)
                        .body(payload)

                        .when()
                        .put(modifyUserUrl);


        return response;
    }

    public static Response deleteUser(String username){

        String deleteUrl= getUrl().getString("deleteUser_deleteUrl");

        Response response =
                given()
                        .pathParam("username", username)

                        .when()
                        .delete(deleteUrl);


        return response;
    }



}
