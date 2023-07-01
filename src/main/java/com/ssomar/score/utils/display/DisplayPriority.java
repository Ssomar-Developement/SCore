package com.ssomar.score.utils.display;

public enum DisplayPriority {
    CUSTOM(250),
    LOWEST(100),
    LOW(200),
    HIGH(300),
    HIGHEST(400);

    private final int weight;

    DisplayPriority(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }
}