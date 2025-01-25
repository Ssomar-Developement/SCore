package com.ssomar.score.sobject.sactivator;

import java.util.List;

public interface SOption {

    public default List<SOption> getOptionWithPlayer(){
        return OptionGlobal.getOptionWithPlayerST();
    }

    public default List<SOption> getOptionWithEntity(){
        return OptionGlobal.getOptionWithEntityST();
    }

    public default List<SOption> getOptionWithWorld(){
        return OptionGlobal.getOptionWithWorldST();
    }

    public default List<SOption> getOptionWithItem(){
        return OptionGlobal.getOptionWithItemST();
    }

    public default List<SOption> getOptionWithOwner(){
        return OptionGlobal.getOptionWithOwnerST();
    }

    public default List<SOption> getOptionWithBlock(){
        return OptionGlobal.getOptionWithBlockST();
    }

    public default List<SOption> getOptionWithTargetBlock(){
        return OptionGlobal.getOptionWithTargetBlockST();
    }

    public default List<SOption> getOptionWithTargetEntity(){
        return OptionGlobal.getOptionWithTargetEntityST();
    }

    public default List<SOption> getOptionWithTargetPlayer(){
        return OptionGlobal.getOptionWithTargetPlayerST();
    }

    public default List<SOption> getPremiumOption(){
        return OptionGlobal.getPremiumOptionST();
    }

    public default boolean isValidOption(String optionStr){
        return OptionGlobal.isValidOptionST(optionStr);
    }

    public default boolean isLoopOption(){
        return OptionGlobal.isLoopOptionST(this);
    }

    public default boolean isCustomTriggerOption(){
        return OptionGlobal.isCustomTriggerOptionST(this);
    }

    public default SOption getOption(String optionStr){
        return OptionGlobal.getOptionST(optionStr);
    }

    public default List<SOption> getValues(){
        return OptionGlobal.getValuesST();
    }

    public default SOption getDefaultValue(){
        return OptionGlobal.getDefaultValueST();
    }

    public default boolean containsThisName(String name) {
        return OptionGlobal.containsThisNameST(name);
    }

    String getName();

}
