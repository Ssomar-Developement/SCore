package com.ssomar.score.features.custom.patterns.subgroup;

import com.ssomar.score.features.custom.patterns.subpattern.SubPatternFeature;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class PatternFeatureEditor extends FeatureEditorInterface<PatternFeature> {

    public final PatternFeature attributesGroupFeature;

    public PatternFeatureEditor(PatternFeature enchantsGroupFeature) {
        super("&lSub Patterns feature Editor", 3 * 9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (SubPatternFeature enchantment : attributesGroupFeature.getSubPattern().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new sub pattern");
    }

    @Override
    public PatternFeature getParent() {
        return attributesGroupFeature;
    }
}
