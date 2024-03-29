package com.ssomar.score.languages.messages;

public enum Text implements TextInterface {

    /* commands */
    EDITOR_BACK_NAME("editor.back.name"),
    EDITOR_CREATION_ID_NAME("editor.creation.id.name"),
    EDITOR_EDIT_DESCRIPTION("editor.edit.description"),
    EDITOR_EXIT_NAME("editor.exit.name"),
    EDITOR_FOLDER_NAME("editor.folder.name"),
    EDITOR_FOLDER_DESCRIPTION("editor.folder.description"),
    EDITOR_DELETE_MESSAGE("editor.delete.message"),
    EDITOR_DELETE_NAME("editor.delete.name"),
    EDITOR_DELETE_NORMAL_DESCRIPTION("editor.delete.normal.description"),
    EDITOR_DELETE_SHIFT_DESCRIPTION("editor.delete.shift.description"),
    EDITOR_DELETE_SHIFT_LEFT_DESCRIPTION("editor.delete.shift.left.description"),
    EDITOR_GIVE_SHIFT_RIGHT_DESCRIPTION("editor.give.shift.right.description"),
    EDITOR_GIVE_RECEIVEDMESSAGE("editor.give.receivedMessage"),
    EDITOR_INVALID_FILE_NAME("editor.invalid.file.name"),
    EDITOR_INVALID_FILE_DESCRIPTION("editor.invalid.file.description"),

    EDITOR_INVALID_CONFIGURATION_NAME("editor.invalid.configuration.name"),
    EDITOR_INVALID_CONFIGURATION_DESCRIPTION("editor.invalid.configuration.description"),
    EDITOR_INVALID_CONFIGURATION_FREELIMIT("editor.invalid.configuration.freeLimit"),
    EDITOR_NEW_NAME("editor.new.name"),
    EDITOR_PAGE_NAME("editor.page.name"),
    EDITOR_PAGE_NEXT_NAME("editor.page.next.name"),
    EDITOR_PAGE_PREVIOUS_NAME("editor.page.previous.name"),
    EDITOR_PATH_NAME("editor.path.name"),
    EDITOR_PATH_DESCRIPTION("editor.path.description"),
    EDITOR_PREMADE_PREMIUM_NAME("editor.premade.premium.name"),
    EDITOR_PREMADE_PACKS_NAME("editor.premade.packs.name"),
    EDITOR_PREMIUM_DESCRIPTION("editor.premium.description"),
    EDITOR_PREMIUM_REQUIREPREMIUMTOADDMORE("editor.premium.requirePremiumToAddMore"),
    EDITOR_RESET_NAME("editor.reset.name"),
    EDITOR_RESET_DESCRIPTION("editor.reset.description"),
    EDITOR_SAVE_NAME("editor.save.name"),
    EDITOR_SAVE_DESCRIPTION("editor.save.description"),
    EDITOR_TITLE_COLOR("editor.title.color"),
    FEATURES_ACTIVATORS_NAME("features.activators.name"),
    FEATURES_ACTIVATORS_DESCRIPTION("features.activators.description"),
    FEATURES_ACTIVATORS_EDITORTITLE("features.activators.editorTitle"),
    FEATURES_ACTIVATOR_NAME("features.activator.name"),
    FEATURES_ACTIVATOR_DESCRIPTION("features.activator.description"),
    FEATURES_AROUNDBLOCK_NAME("features.aroundBlock.name"),
    FEATURES_AROUNDBLOCK_DESCRIPTION("features.aroundBlock.description"),
    FEATURES_AROUNDBLOCK_EDITORTITLE("features.aroundBlock.editorTitle"),
    FEATURES_AROUNDBLOCK_FEATURES_SOUTHVALUE_NAME("features.aroundBlock.features.southValue.name"),
    FEATURES_AROUNDBLOCK_FEATURES_SOUTHVALUE_DESCRIPTION("features.aroundBlock.features.southValue.description"),
    FEATURES_AROUNDBLOCK_FEATURES_NORTHVALUE_NAME("features.aroundBlock.features.northValue.name"),
    FEATURES_AROUNDBLOCK_FEATURES_NORTHVALUE_DESCRIPTION("features.aroundBlock.features.northValue.description"),
    FEATURES_AROUNDBLOCK_FEATURES_EASTVALUE_NAME("features.aroundBlock.features.eastValue.name"),
    FEATURES_AROUNDBLOCK_FEATURES_EASTVALUE_DESCRIPTION("features.aroundBlock.features.eastValue.description"),
    FEATURES_AROUNDBLOCK_FEATURES_WESTVALUE_NAME("features.aroundBlock.features.westValue.name"),
    FEATURES_AROUNDBLOCK_FEATURES_WESTVALUE_DESCRIPTION("features.aroundBlock.features.westValue.description"),
    FEATURES_AROUNDBLOCK_FEATURES_ABOVEVALUE_NAME("features.aroundBlock.features.aboveValue.name"),
    FEATURES_AROUNDBLOCK_FEATURES_ABOVEVALUE_DESCRIPTION("features.aroundBlock.features.aboveValue.description"),
    FEATURES_AROUNDBLOCK_FEATURES_UNDERVALUE_NAME("features.aroundBlock.features.underValue.name"),
    FEATURES_AROUNDBLOCK_FEATURES_UNDERVALUE_DESCRIPTION("features.aroundBlock.features.underValue.description"),
    FEATURES_AROUNDBLOCK_FEATURES_ERRORMESSAGE_NAME("features.aroundBlock.features.errorMessage.name"),
    FEATURES_AROUNDBLOCK_FEATURES_ERRORMESSAGE_DESCRIPTION("features.aroundBlock.features.errorMessage.description"),
    FEATURES_AROUNDBLOCK_FEATURES_BLOCKMUSTBEEXECUTABLEBLOCK_NAME("features.aroundBlock.features.blockMustBeExecutableBlock.name"),
    FEATURES_AROUNDBLOCK_FEATURES_BLOCKMUSTBEEXECUTABLEBLOCK_DESCRIPTION("features.aroundBlock.features.blockMustBeExecutableBlock.description"),
    FEATURES_AROUNDBLOCK_FEATURES_BLOCKTYPEMUSTBE_NAME("features.aroundBlock.features.blockTypeMustBe.name"),
    FEATURES_AROUNDBLOCK_FEATURES_BLOCKTYPEMUSTBE_DESCRIPTION("features.aroundBlock.features.blockTypeMustBe.description"),
    FEATURES_AROUNDBLOCK_FEATURES_BLOCKTYPEMUSTNOTBE_NAME("features.aroundBlock.features.blockTypeMustNotBe.name"),
    FEATURES_AROUNDBLOCK_FEATURES_BLOCKTYPEMUSTNOTBE_DESCRIPTION("features.aroundBlock.features.blockTypeMustNotBe.description"),
    FEATURES_AROUNDBLOCKS_NAME("features.aroundBlocks.name"),
    FEATURES_AROUNDBLOCKS_DESCRIPTION("features.aroundBlocks.description"),
    FEATURES_AROUNDBLOCKS_EDITORTITLE("features.aroundBlocks.editorTitle"),
    FEATURES_AROUNDBLOCKS_PARENTDESCRIPTION_BLOCKAROUNDADDED("features.aroundBlocks.parentDescription.blockAroundAdded"),

