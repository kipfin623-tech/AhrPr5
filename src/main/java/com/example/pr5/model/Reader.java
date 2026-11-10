package com.example.pr5.model;

public class Reader {
    private String groupId;
    private String groupName;

    public Reader(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public String getGroupId() { return groupId; }
    public String getGroupName() { return groupName; }

    @Override
    public String toString() {
        return String.format("Gruppa %-6s | %-28s", groupId, groupName);
    }
}
