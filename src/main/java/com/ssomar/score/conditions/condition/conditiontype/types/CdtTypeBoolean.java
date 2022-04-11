package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.IConditionType;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class CdtTypeBoolean implements IConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        condition.setCondition(cdtSection.getBoolean(condition.getConfigName(), false));
    }

    @Override
    public <T extends Condition> void save(T condition, ConfigurationSection cdtSection) {
        if((Boolean)condition.getCondition()) cdtSection.set(condition.getConfigName(), true);
        else{
            cdtSection.set(condition.getConfigName(), null);
            cdtSection.set(condition.getConfigName()+"Msg", null);
        }
    }

    @Override
    public void updateGUI(NewConditionGUI gui, NewConditions conditions, Condition condition) {
        if (conditions.contains(condition)) {
            gui.updateBoolean(NewConditionGUI.CONDITION, (Boolean) conditions.get(condition).getCondition());
        } else gui.updateBoolean(NewConditionGUI.CONDITION, false);
    }
}
