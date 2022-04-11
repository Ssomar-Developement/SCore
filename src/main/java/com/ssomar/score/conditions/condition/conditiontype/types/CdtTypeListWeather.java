package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.IConditionType;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class CdtTypeListWeather implements IConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {

    }

    @Override
    public <T extends Condition> void save(T condition, ConfigurationSection cdtSection) {

    }

    @Override
    public void updateGUI(NewConditionGUI gui, NewConditions conditions, Condition condition) {
        if (conditions.contains(condition)) {
            gui.updateConditionList(NewConditionGUI.CONDITION, (List<String>) conditions.get(condition).getCondition(), "&6➤ &eNO WEATHER IS REQUIRED");
        } else
            gui.updateConditionList(NewConditionGUI.CONDITION, new ArrayList<>(), "&6➤ &eNO WEATHER IS REQUIRED");
    }
}
