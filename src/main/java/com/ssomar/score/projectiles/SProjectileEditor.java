package com.ssomar.score.projectiles;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;

public class SProjectileEditor extends FeatureEditorInterface<SProjectile> {

    private SProjectile sProjectile;

    public SProjectileEditor(SProjectile sProjectile) {
        super("&lSProjectile Item Editor", 6 * 9);
        this.sProjectile = sProjectile;
        load();
    }

    @Override
    public void load() {
        clearAndSetBackground();
        sProjectile.update();
        int i = 0;
        for (FeatureInterface f : sProjectile.getFeatures()) {
            if (f instanceof FeatureAbstract) {
                FeatureAbstract featureAbstract = (FeatureAbstract) f;
                featureAbstract.initAndUpdateItemParentEditor(this, i);
                i++;
            }
        }

        //Reset menu
        createItem(ORANGE, 1, 46, TM.g(Text.EDITOR_RESET_NAME), false, false, TM.gA(Text.EDITOR_RESET_DESCRIPTION));
        // exit
        createItem(RED, 1, 45, GUI.BACK, false, false);

        //Save menu
        createItem(GREEN, 1, 53, GUI.SAVE, false, false, TM.gA(Text.EDITOR_SAVE_DESCRIPTION));

    }

    @Override
    public SProjectile getParent() {
        return sProjectile;
    }
}
