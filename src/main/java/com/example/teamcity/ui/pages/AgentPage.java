package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByAttribute;
import com.example.teamcity.ui.Selectors;
import com.codeborne.selenide.Condition;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class AgentPage extends Page {
    private SelenideElement authButton = element(new ByAttribute("data-test-authorize-agent", "true"));
    private SelenideElement popup = element(Selectors.byDataTest("ring-popup"));

    public AgentPage open() {
        Selenide.open("/agents/unauthorized");
        waitUntilPageIsLoaded();
        return this;
    }

    public AgentPage setupTeamCityAgent() {
        authButton.click();
        waitUntilPageIsLoaded();
        popup.shouldBe(Condition.visible, Duration.ofSeconds(30));
        submit();
        return this;
    }

}
