package com.ssomar.score.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public enum VariableType implements Serializable {

    STRING("STRING", "STR", "TEXT", "TXT"),
    NUMBER("NUMBER", "DOUBLE");

    @Getter
    @Setter
    private String[] names;

    VariableType(String... names) {
        this.names = names;
    }


    public boolean isValidOption(String entry) {
        for (VariableType var : values()) {
            for (String name : var.getNames()) {
                if (name.equalsIgnoreCase(entry)) {
                    return true;
                }
            }
        }
        return false;
    }

    public VariableType getPrev() {
        VariableType opt = values()[values().length - 1];
        for (VariableType o : values()) {
            if (this.equals(o)) {
                return opt;
            } else opt = o;
        }
        return opt;
    }

    public VariableType getNext() {
        VariableType opt = values()[0];
        boolean next = false;
        for (VariableType o : values()) {
            if (next) {
                opt = o;
                break;
            }
            if (this.equals(o)) {
                next = true;
            }
        }
        return opt;
    }

    public VariableType getOption(String entry) {
        for (VariableType option : values()) {
            for (String name : option.getNames()) {
                if (name.equalsIgnoreCase(entry)) {
                    return option;
                }
            }
        }
        return null;
    }

    public boolean containsThisName(String entry) {
        for (String name : getNames()) {
            if (name.equalsIgnoreCase(entry))
                return true;
        }
        return false;
    }
}
