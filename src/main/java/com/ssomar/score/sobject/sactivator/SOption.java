package com.ssomar.score.sobject.sactivator;

import java.util.List;

public interface SOption {

    default List<SOption> getOptionWithPlayer(){
        return OptionGlobal.getOptionWithPlayerST();
    }

    default List<SOption> getOptionWithEntity(){
        return OptionGlobal.getOptionWithEntityST();
    }

    default List<SOption> getOptionWithWorld(){
        return OptionGlobal.getOptionWithWorldST();
    }

    default List<SOption> getOptionWithItem(){
        return OptionGlobal.getOptionWithItemST();
    }

    default List<SOption> getOptionWithOwner(){
        return OptionGlobal.getOptionWithOwnerST();
    }

    default List<SOption> getOptionWithBlock(){
        return OptionGlobal.getOptionWithBlockST();
    }

    default List<SOption> getOptionWithTargetBlock(){
        return OptionGlobal.getOptionWithTargetBlockST();
    }

    default List<SOption> getOptionWithTargetItem(){
        return OptionGlobal.getOptionWithTargetItemST();
    }

    default List<SOption> getOptionWithTargetEntity(){
        return OptionGlobal.getOptionWithTargetEntityST();
    }

    default List<SOption> getOptionWithTargetPlayer(){
        return OptionGlobal.getOptionWithTargetPlayerST();
    }

    default List<SOption> getPremiumOption(){
        return OptionGlobal.getPremiumOptionST();
    }

    default boolean isValidOption(String optionStr){
        return OptionGlobal.isValidOptionST(optionStr);
    }

    default boolean isLoopOption(){
        return OptionGlobal.isLoopOptionST(this);
    }

    default boolean isCustomTriggerOption(){
        return OptionGlobal.isCustomTriggerOptionST(this);
    }

    default SOption getOption(String optionStr){
        return OptionGlobal.getOptionST(optionStr);
    }

    default List<SOption> getValues(){
        return OptionGlobal.getValuesST();
    }

    default SOption getDefaultValue(){
        return OptionGlobal.getDefaultValueST();
    }

    default boolean containsThisName(String name) {
        return OptionGlobal.containsThisNameST(name);
    }

    String getName();

}
