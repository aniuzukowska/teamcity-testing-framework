package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByAttribute;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.PageElement;
import lombok.Data;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.codeborne.selenide.Selenide.element;

@Data
public abstract class Page {
    private SelenideElement submitButton = element(Selectors.byType("submit"));
    private SelenideElement savingWaitingMarker = element(Selectors.byId("saving"));
    private SelenideElement pageWaitingMarker = element(Selectors.byDataTest("ring-loader"));
    protected SelenideElement manuallyButton = element(new ByAttribute("href", "#createManually"));
    protected SelenideElement header = element(Selectors.byId("header"));

    public void submit() {
        submitButton.click();
        waitUntilDataIsSaved();
    }

    public void waitUntilPageIsLoaded() {
        pageWaitingMarker.shouldNotBe(Condition.visible, Duration.ofMinutes(1));
    }

    public void waitUntilDataIsSaved() {
        savingWaitingMarker.shouldNotBe(Condition.visible, Duration.ofSeconds(30));
    }

    public <T extends PageElement> List<T> generatePageElements(
            ElementsCollection collection,
            Function<SelenideElement, T> creator) {
        var elements = new ArrayList<T>();
        collection.forEach(webElement -> elements.add(creator.apply(webElement)));
        return elements;
    }
}
