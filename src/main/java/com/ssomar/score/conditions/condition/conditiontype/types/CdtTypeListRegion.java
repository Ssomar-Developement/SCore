package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.SCore;
import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.IConditionType;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class CdtTypeListRegion implements IConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        if (cdtSection.contains("ifInRegion")) {
            if (SCore.is1v12()) {
                errorList.add(pluginName + " Error the conditions "+condition.getConfigName()+" are not available in 1.12 due to a changement of worldguard API ");
            } else {
                condition.setCondition(cdtSection.getStringList(condition.getConfigName()));
            }
        }
    }

    @Override
    public <T extends Condition> void save(T condition, ConfigurationSection cdtSection) {
        if(!((List<String>)condition.getCondition()).isEmpty()){
            cdtSection.set(condition.getConfigName(), (List<String>)condition.getCondition());
        }
        else{
            cdtSection.set(condition.getConfigName(), null);
            cdtSection.set(condition.getConfigName()+"Msg", null);
        }
    }

    @Override
    public void updateGUI(NewConditionGUI gui, NewConditions conditions, Condition condition) {

    }
}
