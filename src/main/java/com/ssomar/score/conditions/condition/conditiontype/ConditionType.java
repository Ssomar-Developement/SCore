package com.ssomar.score.conditions.condition.conditiontype;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.types.*;
import com.ssomar.score.menu.conditions.general.ConditionGUI;
import com.ssomar.score.menu.conditions.general.ConditionGUIManager;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public enum ConditionType implements IConditionType {
    BOOLEAN(new CdtTypeBoolean()),
    NUMBER_CONDITION(new CdtTypeNumberCondition()),
    LIST_WEATHER(new CdtTypeListWeather()),
    LIST_MATERIAL(new CdtTypeListMaterial()),
    LIST_ENTITYTYPE(new CdtTypeListEntityType()),
    LIST_WORLD(new CdtTypeListWorld()),
    LIST_BIOME(new CdtTypeListBiome()),
    LIST_REGION(new CdtTypeListRegion()),
    LIST_PERMISSION(new CdtTypeListPermission()),
    LIST_STRING(new CdtTypeListString()),
    MAP_MATERIAL_AMOUNT(new CdtTypeMapMaterialAmount()),
    MAP_ENCHANT_AMOUNT(new CdtTypeMapEnchantAmount()),
    MAP_EFFECT_AMOUNT(new CdtTypeMapEffectAmount()),
    CUSTOM_AROUND_BLOCK(new CdtTypeCustomAroundBlock()),
    CUSTOM_HAS_EXECUTABLE_ITEM(new CdtTypeCustomHasExecutableItem());

    @Getter
    IConditionType instance;


    ConditionType(IConditionType instance) {
        this.instance = instance;
    }

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        instance.load(condition, cdtSection, errorList, pluginName);
    }

    @Override
    public <T extends Condition> void saveIn(ConditionGUI gui, T condition) {
        instance.saveIn(gui, condition);
    }

    @Override
    public <T extends Condition> void writeInConfig(T condition, ConfigurationSection cdtSection) {
        instance.writeInConfig(condition, cdtSection);
    }

    @Override
    public void updateGUI(ConditionGUI gui, Conditions conditions, Condition condition) {
         instance.updateGUI(gui, conditions, condition);
    }

    @Override
    public void updateGUIContains(ConditionGUI gui, Object condition) {
        instance.updateGUIContains(gui, condition);
    }

    @Override
    public void updateGUINotContains(ConditionGUI gui) {
        instance.updateGUINotContains(gui);
    }


    @Override
    public void clickGUI(ConditionGUIManager manager, Player player) {
        instance.clickGUI(manager, player);
    }


    @Override
    public void exitWithDelete(ConditionGUIManager manager, Player player) {
       instance.exitWithDelete(manager, player);
    }

    @Override
    public void exit(ConditionGUIManager manager, Player player) {
        instance.exit(manager, player);
    }

    @Override
    public void deleteLine(ConditionGUIManager manager, Player player) {
        instance.deleteLine(manager, player);
    }

    @Override
    public void addLine(ConditionGUIManager manager, Player player, String message) {
        instance.addLine(manager, player, message);
    }

    @Override
    public void loadParameterEnterByTheUserGUI(ConditionGUIManager guiManager, Player player, String message) {
        instance.loadParameterEnterByTheUserGUI(guiManager, player, message);
    }

    @Override
    public <T extends Condition> boolean isDefined(T condition) {
        return instance.isDefined(condition) || condition.getPlaceHolderCondition().isPresent();
    }

    @Override
    public <X> X buildConditionPlaceholder(Condition condition, StringPlaceholder placeholder) {
        return instance.buildConditionPlaceholder(condition, placeholder);
    }

}
