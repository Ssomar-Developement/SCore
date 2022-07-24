package com.ssomar.score.utils;

public enum DetailedClick {
    LEFT,
    RIGHT,
    RIGHT_OR_LEFT;


    public static boolean isDetailedClick(String s) {
        return s.equalsIgnoreCase("left") || s.equalsIgnoreCase("right") || s.equalsIgnoreCase("right_or_left");
    }

    public static DetailedClick getDetailedClick(String s) {
        if (s.equalsIgnoreCase("left")) {
            return LEFT;
        }
        if (s.equalsIgnoreCase("right")) {
            return RIGHT;
        }
        if (s.equalsIgnoreCase("right_or_left")) {
            return RIGHT_OR_LEFT;
        }
        return null;
    }
}
