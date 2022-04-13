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
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CdtTypeMapMaterialAmount extends AConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        condition.setCondition(transformMaterials(cdtSection.getStringList(condition.getConfigName()), condition, errorList, pluginName));
    }

    @Override
    public <T extends Condition> void saveIn(ConditionGUI gui, T condition) {
        Map<Material, Integer> results = transformMaterials(gui.getConditionList(ConditionGUI.CONDITION, "NO MATERIAL DEFINED"), condition, new ArrayList<>(), gui.getsPlugin().getNameDesign());
        condition.setCondition(results);
    }

    @Override
    public <T extends Condition> void writeInConfig(T condition, ConfigurationSection cdtSection) {
        if (isDefined(condition)) {
            List<String> toSave = new ArrayList<>();
            for (Material effectType : ((Map<Material, Integer>) condition.getCondition()).keySet()) {
                toSave.add(effectType.name() + ":" + ((Map<Material, Integer>) condition.getCondition()).get(effectType));
            }
            cdtSection.set(condition.getConfigName(), toSave);
        }
    }

    @Override
    public void updateGUIContains(ConditionGUI gui, Object condition) {
        List<String> effectsList = new ArrayList<>();
        if (condition instanceof Map) {
            for (Material effectType : ((Map<Material, Integer>) condition).keySet()) {
                effectsList.add(effectType.name() + ":" + ((Map<Material, Integer>) condition).get(effectType));
            }
        } else effectsList = (List<String>) condition;
        gui.updateConditionList(ConditionGUI.CONDITION, effectsList, "&6➤ &eNO MATERIAL DEFINED");
    }

    @Override
    public void updateGUINotContains(ConditionGUI gui) {
        gui.updateConditionList(ConditionGUI.CONDITION, new ArrayList<>(), "&6➤ &eNO MATERIAL DEFINED");
    }


    @Override
    public void clickGUI(ConditionGUIManager manager, Player player) {
        manager.getRequestWriting().put(player, ConditionType.MAP_MATERIAL_AMOUNT.toString());
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
        Boolean error = false;
        if (!uncoloredMessage.isEmpty()) {
            try {

                String potionName = uncoloredMessage.split(":")[0];
                String amplifier = uncoloredMessage.split(":")[1];

                Material material = Material.valueOf(potionName);
                Integer.parseInt(amplifier);

                if (manager.getCurrentWriting().containsKey(player))
                    manager.getCurrentWriting().get(player).add(uncoloredMessage);
                else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(uncoloredMessage);
                    manager.getCurrentWriting().put(player, list);
                }
                player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION &aYou have added new material!"));


            } catch (Exception e) {
                error = true;
            }
        } else error = true;

        if (error)
            player.sendMessage(StringConverter.coloredString("&c&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &4&lERROR &cEnter a valid material please ! &7&oExample: &b&lDIAMOND:3"));

        this.showMaterialEditor(manager, player);
    }

    @Override
    public <T extends Condition> boolean isDefined(T condition) {
        return condition.getCondition() != null && ((Map<PotionEffectType, Integer>) condition.getCondition()).size() > 0;
    }


    public static Map<Material, Integer> transformMaterials(List<String> enchantsConfig, Condition condition, List<String> errorList, String pluginName) {
        Map<Material, Integer> result = new HashMap<>();
        for (String s : enchantsConfig) {
            Material enchant;
            int level;
            String[] decomp;

            boolean error = false;
            if (s.contains(":")) {
                decomp = s.split(":");
                try {
                    enchant = Material.valueOf(decomp[0]);
                    level = Integer.parseInt(decomp[1]);
                    result.put(enchant, level);

                } catch (Exception e) {
                    error = true;
                }
                if (error)
                    errorList.add(pluginName + " Invalid argument for the " + condition.getConfigName() + " condition: " + s + " correct form > MATERIAL:AMOUNT  example> DIAMOND:3 !");
            }
        }
        return result;
    }

    @Override
    public Map<Material, Integer> buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder) {
        Map<Material, Integer> result = new HashMap<>((Map<Material, Integer>)condition.getCondition());

        if(condition.getPlaceHolderCondition().isPresent()){
            for(String s : ((Map<String, String>)condition.getPlaceHolderCondition().get()).keySet()){
                s = placeholder.replacePlaceholder(s);
                try {
                    Material material = Material.valueOf(s);

                    int amount = Integer.valueOf(((Map<String, String>)condition.getPlaceHolderCondition().get()).get(s));

                    result.put(material, amount);
                }   catch (Exception e){}
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
