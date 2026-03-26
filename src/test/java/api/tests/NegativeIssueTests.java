package api.tests;

import api.dto.IssueDTO;
import api.utils.Endpoints;
import api.utils.Specifications;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeIssueTests extends BaseTest {

    @Test
    public void createIssueEmptySummaryTest() {
        IssueDTO issue = new IssueDTO(projectId, "", "Empty summary");

        Response response = spec().body(issue)
                .when()
                .post(Endpoints.ISSUES)
                .then()
                .spec(Specifications.response400)
                .extract().response();

        Assert.assertEquals(response.statusCode(), 400, "Создание Issue с пустым summary не вернуло 400");
    }

    @Test
    public void deleteNonExistingIssueTest() {
        Response response = spec()
                .when()
                .delete(Endpoints.issueById("NonExistingId"))
                .then()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 404, "Удаление несуществующего Issue не вернуло 404");
    }

    @Test
    public void getIssueWrongProjectTest() {
        Response response = spec()
                .when()
                .get(Endpoints.issueById("0000000"))
                .then()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 404, "GET Issue из другого проекта не вернул 404");
    }
}