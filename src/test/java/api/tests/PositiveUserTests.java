package api.tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PositiveUserTests extends BaseTest {

    @Test
    public void getCurrentUserTest() {
        SoftAssert soft = new SoftAssert();

        Response response = givenAuth().get("/api/users/me");
        soft.assertEquals(response.statusCode(), 200, "Не удалось получить текущего пользователя");
        soft.assertTrue(response.contentType().contains("application/json"), "Ответ не JSON");
        soft.assertEquals(response.jsonPath().getString("login"), username, "Login пользователя не совпадает");

        soft.assertAll();
    }

    @Test
    public void getProjectListTest() {
        SoftAssert soft = new SoftAssert();

        Response response = givenAuth().get("/api/admin/projects");
        soft.assertEquals(response.statusCode(), 200, "Не удалось получить список проектов");
        soft.assertTrue(response.contentType().contains("application/json"), "Ответ не JSON");

        soft.assertAll();
    }
}