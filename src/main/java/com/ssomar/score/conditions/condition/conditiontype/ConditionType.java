package com.ssomar.score.conditions.condition.conditiontype;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.types.*;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public enum ConditionType implements IConditionType{
    BOOLEAN,
    NUMBER_CONDITION,
    LIST_WEATHER,
    LIST_MATERIAL,
    LIST_ENTITYTYPE,
    LIST_WORLD,
    LIST_BIOME,
    LIST_REGION,
    LIST_PERMISSION,
    LIST_STRING,
    MAP_MATERIAL_AMOUNT,
    MAP_ENCHANT_AMOUNT,
    MAP_EFFECT_AMOUNT,
    CUSTOM_AROUND_BLOCK,
    CUSTOM_HAS_EXECUTABLE_ITEM;


    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        switch (this){
            case BOOLEAN:
                new CdtTypeBoolean().load(condition, cdtSection, errorList, pluginName);
                break;
            case NUMBER_CONDITION:
                new CdtTypeNumberCondition().load(condition, cdtSection, errorList, pluginName);
                break;
            case LIST_WEATHER:
                new CdtTypeListWeather().load(condition, cdtSection, errorList, pluginName);
                break;
            case LIST_MATERIAL:
                new CdtTypeListMaterial().load(condition, cdtSection, errorList, pluginName);
                break;
            case LIST_ENTITYTYPE:
                new CdtTypeListEntityType().load(condition, cdtSection, errorList, pluginName);
                break;
            case LIST_WORLD:
                new CdtTypeListWorld().load(condition, cdtSection, errorList, pluginName);
                break;
            case LIST_BIOME:
                new CdtTypeListBiome().load(condition, cdtSection, errorList, pluginName);
                break;
            case LIST_REGION:
                new CdtTypeListRegion().load(condition, cdtSection, errorList, pluginName);
                break;
            case LIST_PERMISSION:
                new CdtTypeListPermission().load(condition, cdtSection, errorList, pluginName);
                break;
            case LIST_STRING:
                new CdtTypeListString().load(condition, cdtSection, errorList, pluginName);
                break;
            case MAP_MATERIAL_AMOUNT:
                new CdtTypeMapMaterialAmount().load(condition, cdtSection, errorList, pluginName);
                break;
            case MAP_EFFECT_AMOUNT:
                new CdtTypeMapEffectAmount().load(condition, cdtSection, errorList, pluginName);
                break;
            case CUSTOM_AROUND_BLOCK:
                new CdtTypeCustomAroundBlock().load(condition, cdtSection, errorList, pluginName);
                break;
            case CUSTOM_HAS_EXECUTABLE_ITEM:
                new CdtTypeCustomHasExecutableItem().load(condition, cdtSection, errorList, pluginName);
                break;
        }
    }

    @Override
    public <T extends Condition> void save(T condition, ConfigurationSection cdtSection) {

    }

    @Override
    public void updateGUI(NewConditionGUI gui, NewConditions conditions, Condition condition) {

    }
}
