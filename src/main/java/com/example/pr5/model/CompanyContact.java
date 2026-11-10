package com.example.pr5.model;

public class CompanyContact extends Contact {
    private String inn;

    public CompanyContact(String contactId, String name, String inn) {
        super(contactId, name);
        this.inn = inn;
    }

    public String getInn() { return inn; }

    @Override
    public PublicationCategory getCategory() { return PublicationCategory.COMPANY; }

    @Override
    public String getSpecificMetadata() { return "INN: " + inn; }
}
