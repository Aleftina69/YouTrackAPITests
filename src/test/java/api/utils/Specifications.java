package api.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileInputStream;
import java.util.Properties;

import static org.hamcrest.Matchers.*;

public class Specifications {

    public static RequestSpecification requestSpec;
    public static ResponseSpecification response200;
    public static ResponseSpecification response201;
    public static ResponseSpecification response400;

    static {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("src/test/resources/config.properties"));

            String baseUrl = props.getProperty("base.url");
            String username = props.getProperty("admin.username");
            String password = props.getProperty("admin.password");

            requestSpec = new RequestSpecBuilder()
                    .setBaseUri(baseUrl)
                    .setAuth(io.restassured.RestAssured.preemptive().basic(username, password))
                    .setContentType("application/json")
                    .log(io.restassured.filter.log.LogDetail.ALL)
                    .build();

            response200 = new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .expectContentType("application/json")
                    .build();

            response201 = new ResponseSpecBuilder()
                    .expectStatusCode(201)
                    .expectContentType("application/json")
                    .build();

            response400 = new ResponseSpecBuilder()
                    .expectStatusCode(400)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки конфигурации");
        }
    }
}