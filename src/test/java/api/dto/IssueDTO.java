package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {

    @JsonProperty("project")
    private Project project;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("description")
    private String description;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Project {
        @JsonProperty("id")
        private String id;
    }

    public IssueDTO(String projectId, String summary, String description) {
        this.project = new Project(projectId);
        this.summary = summary;
        this.description = description;
    }
}