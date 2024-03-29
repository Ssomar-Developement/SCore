package com.ssomar.score.features.custom.attributes.attribute;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;

public class AttributeFullOptionsFeatureEditor extends FeatureEditorInterface<AttributeFullOptionsFeature> {

    public final AttributeFullOptionsFeature enchantFeature;

    public AttributeFullOptionsFeatureEditor(AttributeFullOptionsFeature dropFeatures) {
        super("&lAttribute feature Editor", 3 * 9);
        this.enchantFeature = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        enchantFeature.getAttribute().initAndUpdateItemParentEditor(this, 0);
        enchantFeature.getOperation().initAndUpdateItemParentEditor(this, 1);
        enchantFeature.getAmount().initAndUpdateItemParentEditor(this, 2);
        enchantFeature.getSlot().initAndUpdateItemParentEditor(this, 3);
        enchantFeature.getAttributeName().initAndUpdateItemParentEditor(this, 4);
        enchantFeature.getUuid().initAndUpdateItemParentEditor(this, 5);

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, TM.g(Text.EDITOR_RESET_NAME), false, false, "", TM.g(Text.EDITOR_RESET_DESCRIPTION));

        // Save menu
        createItem(GREEN, 1, 26, TM.g(Text.EDITOR_SAVE_NAME), false, false, "", TM.g(Text.EDITOR_SAVE_DESCRIPTION));
    }

    @Override
    public AttributeFullOptionsFeature getParent() {
        return enchantFeature;
    }
}
