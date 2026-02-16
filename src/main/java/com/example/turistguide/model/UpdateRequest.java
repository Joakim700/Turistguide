package com.example.turistguide.model;

public class UpdateRequest {

    private String newName;
    private String newDescription;
    private String oldName;

    public String getNewDescription() {
        return newDescription;
    }

    public String getNewName() {
        return newName;
    }

    public String getOldName() {
        return oldName;
    }
}
