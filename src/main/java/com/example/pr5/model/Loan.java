package com.example.pr5.model;

import java.time.LocalDate;

public class Loan {
    private String interactionId;
    private String contactId;
    private LoanState state;
    private LocalDate date;
    private String description;

    public Loan(String interactionId, String contactId, LoanState state, String description) {
        this.interactionId = interactionId;
        this.contactId = contactId;
        this.state = state;
        this.date = LocalDate.now();
        this.description = description;
    }

    public String getContactId() { return contactId; }

    @Override
    public String toString() {
        return String.format("Vzaimodeystvie %-6s | Contact: %-6s | Type: %-10s | Data: %s | %s",
                interactionId, contactId, state.getLabel(), date, description);
    }
}
