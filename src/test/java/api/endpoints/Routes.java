package api.endpoints;

public class Routes {

    /*
     * Swagger URI --> https://petstore.swagger.io
     *
     * Create user(Post) : https://petstore.swagger.io/v2/user
     * Get user (Get) : https://petstore.swagger.io/v2/user/{username}
     * Update user (Put) : https://petstore.swagger.io/v2/user/{username}
     * Delete user (Delete) : https://petstore.swagger.io/v2/user/{username}
     */

    public static String baseUrl = "https://petstore.swagger.io/v2";


    // User Endpoints
    public static String loginUser_getUrl = baseUrl + "/user/login";
    public static String logoutUser_getUrl = baseUrl + "/user/logout";

    public static String createUser_postUrl = baseUrl + "/user";
    public static String getUser_getUrl = baseUrl + "/user/{username}";
    public static String updateUser_putUrl = baseUrl + "/user/{username}";
    public static String deleteUser_deleteUrl = baseUrl + "/user/{username}";


    // Store Endpoints
    public static String placeOrder_postUrl = baseUrl + "/store/order";

}
