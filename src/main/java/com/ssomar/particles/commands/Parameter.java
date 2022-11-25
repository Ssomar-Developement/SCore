package com.ssomar.particles.commands;

import lombok.Getter;
import org.bukkit.Particle;

import java.util.Arrays;
import java.util.List;

public class Parameter<T> {
    private String name;
    @Getter
    private T value;
    private T defaultValue;
    private String description;

    public Parameter(String name, T defaultValue, String description) {
        this.name = name;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.description = description;
    }

    public void load(String [] parameters){
        load(Arrays.asList(parameters));
    }

    public void load(List<String> parameters){
        for(String s : parameters){
            if(s.contains(name)){
                String[] split = s.split(":");
                if(split.length == 2){
                    String valueStr = split[1];
                    if(value instanceof Integer){
                        value = (T) Integer.valueOf(valueStr);
                    }
                    else if(value instanceof Double){
                        value = (T) Double.valueOf(valueStr);
                    }
                    else if(value instanceof Float){
                        value = (T) Float.valueOf(valueStr);
                    }
                    else if(value instanceof Boolean){
                        value = (T) Boolean.valueOf(valueStr);
                    }
                    else if(value instanceof String){
                        value = (T) valueStr;
                    }
                    else if(value instanceof Particle){
                        value = (T) XParticle.getParticle(valueStr);
                    }
                    else value = defaultValue;
                    return;
                }
            }
        }
        value = defaultValue;
    }

    public String getName() {
        return name;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public String getDescription() {
        return description;
    }
}
