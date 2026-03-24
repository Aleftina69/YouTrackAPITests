package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueDTO {

    @JsonProperty("project")
    private Project project;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("description")
    private String description;

    public IssueDTO() {
    }

    public IssueDTO(String projectId, String summary, String description) {
        this.project = new Project(projectId);
        this.summary = summary;
        this.description = description;
    }

    public static class Project {
        @JsonProperty("id")
        private String id;

        public Project() {
        }

        public Project(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}