package api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class NegativeUserTests extends BaseTest {

    @Test
    public void noAuthTest() {
        SoftAssert soft = new SoftAssert();
        Response response = givenNoAuth().get("/api/users/me");
        soft.assertEquals(response.statusCode(), 401, "Запрос без авторизации не вернул 401");
        soft.assertAll();
    }

    @Test
    public void wrongEndpointTest() {
        SoftAssert soft = new SoftAssert();
        Response response = givenAuth().get("/api/users/invalidEndpoint");
        soft.assertEquals(response.statusCode(), 404, "Неверный эндпоинт не вернул 404");
        soft.assertAll();
    }

    @Test
    public void wrongTokenTest() {
        SoftAssert soft = new SoftAssert();
        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .auth().preemptive().basic("wrong", "wrong")
                .get("/api/users/me");
        soft.assertEquals(response.statusCode(), 401, "Неверный токен не вернул 401");
        soft.assertAll();
    }
}