package api.tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    protected String baseUrl;
    protected String username;
    protected String password;
    protected String projectId;

    public BaseTest() {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            Properties props = new Properties();
            props.load(fis);

            baseUrl = props.getProperty("base.url");
            username = props.getProperty("admin.username");
            password = props.getProperty("admin.password");
            projectId = props.getProperty("project.id");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось прочитать config.properties");
        }
    }

    protected RequestSpecification givenAuth() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .auth().preemptive().basic(username, password)
                .contentType("application/json")
                .log().all();
    }

    protected RequestSpecification givenNoAuth() {
        return RestAssured.given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .log().all();
    }
}