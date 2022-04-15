package com.ssomar.score.conditions;

import lombok.Getter;

@Getter
public class ConditionsVerification {

    private boolean isValid;
    private boolean isCancelEvent;

    public ConditionsVerification(boolean isValid, boolean isCancelEvent) {
        this.isValid = isValid;
        this.isCancelEvent = isCancelEvent;
    }
}
