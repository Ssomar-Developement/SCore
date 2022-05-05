package com.ssomar.score.sobject.sactivator.features.detailedentities;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.utils.NTools;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter @Setter
public class DetailedEntity {

    private EntityType entityType;
    /* Key | Value in String */
    private Map<String, String> entityData;
    private static final String symbolStart = "{";
    private static final String symbolEnd= "}";
    private static final String symbolEquals = ":";
    private static final String symbolSeparator = ",";

    private static final Boolean DEBUG = false;

    public DetailedEntity(String config) throws ConfigException {
        entityData = new HashMap<>();
        String entityTypeStr = config;
        if(config.contains(symbolStart)) {
            entityTypeStr = config.split("\\"+symbolStart)[0];
            String datas = config.split("\\"+symbolStart)[1].replace(symbolEnd, "");
            for(String data : datas.split(symbolSeparator)){
                String[] dataSplit = data.split(symbolEquals);
                if(dataSplit.length != 2) throw new ConfigException("DETAILED ENTITIES Invalid data format: " + data+ " Example: ZOMBIE{IsBaby:1}");
                entityData.put(dataSplit[0], dataSplit[1]);
            }
        }


        try {
            this.entityType = EntityType.valueOf(entityTypeStr.toUpperCase());
        }catch (IllegalArgumentException e) {
            throw new ConfigException("DETAILED ENTITIES Invalid entity type" + entityTypeStr);
        }
    }

    public boolean isValidEntity(Entity entity) {
        EntityType entityType = entity.getType();
        if (!entityType.equals(this.entityType)) return false;

        if(entityData.size() != 0){
            for(Map.Entry<String, String> entry : entityData.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();

                NBTEntity nbtent = new NBTEntity(entity);
                NBTType type = nbtent.getType(key);

                SsomarDev.testMsg("VERIF key: " + key + " value: " + value+ " type: " + type, DEBUG);

                switch (type) {
                    case NBTTagEnd:
                        break;
                    case NBTTagByte:
                        if(value.equals("0") || value.equals("1")){
                            SsomarDev.testMsg("Byte: " + nbtent.getByte(key), DEBUG);
                            if(nbtent.getByte(key) != Byte.parseByte(value)) return false;
                        }
                        break;
                    case NBTTagShort:
                        break;
                    case NBTTagInt:
                        Optional<Integer> optInt = NTools.getInteger(value);
                        if(optInt.isPresent()){
                            SsomarDev.testMsg("Int: " + nbtent.getInteger(key), DEBUG);
                           if(nbtent.getInteger(key) != optInt.get()) return false;
                        }
                        break;
                    case NBTTagLong:
                        Optional<Long> optLong = NTools.getLong(value);
                        if(optLong.isPresent()){
                            SsomarDev.testMsg("Long: " + nbtent.getLong(key), DEBUG);
                            if(nbtent.getLong(key) != optLong.get()) return false;
                        }
                        break;
                    case NBTTagFloat:
                        Optional<Float> optFloat = NTools.getFloat(value);
                        if(optFloat.isPresent()){
                            SsomarDev.testMsg("Float: " + nbtent.getFloat(key), DEBUG);
                            if(nbtent.getFloat(key) != optFloat.get()) return false;
                        }
                        break;
                    case NBTTagDouble:
                        Optional<Double> optDouble = NTools.getDouble(value);
                        if(optDouble.isPresent()){
                            SsomarDev.testMsg("Double: " + nbtent.getDouble(key),DEBUG);
                            if(nbtent.getDouble(key) != optDouble.get()) return false;
                        }
                        break;
                    case NBTTagByteArray:
                        break;
                    case NBTTagIntArray:
                        break;
                    case NBTTagString:
                        if(value.toLowerCase().equals("true") || value.toLowerCase().equals("false")){
                            SsomarDev.testMsg("Boolean: " + nbtent.getBoolean(key), DEBUG);
                            if(nbtent.getBoolean(key) != Boolean.parseBoolean(value)) return false;
                        }
                        break;
                    case NBTTagList:
                        break;
                    case NBTTagCompound:
                        break;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(entityType.toString());
        if(entityData.size() != 0){
            sb.append(symbolStart);
            int i = 0;
            for(Map.Entry<String, String> entry : entityData.entrySet()){
                i++;
                sb.append(entry.getKey()).append(symbolEquals).append(entry.getValue());
                if(i != entityData.size()) sb.append(symbolSeparator);
            }
            sb.append(symbolEnd);
        }
        return sb.toString();
    }

}
