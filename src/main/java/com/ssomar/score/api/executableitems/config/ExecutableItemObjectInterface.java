package com.ssomar.score.api.executableitems.config;

import com.ssomar.score.utils.emums.VariableUpdateType;

import java.util.HashMap;

public interface ExecutableItemObjectInterface {

    boolean isValid();

    HashMap<String, String> getVariablesValues();

    String updateVariable(String variableName, String value, VariableUpdateType type);

    int getUsage();

    void updateUsage(int usage);

    void refreshItem();
}