    EI_FEATURES_LORE_DESCRIPTION("ei.features.lore.description"),
    EI_FEATURES_MATERIAL_DESCRIPTION("ei.features.material.description"),
    EI_FEATURES_NAME_DESCRIPTION("ei.features.name.description"),
    EI_FEATURES_GLOW_DESCRIPTION("ei.features.glow.description"),
    EI_FEATURES_USAGE_DESCRIPTION("ei.features.usage.description"),
    EI_FEATURES_USAGELIMIT_DESCRIPTION("ei.features.usageLimit.description"),
    FEATURES_USEPERDAY_DESCRIPTION("features.usePerDay.description"),
    FEATURES_GIVEFIRSTJOIN_DESCRIPTION("features.giveFirstJoin.description"),
    EI_FEATURES_DURABILITY_DESCRIPTION("ei.features.durability.description"),
    EI_FEATURES_CUSTOMMODELDATA_DESCRIPTION("ei.features.customModelData.description"),
    FEATURES_ENCHANTMENTS_DESCRIPTION("features.enchantments.description"),
    EI_FEATURES_UNBREAKABLE_DESCRIPTION("ei.features.unbreakable.description"),
    FEATURES_ATTRIBUTES_DESCRIPTION("features.attributes.description"),
    EI_FEATURES_KEEPITEMONDEATH_DESCRIPTION("ei.features.keepItemOnDeath.description"),
    EI_FEATURES_DISABLESTACK_DESCRIPTION("ei.features.disableStack.description"),
    EI_FEATURES_DISPLAYCONDITIONS_DESCRIPTION("ei.features.displayConditions.description"),
    FEATURES_DROPS_DESCRIPTION("features.drops.description"),
    FEATURES_RESTRICTIONS_DESCRIPTION("features.restrictions.description"),
    FEATURES_HIDERS_DESCRIPTION("features.hiders.description"),
    FEATURES_ARMORTRIM_DESCRIPTION("features.armorTrim.description"),
    EI_FEATURES_NBTTAGS_DESCRIPTION("ei.features.nbtTags.description"),
    FEATURES_CANCELEVENTS_DESCRIPTION("features.cancelEvents.description"),
    EI_FEATURES_CANBEUSEDONLYBYTHEOWNER_DESCRIPTION("ei.features.canBeUsedOnlyByTheOwner.description"),
    EI_FEATURES_STOREITEMINFO_DESCRIPTION("ei.features.storeItemInfo.description"),
    EI_FEATURES_DISABLEDWORLDS_DESCRIPTION("ei.features.disabledWorlds.description"),
    FEATURES_VARIABLES_DESCRIPTION("features.variables.description");


    private String key;

    Text(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
