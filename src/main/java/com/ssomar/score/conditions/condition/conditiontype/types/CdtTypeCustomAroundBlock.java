package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockCondition;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockConditions;
import com.ssomar.score.conditions.condition.conditiontype.AConditionTypeWithSubMenu;
import com.ssomar.score.menu.conditions.blockaroundcdt.AroundBlockConditionsGUIManager;
import com.ssomar.score.menu.conditions.general.ConditionGUI;
import com.ssomar.score.menu.conditions.general.ConditionGUIManager;
import com.ssomar.score.menu.conditions.general.ConditionsGUI;
import com.ssomar.score.menu.conditions.general.ConditionsGUIManager;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CdtTypeCustomAroundBlock extends AConditionTypeWithSubMenu {
    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        List<AroundBlockCondition> aroundBlockConditions = new ArrayList<>();
        for(String s : cdtSection.getConfigurationSection("blockAroundCdts").getKeys(false)) {
            ConfigurationSection section = cdtSection.getConfigurationSection("blockAroundCdts."+s);
            aroundBlockConditions.add(AroundBlockCondition.get(section));
        }
        condition.setCondition(aroundBlockConditions);
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
        return condition.getCondition() != null && ((List<AroundBlockCondition>)condition.getCondition()).size() > 0;
    }

    @Override
    public <X> X buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder) {
        return null;
    }


    @Override
    public void openSubMenu(ConditionsGUIManager manager, Condition condition, Player player) {
        ConditionsGUI gui = manager.getCache().get(player);
        AroundBlockConditionsGUIManager.getInstance().startEditing(player, gui.getsPlugin(), gui.getSObject(), gui.getSAct(), gui.getConditionsManager(), gui.getConditions(), (AroundBlockConditions) condition, gui.getDetail());
        manager.getCache().remove(player);
        manager.getRequestWriting().remove(player);
    }
}
