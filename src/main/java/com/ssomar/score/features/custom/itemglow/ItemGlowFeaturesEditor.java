package com.ssomar.score.features.custom.itemglow;

import com.ssomar.score.SCore;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class ItemGlowFeaturesEditor extends FeatureEditorInterface<ItemGlowFeatures> {

    public ItemGlowFeatures furnaceFeatures;

    public ItemGlowFeaturesEditor(ItemGlowFeatures containerFeatures) {
        super("&lItem glow features Editor", 3 * 9);
        this.furnaceFeatures = containerFeatures;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        furnaceFeatures.getGlow().initAndUpdateItemParentEditor(this, i);
        i++;
        if(SCore.is1v20v5Plus()) furnaceFeatures.getDisableEnchantGlide().initAndUpdateItemParentEditor(this, i);



        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, GUI.RESET, false, false, "", "&c&oClick here to reset");

        // Save menu
        createItem(GREEN, 1, 26, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public ItemGlowFeatures getParent() {
        return furnaceFeatures;
    }
}
