package com.ssomar.score.sobject.sactivator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum OptionGlobal implements SOption {

    LOOP("LOOP"),
    CUSTOM_TRIGGER("CUSTOM_TRIGGER");

    private String[] names;

    OptionGlobal(String... names) {
        this.names = names;
    }

    public String[] getNames() {
        return names;
    }

    public static List<SOption> getOptionWithPlayerST() {
        List<SOption> result = new ArrayList<>();
        return result;
    }

    public static List<SOption> getOptionWithEntityST() {
        List<SOption> result = new ArrayList<>();
        return result;
    }

    public static List<SOption> getOptionWithWorldST() {
        List<SOption> result = new ArrayList<>();
        return result;
    }

    public static List<SOption> getOptionWithItemST() {
        List<SOption> result = new ArrayList<>();
        return result;
    }

    public static List<SOption> getOptionWithOwnerST() {
        List<SOption> result = new ArrayList<>();
        return result;
    }

    public static List<SOption> getOptionWithBlockST() {
        List<SOption> result = new ArrayList<>();
        return result;
    }

    public static List<SOption> getOptionWithTargetBlockST() {
        List<SOption> result = new ArrayList<>();
        return result;
    }

    public static List<SOption> getOptionWithTargetEntityST() {
        List<SOption> result = new ArrayList<>();
        return result;
    }

    public static List<SOption> getOptionWithTargetPlayerST() {
        List<SOption> result = new ArrayList<>();
        return result;
    }


    public static List<SOption> getPremiumOptionST() {
        List<SOption> premiumOption = new ArrayList<>();
        premiumOption.add(OptionGlobal.LOOP);
        return premiumOption;
    }

    public static boolean isValidOptionST(String entry) {
        for (OptionGlobal option : values()) {
            for (String name : option.getNames()) {
                if (name.equalsIgnoreCase(entry)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isLoopOptionST(SOption option){
        return option.equals(LOOP);
    }

    public static boolean isCustomTriggerOptionST(SOption option){
        return option.equals(CUSTOM_TRIGGER);
    }

    public static SOption getOptionST(String entry) {
        for (OptionGlobal option : values()) {
            for (String name : option.getNames()) {
                if (name.equalsIgnoreCase(entry)) {
                    return option;
                }
            }
        }
        return null;
    }

    public static List<SOption> getValuesST() {
        return new ArrayList<>(Arrays.asList(OptionGlobal.values()));
    }

    public static SOption getDefaultValueST() {
        return OptionGlobal.LOOP;
    }

    public static List<String> getNamesST() {
        List<String> names = new ArrayList<>();
        for (OptionGlobal option : values()) {
            for (String name : option.getNames()) {
                names.add(name);
            }
        }
        return names;
    }

    public static boolean containsThisNameST(String name){
        for (OptionGlobal option : values()) {
            for (String nameOption : option.getNames()) {
                if(nameOption.equalsIgnoreCase(name)) return true;
            }
        }
        return false;
    }
}
