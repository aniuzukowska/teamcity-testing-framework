package com.example.teamcity.api;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class CreateBuildConfigurationTest extends BaseApiTest {

    @Test
    public void createBuildConfigurationTest() {
        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());

        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void shouldNotCreateBuildConfigurationWithExistId() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();
        secondTestData.getBuildType().setId(firstTestData.getBuildType().getId());

        checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());
        checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());

        checkedWithSuperUser.getBuildConfigRequest().create(firstTestData.getBuildType());

        uncheckedWithSuperUser.getBuildConfigRequest()
                .create(secondTestData.getBuildType())
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \""
                        + secondTestData.getBuildType().getId() + "\" is already used by another configuration or template"));

    }

    @Test
    public void shouldNotCreateBuildConfigurationWithNotExistProjectId() {
        var testData = testDataStorage.addTestData();

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by locator 'count:1,id:" + testData.getProject().getId() +
                        "'. Project cannot be found by external id '" + testData.getProject().getId() +"'."));
    }

    @Test
    public void shouldNotCreateBuildConfigurationWithoutProjectId() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().getProject().setId(null);

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("No project specified. Either 'id', 'internalId' " +
                        "or 'locator' attribute should be present."));
    }

    @Test
    public void shouldNotCreateBuildConfigurationWithoutName() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().setName(null);

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("When creating a build type, non empty name should be provided."));
    }

    @Test
    public void shouldNotCreateBuildConfigurationWithEmptyName() {
        var testData = testDataStorage.addTestData();
        testData.getBuildType().setName("");

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("When creating a build type, non empty name should be provided."));
    }
}
