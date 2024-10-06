package com.ssomar.score.features.custom.Instrument;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class InstrumentFeaturesEditor extends FeatureEditorInterface<InstrumentFeatures> {

    public final InstrumentFeatures dropFeatures;

    public InstrumentFeaturesEditor(InstrumentFeatures dropFeatures) {
        super("&lInstrument features Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getEnable().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getInstrument().initAndUpdateItemParentEditor(this, 1);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public InstrumentFeatures getParent() {
        return dropFeatures;
    }
}
