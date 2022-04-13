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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CdtTypeListEntityType extends AConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        buildCondition(cdtSection.getStringList(condition.getConfigName()), condition);
    }

    public void buildCondition(List<String> stringList, Condition condition) {
        List<EntityType> entityType = new ArrayList<>();
        List<String> entitiesPlaceholder = new ArrayList<>();

        for(String materialStr : stringList) {
            if(materialStr.startsWith("%")) {
                entitiesPlaceholder.add(materialStr);
                continue;
            }
            try {
                entityType.add(EntityType.valueOf(materialStr.toUpperCase()));
            } catch (Exception ignored) {}
        }
        condition.setCondition(entityType);
        if(entitiesPlaceholder.size() > 0) condition.setPlaceHolderCondition(Optional.of(entitiesPlaceholder));
    }

    @Override
    public <T extends Condition> void saveIn(ConditionGUI gui, T condition) {
        buildCondition(gui.getConditionList(ConditionGUI.CONDITION, "NO ENTITY_TYPE DEFINED"), condition);
    }

    @Override
    public <T extends Condition> void writeInConfig(T condition, ConfigurationSection cdtSection) {
        if(isDefined(condition)){
            List<String> toSave = new ArrayList<>();
            for(EntityType entityType : (List<EntityType>)condition.getCondition()) {
                toSave.add(entityType.name());
            }
            if(condition.getPlaceHolderCondition().isPresent()) {
                for (String s : (List<String>)condition.getPlaceHolderCondition().get()) {
                    toSave.add(s);
                }
            }
            cdtSection.set(condition.getConfigName(), toSave);
        }
    }

    @Override
    public void updateGUIContains(ConditionGUI gui, Object condition) {
        List<String> sentityList = new ArrayList<>();
        for(Object obj : (List)condition){
            if(obj instanceof EntityType){
                sentityList.add(((EntityType)obj).name());
            }
            else sentityList.add((String)obj);
        }
        gui.updateConditionList(ConditionGUI.CONDITION, sentityList, "&6➤ &eNO ENTITY_TYPE DEFINED");
    }

    @Override
    public void updateGUINotContains(ConditionGUI gui) {
        gui.updateConditionList(ConditionGUI.CONDITION, new ArrayList<>(), "&6➤ &eNO ENTITY_TYPE DEFINED");
    }


    @Override
    public void clickGUI(ConditionGUIManager manager, Player player) {
        manager.getRequestWriting().put(player, ConditionType.LIST_ENTITYTYPE.toString());
        if (!manager.getCurrentWriting().containsKey(player)) {
            manager.getCurrentWriting().put(player, manager.getCache().get(player).getConditionList(ConditionGUI.CONDITION, "NO ENTITY_TYPE DEFINED"));
        }
        player.closeInventory();
        manager.space(player);
        player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION " + manager.getCache().get(player).getCondition().getEditorName() + ":"));

        this.showEntityTypeEditor(manager, player);
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
        this.showEntityTypeEditor(manager, player);
    }

    @Override
    public void addLine(ConditionGUIManager manager, Player player, String message) {
        String uncoloredMessage = StringConverter.decoloredString(message).trim().toUpperCase();

        boolean error = false;
        if(uncoloredMessage.contains("%")) {
            if (!uncoloredMessage.isEmpty()) {
                try {
                    EntityType.valueOf(uncoloredMessage);
                    error = false;
                } catch (Exception e) {
                    error = true;
                }
            } else error = true;

            if (error){
                player.sendMessage(StringConverter.coloredString("&c&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &4&lERROR &cEnter a valid entity type please !"));
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
        player.sendMessage(StringConverter.coloredString("&a&l" + manager.getCache().get(player).getsPlugin().getNameDesign() + " &2&lEDITION &aYou have added new entity type!"));

        this.showEntityTypeEditor(manager, player);
    }


    @Override
    public <T extends Condition> boolean isDefined(T condition) {
        return condition.getCondition() != null && ((List<EntityType>)condition.getCondition()).size() > 0;
    }

    @Override
    public List<EntityType> buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder) {
        List<EntityType> result = new ArrayList<>((List<EntityType>)condition.getCondition());

        if(condition.getPlaceHolderCondition().isPresent()){
            for(String s : (List<String>)condition.getPlaceHolderCondition().get()){
                s = placeholder.replacePlaceholder(s);
                try {
                    EntityType biome = EntityType.valueOf(s);
                    result.add(biome);
                }   catch (Exception e){}
            }
        }

        return result;
    }

    public void showEntityTypeEditor(ConditionGUIManager manager, Player player) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ " + manager.getCache().get(player).getCondition().getConfigName() + ":");

        HashMap<String, String> suggestions = new HashMap<>();
        for(EntityType et : EntityType.values()) {
            suggestions.put("&6&l[&e&l"+et.name()+"&6&l]", et.name());
        }

        EditorCreator editor = new EditorCreator(beforeMenu, manager.getCurrentWriting().get(player), "EntityType", false, false, false, true, true, true, true, "&7&oYou can click on a entity type bellow to add it !", suggestions);
        editor.generateTheMenuAndSendIt(player);
    }
}
