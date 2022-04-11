package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.IConditionType;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CdtTypeMapMaterialAmount implements IConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        Map<Material, Integer> verifI = new HashMap<>();
        for (String s : cdtSection.getStringList(condition.getConfigName())) {
            String[] spliter;
            if (s.contains(":") && (spliter = s.split(":")).length == 2) {
                int slot = 0;
                Material material = null;
                try {
                    material = Material.valueOf(spliter[0]);
                } catch (Exception e) {
                    errorList.add(pluginName + " Invalid argument for the "+condition.getConfigName()+" condition: " + s + " correct form > MATERIAL:SLOT  example> DIAMOND:5 !");
                    continue;
                }
                try {
                    slot = Integer.parseInt(spliter[1]);
                } catch (Exception e) {
                    errorList.add(pluginName + " Invalid argument for the "+condition.getConfigName()+" condition: " + s + " correct form > MATERIAL:SLOT  example> DIAMOND:5 !");
                    continue;
                }
                verifI.put(material, slot);
            }
        }
        condition.setCondition(verifI);
    }

    @Override
    public <T extends Condition> void save(T condition, ConfigurationSection cdtSection) {

    }

    @Override
    public void updateGUI(NewConditionGUI gui, NewConditions conditions, Condition condition) {

    }
}
