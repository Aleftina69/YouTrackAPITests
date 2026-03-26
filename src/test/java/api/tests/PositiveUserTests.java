package api.tests;

import api.utils.Endpoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class PositiveUserTests extends BaseTest {

    @Test
    public void getCurrentUserTest() {
        Response response = spec()
                .when()
                .get(Endpoints.USERS_ME)
                .then()
                .extract().response();

        soft.assertEquals(response.statusCode(), 200, "Не удалось получить текущего пользователя");
        checkJsonResponse(response);
        soft.assertEquals(response.jsonPath().getString("login"), username, "Login пользователя не совпадает");

        assertAll();
    }

    @Test
    public void getProjectListTest() {
        Response response = spec()
                .when()
                .get(Endpoints.PROJECTS)
                .then()
                .extract().response();

        soft.assertEquals(response.statusCode(), 200, "Не удалось получить список проектов");
        checkJsonResponse(response);

        assertAll();
    }
}