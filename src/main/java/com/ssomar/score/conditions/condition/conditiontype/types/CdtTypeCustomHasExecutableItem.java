package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.IConditionType;
import com.ssomar.score.conditions.condition.playercondition.custom.IfPlayerHasExecutableItem;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class CdtTypeCustomHasExecutableItem implements IConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        List<IfPlayerHasExecutableItem> verifEI = new ArrayList<>();
        if (cdtSection.contains(condition.getConfigName())) {
            ConfigurationSection sec = cdtSection.getConfigurationSection(condition.getConfigName());
            if (sec != null && sec.getKeys(false).size() > 0) {
                for (String s : sec.getKeys(false)) {
                    IfPlayerHasExecutableItem cdt = new IfPlayerHasExecutableItem(sec.getConfigurationSection(s));
                    if (cdt.isValid())
                        verifEI.add(cdt);
                    else
                        errorList.add(pluginName + " Invalid configuration of "+condition.getConfigName()+" with id : " + s + " !");
                }
            } else if (cdtSection.getStringList(condition.getConfigName()).size() > 0) {
                for (String s : cdtSection.getStringList(condition.getConfigName())) {
                    IfPlayerHasExecutableItem cdt = new IfPlayerHasExecutableItem(s);
                    if (cdt.isValid())
                        verifEI.add(cdt);
                    else
                        errorList.add(pluginName + " Invalid configuration of "+condition.getConfigName()+": " + s + " !");
                }
            }
        }
        condition.setCondition(verifEI);
    }

    @Override
    public <T extends Condition> void save(T condition, ConfigurationSection cdtSection) {

    }

    @Override
    public void updateGUI(NewConditionGUI gui, NewConditions conditions, Condition condition) {

    }
}
