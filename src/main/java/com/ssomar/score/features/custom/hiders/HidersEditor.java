package com.ssomar.score.features.custom.hiders;

import com.ssomar.score.SCore;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class HidersEditor extends FeatureEditorInterface<Hiders> {

    public final Hiders dropFeatures;

    public HidersEditor(Hiders dropFeatures) {
        super("&lHiders Editor", 3 * 9);
        this.dropFeatures = dropFeatures.clone(dropFeatures.getParent());
        load();
    }

    @Override
    public void load() {
        dropFeatures.getHideEnchantments().initAndUpdateItemParentEditor(this, 0);
        dropFeatures.getHideUnbreakable().initAndUpdateItemParentEditor(this, 1);
        dropFeatures.getHideAttributes().initAndUpdateItemParentEditor(this, 2);
        if(SCore.is1v20v5Plus())
            dropFeatures.getHideAdditionalTooltip().initAndUpdateItemParentEditor(this, 3);
        else
            dropFeatures.getHidePotionEffects().initAndUpdateItemParentEditor(this, 3);
        dropFeatures.getHideUsage().initAndUpdateItemParentEditor(this, 4);
        if(!SCore.is1v11Less()){
            dropFeatures.getHideDestroys().initAndUpdateItemParentEditor(this, 5);
            dropFeatures.getHidePlacedOn().initAndUpdateItemParentEditor(this, 6);
        }
        if(SCore.is1v17Plus()) {
            dropFeatures.getHideDye().initAndUpdateItemParentEditor(this, 7);
        }
        if(SCore.is1v20Plus()){
            dropFeatures.getHideArmorTrim().initAndUpdateItemParentEditor(this, 8);
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public Hiders getParent() {
        return dropFeatures;
    }
}
