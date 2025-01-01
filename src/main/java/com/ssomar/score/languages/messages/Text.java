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
    EDITOR_TITLE_COLOR("editor.title.color");


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
