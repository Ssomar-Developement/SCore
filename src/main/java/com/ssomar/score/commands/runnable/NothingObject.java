package com.ssomar.score.commands.runnable;

import lombok.Getter;

@Getter
public class NothingObject {

    private int nothingCount;

    private String nothingString;

    public NothingObject(int nothingCount, String nothingString) {
        this.nothingCount = nothingCount;
        this.nothingString = nothingString;
    }

    public boolean hasNothingString() {
        return nothingString != null && !nothingString.isEmpty();
    }
}
