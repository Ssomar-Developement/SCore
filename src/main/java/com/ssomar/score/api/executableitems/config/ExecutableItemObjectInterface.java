package com.ssomar.score.api.executableitems.config;

import com.ssomar.score.utils.emums.ResetSetting;
import com.ssomar.score.utils.emums.VariableUpdateType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public interface ExecutableItemObjectInterface {

    boolean isValid();

    Map<String, String> getVariablesValues();

    String updateVariable(String variableName, String value, VariableUpdateType type);

    int getUsage();

    void updateUsage(int usage);

    void refreshItem();

    ItemStack refresh(List<ResetSetting> resetSettings);
}
