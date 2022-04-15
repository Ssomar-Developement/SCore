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
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CdtTypeMapEffectAmount extends AConditionType {
    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        condition.setCondition(transformEffects(cdtSection.getStringList(condition.getConfigName()), condition, errorList, pluginName));
    }

    @Override
    public <T extends Condition> void saveIn(ConditionGUI gui, T condition) {
        Map<PotionEffectType, Integer> results = transformEffects(gui.getConditionList(ConditionGUI.CONDITION, "NO EFFECT DEFINED"), condition, new ArrayList<>(), gui.getsPlugin().getNameDesign());
        condition.setCondition(results);
    }

    @Override
    public <T extends Condition> void writeInConfig(T condition, ConfigurationSection cdtSection) {
        if (isDefined(condition)) {
            List<String> toSave = new ArrayList<>();
            for (PotionEffectType effectType : ((Map<PotionEffectType, Integer>) condition.getCondition()).keySet()) {
                toSave.add(effectType.getName() + ":" + ((Map<PotionEffectType, Integer>) condition.getCondition()).get(effectType));
            }
            cdtSection.set(condition.getConfigName(), toSave);
        }
    }

    @Override
    public void updateGUIContains(ConditionGUI gui, Object condition) {
        List<String> effectsList = new ArrayList<>();
        if (condition instanceof Map) {
            for (PotionEffectType effectType : ((Map<PotionEffectType, Integer>) condition).keySet()) {
                effectsList.add(effectType.getName() + ":" + ((Map<PotionEffectType, Integer>) condition).get(effectType));
            }
        } else effectsList = (List<String>) condition;
        gui.updateConditionList(ConditionGUI.CONDITION, effectsList, "&6➤ &eNO EFFECT DEFINED");
    }

    @Override
    public void updateGUINotContains(ConditionGUI gui) {
        gui.updateConditionList(ConditionGUI.CONDITION, new ArrayList<>(), "&6➤ &eNO EFFECT DEFINED");
    }


    @Override
    public void clickGUI(ConditionGUIManager manager, Player player) {
        manager.getRequestWriting().put(player, ConditionType.MAP_EFFECT_AMOUNT.toString());
        if (!manager.getCurrentWriting().containsKey(player)) {
            manager.getCurrentWriting().put(player, manager.getCache().get(player).getConditionList(ConditionGUI.CONDITION, "NO EFFECT DEFINED"));
        }
        player.closeInventory();
        manager.space(player);
        player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION " + manager.getCache().get(player).getCondition().getEditorName() + ":"));

        this.showEffectEditor(manager, player);
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
        this.showEffectEditor(manager, player);
    }

    @Override
    public void addLine(ConditionGUIManager manager, Player player, String message) {
        String uncoloredMessage = StringConverter.decoloredString(message).trim().toUpperCase();

        boolean error = false;
        if (!uncoloredMessage.isEmpty()) {
            try {

                String potionName = uncoloredMessage.split(":")[0];
                String amplifier = uncoloredMessage.split(":")[1];

                if(PotionEffectType.getByName(potionName) == null){
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
                    player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION &aYou have added new effect!"));
                    error = false;
                }
            }
            catch (Exception e) {
                error = true;
            }
        } else error = true;

        if (error)
            player.sendMessage(StringConverter.coloredString("&c&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &4&lERROR &cEnter a valid effect please ! &7&oExample: &b&lSPEED:1"));

        this.showEffectEditor(manager, player);
    }

    @Override
    public <T extends Condition> boolean isDefined(T condition) {
        return condition.getCondition() != null && ((Map<PotionEffectType, Integer>) condition.getCondition()).size() > 0;
    }

    @Override
    public Map<PotionEffectType, Integer> buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder) {
        Map<PotionEffectType, Integer> result = new HashMap<>((Map<PotionEffectType, Integer>)condition.getCondition());

        if(condition.getPlaceHolderCondition().isPresent()){
            for(String s : ((Map<String, String>)condition.getPlaceHolderCondition().get()).keySet()){
                s = placeholder.replacePlaceholder(s);
                try {
                    PotionEffectType effect = PotionEffectType.getByName(s);
                    if(effect == null) continue;

                    int amplifier = Integer.valueOf(((Map<String, String>)condition.getPlaceHolderCondition().get()).get(s));

                    result.put(effect, amplifier);
                }   catch (Exception e){}
            }
        }

        return result;
    }

    public static Map<PotionEffectType, Integer> transformEffects(List<String> enchantsConfig, Condition condition, List<String> errorList, String pluginName) {
        Map<PotionEffectType, Integer> result = new HashMap<>();
        for (String s : enchantsConfig) {
            PotionEffectType effect;
            int level;
            String[] decomp;

            boolean error = false;
            if (s.contains(":")) {
                decomp = s.split(":");
                try {
                    effect = PotionEffectType.getByName(decomp[0]);
                    if(effect == null){
                        error = true;
                    }
                    else {
                        level = Integer.parseInt(decomp[1]);
                        result.put(effect, level);
                    }
                } catch (Exception e) {
                    error = true;
                }
                if (error) errorList.add(pluginName + " Invalid argument for the " + condition.getConfigName() + " condition: " + s + " correct form > EFFECT:MINIMUM_AMPLIFIER_REQUIRED  example> SPEED:0 !");
            }
        }
        return result;
    }

    public void showEffectEditor(ConditionGUIManager manager, Player player) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ " + manager.getCache().get(player).getCondition().getConfigName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, manager.getCurrentWriting().get(player), "Effect", false, false, false, true, true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(player);
    }

}
