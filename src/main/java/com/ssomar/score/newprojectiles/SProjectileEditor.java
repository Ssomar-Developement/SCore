package com.ssomar.score.newprojectiles;

/* public class SProjectileEditor extends FeatureEditorInterface<NewExecutableItem> {

    private NewExecutableItem newExecutableItem;

    public SProjectileEditor(NewExecutableItem newExecutableItem) {
        super("&lExecutable Item Editor", 6*9);
        this.newExecutableItem = newExecutableItem;
        load();
    }

    @Override
    public void load() {
        clearAndSetBackground();
        newExecutableItem.getMaterial().initAndUpdateItemParentEditor(this, 0);
        newExecutableItem.getDisplayName().initAndUpdateItemParentEditor(this, 1);
        newExecutableItem.getLore().initAndUpdateItemParentEditor(this, 2);

        newExecutableItem.getGlow().initAndUpdateItemParentEditor(this, 3);
        newExecutableItem.getUsage().initAndUpdateItemParentEditor(this, 4);
        newExecutableItem.getUsageLimit().initAndUpdateItemParentEditor(this, 5);
        newExecutableItem.getUsePerDay().initAndUpdateItemParentEditor(this, 6);

        newExecutableItem.getGiveFirstJoin().initAndUpdateItemParentEditor(this, 8);

        if(newExecutableItem.getFeatures().contains(newExecutableItem.getHeadFeatures())){
            newExecutableItem.getHeadFeatures().initAndUpdateItemParentEditor(this, 9);
        }
        else if (newExecutableItem.getFeatures().contains(newExecutableItem.getPotionSettings())){
            newExecutableItem.getPotionSettings().initAndUpdateItemParentEditor(this, 9);
        }
        else if(newExecutableItem.getFeatures().contains(newExecutableItem.getArmorColor())){
            newExecutableItem.getArmorColor().initAndUpdateItemParentEditor(this, 9);
        }
        else if(newExecutableItem.getFeatures().contains(newExecutableItem.getFireworkColor())){
            newExecutableItem.getFireworkColor().initAndUpdateItemParentEditor(this, 9);
        }
        else if(newExecutableItem.getFeatures().contains(newExecutableItem.getBannerSettings())){
            newExecutableItem.getBannerSettings().initAndUpdateItemParentEditor(this, 9);
        }

        newExecutableItem.getDurability().initAndUpdateItemParentEditor(this, 10);
        if(!SCore.is1v13Less()) newExecutableItem.getCustomModelData().initAndUpdateItemParentEditor(this, 11);

        newExecutableItem.getEnchantments().initAndUpdateItemParentEditor(this, 13);
        newExecutableItem.getUnbreakable().initAndUpdateItemParentEditor(this, 14);
        if(!SCore.is1v13Less()) newExecutableItem.getAttributes().initAndUpdateItemParentEditor(this, 15);

        newExecutableItem.getKeepItemOnDeath().initAndUpdateItemParentEditor(this, 18);
        newExecutableItem.getDisableStack().initAndUpdateItemParentEditor(this, 19);

        newExecutableItem.getDropFeatures().initAndUpdateItemParentEditor(this, 22);
        newExecutableItem.getRestrictions().initAndUpdateItemParentEditor(this, 23);
        newExecutableItem.getHiders().initAndUpdateItemParentEditor(this, 24);

        newExecutableItem.getActivatorsFeature().initAndUpdateItemParentEditor(this, 26);

        newExecutableItem.getCancelEventFeatures().initAndUpdateItemParentEditor(this, 32);

        newExecutableItem.getCanBeUsedOnlyByTheOwner().initAndUpdateItemParentEditor(this, 36);

        newExecutableItem.getStoreItemInfo().initAndUpdateItemParentEditor(this, 37);
        newExecutableItem.getDisabledWorlds().initAndUpdateItemParentEditor(this, 38);

        newExecutableItem.getVariables().initAndUpdateItemParentEditor(this, 41);

        //Reset menu
        createItem(ORANGE, 1, 46, GUI.RESET, false, false, "", "&c&oClick here to reset", "&c&oall options of this item");
        // exit
        createItem(RED, 1, 45, GUI.BACK, false, false);

        getInv().setItem(52, newExecutableItem.buildItem(1, Optional.empty()));

        //Save menu
        createItem(GREEN, 1, 53, GUI.SAVE, false, false, "", "&a&oClick here to save", "&a&oyour modification in config.yml");

    }

    @Override
    public NewExecutableItem getParent() {
        return newExecutableItem;
    }
}*/
