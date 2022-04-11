package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.IConditionType;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class CdtTypeListEntityType implements IConditionType {
    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        List<EntityType> list = new ArrayList<>();
        for (String s : cdtSection.getStringList(condition.getConfigName())) {
            try {
                list.add(EntityType.valueOf(s));
            } catch (Exception ignored) {}
        }
        condition.setCondition(list);
    }

    @Override
    public <T extends Condition> void save(T condition, ConfigurationSection cdtSection) {

    }

    @Override
    public void updateGUI(NewConditionGUI gui, NewConditions conditions, Condition condition) {

    }
}
