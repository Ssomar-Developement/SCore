package com.ssomar.score.utils.emums;

public enum VariableUpdateType {

    MODIFICATION,
    SET;

    public VariableUpdateType getPrev() {
        VariableUpdateType opt = values()[values().length - 1];
        for (VariableUpdateType o : values()) {
            if (this.equals(o)) {
                return opt;
            } else opt = o;
        }
        return opt;
    }

    public VariableUpdateType getNext() {
        VariableUpdateType opt = values()[0];
        boolean next = false;
        for (VariableUpdateType o : values()) {
            if (next) {
                opt = o;
                break;
            }
            if (this.equals(o)) {
                next = true;
            }
        }
        return opt;
    }
}
