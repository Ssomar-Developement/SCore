package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.IConditionType;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class CdtTypeListMaterial implements IConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        List<Material> materials = new ArrayList<>();
        for(String materialStr : cdtSection.getStringList(condition.getConfigName())) {
            try {
                materials.add(Material.valueOf(materialStr.toUpperCase()));
            } catch (Exception ignored) {}
        }
        condition.setCondition(materials);
    }

    @Override
    public <T extends Condition> void save(T condition, ConfigurationSection cdtSection) {
        if(!((List<Material>)condition.getCondition()).isEmpty()){
            List<String> toSave = new ArrayList<>();
            for(Material material : (List<Material>)condition.getCondition()) {
                toSave.add(material.name());
            }
            cdtSection.set(condition.getConfigName(), toSave);
        }
        else{
            cdtSection.set(condition.getConfigName(), null);
            cdtSection.set(condition.getConfigName()+"Msg", null);
        }
    }

    @Override
    public void updateGUI(NewConditionGUI gui, NewConditions conditions, Condition condition) {

    }
}
