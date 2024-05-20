package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.favorites.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateNewProject;
import com.example.teamcity.ui.pages.admin.CreateNewProjectManually;
import org.testng.annotations.Test;

public class CreateNewProjectTest extends BaseUiTest{
    @Test
    public void authorizedUserShouldBeAbleCreateNewProjectFromRepositoryUrl() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/AlexPshe/spring-core-for-qa";

        loginAsUser(testData.getUser());

        new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getProject().getName()));
    }

    @Test
    public void authorizedUserShouldBeAbleCreateNewProjectManually() {
        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());

        new CreateNewProjectManually()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectManually(testData.getProject().getName(), testData.getProject().getId());

        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getProject().getName()));

        checkedWithSuperUser.getProjectRequest().get(testData.getProject().getId());
    }
}
