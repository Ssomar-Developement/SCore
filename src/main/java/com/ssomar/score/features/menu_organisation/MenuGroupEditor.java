package com.ssomar.score.features.menu_organisation;

import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;

public class MenuGroupEditor extends FeatureEditorInterface<MenuGroup> {

    public final MenuGroup menuGroup;

    public MenuGroupEditor(MenuGroup enchantsGroupFeature) {
        super("&lGroup of features Editor", 3 * 9);
        this.menuGroup = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (FeatureInterface feature : menuGroup.getFeatures()) {
            feature.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, TM.g(Text.EDITOR_RESET_NAME), false, false, "", TM.g(Text.EDITOR_RESET_DESCRIPTION));

        // Save menu
        createItem(GREEN, 1, getSize()-1, GUI.SAVE, false, false, "", "&a&oClick here to save");
    }

    @Override
    public MenuGroup getParent() {
        return menuGroup;
    }
}
