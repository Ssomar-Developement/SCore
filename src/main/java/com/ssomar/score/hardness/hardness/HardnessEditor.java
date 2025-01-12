package com.ssomar.score.hardness.hardness;

import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;

public class HardnessEditor extends FeatureEditorInterface<Hardness> {

    private Hardness hardness;

    public HardnessEditor(Hardness hardness) {
        super(FeatureSettingsSCore.HARDNESS, 6 * 9);
        this.hardness = hardness;
        load();
    }

    @Override
    public void load() {
        clearAndSetBackground();
        int i = 0;
        hardness.getEnabled().initAndUpdateItemParentEditor(this, i);
        i++;
        hardness.getDetailedBlocks().initAndUpdateItemParentEditor(this, i);
        i++;
        hardness.getDetailedItems().initAndUpdateItemParentEditor(this, i);
        i++;
        hardness.getPeriod().initAndUpdateItemParentEditor(this, i);
        i++;
        hardness.getPeriodInTicks().initAndUpdateItemParentEditor(this, i);

        //Reset menu
        createItem(ORANGE, 1, 46, TM.g(Text.EDITOR_RESET_NAME), false, false, TM.gA(Text.EDITOR_RESET_DESCRIPTION));
        // exit
        createItem(RED, 1, 45, GUI.BACK, false, false);

        // change lang menu
        createItem(YELLOW, 1, 47, GUI.CHANGE_LANGUAGE, false, false, GeneralConfig.getInstance().getAvailableLocales("", "&e&oClick here to change the language"));


        //Save menu
        createItem(GREEN, 1, 53, GUI.SAVE, false, false, TM.gA(Text.EDITOR_SAVE_DESCRIPTION));

    }

    @Override
    public Hardness getParent() {
        return hardness;
    }
}
