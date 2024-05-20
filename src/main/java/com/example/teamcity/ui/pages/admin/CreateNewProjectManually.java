package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import static com.codeborne.selenide.Selenide.element;

public class CreateNewProjectManually extends Page {
    private SelenideElement nameInput = element(Selectors.byId("name"));
    private SelenideElement externalProjectId = element(Selectors.byId("externalId"));
    private SelenideElement createButton = element(Selectors.byId("createProject"));

    public CreateNewProjectManually open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId="
                + parentProjectId + "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public void createProjectManually(String projectName, String projectId) {
        manuallyButton.click();
        nameInput.sendKeys(projectName);
        externalProjectId.clear();
        externalProjectId.sendKeys(projectId);
        createButton.click();
    }
}
