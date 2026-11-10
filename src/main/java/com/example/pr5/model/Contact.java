package com.example.pr5.model;

import com.example.pr5.exception.InvalidPhoneException;

public abstract class Contact {
    private String contactId;
    private String name;
    private String email;
    private String[] phoneNumbers;
    private int phoneCount;

    public Contact(String contactId, String name) {
        this.contactId = contactId;
        this.name = name;
        this.email = "N/A";
        this.phoneNumbers = new String[4];
        this.phoneCount = 0;
    }

    public abstract PublicationCategory getCategory();
    public abstract String getSpecificMetadata();

    public String getContactId() { return contactId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void addPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidPhoneException("Number can't be empty.");
        }
        if (phoneCount == phoneNumbers.length) {
            String[] grown = new String[phoneNumbers.length * 2];
            System.arraycopy(phoneNumbers, 0, grown, 0, phoneCount);
            phoneNumbers = grown;
        }
        phoneNumbers[phoneCount++] = phone.trim();
    }

    public String[] getPhoneNumbers() { return phoneNumbers; }
    public int getPhoneCount() { return phoneCount; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Contact)) return false;
        Contact other = (Contact) obj;
        return contactId.equals(other.contactId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < phoneCount; i++) {
            sb.append(phoneNumbers[i]).append(i == phoneCount - 1 ? "" : ", ");
        }
        return String.format("%-6s | %-18s | %-28s | Tel: %-25s | %s",
                contactId, getCategory().getTitle(), name, sb.length() == 0 ? "None number" : sb.toString(), getSpecificMetadata());
    }
}
