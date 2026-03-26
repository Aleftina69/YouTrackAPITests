package api.tests;

import api.utils.Specifications;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.io.InputStream;
import java.util.Properties;

public class BaseTest {

    protected SoftAssert soft;

    protected String baseUrl;
    protected String username;
    protected String password;
    protected String projectId;

    @BeforeMethod
    protected void initSoftAssert() {
        soft = new SoftAssert();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) throw new RuntimeException("config.properties не найден");
            Properties props = new Properties();
            props.load(input);

            baseUrl = props.getProperty("base.url");
            username = props.getProperty("admin.username");
            password = props.getProperty("admin.password");
            projectId = props.getProperty("project.id");

        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки config.properties", e);
        }
    }

    protected RequestSpecification spec() {
        return Specifications.requestSpec;
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