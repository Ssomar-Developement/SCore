package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.SCore;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.AConditionType;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.general.ConditionGUI;
import com.ssomar.score.menu.conditions.general.ConditionGUIManager;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CdtTypeListRegion extends AConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        // TODO : check if the region is valid
        if (cdtSection.contains(condition.getConfigName())) {
            if (SCore.is1v12()) {
                errorList.add(pluginName + " Error the conditions " + condition.getConfigName() + " are not available in 1.12 due to a changement of worldguard API ");
            } else {
                buildCondition(cdtSection.getStringList(condition.getConfigName()), condition);
            }
        }
    }

    public void buildCondition(List<String> stringList, Condition condition) {
        List<String> regions = new ArrayList<>();
        List<String> regionsPlaceholder = new ArrayList<>();
        // TODO : check if the region is valid
        for (String biomeStr : stringList) {
            if (biomeStr.startsWith("%")) {
                regionsPlaceholder.add(biomeStr);
                continue;
            }
            try {
                regions.add(biomeStr);
            } catch (Exception ignored) {
            }
        }
        condition.setCondition(regions);
        if (regionsPlaceholder.size() > 0) condition.setPlaceHolderCondition(Optional.of(regionsPlaceholder));
    }

    @Override
    public <T extends Condition> void saveIn(ConditionGUI gui, T condition) {
        buildCondition(gui.getConditionList(ConditionGUI.CONDITION, "NO REGION DEFINED"), condition);
    }

    @Override
    public <T extends Condition> void writeInConfig(T condition, ConfigurationSection cdtSection) {
        if (isDefined(condition)) {
            List<String> toSave = new ArrayList<>((List<String>) condition.getCondition());
            if (condition.getPlaceHolderCondition().isPresent()) {
                toSave.addAll((List<String>) condition.getPlaceHolderCondition().get());
            }
            cdtSection.set(condition.getConfigName(), toSave);

        }
    }

    @Override
    public void updateGUIContains(ConditionGUI gui, Object condition) {
        gui.updateConditionList(ConditionGUI.CONDITION, (List<String>) condition, "&6➤ &eNO REGION DEFINED");
    }

    @Override
    public void updateGUINotContains(ConditionGUI gui) {
        gui.updateConditionList(ConditionGUI.CONDITION, new ArrayList<>(), "&6➤ &eNO REGION DEFINED");
    }


    @Override
    public void clickGUI(ConditionGUIManager manager, Player player) {
        manager.getRequestWriting().put(player, ConditionType.LIST_REGION.toString());
        if (!manager.getCurrentWriting().containsKey(player)) {
            manager.getCurrentWriting().put(player, manager.getCache().get(player).getConditionList(ConditionGUI.CONDITION, "NO REGION DEFINED"));
        }
        player.closeInventory();
        manager.space(player);
        player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION " + manager.getCache().get(player).getCondition().getEditorName() + ":"));

        this.showRegionEditor(manager, player);
        manager.space(player);
    }

    @Override
    public void exitWithDelete(ConditionGUIManager manager, Player player) {
        updateGUIContains(manager.getCache().get(player), new ArrayList<>());
    }

    @Override
    public void exit(ConditionGUIManager manager, Player player) {
        updateGUIContains(manager.getCache().get(player), new ArrayList<>(manager.getCurrentWriting().get(player)));
    }

    @Override
    public void deleteLine(ConditionGUIManager manager, Player player) {
        this.showRegionEditor(manager, player);
    }

    @Override
    public void addLine(ConditionGUIManager manager, Player player, String message) {
        String uncoloredMessage = StringConverter.decoloredString(message).trim().toUpperCase();

        // TODO : check if the region is valid
        boolean error = false;
        if (!uncoloredMessage.isEmpty()) {
            if (uncoloredMessage.equals("CLEAR") || uncoloredMessage.equals("STORM") || uncoloredMessage.equals("RAIN")) {
                if (manager.getCurrentWriting().containsKey(player))
                    manager.getCurrentWriting().get(player).add(uncoloredMessage);
                else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(uncoloredMessage);
                    manager.getCurrentWriting().put(player, list);
                }
                player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION &aYou have added new weather!"));
            } else error = true;
        } else error = true;

        if (error)
            player.sendMessage(StringConverter.coloredString("&c&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &4&lERROR &cEnter a valid weather (CLEAR, RAIN, STORM) please !"));

        this.showRegionEditor(manager, player);
    }


    @Override
    public <T extends Condition> boolean isDefined(T condition) {
        return condition.getCondition() != null && ((List<String>) condition.getCondition()).size() > 0;
    }

    @Override
    public List<String> buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder) {
        List<String> result = new ArrayList<>((List<String>) condition.getCondition());
// TODO : check if the region is valid
        if (condition.getPlaceHolderCondition().isPresent()) {
            for (String s : (List<String>) condition.getPlaceHolderCondition().get()) {
                s = placeholder.replacePlaceholder(s);
                if (s.contains("%")) {
                    result.add(s);
                }

            }
        }

        return result;
    }

    public void showRegionEditor(ConditionGUIManager manager, Player player) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ " + manager.getCache().get(player).getCondition().getConfigName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, manager.getCurrentWriting().get(player), "Region", false, false, false, true, true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(player);
    }

}
