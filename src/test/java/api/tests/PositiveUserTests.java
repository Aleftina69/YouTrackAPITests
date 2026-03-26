package api.tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import api.utils.Endpoints;

public class PositiveUserTests extends BaseTest {

    @Test
    public void getCurrentUserTest() {
        initSoftAssert();

        Response response = givenAuth().get(Endpoints.USERS_ME);
        soft.assertEquals(response.statusCode(), 200, "Не удалось получить текущего пользователя");
        checkJsonResponse(response);
        soft.assertEquals(response.jsonPath().getString("login"), username, "Login пользователя не совпадает");

        assertAll();
    }

    @Test
    public void getProjectListTest() {
        initSoftAssert();

        Response response = givenAuth().get(Endpoints.PROJECTS);
        soft.assertEquals(response.statusCode(), 200, "Не удалось получить список проектов");
        checkJsonResponse(response);

        assertAll();
    }
}