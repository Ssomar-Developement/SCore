package com.ssomar.score.api.executableblocks.config;

import com.ssomar.score.sobject.InternalData;
import com.ssomar.score.utils.emums.VariableUpdateType;

public interface ExecutableBlockObjectInterface {

    boolean isValid();

    InternalData getInternalData();

    String updateVariable(String variableName, String value, VariableUpdateType type);

}
