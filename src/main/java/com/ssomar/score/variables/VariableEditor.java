package com.ssomar.score.variables;

import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.menu.GUI;

public class VariableEditor extends FeatureEditorInterface<Variable> {

    private Variable sProjectile;

    public VariableEditor(Variable sProjectile) {
        super(FeatureSettingsSCore.VARIABLE, 6 * 9);
        this.sProjectile = sProjectile;
        load();
    }

    @Override
    public void load() {
        clearAndSetBackground();
        int i = 0;
        for (FeatureInterface f : sProjectile.getFeatures()) {
            if (f instanceof FeatureAbstract) {
                FeatureAbstract featureAbstract = (FeatureAbstract) f;
                featureAbstract.initAndUpdateItemParentEditor(this, i);
                i++;
            }
        }

        //Reset menu
        createItem(ORANGE, 1, 46, GUI.RESET, false, false, "", "&c&oClick here to reset", "&c&oall options of this variable");
        // exit
        createItem(RED, 1, 45, GUI.BACK, false, false);

        // change lang menu
        createItem(YELLOW, 1, 47, GUI.CHANGE_LANGUAGE, false, false, GeneralConfig.getInstance().getAvailableLocales("", "&e&oClick here to change the language"));


        //Save menu
        createItem(GREEN, 1, 53, GUI.SAVE, false, false, "", "&a&oClick here to save", "&a&oyour modification in the .yml");

    }

    @Override
    public Variable getParent() {
        return sProjectile;
    }
}
