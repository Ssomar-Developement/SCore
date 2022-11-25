package com.ssomar.score.features.custom.variables.real;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.utils.DynamicMeta;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VariableRealsList extends ArrayList<VariableReal> {

    private final static boolean DEBUG = true;

    public HashMap<String, String> getFlatValues(){
        HashMap<String, String> flatValues = new HashMap<>();
        for(VariableReal vR : this){
            flatValues.put(vR.getConfig().getVariableName().getValue().get().toUpperCase(), vR.getValue().toString());
        }
        return flatValues;
    }

    public void save(ConfigurationSection configurationSection) {
        for(VariableReal vR : this){
            vR.writeValue(configurationSection);
        }
    }

    public void buildWithCustomValues(Map<String, String> variables, ConfigurationSection configurationSection, @Nullable UUID owner){
        if(!this.isEmpty()) {
            for(String key : variables.keySet()){

                for(VariableReal vR : this){
                    if(vR.getConfig().getVariableName().getValue().get().equalsIgnoreCase(key)){
                        if(vR instanceof VariableRealDouble){
                            VariableRealDouble vRD = (VariableRealDouble) vR;
                            try {
                                vRD.setValue(Double.parseDouble(variables.get(key)));
                            }catch (NumberFormatException e){}
                        }
                        else if(vR instanceof VariableRealString){
                            VariableRealString vRS = (VariableRealString) vR;
                            StringPlaceholder sp = new StringPlaceholder();
                            sp.setPlayerPlcHldr(owner);
                            String value = sp.replacePlaceholder(variables.get(key), true);
                            vRS.setValue(value);
                        }
                        vR.writeValue(configurationSection);
                    }
                }
            }
        }
    }

    public void buildWithCustomValues(Map<String, String> variables, @NotNull ItemStack item, @Nullable UUID owner){

        if(!this.isEmpty()) {
            SsomarDev.testMsg("NOT EMPTY", DEBUG);
            DynamicMeta dynamicMeta = new DynamicMeta(item.getItemMeta());

            for(String key : variables.keySet()){

                for(VariableReal vR : this){
                    if(vR.getConfig().getVariableName().getValue().get().equalsIgnoreCase(key)){
                        if(vR instanceof VariableRealDouble){
                            VariableRealDouble vRD = (VariableRealDouble) vR;
                            try {
                                vRD.setValue(Double.parseDouble(variables.get(key)));
                            }catch (NumberFormatException e){}
                        }
                        else if(vR instanceof VariableRealString){
                            VariableRealString vRS = (VariableRealString) vR;
                            StringPlaceholder sp = new StringPlaceholder();
                            sp.setPlayerPlcHldr(owner);
                            String value = sp.replacePlaceholder(variables.get(key), true);
                            vRS.setValue(value);
                        }
                        SsomarDev.testMsg("WRITE VALUE for "+key +">>>>" + vR.toString(), DEBUG);
                        vR.writeValue(item, dynamicMeta);
                    }
                }
            }
            item.setItemMeta(dynamicMeta.getMeta());
        }
    }

}
