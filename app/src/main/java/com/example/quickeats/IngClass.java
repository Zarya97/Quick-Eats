package com.example.quickeats;

public class IngClass {
    private boolean checked;
    private String text;

    public IngClass(String text) {
        this.checked = false;
        this.text = text;
    }

    public boolean getCheck() {
        return checked;
    }

    public String getText() {
        return text;
    }
}
