package com.ssomar.score.sobject.sactivator;

import com.ssomar.executableitems.items.activators.Option;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ActivatorsLoader<T extends SActivator, Y extends SPlugin>{

     public List<T> loadActivators(Y plugin, ActivatorLoader<T> activatorLoader, ConfigurationSection config, SObject sObject, boolean isDefaultSObject, List<String> error){

        /* CONFIG ACTIVATORS */
        List<T> activators = new ArrayList<>();
        int cptAct = 0;

        if (config.isConfigurationSection("activators")) {
            ConfigurationSection activatorsSection = config.getConfigurationSection("activators");
            for (String activatorID : activatorsSection.getKeys(false)) {

                ConfigurationSection activatorSection = activatorsSection.getConfigurationSection(activatorID);

                String optionsStr = activatorSection.getString("activator", "NO_OPTION_IN_CONFIG");
                optionsStr = optionsStr.toUpperCase();

                String[] optionsSpliter = optionsStr.split(",");

                int cptOption = 0;
                for (String optionStr : optionsSpliter) {
                    cptOption++;
                    optionsStr = optionsStr.trim();
                    String modifiedActivatorID = activatorID;
                    if(cptOption >= 2){
                        modifiedActivatorID = activatorID+"--"+cptOption;
                    }

                    /* Limit of 1 activator in the free version */
                    if ((plugin.isLotOfWork() && !isDefaultSObject) && cptAct >= 1) {
                        error.add(plugin.getNameDesign()+" " + sObject.getID() + " REQUIRE PREMIUM: to add more than one activator you need the premium version");
                        return activators;
                    }

                    /* Check if the option is valid */
                    if (!Option.isValidOption(optionStr)) {
                        error.add(plugin.getNameDesign()+"  Invalid option " + optionStr + " for item: " + sObject.getID());
                        continue;
                    }

                    /* Load the activator */
                    SOption sOption = Option.getOption(optionsStr);
                    Optional<T> sActivatorOpt = activatorLoader.loadActivator(activatorSection, sObject, sOption, modifiedActivatorID, isDefaultSObject, error);
                    if (sActivatorOpt.isPresent()) {
                        activators.add(sActivatorOpt.get());
                        cptAct++;
                    }
                }
            }
        }
        return activators;
    }
}
