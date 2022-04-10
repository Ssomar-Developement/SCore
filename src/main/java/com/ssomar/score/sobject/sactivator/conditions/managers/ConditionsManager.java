package com.ssomar.score.sobject.sactivator.conditions.managers;

import com.google.common.base.Charsets;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.NewConditions;
import com.ssomar.score.sobject.sactivator.conditions.condition.Condition;
import com.ssomar.score.sobject.sactivator.conditions.condition.blockcondition.custom.AroundBlockCondition;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.messages.MessageDesign;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public abstract class ConditionsManager<T extends NewConditions, Y extends Condition> {

    private Map<String, Y> conditions;
    @Getter(AccessLevel.NONE)
    private T instance;

    public ConditionsManager(T instance){
        this.instance = instance;
        this.conditions = new HashMap<>();
    }

    public void add(Y blockCondition){
        conditions.put(blockCondition.getConfigName(), blockCondition);
    }

    public T loadConditions(ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
       T loadedConditions = (T) instance.createNewInstance();

        for(Y condition : conditions.values()){
            if(cdtSection.contains(condition.getConfigName())){
                Condition cloneCondition = (Condition) condition.clone();
                switch (condition.getConditionType()){

                    case BOOLEAN:
                        cloneCondition.setCondition(cdtSection.getBoolean(condition.getConfigName(), false));
                        break;

                    case NUMBER_CONDITION:
                        cloneCondition.setCondition(cdtSection.getString(condition.getConfigName(), ""));
                        break;

                    case CUSTOM_AROUND_BLOCK:
                        for(String s : cdtSection.getConfigurationSection("blockAroundCdts").getKeys(false)) {
                            ConfigurationSection section = cdtSection.getConfigurationSection("blockAroundCdts."+s);
                            ((List<AroundBlockCondition>) cloneCondition.getCondition()).add(AroundBlockCondition.get(section));
                        }
                        cloneCondition.setCondition(cdtSection.getString(condition.getConfigName(), ""));
                        break;
                }
                cloneCondition.setCustomErrorMsg(Optional.ofNullable(cdtSection.getString(condition.getConfigName()+"Msg", MessageDesign.ERROR_CODE_FIRST+pluginName+condition.getErrorMsg())));

                loadedConditions.add(cloneCondition);
            }
        }
        return loadedConditions;
    }

    public void saveConditions(SPlugin sPlugin, SObject sObject, SActivator sActivator, T conditionsToSave, String detail) {

        if(!new File(sObject.getPath()).exists()) {
            sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file in the folder ! ("+sObject.getId()+".yml)");
            return;
        }
        File file = new File(sObject.getPath());
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
        /* Force the creation of the section if it doesnt exist */
        activatorConfig.set("conditions."+detail+".X", false);
        String pluginName = sPlugin.getNameDesign();

        ConfigurationSection conditionSection = config.getConfigurationSection("activators."+sActivator.getID()+".conditions."+detail);

        for(Y conditionConfig : conditions.values()){
            if(conditionsToSave.contains(conditionConfig)){
                Y condition = (Y) conditionsToSave.get(conditionConfig);
                switch (condition.getConditionType()){

                    case BOOLEAN:
                        if((Boolean)condition.getCondition()) conditionSection.set(condition.getConfigName(), true);
                        else{
                            conditionSection.set(condition.getConfigName(), null);
                            conditionSection.set(conditionConfig.getConfigName()+"Msg", null);
                            continue;
                        }
                        break;

                    case NUMBER_CONDITION:
                        if(((String)condition.getCondition()).trim().length() > 0) conditionSection.set(condition.getConfigName(), (String)condition.getCondition());
                        else{
                            conditionSection.set(condition.getConfigName(), null);
                            conditionSection.set(conditionConfig.getConfigName()+"Msg", null);
                            continue;
                        }
                        break;
                }

                if(condition.getCustomErrorMsg().isPresent()) conditionSection.set(condition.getConfigName()+"Msg", condition.getCustomErrorMsg().get());
                else conditionSection.set(condition.getConfigName()+"Msg", MessageDesign.ERROR_CODE_FIRST+pluginName+conditionConfig.getDefaultErrorMsg());
            }
            else {
                if(conditionSection.contains(conditionConfig.getConfigName()+"Msg")){
                    String msg = conditionSection.getString(conditionConfig.getConfigName()+"Msg");
                    if(msg == null || msg.equals(MessageDesign.ERROR_CODE_FIRST+pluginName+conditionConfig.getDefaultErrorMsg())){
                        conditionSection.set(conditionConfig.getConfigName()+"Msg", null);
                    }
                }
            }
        }
        /* Remove the thing to force the creation of the section if it doesnt exist */
        activatorConfig.set("conditions."+detail+".X", null);

        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

            try {
                writer.write(config.saveToString());
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
