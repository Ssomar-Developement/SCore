package com.ssomar.score.sobject.sactivator.features.detailedentities;

import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
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
        this.detailedEntities = new ArrayList<>();
    }

    public void load(ConfigurationSection config, List<String> errors) {
        List<String> detailedEntities = config.getStringList("detailedEntities");
        load(detailedEntities, errors);
    }

    public void load(List<String> detailedEntities, List<String> errors) {
        for(String detailedEntity : detailedEntities) {
            try {
                this.detailedEntities.add(new DetailedEntity(detailedEntity));
            } catch (Exception e) {
                errors.add(e.getMessage()+ " (object: "+sObject.getId()+" | activator: "+sActivator.getID()+")");
            }
        }
    }

    public boolean isValidEntity(Entity entity) {
        if(detailedEntities.isEmpty()) return true;

        for(DetailedEntity detailedEntity : detailedEntities) {
            if(detailedEntity.isValidEntity(entity)) return true;
        }
        return false;
    }

    public void save(ConfigurationSection config) {
        List<String> detailedEntities = new ArrayList<>();
        for(DetailedEntity detailedEntity : this.detailedEntities) {
            detailedEntities.add(detailedEntity.toString());
        }
        config.set("detailedEntities", detailedEntities);
    }

    public List<String> getDetailedEntitiesToListString() {
        List<String> detailedEntities = new ArrayList<>();
        for(DetailedEntity detailedEntity : this.detailedEntities) {
            detailedEntities.add(detailedEntity.toString());
        }
        return detailedEntities;
    }
}
