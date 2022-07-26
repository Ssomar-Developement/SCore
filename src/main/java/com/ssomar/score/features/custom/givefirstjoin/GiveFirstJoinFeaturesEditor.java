package com.ssomar.score.features.custom.givefirstjoin;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.features.editor.FeatureEditorInterface;

public class GiveFirstJoinFeaturesEditor extends FeatureEditorInterface<GiveFirstJoinFeatures> {

    public GiveFirstJoinFeatures giveFirstJoinFeatures;

    public GiveFirstJoinFeaturesEditor(GiveFirstJoinFeatures dropFeatures) {
        super("&lGive first join features Editor", 3 * 9);
        this.giveFirstJoinFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        giveFirstJoinFeatures.getGiveFirstJoin().initAndUpdateItemParentEditor(this, 0);
        giveFirstJoinFeatures.getGiveFirstJoinAmount().initAndUpdateItemParentEditor(this, 1);
        giveFirstJoinFeatures.getGiveFirstJoinSlot().initAndUpdateItemParentEditor(this, 2);


        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public GiveFirstJoinFeatures getParent() {
        return giveFirstJoinFeatures;
    }
}
