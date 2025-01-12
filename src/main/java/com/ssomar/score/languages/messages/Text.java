package com.ssomar.score.languages.messages;

public enum Text implements TextInterface {

    /* commands */
    EDITOR_BACK_NAME("editor.back.name","&4&l⬅ &cBack"),
    EDITOR_CREATION_ID_NAME("editor.creation.id.name", "&2&l✚ &a&lCreation ID:"),
    EDITOR_EDIT_DESCRIPTION("editor.edit.description", "&a✎ Click here to change"),
    EDITOR_EXIT_NAME("editor.exit.name", "&4&l✘ &cExit"),
    EDITOR_FOLDER_NAME("editor.folder.name", "&2&l✦ FOLDER: &a"),
    EDITOR_FOLDER_DESCRIPTION("editor.folder.description", new String[]{"", "&7Click here to open the folder"}),
    EDITOR_DELETE_MESSAGE("editor.delete.message", "&cHey if you want to delete the %object%"),
    EDITOR_DELETE_NAME("editor.delete.name", "&4&l✘ &cDelete"),
    EDITOR_DELETE_NORMAL_DESCRIPTION("editor.delete.normal.description", "&4&lX &cClick here to delete"),
    EDITOR_DELETE_SHIFT_DESCRIPTION("editor.delete.shift.description", "&4&lX &cShift + Click to delete"),
    EDITOR_DELETE_SHIFT_LEFT_DESCRIPTION("editor.delete.shift.left.description", "&4&lX &cShift + Left Click to delete"),
    EDITOR_GIVE_SHIFT_RIGHT_DESCRIPTION("editor.give.shift.right.description", "&2✔ &aShift + right click to give to yourself"),
    EDITOR_GIVE_RECEIVEDMESSAGE("editor.give.receivedMessage", "&aYou received &e"),
    EDITOR_INVALID_FILE_NAME("editor.invalid.file.name", "&4&l✦ INVALID FILE: &c&o"),
    EDITOR_INVALID_FILE_DESCRIPTION("editor.invalid.file.description", "&7The plugin can only load &e.yml &7files"),

    EDITOR_INVALID_CONFIGURATION_NAME("editor.invalid.configuration.name", "&4&l✦ INVALID CONFIGURATION: &c&o"),
    EDITOR_INVALID_CONFIGURATION_DESCRIPTION("editor.invalid.configuration.description", "&7You should edit your configuration file"),
    EDITOR_INVALID_CONFIGURATION_FREELIMIT("editor.invalid.configuration.freeLimit", "&7Also be sure that you haven't reached the limit of the free version"),
    EDITOR_NEW_NAME("editor.new.name", "&a&l➕ &aNew"),
    EDITOR_PAGE_NAME("editor.page.name", " - Page "),
    EDITOR_PAGE_NEXT_NAME("editor.page.next.name", "&dNext page &5&l▶"),
    EDITOR_PAGE_PREVIOUS_NAME("editor.page.previous.name", "&5&l◀ &dPrevious page"),
    EDITOR_PATH_NAME("editor.path.name", "&6&l✦ &ePATH:"),
    EDITOR_PATH_DESCRIPTION("editor.path.description", new String[]{"&8&oClick here to come back", "&8&oin previous folder"}),
    EDITOR_PREMADE_PREMIUM_NAME("editor.premade.premium.name", "&5&l✚ &dDefault Premium %object%"),
    EDITOR_PREMADE_PACKS_NAME("editor.premade.packs.name", "&5&l✚ &d%object% from Custom packs"),
    EDITOR_PREMIUM_DESCRIPTION("editor.premium.description", "&5&l❂ &d&lPremium only &5&l❂"),
    EDITOR_PREMIUM_REQUIREPREMIUMTOADDMORE("editor.premium.requirePremiumToAddMore", "&cREQUIRE PREMIUM: to add more than %limit% %object% you need the premium version"),
    EDITOR_RESET_NAME("editor.reset.name", "&4&l✘ &cReset"),
    EDITOR_RESET_DESCRIPTION("editor.reset.description", new String[]{"", "&c&oClick here to reset" ,"&c&oall options of this creation"}),
    EDITOR_SAVE_NAME("editor.save.name", "&a&l✔ &aSave"),
    EDITOR_SAVE_DESCRIPTION("editor.save.description", new String[]{"", "&a&oClick here to save", "&a&oyour configuration !"}),
    EDITOR_TITLE_COLOR("editor.title.color", "&e&l"),
    EDITOR_CURRENTLY_NAME("editor.currently.name", "&7✦ Currently:");


    private String key;

    private String defaultValueString;

    private String[] defaultValueStringArray;

    private TypeText type;

    Text(String key, String defaultValue) {
        this.key = key;
        this.defaultValueString = defaultValue;
        this.type = TypeText.STRING;
    }

    Text(String key, String[] defaultValue) {
        this.key = key;
        this.defaultValueStringArray = defaultValue;
        this.type = TypeText.ARRAY;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDefaultValueString() {
        return defaultValueString;
    }

    @Override
    public String[] getDefaultValueArray() {
        return defaultValueStringArray;
    }

    @Override
    public TypeText getType() {
        return type;
    }

}
