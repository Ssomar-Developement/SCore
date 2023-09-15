package com.ssomar.score.features.custom.restrictions;

import lombok.Getter;

public enum RestrictionEnum {
    CANCEL_DROP("cancel-item-drop"),
    CANCEL_PLACE("cancel-item-place"),

    CANCEL_TOOL_INTERACTIONS("cancel-tool-interactions"),
    CANCEL_CONSUMPTION("cancel-consumption"),

    CANCEL_HORN("cancel-horn"),

    CANCEL_CRAFT("cancel-item-craft-no-custom"),
    CANCEL_ALL_CRAFT("cancel-item-craft"),

    CANCEL_DEPOSIT_IN_CHEST("cancel-deposit-in-chest"),
    CANCEL_DEPOSIT_IN_FURNACE("cancel-deposit-in-furnace"),
    CANCEL_STONE_CUTTER("cancel-stone-cutter"),
    CANCEL_ENCHANT("cancel-enchant"),
    CANCEL_ANVIL("cancel-anvil"),
    CANCEL_ACTION_RENAME_IN_ANVIL("cancel-rename-anvil"),
    CANCEL_ACTION_ENCHANT_IN_ANVIL("cancel-enchant-anvil"),
    CANCEL_GRIND_STONE("cancel-grind-stone"),
    CANCEL_ITEM_FRAME("cancel-item-frame"),
    CANCEL_SMITHING_TABLE("cancel-smithing-table"),
    CANCEL_BREWING("cancel-brewing"),
    CANCEL_BEACON("cancel-beacon"),
    CANCEL_CARTOGRAPHY("cancel-cartography"),
    CANCEL_COMPOSTER("cancel-composter"),
    CANCEL_DISPENSER("cancel-dispenser"),
    CANCEL_DROPPER("cancel-dropper"),
    CANCEL_HOPPER("cancel-hopper"),
    CANCEL_LECTERN("cancel-lectern"),
    CANCEL_LOOM("cancel-loom"),

    CANCEL_MERCHANT("cancel-merchant"),
    CANCEL_HORSE("cancel-horse"),

    CANCEL_ITEM_BURN("cancel-item-burn"),
    CANCEL_ITEM_DELETE_BY_CACTUS("cancel-item-delete-by-cactus"),
    CANCEL_ITEM_DELETE_BY_LIGHTNING("cancel-item-delete-by-lightning"),

    CANCEL_SWAPHAND("cancel-swap-hand"),
    LOCKED_INVENTORY("locked-in-inventory");

    @Getter
    public final String editName;

    RestrictionEnum(String editName) {
        this.editName = editName;
    }

}
