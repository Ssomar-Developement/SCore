package com.ssomar.testRecode.features;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class FeatureReturnCheckPremium<T> {

    private boolean hasError;
    private String error;
    private T newValue;

    public FeatureReturnCheckPremium(String error, T newValue) {
        this.hasError = true;
        this.error = error;
        this.newValue = newValue;
    }

    public FeatureReturnCheckPremium() {
        this.hasError = false;
        this.error = null;
        this.newValue = null;
    }
}
