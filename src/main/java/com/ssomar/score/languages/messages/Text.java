package com.ssomar.score.languages.messages;

public enum Text implements TextInterface {

    /* commands */

    EDITOR_BACK_NAME("editor.back.name"),
    EDITOR_EDIT_DESCRIPTION("editor.edit.description"),
    EDITOR_EXIT_NAME("editor.exit.name"),
    EDITOR_DELETE_NAME("editor.delete.name"),
    EDITOR_DELETE_NORMAL_DESCRIPTION("editor.delete.normal.description"),
    EDITOR_DELETE_SHIFT_DESCRIPTION("editor.delete.shift.description"),
    EDITOR_NEW_NAME("editor.new.name"),
    EDITOR_PAGE_NAME("editor.page.name"),
    EDITOR_PAGE_NEXT_NAME("editor.page.next.name"),
    EDITOR_PAGE_PREVIOUS_NAME("editor.page.previous.name"),
    EDITOR_PREMIUM_DESCRIPTION("editor.premium.description"),
    EDITOR_RESET_NAME("editor.reset.name"),
    EDITOR_RESET_DESCRIPTION("editor.reset.description"),
    EDITOR_SAVE_NAME("editor.save.name"),
    EDITOR_SAVE_DESCRIPTION("editor.save.description"),
    EDITOR_TITLE_COLOR("editor.title_color"),
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
    FEATURES_AROUNDBLOCKS_PARENTDESCRIPTION_BLOCKAROUNDADDED("features.aroundBlocks.parentDescription.blockAroundAdded");


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
