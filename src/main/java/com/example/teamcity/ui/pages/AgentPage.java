package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;

import static com.codeborne.selenide.Selenide.element;

public class AgentPage extends Page {
    private SelenideElement authButton = element(Selectors.byClass("ring-button-button"));

    public AgentPage open() {
        Selenide.open("/agents/unauthorized");
        waitUntilPageIsLoaded();
        return this;
    }

    public AgentPage setupTeamCityAgent() {
        authButton.click();
        waitUntilPageIsLoaded();
        submit();
        return this;
    }

}
