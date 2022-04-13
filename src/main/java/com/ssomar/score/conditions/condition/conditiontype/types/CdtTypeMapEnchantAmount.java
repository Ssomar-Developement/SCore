package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.AConditionType;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.general.ConditionGUI;
import com.ssomar.score.menu.conditions.general.ConditionGUIManager;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CdtTypeMapEnchantAmount extends AConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        condition.setCondition(transformEnchants(cdtSection.getStringList(condition.getConfigName()), condition, errorList, pluginName));
    }

    @Override
    public <T extends Condition> void saveIn(ConditionGUI gui, T condition) {
        Map<Enchantment, Integer> results = transformEnchants(gui.getConditionList(ConditionGUI.CONDITION, "NO ENCHANT DEFINED"), condition, new ArrayList<>(), gui.getsPlugin().getNameDesign());
        condition.setCondition(results);
    }

    @Override
    public <T extends Condition> void writeInConfig(T condition, ConfigurationSection cdtSection) {
        if (isDefined(condition)) {
            List<String> toSave = new ArrayList<>();
            for (Enchantment effectType : ((Map<Enchantment, Integer>) condition.getCondition()).keySet()) {
                toSave.add(effectType.getName() + ":" + ((Map<Enchantment, Integer>) condition.getCondition()).get(effectType));
            }
            cdtSection.set(condition.getConfigName(), toSave);
        }
    }

    @Override
    public void updateGUIContains(ConditionGUI gui, Object condition) {
        List<String> effectsList = new ArrayList<>();
        if (condition instanceof Map) {
            for (Enchantment effectType : ((Map<Enchantment, Integer>) condition).keySet()) {
                effectsList.add(effectType.getName() + ":" + ((Map<PotionEffectType, Integer>) condition).get(effectType));
            }
        } else effectsList = (List<String>) condition;
        gui.updateConditionList(ConditionGUI.CONDITION, effectsList, "&6➤ &eNO ENCHANT DEFINED");
    }

    @Override
    public void updateGUINotContains(ConditionGUI gui) {
        gui.updateConditionList(ConditionGUI.CONDITION, new ArrayList<>(), "&6➤ &eNO ENCHANT DEFINED");
    }


    @Override
    public void clickGUI(ConditionGUIManager manager, Player player) {
        manager.getRequestWriting().put(player, ConditionType.MAP_ENCHANT_AMOUNT.toString());
        if (!manager.getCurrentWriting().containsKey(player)) {
            manager.getCurrentWriting().put(player, manager.getCache().get(player).getConditionList(ConditionGUI.CONDITION, "NO ENCHANT DEFINED"));
        }
        player.closeInventory();
        manager.space(player);
        player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION " + manager.getCache().get(player).getCondition().getEditorName() + ":"));

        this.showEnchantEditor(manager, player);
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
        this.showEnchantEditor(manager, player);
    }

    @Override
    public void addLine(ConditionGUIManager manager, Player player, String message) {
        String uncoloredMessage = StringConverter.decoloredString(message).trim().toUpperCase();

        boolean error = false;
        if (!uncoloredMessage.isEmpty()) {
            try {

                String potionName = uncoloredMessage.split(":")[0];
                String amplifier = uncoloredMessage.split(":")[1];

                if(Enchantment.getByName(potionName) == null){
                    error = true;
                }
                else {
                    Integer.parseInt(amplifier);

                    if (manager.getCurrentWriting().containsKey(player))
                        manager.getCurrentWriting().get(player).add(uncoloredMessage);
                    else {
                        ArrayList<String> list = new ArrayList<>();
                        list.add(uncoloredMessage);
                        manager.getCurrentWriting().put(player, list);
                    }
                    player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION &aYou have added new enchant!"));
                    error = false;
                }
            }
            catch (Exception e) {
                error = true;
            }
        } else error = true;

        if (error)
            player.sendMessage(StringConverter.coloredString("&c&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &4&lERROR &cEnter a valid enchant please ! &7&oExample: &b&lPOWER:1"));

        this.showEnchantEditor(manager, player);
    }

    @Override
    public <T extends Condition> boolean isDefined(T condition) {
        return condition.getCondition() != null && ((Map<PotionEffectType, Integer>)condition.getCondition()).size() > 0;
    }

    @Override
    public Map<Enchantment, Integer> buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder) {
        Map<Enchantment, Integer> result = new HashMap<>((Map<Enchantment, Integer>)condition.getCondition());

        if(condition.getPlaceHolderCondition().isPresent()){
            for(String s : ((Map<String, String>)condition.getPlaceHolderCondition().get()).keySet()){
                s = placeholder.replacePlaceholder(s);
                try {
                    Enchantment enchant = Enchantment.getByName(s);
                    if(enchant == null) continue;

                    int level = Integer.valueOf(((Map<String, String>)condition.getPlaceHolderCondition().get()).get(s));

                    result.put(enchant, level);
                }   catch (Exception e){}
            }
        }

        return result;
    }

    public static Map<Enchantment, Integer> transformEnchants(List<String> enchantsConfig, Condition condition, List<String> errorList, String pluginName){
        Map<Enchantment, Integer> result = new HashMap<>();
        for(String s : enchantsConfig){
            Enchantment enchant;
            int level;
            String [] decomp;

            boolean error = false;
            if (s.contains(":")) {
                decomp = s.split(":");
                try {
                    enchant = Enchantment.getByName(decomp[0]);
                    if(enchant == null){
                        error = true;
                    }
                    else {
                        level = Integer.parseInt(decomp[1]);
                        result.put(enchant, level);
                    }
                } catch (Exception e) {
                    error = true;
                }
                if (error) errorList.add(pluginName + " Invalid argument for the " + condition.getConfigName() + " condition: " + s + " correct form > ENCHANTMENT:LEVEL  example> POWER:1 !");
            }
        }
        return result;
    }

    public void showEnchantEditor(ConditionGUIManager manager, Player player) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ " + manager.getCache().get(player).getCondition().getConfigName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, manager.getCurrentWriting().get(player), "Enchant", false, false, false, true, true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(player);
    }
}
