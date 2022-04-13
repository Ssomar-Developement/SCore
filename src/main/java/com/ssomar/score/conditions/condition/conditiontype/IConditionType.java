package com.ssomar.score.conditions.condition.conditiontype;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.menu.conditions.general.ConditionGUI;
import com.ssomar.score.menu.conditions.general.ConditionGUIManager;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public interface IConditionType {

    public abstract <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName);

    public abstract <T extends Condition> void saveIn(ConditionGUI gui, T condition);

    public abstract <T extends Condition> void writeInConfig(T condition, ConfigurationSection cdtSection);

    public abstract void updateGUI(ConditionGUI gui, Conditions conditions, Condition condition);

    public abstract void updateGUIContains(ConditionGUI gui, Object condition);

    public abstract void updateGUINotContains(ConditionGUI gui);

    public abstract void clickGUI(ConditionGUIManager manager, Player player);

    public abstract void exitWithDelete(ConditionGUIManager manager, Player player);

    public abstract void exit(ConditionGUIManager manager, Player player);

    public abstract void deleteLine(ConditionGUIManager manager, Player player);

    public abstract void addLine(ConditionGUIManager manager, Player player, String message);

    public abstract void loadParameterEnterByTheUserGUI(ConditionGUIManager guiManager, Player player, String message);

    public <T extends Condition> boolean isDefined(T condition);

    public <X> X buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder);
}
