package com.sparta.academy.enums;

public enum DepartmentEnum {
    CURRICULUM("커리큘럼"),
    MARKETING("마케팅"),
    DEVELOPMENT("개발");

    private String description;

    DepartmentEnum(String description) {
        this.description = description;
    }
}
