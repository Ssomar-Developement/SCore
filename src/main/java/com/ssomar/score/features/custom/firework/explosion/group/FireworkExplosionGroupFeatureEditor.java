package com.ssomar.score.features.custom.firework.explosion.group;

import com.ssomar.score.features.editor.FeatureEditorInterface;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.features.custom.firework.explosion.FireworkExplosionFeatures;

public class FireworkExplosionGroupFeatureEditor extends FeatureEditorInterface<FireworkExplosionGroupFeature> {

    public final FireworkExplosionGroupFeature attributesGroupFeature;

    public FireworkExplosionGroupFeatureEditor(FireworkExplosionGroupFeature enchantsGroupFeature) {
        super("&lExplosions feature Editor", 3 * 9);
        this.attributesGroupFeature = enchantsGroupFeature;
        load();
    }

    @Override
    public void load() {
        int i = 0;
        for (FireworkExplosionFeatures enchantment : attributesGroupFeature.getExplosions().values()) {
            enchantment.initAndUpdateItemParentEditor(this, i);
            i++;
        }

        // Back
        createItem(RED, 1, 18, GUI.BACK, false, false);

        // Reset menu
        createItem(ORANGE, 1, 19, TM.g(Text.EDITOR_RESET_NAME), false, false, "", TM.g(Text.EDITOR_RESET_DESCRIPTION));

        // new attribute
        if (!attributesGroupFeature.isPremium() && attributesGroupFeature.getExplosions().size() >= attributesGroupFeature.getPremiumLimit()) {
            createItem(PURPLE, 1, 22, GUI.PREMIUM, false, false, "", "&d&oIt requires premium to", "&d&ohave more than " + attributesGroupFeature.getPremiumLimit() + " explosions !");
        } else
            createItem(GREEN, 1, 22, GUI.NEW, false, false, "", "&a&oClick here to add new explosion");
    }

    @Override
    public FireworkExplosionGroupFeature getParent() {
        return attributesGroupFeature;
    }
}
