package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockConditions;
import com.ssomar.score.conditions.condition.conditiontype.AConditionTypeWithSubMenu;
import com.ssomar.score.conditions.condition.player.custom.IfPlayerHasExecutableItem;
import com.ssomar.score.conditions.condition.player.custom.IfPlayerHasExecutableItems;
import com.ssomar.score.menu.conditions.blockaroundcdt.AroundBlockConditionsGUIManager;
import com.ssomar.score.menu.conditions.general.ConditionGUI;
import com.ssomar.score.menu.conditions.general.ConditionGUIManager;
import com.ssomar.score.menu.conditions.general.ConditionsGUI;
import com.ssomar.score.menu.conditions.general.ConditionsGUIManager;
import com.ssomar.score.menu.conditions.hasexecutableitemcdt.IfHasExecutableItemConditionsGUIManager;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CdtTypeCustomHasExecutableItem extends AConditionTypeWithSubMenu {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        List<IfPlayerHasExecutableItem> verifEI = new ArrayList<>();
        if (cdtSection.contains(condition.getConfigName())) {
            ConfigurationSection sec = cdtSection.getConfigurationSection(condition.getConfigName());
            if (sec != null && sec.getKeys(false).size() > 0) {
                for (String s : sec.getKeys(false)) {
                    IfPlayerHasExecutableItem cdt = new IfPlayerHasExecutableItem(s, sec.getConfigurationSection(s));
                    if (!cdt.isValid())
                        errorList.add(pluginName + " Invalid configuration of "+condition.getConfigName()+" with id : " + s + " !");
                    verifEI.add(cdt);
                }
            }
        }
        condition.setCondition(verifEI);
    }

    @Override
    public <T extends Condition> void saveIn(ConditionGUI gui, T condition) {

    }

    @Override
    public <T extends Condition> void writeInConfig(T condition, ConfigurationSection cdtSection) {

    }

    @Override
    public void updateGUIContains(ConditionGUI gui, Object condition) {

    }

    @Override
    public void updateGUINotContains(ConditionGUI gui) {

    }


    @Override
    public void clickGUI(ConditionGUIManager manager, Player player) {

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
        return condition.getCondition() != null && ((List<IfPlayerHasExecutableItem>)condition.getCondition()).size() > 0;
    }

    @Override
    public <X> X buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder) {
        return null;
    }


    @Override
    public void openSubMenu(ConditionsGUIManager manager, Condition condition, Player player) {
        ConditionsGUI gui = manager.getCache().get(player);
        IfHasExecutableItemConditionsGUIManager.getInstance().startEditing(player, gui.getsPlugin(), gui.getSObject(), gui.getSAct(), gui.getConditionsManager(), gui.getConditions(), (IfPlayerHasExecutableItems) condition, gui.getDetail());
        manager.getCache().remove(player);
        manager.getRequestWriting().remove(player);
    }
}
