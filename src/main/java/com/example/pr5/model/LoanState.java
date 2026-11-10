package com.example.pr5.model;

public enum LoanState {
    CALL("Zvonok"), MEETING("Vstrecha"), EMAIL("Pismo");

    private final String label;
    LoanState(String label) { this.label = label; }
    public String getLabel() { return label; }
}
