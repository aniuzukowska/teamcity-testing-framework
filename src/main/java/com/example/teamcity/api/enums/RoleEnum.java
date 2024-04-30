package com.example.teamcity.api.enums;

public enum RoleEnum {
    SYSTEM_ADMIN("SYSTEM_ADMIN"),
    PROJECT_VIEWER("PROJECT_VIEWER"),
    PROJECT_DEVELOPER("PROJECT_DEVELOPER"),
    PROJECT_ADMIN("PROJECT_ADMIN"),
    AGENT_MANAGER("AGENT_MANAGER"),
    TOOLS_INTEGRATION("TOOLS_INTEGRATION");

    private String text;

    RoleEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
