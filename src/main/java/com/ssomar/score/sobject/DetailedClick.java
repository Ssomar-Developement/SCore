package com.ssomar.score.sobject;

import lombok.Getter;

public enum DetailedClick {

    RIGHT("RIGHT"),
    LEFT("LEFT"),
    RIGHT_OR_LEFT("RIGHTORLEFT", "RIGHT_OR_LEFT");

    @Getter
    private String[] names;

    DetailedClick(String... names) {
        this.names = names;
    }

    public static boolean isDetailedClick(String entry) {
        for (DetailedClick dC : values()) {
            for (String name : dC.getNames()) {
                if (name.equalsIgnoreCase(entry)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static DetailedClick getDetailedClick(String entry) {
        for (DetailedClick dC : values()) {
            for (String name : dC.getNames()) {
                if (name.equalsIgnoreCase(entry)) {
                    return dC;
                }
            }
        }
        return null;
    }
}
