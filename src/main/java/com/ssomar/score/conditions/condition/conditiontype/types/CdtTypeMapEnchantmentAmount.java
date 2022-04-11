package com.ssomar.score.conditions.condition.conditiontype.types;

import com.iridium.iridiumskyblock.dependencies.ormlite.stmt.query.In;
import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.IConditionType;
import com.ssomar.score.menu.conditions.clean.NewConditionGUI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CdtTypeMapEnchantmentAmount implements IConditionType {

    @Override
    public <T extends Condition> void load(T condition, ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
        Map<Enchantment, Integer> enchants = transformEnchants(cdtSection.getStringList(condition.getConfigName()));
        condition.setCondition(enchants);
    }

    @Override
    public <T extends Condition> void save(T condition, ConfigurationSection cdtSection) {
        Map<Enchantment, Integer> enchants = (Map<Enchantment, Integer>) condition.getCondition();
        if (enchants != null && enchants.size() != 0) {
            List<String> result = new ArrayList<>();
            for(Enchantment enchant : enchants.keySet()){
                result.add(enchant.getName() +":"+enchants.get(enchant));
            }
            cdtSection.set(condition.getConfigName(), result);
        }
        else{
            cdtSection.set(condition.getConfigName(), null);
            cdtSection.set(condition.getConfigName()+"Msg", null);
        }
    }

    @Override
    public void updateGUI(NewConditionGUI gui, NewConditions conditions, Condition condition) {

    }

    public static Map<Enchantment, Integer> transformEnchants(List<String> enchantsConfig){
        Map<Enchantment, Integer> result = new HashMap<>();
        for(String s : enchantsConfig){
            Enchantment enchant;
            int level;
            String [] decomp;

            if(s.contains(":")){
                decomp = s.split(":");
                try {
                    enchant = Enchantment.getByName(decomp[0]);
                    level = Integer.parseInt(decomp[1]);
                    result.put(enchant, level);
                }catch(Exception e){ e.printStackTrace();}
            }
        }
        return result;
    }
}
