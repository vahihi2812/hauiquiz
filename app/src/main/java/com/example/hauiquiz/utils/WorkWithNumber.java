package com.example.hauiquiz.utils;

public class WorkWithNumber {
    public static double roundOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    public static double randomScore() {
        return (int) (Math.random() * 21) * 0.5;
    }
}
