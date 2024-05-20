package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import static com.codeborne.selenide.Selenide.element;

public class CreateNewBuildConfigurationManually extends Page {
    private SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));
    private SelenideElement buildTypeExternalIdInput = element(Selectors.byId("buildTypeExternalId"));

    public CreateNewBuildConfigurationManually open(String projectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId="
                + projectId + "&showMode=createBuildTypeMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewBuildConfigurationManually createBuildConfigManually(String buildName, String buildId) {
        manuallyButton.click();
        buildTypeNameInput.sendKeys(buildName);
        buildTypeExternalIdInput.clear();
        buildTypeExternalIdInput.sendKeys(buildId);
        submit();
        return this;
    }
}
