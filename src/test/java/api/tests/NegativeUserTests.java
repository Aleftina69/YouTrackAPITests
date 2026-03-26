package api.tests;

import api.utils.Endpoints;
import api.utils.Specifications;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class NegativeUserTests extends BaseTest {

    @Test
    public void noAuthTest() {
        Response response = given()
                .baseUri(baseUrl)
                .when()
                .get(Endpoints.USERS_ME)
                .then()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 401, "Запрос без авторизации не вернул 401");
    }

    @Test
    public void wrongEndpointTest() {
        Response response = given()
                .spec(Specifications.requestSpec)
                .when()
                .get(Endpoints.INVALID_ENDPOINT)
                .then()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 404, "Неверный эндпоинт не вернул 404");
    }

    @Test
    public void wrongTokenTest() {
        Response response = given()
                .baseUri(baseUrl)
                .auth().preemptive().basic("wrong", "wrong")
                .when()
                .get(Endpoints.USERS_ME)
                .then()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 401, "Неверный токен не вернул 401");
    }
}