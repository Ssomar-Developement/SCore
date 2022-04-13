package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.AConditionType;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.menu.conditions.general.ConditionGUI;
import com.ssomar.score.menu.conditions.general.ConditionGUIManager;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class CdtTypeNumberCondition extends AConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        String conditionString = cdtSection.getString(condition.getConfigName());
        if (conditionString.contains("%")) {
            condition.setPlaceHolderCondition(Optional.of(conditionString));
        } else condition.setCondition(cdtSection.getString(condition.getConfigName(), ""));
    }

    @Override
    public <T extends Condition> void saveIn(ConditionGUI gui, T condition) {
        String conditionString = gui.getCondition(ConditionGUI.CONDITION);
        if (conditionString.contains("%")) {
            condition.setPlaceHolderCondition(Optional.of(conditionString));
        } else condition.setCondition(gui.getCondition(ConditionGUI.CONDITION));
    }

    @Override
    public <T extends Condition> void writeInConfig(T condition, ConfigurationSection cdtSection) {
        if (((String) condition.getCondition()).trim().length() > 0) {
            if (condition.getPlaceHolderCondition().isPresent()) {
                cdtSection.set(condition.getConfigName(), condition.getPlaceHolderCondition().get());
            } else cdtSection.set(condition.getConfigName(), (String) condition.getCondition());
        }
    }

    @Override
    public void updateGUIContains(ConditionGUI gui, Object condition) {
        gui.updateCondition(ConditionGUI.CONDITION, (String) condition);
    }

    @Override
    public void updateGUINotContains(ConditionGUI gui) {
        gui.updateCondition(ConditionGUI.CONDITION, "");
    }


    @Override
    public void clickGUI(ConditionGUIManager manager, Player player) {
        manager.getRequestWriting().put(player, ConditionType.NUMBER_CONDITION.toString());
        player.closeInventory();
        manager.space(player);
        player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION " + manager.getCache().get(player).getCondition().getEditorName() + ": &7&o(Example: &6>6 &7&o, &6 4 < CONDITION <= 8 &7&o)"));

        manager.showCalculationGUI(player, "Condition", manager.getCache().get(player).getCondition(ConditionGUI.CONDITION));
        manager.space(player);
    }

    @Override
    public void exitWithDelete(ConditionGUIManager manager, Player player) {
        updateGUIContains(manager.getCache().get(player), "");
    }

    @Override
    public void exit(ConditionGUIManager manager, Player player) {

    }

    @Override
    public void deleteLine(ConditionGUIManager manager, Player player) {

    }

    @Override
    public void addLine(ConditionGUIManager manager, Player player, String message) {

        String uncolored = StringConverter.decoloredString(message);
        if (StringCalculation.isStringCalculation(uncolored) || uncolored.contains("%")) {
            updateGUIContains(manager.getCache().get(player), uncolored);
            manager.getRequestWriting().remove(player);
            manager.getCache().get(player).openGUISync(player);
        } else
            player.sendMessage(StringConverter.coloredString("&c&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &4&lERROR &cEnter a condition ! &7&o(Example: &6>6 &7&o, &6 4 < CONDITION <= 8 &7&o)"));
    }

    @Override
    public <T extends Condition> boolean isDefined(T condition) {
        return condition.getCondition() != null && ((String) condition.getCondition()).trim().length() > 0;
    }

    @Override
    public String buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder) {
        String result = "";

        if (condition.getPlaceHolderCondition().isPresent()) {
            try {
                String s = (String) condition.getPlaceHolderCondition().get();
                s = placeholder.replacePlaceholder(s);
                if (StringCalculation.isStringCalculation(s)) result = s;
            } catch (Exception e) {
            }
        }

        return result;
    }


}
