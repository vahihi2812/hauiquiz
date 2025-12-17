package com.example.hauiquiz.utils;

import androidx.annotation.NonNull;

import java.util.Random;

public class WorkWithString {
    public static final int len = 40;
    @NonNull
    public static String shortenString(String s) {
        if (s == null) return "";
        if (s.length() <= len) return s;
        return s.substring(0, len) + "...";
    }

    private static final String[] LAST_NAMES = {
            "Nguyễn", "Trần", "Lê", "Phạm", "Hoàng", "Huỳnh",
            "Phan", "Vũ", "Võ", "Đặng", "Bùi", "Đỗ"
    };

    private static final String[] MIDDLE_NAMES = {
            "Văn", "Thị", "Hữu", "Minh", "Đức", "Ngọc", "Quang", ""
    };

    private static final String[] FIRST_NAMES = {
            "An", "Bình", "Cường", "Dũng", "Hà", "Hải", "Hạnh",
            "Hùng", "Khánh", "Linh", "Long", "Mai", "Nam",
            "Phúc", "Quân", "Trang", "Tuấn", "Vy", "Yến"
    };

    private static final Random random = new Random();

    @NonNull
    public static String randomName() {
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        String middleName = MIDDLE_NAMES[random.nextInt(MIDDLE_NAMES.length)];
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];

        if (middleName.isEmpty()) {
            return lastName + " " + firstName;
        }
        return lastName + " " + middleName + " " + firstName;
    }
}
