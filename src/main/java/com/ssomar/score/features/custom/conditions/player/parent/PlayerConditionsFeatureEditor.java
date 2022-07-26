package com.ssomar.score.features.custom.conditions.player.parent;

import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class PlayerConditionsFeatureEditor extends FeatureEditorInterface<PlayerConditionsFeature> {

    public PlayerConditionsFeature bCF;
    private int page;
    private int perPage;

    public PlayerConditionsFeatureEditor(PlayerConditionsFeature bCF) {
        super("&lPlayer Conditions Editor", 5 * 9);
        this.bCF = bCF.clone(bCF.getParent());
        this.page = 1;
        this.perPage = 27;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        int total = 0;

        for (PlayerConditionFeature condition : bCF.getConditions()) {
            if ((page - 1) * perPage <= total && total < page * perPage) {
                condition.initAndUpdateItemParentEditor(this, i);
                i++;

            }
            total++;
        }

        if (bCF.getConditions().size() > perPage && page * perPage < bCF.getConditions().size()) {
            createItem(NEXT_PAGE_MAT, 1, 43, GUI.NEXT_PAGE, false, false);
        }
        if (page > 1) {
            createItem(PREVIOUS_PAGE_MAT, 1, 38, GUI.PREVIOUS_PAGE, false, false);
        }

        // Back
        createItem(RED, 1, 36, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 37, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 44, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public PlayerConditionsFeature getParent() {
        return bCF;
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
