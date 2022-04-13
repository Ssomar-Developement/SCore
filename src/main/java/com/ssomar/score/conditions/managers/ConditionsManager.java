package com.ssomar.score.conditions.managers;

import com.google.common.base.Charsets;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.messages.MessageDesign;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.*;

@Getter
public abstract class ConditionsManager<T extends Conditions, Y extends Condition> {

    private Map<String, Y> conditions;
    private List<Y> conditionsList;
    @Getter(AccessLevel.NONE)
    private T instance;

    public ConditionsManager(T instance){
        this.instance = instance;
        this.conditions = new HashMap<>();
        this.conditionsList = new ArrayList<>();
    }

    public void add(Y blockCondition){
        conditions.put(blockCondition.getConfigName(), blockCondition);
        conditionsList.add(blockCondition);
    }

    public Condition get(String conditionKey){
        return  conditions.get(conditionKey);
    }

    public T loadConditions(ConfigurationSection cdtSection, List<String> errorList, String pluginName) {
       T loadedConditions = (T) instance.createNewInstance();

        for(Y condition : conditions.values()){
            if(cdtSection.contains(condition.getConfigName())){
                Condition cloneCondition = (Condition) condition.clone();
                cloneCondition.getConditionType().load(cloneCondition, cdtSection, errorList, pluginName);
                cloneCondition.setCustomErrorMsg(Optional.ofNullable(cdtSection.getString(condition.getConfigName()+"Msg", MessageDesign.ERROR_CODE_FIRST+pluginName+condition.getErrorMsg())));
                cloneCondition.setErrorCancelEvent(cdtSection.getBoolean(condition.getConfigName()+"CE", false));

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

                if(condition.isDefined()) {
                    condition.getConditionType().writeInConfig(condition, conditionSection);
                    conditionSection.set(condition.getConfigName() + "Msg", condition.getCustomErrorMsg().get());
                }
                else {
                    conditionSection.set(condition.getConfigName(), null);
                    /* If the custom message is present but different that the default message, we save it*/
                    if(condition.getCustomErrorMsg().isPresent() && ((String)condition.getCustomErrorMsg().get()).equals(MessageDesign.ERROR_CODE_FIRST+pluginName+conditionConfig.getDefaultErrorMsg()))
                        conditionSection.set(condition.getConfigName()+"Msg", condition.getCustomErrorMsg().get());
                    else conditionSection.set(condition.getConfigName()+"Msg", null);
                }

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
