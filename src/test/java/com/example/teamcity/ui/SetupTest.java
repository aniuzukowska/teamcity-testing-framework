package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.AgentPage;
import com.example.teamcity.ui.pages.StartUpPage;
import org.testng.annotations.Test;

public class SetupTest extends BaseUiTest {
    @Test
    public void startUpTest() {
        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());
        new StartUpPage()
                .open()
                .setupTeamCityServer()
                .getHeader().shouldHave(Condition.text("Create Administrator Account"));
    }

    @Test
    public void setupTeamCityAgentTest() {
        new AgentPage()
                .open()
                .setupTeamCityAgent();
    }
}
