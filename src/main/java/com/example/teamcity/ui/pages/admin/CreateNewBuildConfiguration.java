package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class CreateNewBuildConfiguration extends Page {
    private SelenideElement urlInput = element(Selectors.byId("url"));
    private SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));

    public CreateNewBuildConfiguration open(String projectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId="
                + projectId + "&showMode=createBuildTypeMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewBuildConfiguration createBuildConfigByUrl(String url) {
        urlInput.sendKeys(url);
        submit();
        return this;
    }

    public void setupBuildConfig(String buildName) {
        buildTypeNameInput.shouldBe(Condition.visible, Duration.ofMinutes(1));
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildName);
        submit();
    }
}
