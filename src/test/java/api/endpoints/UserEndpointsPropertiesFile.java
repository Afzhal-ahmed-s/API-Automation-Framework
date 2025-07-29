package api.endpoints;


import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static io.restassured.RestAssured.given;

@Slf4j
public class UserEndpointsPropertiesFile {

    public static Response createUser(User payload){

        Response response =
                given()
                .contentType("application/json")
                .accept("application/json")
                .body(payload)

                .when()
                .post(Routes.createUser_postUrl);


        return response;
    }

    public static Response getUser(String username){

        Response response =
                given()
                        .pathParam("username", username)

                        .when()
                        .get(Routes.getUser_getUrl);


        return response;
    }

    public static Response updateUser(String username, User payload){

        Response response =
                given()
                        .contentType("application/json")
                        .accept(ContentType.JSON) // .accept("application/json") also correct
                        .pathParam("username", username)
                        .body(payload)

                        .when()
                        .put(Routes.updateUser_putUrl);


        return response;
    }

    public static Response deleteUser(String username){

        Response response =
                given()
                        .pathParam("username", username)

                        .when()
                        .delete(Routes.deleteUser_deleteUrl);


        return response;
    }



}
