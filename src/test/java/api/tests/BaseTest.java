package api.tests;

import io.restassured.specification.RequestSpecification;
import org.testng.asserts.SoftAssert;
import java.util.Properties;
import static io.restassured.RestAssured.given;

public class BaseTest {

    protected String baseUrl;
    protected String username;
    protected String password;
    protected String projectId;

    protected SoftAssert soft;

    public BaseTest() {
        try {
            Properties props = new Properties();
            try (var input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) throw new RuntimeException("config.properties не найден");
                props.load(input);
            }

            baseUrl = props.getProperty("base.url");
            username = props.getProperty("admin.username");
            password = props.getProperty("admin.password");
            projectId = props.getProperty("project.id");

        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки config.properties", e);
        }
    }

    protected RequestSpecification givenAuth() {
        return given()
                .baseUri(baseUrl)
                .auth().preemptive().basic(username, password)
                .contentType("application/json");
    }

    protected RequestSpecification givenNoAuth() {
        return given()
                .baseUri(baseUrl)
                .contentType("application/json");
    }

    protected void initSoftAssert() {
        soft = new SoftAssert();
    }

    protected void assertAll() {
        if (soft != null) soft.assertAll();
    }

    protected void checkJsonResponse(io.restassured.response.Response response) {
        if (!response.contentType().contains("application/json")) {
            throw new AssertionError("Ответ не JSON: " + response.asString());
        }
    }

    protected String getId(io.restassured.response.Response response) {
        return response.jsonPath().getString("id");
    }
}