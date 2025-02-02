package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

@Getter
public class BuildConfigElement extends PageElement {
    private final SelenideElement header;
    private final SelenideElement icon;

    public BuildConfigElement(SelenideElement element) {
        super(element);
        this.header = findElement(Selectors.byClass("MiddleEllipsis__searchable--uZ"));
        this.icon = findElement("svg");
    }


}
