package api.tests;

import api.dto.IssueDTO;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import api.utils.Endpoints;

public class PositiveIssueTests extends BaseTest {

    @DataProvider(name = "issueData")
    public Object[][] issueDataProvider() throws IOException {
        return Files.lines(Paths.get("src/test/resources/issueData.csv"))
                .skip(1)
                .map(line -> line.split(",", 2))
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "issueData")
    public void createGetDeleteIssueTest(String summary, String description) {
        initSoftAssert();

        IssueDTO issue = new IssueDTO(projectId, summary, description);

        Response createResponse = givenAuth().body(issue).post(Endpoints.ISSUES);
        soft.assertEquals(createResponse.statusCode(), 201, "Issue не создан");
        String issueId = getId(createResponse);
        soft.assertNotNull(issueId, "ID задачи отсутствует");

        Response getResponse = givenAuth().get(Endpoints.issueById(issueId));
        soft.assertEquals(getResponse.statusCode(), 200, "Не удалось получить issue");
        soft.assertEquals(getResponse.jsonPath().getString("summary"), summary, "Summary не совпадает");

        Response deleteResponse = givenAuth().delete(Endpoints.issueById(issueId));
        soft.assertTrue(deleteResponse.statusCode() == 204 || deleteResponse.statusCode() == 200,
                "Issue не удалось удалить");

        assertAll();
    }
}