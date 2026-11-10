package com.example.pr5.model;

public class VIPContact extends Contact {
    private String personalManager;

    public VIPContact(String contactId, String name, String personalManager) {
        super(contactId, name);
        this.personalManager = personalManager;
    }

    public String getPersonalManager() { return personalManager; }

    @Override
    public PublicationCategory getCategory() { return PublicationCategory.VIP; }

    @Override
    public String getSpecificMetadata() { return "VIP Client [Manager: " + personalManager + "]"; }
}
