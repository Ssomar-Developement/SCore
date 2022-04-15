package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.AConditionType;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.general.ConditionGUI;
import com.ssomar.score.menu.conditions.general.ConditionGUIManager;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CdtTypeListBiome extends AConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        buildCondition(cdtSection.getStringList(condition.getConfigName()), condition);
    }

    public void buildCondition(List<String> stringList, Condition condition) {
        List<Biome> biomes = new ArrayList<>();
        List<String> biomesPlaceholder = new ArrayList<>();

        for (String biomeStr : stringList) {
            if (biomeStr.startsWith("%")) {
                biomesPlaceholder.add(biomeStr);
                continue;
            }
            try {
                biomes.add(Biome.valueOf(biomeStr.toUpperCase()));
            } catch (Exception ignored) {
            }
        }
        condition.setCondition(biomes);
        if (biomesPlaceholder.size() > 0) condition.setPlaceHolderCondition(Optional.of(biomesPlaceholder));
    }

    @Override
    public <T extends Condition> void saveIn(ConditionGUI gui, T condition) {
        buildCondition(gui.getConditionList(ConditionGUI.CONDITION, "NO BIOME DEFINED"), condition);
    }

    @Override
    public <T extends Condition> void writeInConfig(T condition, ConfigurationSection cdtSection) {
        if (isDefined(condition)) {
            List<String> toSave = new ArrayList<>();
            for (Biome biome : (List<Biome>) condition.getCondition()) {
                toSave.add(biome.name());
            }
            if (condition.getPlaceHolderCondition().isPresent()) {
                toSave.addAll((List<String>) condition.getPlaceHolderCondition().get());
            }
            cdtSection.set(condition.getConfigName(), toSave);
        }
    }

    @Override
    public void updateGUIContains(ConditionGUI gui, Object condition) {
        List<String> biomeList = new ArrayList<>();
        for (Object obj : (List) condition) {
            if (obj instanceof Biome) {
                biomeList.add(((Biome) obj).name());
            } else biomeList.add((String) obj);
        }
        gui.updateConditionList(ConditionGUI.CONDITION, biomeList, "&6➤ &eNO BIOME DEFINED");
    }

    @Override
    public void updateGUINotContains(ConditionGUI gui) {
        gui.updateConditionList(ConditionGUI.CONDITION, new ArrayList<>(), "&6➤ &eNO BIOME DEFINED");
    }


    @Override
    public void clickGUI(ConditionGUIManager manager, Player player) {
        manager.getRequestWriting().put(player, ConditionType.LIST_BIOME.toString());
        if (!manager.getCurrentWriting().containsKey(player)) {
            manager.getCurrentWriting().put(player, manager.getCache().get(player).getConditionList(ConditionGUI.CONDITION, "NO BIOME DEFINED"));
        }
        player.closeInventory();
        manager.space(player);
        player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION " + manager.getCache().get(player).getCondition().getEditorName() + ":"));

        this.showBiomeEditor(manager, player);
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
        this.showBiomeEditor(manager, player);
    }

    @Override
    public void addLine(ConditionGUIManager manager, Player player, String message) {
        String uncoloredMessage = StringConverter.decoloredString(message).trim().toUpperCase();

        boolean error = false;
        if (!uncoloredMessage.contains("%")) {
            if (!uncoloredMessage.isEmpty()) {
                try {
                    Biome.valueOf(uncoloredMessage);
                    error = false;
                } catch (Exception e) {
                    error = true;
                }
            } else error = true;
        }

        if (error) {
            player.sendMessage(StringConverter.coloredString("&c&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &4&lERROR &cEnter a valid biome please !"));
            return;
        }
        if (manager.getCurrentWriting().containsKey(player))
            manager.getCurrentWriting().get(player).add(uncoloredMessage);
        else {
            ArrayList<String> list = new ArrayList<>();
            list.add(uncoloredMessage);
            manager.getCurrentWriting().put(player, list);
        }
        player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION &aYou have added new biome!"));

        this.showBiomeEditor(manager, player);
    }


    @Override
    public <T extends Condition> boolean isDefined(T condition) {
        return condition.getCondition() != null && ((List<Biome>) condition.getCondition()).size() > 0;
    }

    @Override
    public List<Biome> buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder) {
        List<Biome> result = new ArrayList<>((List<Biome>) condition.getCondition());

        if (condition.getPlaceHolderCondition().isPresent()) {
            for (String s : (List<String>) condition.getPlaceHolderCondition().get()) {
                s = placeholder.replacePlaceholder(s);
                try {
                    Biome biome = Biome.valueOf(s);
                    result.add(biome);
                } catch (Exception e) {
                }
            }
        }

        return result;
    }

    public void showBiomeEditor(ConditionGUIManager manager, Player player) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ " + manager.getCache().get(player).getCondition().getConfigName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();
        for (Biome biome : Biome.values()) {
            suggestions.put("&6&l[&e&l" + biome.name() + "&6&l]", biome.name());
        }

        EditorCreator editor = new EditorCreator(beforeMenu, manager.getCurrentWriting().get(player), "Biome", false, false, false, true, true, true, true, "&7&oYou can click on a biome bellow to add it !", suggestions);
        editor.generateTheMenuAndSendIt(player);
    }


}
