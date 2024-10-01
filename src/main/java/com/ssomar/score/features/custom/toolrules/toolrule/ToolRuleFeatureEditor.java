package com.ssomar.score.features.custom.toolrules.toolrule;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;

public class ToolRuleFeatureEditor extends FeatureEditorInterface<ToolRuleFeature> {

    public final ToolRuleFeature enchantFeature;

    public ToolRuleFeatureEditor(ToolRuleFeature dropFeatures) {
        super("&lTool rule feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getMaterials().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getMiningSpeed().initAndUpdateItemParentEditor(this, 1);
        enchantFeature.getCorrectForDrops().initAndUpdateItemParentEditor(this, 2);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, TM.g(Text.EDITOR_RESET_NAME), false, false, "", TM.g(Text.EDITOR_RESET_DESCRIPTION));

        // Save menu
        createItem(GREEN, 1, 26, TM.g(Text.EDITOR_SAVE_NAME), false, false, "", TM.g(Text.EDITOR_SAVE_DESCRIPTION));
    }

    @Override
    public ToolRuleFeature getParent() {
        return enchantFeature;
    }
}
