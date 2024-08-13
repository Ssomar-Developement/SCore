package com.ssomar.score.scheduler;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class ScheduleFeaturesEditor extends FeatureEditorInterface<ScheduleFeatures> {

    public final ScheduleFeatures dropFeatures;

    public ScheduleFeaturesEditor(ScheduleFeatures dropFeatures) {
        super("&lSchedule features Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getStartDateFeature().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getEndDateFeature().initAndUpdateItemParentEditor(this, 1);
        dropFeatures.getWhen().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public ScheduleFeatures getParent() {
        return dropFeatures;
    }
}
