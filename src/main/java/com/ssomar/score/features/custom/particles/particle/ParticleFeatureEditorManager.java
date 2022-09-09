package com.ssomar.score.features.custom.particles.particle;


import com.ssomar.score.editor.NewInteractionClickedGUIManager;
import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ParticleFeatureEditorManager extends FeatureEditorManagerAbstract<ParticleFeatureEditor, ParticleFeature> {

    private static ParticleFeatureEditorManager instance;

    public static ParticleFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new ParticleFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public ParticleFeatureEditor buildEditor(ParticleFeature parent) {
        return new ParticleFeatureEditor(parent.clone(parent.getParent()));
    }

    public void reloadEditor(NewInteractionClickedGUIManager<ParticleFeatureEditor> i) {
        i.gui.load();
        i.player.updateInventory();
    }


    @Override
    public void clicked(ItemStack item, NewInteractionClickedGUIManager<ParticleFeatureEditor> interact, ClickType click) {
        super.clicked(item, interact, click);
        reloadEditor(interact);
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<ParticleFeatureEditor> i) {
        boolean result = super.leftClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<ParticleFeatureEditor> i) {
        boolean result = super.rightClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

}
