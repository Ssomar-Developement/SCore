package com.ssomar.score.conditions.condition.conditiontype;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public interface IConditionType {

    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName);

    public <T extends Condition> void save(T condition, ConfigurationSection cdtSection);

    public void updateGUI(NewConditionGUI gui, NewConditions conditions, Condition condition);
}
