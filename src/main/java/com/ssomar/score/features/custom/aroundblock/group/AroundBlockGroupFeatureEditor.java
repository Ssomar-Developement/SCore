package com.ssomar.score.features.custom.aroundblock.group;

import com.ssomar.score.features.custom.aroundblock.aroundblock.AroundBlockFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class AroundBlockGroupFeatureEditor extends FeatureEditorInterface<AroundBlockGroupFeature> {

    public final AroundBlockGroupFeature attributesGroupFeature;
    private final int perPage;
    private int page;

    public AroundBlockGroupFeatureEditor(AroundBlockGroupFeature enchantsGroupFeature) {
        super("&lAroundBlockGroup feature Editor", 5 * 9);
        this.attributesGroupFeature = enchantsGroupFeature;
        this.page = 1;
        this.perPage = 27;
        load();
    }

    @Override
    public void load() {

        int i = 0;
        int total = 0;

        for (AroundBlockFeature enchantment : attributesGroupFeature.getAroundBlockGroup().values()) {
            if ((page - 1) * perPage <= total && total < page * perPage) {
                enchantment.initAndUpdateItemParentEditor(this, i);
                i++;

            }
            total++;
        }

        if (attributesGroupFeature.getAroundBlockGroup().values().size() > perPage && page * perPage < attributesGroupFeature.getAroundBlockGroup().values().size()) {
            createItem(NEXT_PAGE_MAT, 1, 43, GUI.NEXT_PAGE, false, false);
        }
        if (page > 1) {
            createItem(PREVIOUS_PAGE_MAT, 1, 38, GUI.PREVIOUS_PAGE, false, false);
        }

        // Back
        createItem(RED, 1, 36, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 37, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new attribute
        createItem(GREEN, 1, 44, GUI.NEW, false, false, "", "&a&oClick here to add new around block cdt");

    }

    @Override
    public AroundBlockGroupFeature getParent() {
        return attributesGroupFeature;
    }

    public void nextPage() {
        page++;
        clearAndSetBackground();
        load();
    }

    public void prevPage() {
        page--;
        clearAndSetBackground();
        load();
    }
}
