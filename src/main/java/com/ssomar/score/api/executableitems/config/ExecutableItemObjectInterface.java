package com.ssomar.score.api.executableitems.config;

import com.ssomar.score.utils.VariableUpdateType;

import java.util.HashMap;

public interface ExecutableItemObjectInterface {

    boolean isValid();

    HashMap<String, String> getVariablesValues();

    String updateVariable(String variableName, String value, VariableUpdateType type);

    void refreshItem();
}
