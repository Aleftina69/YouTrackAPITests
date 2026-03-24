package api.tests;

import api.dto.IssueDTO;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PositiveIssueTests extends BaseTest {

    @DataProvider(name = "issueData")
    public Object[][] issueDataProvider() throws IOException {
        List<Object[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/issueData.csv"))) {
            String line = br.readLine(); // пропустить заголовок
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                records.add(new Object[]{parts[0], parts[1]});
            }
        }
        return records.toArray(new Object[0][]);
    }

    @Test(dataProvider = "issueData")
    public void createGetDeleteIssueTest(String summary, String description) {
        SoftAssert soft = new SoftAssert();

        IssueDTO issue = new IssueDTO(projectId, summary, description);

        Response createResponse = givenAuth().body(issue).post("/api/issues");
        soft.assertEquals(createResponse.statusCode(), 201, "Issue не создан");

        String issueId = createResponse.jsonPath().getString("id");
        soft.assertNotNull(issueId, "ID задачи отсутствует");

        Response getResponse = givenAuth().get("/api/issues/" + issueId);
        soft.assertEquals(getResponse.statusCode(), 200, "Не удалось получить issue");
        soft.assertEquals(getResponse.jsonPath().getString("summary"), summary, "Summary не совпадает");

        Response deleteResponse = givenAuth().delete("/api/issues/" + issueId);
        soft.assertTrue(deleteResponse.statusCode() == 204 || deleteResponse.statusCode() == 200,
                "Issue не удалось удалить");

        soft.assertAll();
    }
}