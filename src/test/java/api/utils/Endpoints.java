package api.utils;

public class Endpoints {

    public static final String USERS_ME = "/api/users/me";
    public static final String PROJECTS = "/api/admin/projects";
    public static final String ISSUES = "/api/issues";
    public static final String INVALID_ENDPOINT = "/api/users/invalidEndpoint";

    public static String issueById(String id) {
        return ISSUES + "/" + id;
    }
}