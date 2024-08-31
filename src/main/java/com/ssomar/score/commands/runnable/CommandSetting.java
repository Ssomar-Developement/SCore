package com.ssomar.score.commands.runnable;

import com.ssomar.score.SsomarDev;
import lombok.Getter;

@Getter
public class CommandSetting {

    private String name;

    private int oldSystemIndex;
    private boolean oldSystemOptional = false;

    private Object type;

    private Object defaultValue;


    public CommandSetting(String name, int oldSystemIndex, Object type, Object defaultValue) {
        this.name = name;
        this.oldSystemIndex = oldSystemIndex;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public CommandSetting(String name, int oldSystemIndex, Object type, Object defaultValue, boolean oldSystemOptional) {
        this.name = name;
        this.oldSystemIndex = oldSystemIndex;
        this.type = type;
        this.defaultValue = defaultValue;
        this.oldSystemOptional = oldSystemOptional;
    }

    public Object getValue(String value) {
        SsomarDev.testMsg("CommandSetting getValue value: "+value+" >> type:"+type+" >> defaultValue: "+defaultValue, true);
        if(value == null) return defaultValue;
        if(type == Double.class) return Double.parseDouble(value);
        else if(type == Integer.class) return Double.valueOf(value).intValue();
        else if(type == Float.class) return Float.parseFloat(value);
        else if(type == Boolean.class) return Boolean.parseBoolean(value);
        return value;
    }
}
