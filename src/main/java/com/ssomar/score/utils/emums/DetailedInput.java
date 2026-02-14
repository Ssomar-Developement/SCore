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
    SPRINT_RELEASE,
    // Grouped options (like RIGHT_OR_LEFT in DetailedClick)
    LEFT_PRESS_OR_RELEASE,
    RIGHT_PRESS_OR_RELEASE,
    FORWARD_PRESS_OR_RELEASE,
    BACKWARD_PRESS_OR_RELEASE,
    JUMP_PRESS_OR_RELEASE,
    SNEAK_PRESS_OR_RELEASE,
    SPRINT_PRESS_OR_RELEASE;


    public static boolean isDetailedInput(String s) {
        for (DetailedInput input : values()) {
            if (input.name().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    public static DetailedInput getDetailedInput(String s) {
        for (DetailedInput input : values()) {
            if (input.name().equalsIgnoreCase(s)) {
                return input;
            }
        }
        return null;
    }

    /**
     * Checks if the given input matches this DetailedInput configuration.
     * Handles grouped options like LEFT_PRESS_OR_RELEASE.
     */
    public boolean matches(DetailedInput input) {
        if (input == null) return false;
        if (this == input) return true;

        switch (this) {
            case LEFT_PRESS_OR_RELEASE:
                return input == LEFT_PRESS || input == LEFT_RELEASE;
            case RIGHT_PRESS_OR_RELEASE:
                return input == RIGHT_PRESS || input == RIGHT_RELEASE;
            case FORWARD_PRESS_OR_RELEASE:
                return input == FORWARD_PRESS || input == FORWARD_RELEASE;
            case BACKWARD_PRESS_OR_RELEASE:
                return input == BACKWARD_PRESS || input == BACKWARD_RELEASE;
            case JUMP_PRESS_OR_RELEASE:
                return input == JUMP_PRESS || input == JUMP_RELEASE;
            case SNEAK_PRESS_OR_RELEASE:
                return input == SNEAK_PRESS || input == SNEAK_RELEASE;
            case SPRINT_PRESS_OR_RELEASE:
                return input == SPRINT_PRESS || input == SPRINT_RELEASE;
            default:
                return false;
        }
    }

    // Deprecated methods kept for backwards compatibility
    @Deprecated
    public static boolean isDetailedClick(String s) {
        return isDetailedInput(s);
    }

    @Deprecated
    public static DetailedInput getDetailedClick(String s) {
        return getDetailedInput(s);
    }
}
