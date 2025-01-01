package com.ssomar.score.sobject;

import com.ssomar.score.SCore;
import com.ssomar.score.features.custom.variables.base.variable.VariableFeature;
import com.ssomar.score.features.custom.variables.real.VariableRealBuilder;
import com.ssomar.score.features.custom.variables.real.VariableRealsList;
import com.ssomar.score.utils.writer.NameSpaceKeyWriterReader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
public class InternalData {

    private int usage;
    private VariableRealsList variableRealsList;
    private Map<String, String> variables;
    private UUID ownerUUID;

    public InternalData(){
        this.usage = -1;
        this.variableRealsList = null;
        this.variables = null;
        this.ownerUUID = null;
    }

    public InternalData setUsage(int usage){
        this.usage = usage;
        return this;
    }

    public InternalData setVariableRealsList(VariableRealsList variableRealsList){
        this.variableRealsList = variableRealsList;
        return this;
    }

    public InternalData setVariables(Map<String, String> variables){
        this.variables = variables;
        return this;
    }

    public InternalData setOwnerUUID(UUID ownerUUID){
        this.ownerUUID = ownerUUID;
        return this;
    }

    public Optional<Integer> getUsageOptional(){
        if(usage == -1) return Optional.empty();
        else return Optional.of(usage);
    }

    public Optional<UUID> getOwnerUUIDOptional(){
        if(ownerUUID == null) return Optional.empty();
        else return Optional.of(ownerUUID);
    }

    public Optional<Player> getOwnerOptional(){
        if(ownerUUID == null) return Optional.empty();
        else {
            Player player = Bukkit.getPlayer(ownerUUID);
            if(player == null) return Optional.empty();
            else return Optional.of(player);
        }
    }

    public Optional<OfflinePlayer> getOfflineOwnerOptional(){
        if(ownerUUID == null) return Optional.empty();
        else {
            OfflinePlayer player = Bukkit.getOfflinePlayer(ownerUUID);
            if(player == null) return Optional.empty();
            else return Optional.of(player);
        }
    }

    public Map<String, String> getVariables(){
        if(variableRealsList != null) return variableRealsList.getFlatValues();
        else if(variables == null) return new HashMap<>();
        else return  variables;
    }

    public InternalData clone() {
    	InternalData internalData = new InternalData().setUsage(usage).setOwnerUUID(ownerUUID).setVariables(variables).setVariableRealsList(variableRealsList);
        return internalData;
    }

    public void loadFromItem(ItemStack item, SObjectWithVariables config){
        loadFromPersistentDataContainer(item.getItemMeta().getPersistentDataContainer(), config);
    }


    public void loadFromEntity(Entity entity, SObjectWithVariables config){
        loadFromPersistentDataContainer(entity.getPersistentDataContainer(), config);
    }

    public void loadFromPersistentDataContainer(PersistentDataContainer persistentDataContainer , SObjectWithVariables config){
        NameSpaceKeyWriterReader.readInteger(SCore.plugin, persistentDataContainer, "usage").ifPresent(value -> usage = value);
        NameSpaceKeyWriterReader.readString(SCore.plugin, persistentDataContainer, "ownerUUID").ifPresent(value -> {
            try{
                ownerUUID = UUID.fromString(value);
            }catch (Exception e){
                ownerUUID = null;
            }
        });
        variableRealsList = new VariableRealsList();
        for (VariableFeature vC : config.getVariables().getVariables().values()) {
            variableRealsList.add(VariableRealBuilder.build(vC, persistentDataContainer).get());
        }
    }

    public void saveInEntity(Entity entity){
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        NameSpaceKeyWriterReader.writeInteger(SCore.plugin, persistentDataContainer, "usage", usage);
        NameSpaceKeyWriterReader.writeString(SCore.plugin, persistentDataContainer, "ownerUUID", ownerUUID == null ? "" :ownerUUID.toString());
        if(variableRealsList != null){
            variableRealsList.save(persistentDataContainer);
        }
    }
}
