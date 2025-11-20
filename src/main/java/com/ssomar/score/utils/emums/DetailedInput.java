package com.ssomar.score.utils.emums;

public enum DetailedInput {
    LEFT_PRESS,
    RIGHT_PRESS,
    FORWARD_PRESS,
    BACKWARD_PRESS,
    JUMP_PRESS,
    SNEAK_PRESS,
    SPRINT_PRESS,
    LEFT_RELEASE,
    RIGHT_RELEASE,
    FORWARD_RELEASE,
    BACKWARD_RELEASE,
    JUMP_RELEASE,
    SNEAK_RELEASE,
    SPRINT_RELEASE;


    public static boolean isDetailedClick(String s) {
        return s.equalsIgnoreCase("left_press") || s.equalsIgnoreCase("right_press") || s.equalsIgnoreCase("forward_press")
                || s.equalsIgnoreCase("backward_press")
                || s.equalsIgnoreCase("jump_press") || s.equalsIgnoreCase("sneak_press") || s.equalsIgnoreCase("sprint_press")
                || s.equalsIgnoreCase("left_release") || s.equalsIgnoreCase("right_release") || s.equalsIgnoreCase("forward_release")
                || s.equalsIgnoreCase("backward_release")
                || s.equalsIgnoreCase("jump_release") || s.equalsIgnoreCase("sneak_release") || s.equalsIgnoreCase("sprint_release");
    }

    public static DetailedInput getDetailedClick(String s) {
        if (s.equalsIgnoreCase("left_press")) {
            return LEFT_PRESS;
        }
        else if (s.equalsIgnoreCase("right_press")) {
            return RIGHT_PRESS;
        }
        else if (s.equalsIgnoreCase("forward_press")) {
            return FORWARD_PRESS;
        }
        else if (s.equalsIgnoreCase("backward_press")) {
            return BACKWARD_PRESS;
        }
        else if (s.equalsIgnoreCase("jump_press")) {
            return JUMP_PRESS;
        }
        else if (s.equalsIgnoreCase("sneak_press")) {
            return SNEAK_PRESS;
        }
        else if (s.equalsIgnoreCase("sprint_press")) {
            return SPRINT_PRESS;
        }
        else if (s.equalsIgnoreCase("left_release")) {
            return LEFT_RELEASE;
        }
        else if (s.equalsIgnoreCase("right_release")) {
            return RIGHT_RELEASE;
        }
        else if (s.equalsIgnoreCase("forward_release")) {
            return FORWARD_RELEASE;
        }
        else if (s.equalsIgnoreCase("backward_release")) {
            return BACKWARD_RELEASE;
        }
        else if (s.equalsIgnoreCase("jump_release")) {
            return JUMP_RELEASE;
        }
        else if (s.equalsIgnoreCase("sneak_release")) {
            return SNEAK_RELEASE;
        }
        else if (s.equalsIgnoreCase("sprint_release")) {
            return SPRINT_RELEASE;
        }
        return null;
    }
}
