package com.example.lab_4a_memopad;

import androidx.annotation.NonNull;

public class Memo {
    private int id;
    private final String text;


    public Memo(int id, String text) {
        this.id = id;
        this.text = text;
    }
    public Memo(String name) {
        this.text = name;
    }


    public int getId() {
        return id;
    }
    public String getMemoText() {
        return text;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append("#").append(id).append(": ").append(text);
        return s.toString();
    }
}