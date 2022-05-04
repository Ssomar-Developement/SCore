package com.ssomar.score.sobject.sactivator.features.detailedentities;

import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.List;

public class DetailedEntities {

    private List<DetailedEntity> detailedEntities;
    private SPlugin plugin;
    private SObject sObject;
    private SActivator sActivator;
    
    public DetailedEntities(SPlugin plugin, SObject sObject, SActivator sActivator) {
        this.plugin = plugin;
        this.sObject = sObject;
        this.sActivator = sActivator;
    }

    public DetailedEntities(ConfigurationSection config, SPlugin sPlugin, SObject sO, SActivator sActivator, List<String> errors) {
        this.plugin = sPlugin;
        this.sObject = sO;
        this.sActivator = sActivator;
        List<String> detailedEntities = config.getStringList("detailedEntities");
        for(String detailedEntity : detailedEntities) {
            try {
                this.detailedEntities.add(new DetailedEntity(detailedEntity));
            } catch (Exception e) {
                errors.add(e.getMessage()+ " (object: "+sObject.getId()+" | activator: "+sActivator.getID()+")");
            }
        }
    }

    boolean isValidEntity(Entity entity) {
        for(DetailedEntity detailedEntity : detailedEntities) {
            if(detailedEntity.isValidEntity(entity)) return true;
        }
        return false;
    }

    public void save() {

    }
}
