package com.ssomar.score.conditions.condition.conditiontype.types;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.IConditionType;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CdtTypeMapEffectAmount implements IConditionType {
    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        Map<PotionEffectType, Integer> verifETP = new HashMap<>();
        for (String s : cdtSection.getStringList(condition.getConfigName())) {
            String[] spliter;
            if (s.contains(":") && (spliter = s.split(":")).length == 2) {
                int value = 0;
                PotionEffectType type = PotionEffectType.getByName(spliter[0]);
                if (type == null) {
                    errorList.add(pluginName + " Invalid argument for the "+condition.getConfigName()+" condition: " + s + " correct form > EFFECT:MINIMUM_AMPLIFIER_REQUIRED  example> SPEED:0 !");
                    continue;
                }

                try {
                    value = Integer.parseInt(spliter[1]);
                } catch (Exception e) {
                    errorList.add(pluginName + " Invalid argument for the "+condition.getConfigName()+" condition: " + s + " correct form > EFFECT:MINIMUM_AMPLIFIER_REQUIRED  example> SPEED:0 !");
                    continue;
                }
                verifETP.put(type, value);
            }
        }
        condition.setCondition(verifETP);
    }

    @Override
    public <T extends Condition> void save(T condition, ConfigurationSection cdtSection) {

    }

    @Override
    public void updateGUI(NewConditionGUI gui, NewConditions conditions, Condition condition) {

    }
}
