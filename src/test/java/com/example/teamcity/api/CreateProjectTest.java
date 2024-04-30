package com.example.teamcity.api;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class CreateProjectTest extends BaseApiTest {

    @Test
    public void createProjectTest() {
        var testData = testDataStorage.addTestData();

        var project = checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void shouldNotCreateProjectWithExistId() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();
        secondTestData.getProject().setId(firstTestData.getProject().getId());

        checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());

        uncheckedWithSuperUser.getProjectRequest()
                .create(secondTestData.getProject())
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project ID \""+ secondTestData.getProject().getId() + "\" is already used by another project"));

    }

    @Test
    public void shouldNotCreateProjectWithoutLocator() {
        var testData = testDataStorage.addTestData();
        testData.getProject().getParentProject().setLocator(null);

        uncheckedWithSuperUser.getProjectRequest()
                .create(testData.getProject())
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("No project specified. Either 'id', 'internalId' or 'locator' attribute should be present."));
    }

    @Test
    public void shouldNotCreateProjectWithNotExistLocator() {
        var testData = testDataStorage.addTestData();
        testData.getProject().getParentProject().setLocator("qwerty");

        uncheckedWithSuperUser.getProjectRequest()
                .create(testData.getProject())
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by name or internal/external id '" + testData.getProject().getParentProject().getLocator() + "'."));
    }

    @Test
    public void shouldNotCreateProjectWithEmptyName() {
        var testData = testDataStorage.addTestData();
        testData.getProject().setName("");

        uncheckedWithSuperUser.getProjectRequest()
                .create(testData.getProject())
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project name cannot be empty."));
    }

    @Test
    public void shouldNotCreateProjectWithEmptyId() {
        var testData = testDataStorage.addTestData();
        testData.getProject().setId("");

        uncheckedWithSuperUser.getProjectRequest()
                .create(testData.getProject())
                .then()
                .assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID must not be empty."));
    }

}
