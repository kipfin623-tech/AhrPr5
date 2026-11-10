package com.example.pr5.model;

import java.time.LocalDate;

public class PersonContact extends Contact {
    private LocalDate birthday;

    public PersonContact(String contactId, String name, LocalDate birthday) {
        super(contactId, name);
        this.birthday = birthday;
    }

    public LocalDate getBirthday() { return birthday; }

    @Override
    public PublicationCategory getCategory() { return PublicationCategory.PERSON; }

    @Override
    public String getSpecificMetadata() {
        return "Den Rojdenia: " + (birthday != null ? birthday.toString() : "N/A");
    }
}
