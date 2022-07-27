package com.ssomar.score.features.custom.useperday;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class UsePerDayFeatureEditor extends FeatureEditorInterface<UsePerDayFeature> {

    public final UsePerDayFeature dropFeatures;

    public UsePerDayFeatureEditor(UsePerDayFeature dropFeatures) {
        super("&lUse per day Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getMaxUsePerDay().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getMessageIfMaxReached().initAndUpdateItemParentEditor(this, 1);
        dropFeatures.getCancelEventIfMaxReached().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public UsePerDayFeature getParent() {
        return dropFeatures;
    }
}
