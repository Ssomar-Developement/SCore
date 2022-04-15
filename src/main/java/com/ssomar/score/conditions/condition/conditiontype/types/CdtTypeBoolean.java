package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.AConditionType;
import com.ssomar.score.menu.conditions.general.ConditionGUI;
import com.ssomar.score.menu.conditions.general.ConditionGUIManager;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class CdtTypeBoolean extends AConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        String booleanStr = cdtSection.getString(condition.getConfigName(), "");
        if(booleanStr.contains("%")){
            condition.setPlaceHolderCondition(Optional.of(booleanStr));
        }
        else condition.setCondition(cdtSection.getBoolean(condition.getConfigName(), false));
    }

    @Override
    public <T extends Condition> void saveIn(ConditionGUI gui, T condition) {
        condition.setCondition(gui.getBoolean(ConditionGUI.CONDITION));
    }

    @Override
    public <T extends Condition> void writeInConfig(T condition, ConfigurationSection cdtSection) {
        if(isDefined(condition)){
            if(condition.getPlaceHolderCondition().isPresent()){
                cdtSection.set(condition.getConfigName(), condition.getPlaceHolderCondition().get());
            }
            else cdtSection.set(condition.getConfigName(), true);
        }
    }

    @Override
    public void updateGUIContains(ConditionGUI gui, Object condition) {
        gui.updateBoolean(ConditionGUI.CONDITION, (Boolean)condition);
    }

    @Override
    public void updateGUINotContains(ConditionGUI gui) {
        gui.updateBoolean(ConditionGUI.CONDITION, false);
    }


    @Override
    public void clickGUI(ConditionGUIManager manager, Player player) {
        manager.getCache().get(player).changeBoolean(ConditionGUI.CONDITION);
    }


    @Override
    public void exitWithDelete(ConditionGUIManager manager, Player player) {

    }

    @Override
    public void exit(ConditionGUIManager manager, Player player) {

    }

    @Override
    public void deleteLine(ConditionGUIManager manager, Player player) {

    }

    @Override
    public void addLine(ConditionGUIManager manager, Player player, String message) {

    }


    @Override
    public <T extends Condition> boolean isDefined(T condition) {
        return (Boolean)condition.getCondition();
    }

    @Override
    public Boolean buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder) {
        boolean result = false;

        if(condition.getPlaceHolderCondition().isPresent()){
            try {
                String s = (String) condition.getPlaceHolderCondition().get();
                s = placeholder.replacePlaceholder(s);
                result = Boolean.valueOf(placeholder.replacePlaceholder(s));
            }catch (Exception e){}
        }
        else result = (Boolean)condition.getCondition();

        return result;
    }


}
