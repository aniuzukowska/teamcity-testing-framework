package com.example.teamcity.ui.pages.project;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.BuildConfigElement;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.elements;

public class ProjectPage extends Page {
    private ElementsCollection subprojects = elements(Selectors.byClass("BuildTypes__item--UX"));
    private SelenideElement pageHeader = element(Selectors.byClass("ProjectPageHeader__title--ih"));

    public ProjectPage open(String projectId) {
        Selenide.open("/project/" + projectId + "?mode=builds");
        pageHeader.shouldBe(Condition.visible, Duration.ofMinutes(1));
        return this;
    }

    public List<BuildConfigElement> getSubBuilds() {
        return generatePageElements(subprojects, BuildConfigElement::new);
    }
}
