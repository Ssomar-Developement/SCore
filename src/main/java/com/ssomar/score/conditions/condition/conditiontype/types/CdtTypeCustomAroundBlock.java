package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockCondition;
import com.ssomar.score.conditions.condition.conditiontype.IConditionType;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class CdtTypeCustomAroundBlock implements IConditionType {
    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        for(String s : cdtSection.getConfigurationSection("blockAroundCdts").getKeys(false)) {
            ConfigurationSection section = cdtSection.getConfigurationSection("blockAroundCdts."+s);
            ((List<AroundBlockCondition>) condition.getCondition()).add(AroundBlockCondition.get(section));
        }
        condition.setCondition(cdtSection.getString(condition.getConfigName(), ""));
    }

    @Override
    public <T extends Condition> void save(T condition, ConfigurationSection cdtSection) {

    }

    @Override
    public void updateGUI(NewConditionGUI gui, NewConditions conditions, Condition condition) {

    }
}
