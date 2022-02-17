package com.ssomar.score.sobject.sactivator;

import com.ssomar.score.sobject.SObject;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Optional;

public interface ActivatorLoader<T extends SActivator> {

    Optional<T> loadActivator(ConfigurationSection config, SObject sObject, SOption sOption, String activatorID, boolean isDefaultSObject, List<String> error);

}
