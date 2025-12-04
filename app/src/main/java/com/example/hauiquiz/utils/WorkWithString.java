package com.example.hauiquiz.utils;

public class WorkWithString {
    public static final int len = 40;
    public static String shortenString(String s) {
        if (s == null) return "";
        if (s.length() <= len) return s;
        return s.substring(0, len) + "...";
    }
}
