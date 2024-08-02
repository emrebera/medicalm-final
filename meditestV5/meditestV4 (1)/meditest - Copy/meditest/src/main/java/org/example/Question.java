package org.example;

public class Question {
    private String text;
    private String type;

    public Question(String text, String type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }
}
