package com.ssomar.scoretestrecode.features.custom.patterns.group;

import com.ssomar.score.menu.GUI;
import com.ssomar.scoretestrecode.features.custom.patterns.subgroup.PatternFeature;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorInterface;

public class PatternsGroupFeatureEditor extends FeatureEditorInterface<PatternsGroupFeature> {

    public PatternsGroupFeature attributesGroupFeature;

    public PatternsGroupFeatureEditor(PatternsGroupFeature enchantsGroupFeature) {
        super("&lPatterns feature Editor", 3*9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for(PatternFeature enchantment : attributesGroupFeature.getPatterns().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 	1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 			1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // new enchant
        createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new pattern");
    }

    @Override
    public PatternsGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
