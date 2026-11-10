package com.example.pr5.model;

public enum PublicationCategory {
    PERSON("Fizicheskoe Litso"),
    COMPANY("Companiya"),
    VIP("VIP Client");

    private final String title;

    PublicationCategory(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
