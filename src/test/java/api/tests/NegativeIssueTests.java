package api.tests;

import api.dto.IssueDTO;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class NegativeIssueTests extends BaseTest {

    @Test
    public void createIssueEmptySummaryTest() {
        SoftAssert soft = new SoftAssert();
        IssueDTO issue = new IssueDTO(projectId, "", "Empty summary");

        Response response = givenAuth().body(issue).post("/api/issues");
        soft.assertEquals(response.statusCode(), 400, "Создание Issue с пустым summary не вернуло 400");

        soft.assertAll();
    }

    @Test
    public void deleteNonExistingIssueTest() {
        SoftAssert soft = new SoftAssert();

        Response response = givenAuth().delete("/api/issues/NonExistingId");
        soft.assertEquals(response.statusCode(), 404, "Удаление несуществующего Issue не вернуло 404");

        soft.assertAll();
    }

    @Test
    public void getIssueWrongProjectTest() {
        SoftAssert soft = new SoftAssert();

        Response response = givenAuth().get("/api/issues/0000000");
        soft.assertEquals(response.statusCode(), 404, "GET Issue из другого проекта не вернул 404");

        soft.assertAll();
    }
}