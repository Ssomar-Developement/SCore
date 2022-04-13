package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.AConditionType;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.general.ConditionGUI;
import com.ssomar.score.menu.conditions.general.ConditionGUIManager;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CdtTypeListMaterial extends AConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        buildCondition(cdtSection.getStringList(condition.getConfigName()), condition);
    }

    public void buildCondition(List<String> stringList, Condition condition) {
        List<Material> materials = new ArrayList<>();
        List<String> materialsPlaceholder = new ArrayList<>();

        for (String materialStr : stringList) {
            if (materialStr.startsWith("%")) {
                materialsPlaceholder.add(materialStr);
                continue;
            }
            try {
                materials.add(Material.valueOf(materialStr.toUpperCase()));
            } catch (Exception ignored) {
            }
        }
        condition.setCondition(materials);
        if (materialsPlaceholder.size() > 0) condition.setPlaceHolderCondition(Optional.of(materialsPlaceholder));
    }

    @Override
    public <T extends Condition> void saveIn(ConditionGUI gui, T condition) {
        buildCondition(gui.getConditionList(ConditionGUI.CONDITION, "NO MATERIAL DEFINED"), condition);
    }

    @Override
    public <T extends Condition> void writeInConfig(T condition, ConfigurationSection cdtSection) {
        if (isDefined(condition)) {
            List<String> toSave = new ArrayList<>();
            for (Material material : (List<Material>) condition.getCondition()) {
                toSave.add(material.name());
            }
            if (condition.getPlaceHolderCondition().isPresent()) {
                for (String s : (List<String>) condition.getPlaceHolderCondition().get()) {
                    toSave.add(s);
                }
            }
            cdtSection.set(condition.getConfigName(), toSave);
        }
    }

    @Override
    public void updateGUIContains(ConditionGUI gui, Object condition) {
        List<String> materialList = new ArrayList<>();
        for (Object obj : (List) condition) {
            if (obj instanceof Material) {
                materialList.add(((Material) obj).name());
            } else materialList.add((String) obj);
        }
        gui.updateConditionList(ConditionGUI.CONDITION, materialList, "&6➤ &eNO MATERIAL DEFINED");
    }

    @Override
    public void updateGUINotContains(ConditionGUI gui) {
        gui.updateConditionList(ConditionGUI.CONDITION, new ArrayList<>(), "&6➤ &eNO MATERIAL DEFINED");
    }


    @Override
    public void clickGUI(ConditionGUIManager manager, Player player) {
        manager.getRequestWriting().put(player, ConditionType.LIST_MATERIAL.toString());
        if (!manager.getCurrentWriting().containsKey(player)) {
            manager.getCurrentWriting().put(player, manager.getCache().get(player).getConditionList(ConditionGUI.CONDITION, "NO MATERIAL DEFINED"));
        }
        player.closeInventory();
        manager.space(player);
        player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION " + manager.getCache().get(player).getCondition().getEditorName() + ":"));

        this.showMaterialEditor(manager, player);
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
        this.showMaterialEditor(manager, player);
    }

    @Override
    public void addLine(ConditionGUIManager manager, Player player, String message) {
        String uncoloredMessage = StringConverter.decoloredString(message).trim().toUpperCase();

        boolean error = false;
        if (uncoloredMessage.contains("%")) {
            if (!uncoloredMessage.isEmpty()) {
                try {
                    Material.valueOf(uncoloredMessage);
                    error = false;
                } catch (Exception e) {
                    error = true;
                }
            } else error = true;

            if (error) {
                player.sendMessage(StringConverter.coloredString("&c&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &4&lERROR &cEnter a valid material please !"));
                return;
            }
        }
        if (manager.getCurrentWriting().containsKey(player))
            manager.getCurrentWriting().get(player).add(uncoloredMessage);
        else {
            ArrayList<String> list = new ArrayList<>();
            list.add(uncoloredMessage);
            manager.getCurrentWriting().put(player, list);
        }
        player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION &aYou have added new material!"));

        this.showMaterialEditor(manager, player);
    }


    @Override
    public <T extends Condition> boolean isDefined(T condition) {
        return condition.getCondition() != null && ((List<Material>) condition.getCondition()).size() > 0;
    }

    @Override
    public List<Material> buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder) {
        List<Material> result = new ArrayList<>((List<Material>) condition.getCondition());

        if (condition.getPlaceHolderCondition().isPresent()) {
            for (String s : (List<String>) condition.getPlaceHolderCondition().get()) {
                s = placeholder.replacePlaceholder(s);
                try {
                    Material biome = Material.valueOf(s);
                    result.add(biome);
                } catch (Exception e) {
                }
            }
        }

        return result;
    }

    public void showMaterialEditor(ConditionGUIManager manager, Player player) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ " + manager.getCache().get(player).getCondition().getConfigName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, manager.getCurrentWriting().get(player), "Material", false, false, false, true, true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(player);
    }

}
