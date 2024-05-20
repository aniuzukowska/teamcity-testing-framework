package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.admin.CreateNewBuildConfiguration;
import com.example.teamcity.ui.pages.admin.CreateNewBuildConfigurationManually;
import com.example.teamcity.ui.pages.project.ProjectPage;
import org.testng.annotations.Test;

public class CreateNewBuildConfigurationTest extends BaseUiTest {
    @Test
    public void authorizedUserShouldBeAbleCreateNewBuildConfigurationByUrl() {
        var testData = testDataStorage.addTestData();
        var url = "https://github.com/AlexPshe/spring-core-for-qa";

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        loginAsUser(testData.getUser());

        new CreateNewBuildConfiguration()
                .open(testData.getProject().getId())
                .createBuildConfigByUrl(url)
                .setupBuildConfig(testData.getBuildType().getName());

        new ProjectPage()
                .open(testData.getProject().getId())
                .getSubBuilds()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getBuildType().getName()));

    }

    @Test
    public void authorizedUserShouldBeAbleCreateNewBuildConfigurationManually() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        loginAsUser(testData.getUser());

        new CreateNewBuildConfigurationManually()
                .open(testData.getProject().getId())
                .createBuildConfigManually(testData.getBuildType().getName(), testData.getBuildType().getId());

        new ProjectPage()
                .open(testData.getProject().getId())
                .getSubBuilds()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getBuildType().getName()));

        checkedWithSuperUser.getBuildConfigRequest().get(testData.getBuildType().getId());
    }
}
