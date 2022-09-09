package com.ssomar.score.newprojectiles;

import com.ssomar.score.editor.NewInteractionClickedGUIManager;
import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class SProjectileEditorManager extends FeatureEditorManagerAbstract<SProjectileEditor, SProjectile> {

    private static SProjectileEditorManager instance;

    @Override
    public SProjectileEditor buildEditor(SProjectile featureParentInterface) {
        return new SProjectileEditor(featureParentInterface.clone(featureParentInterface.getParent()));
    }

    public static SProjectileEditorManager getInstance() {
        if (instance == null) {
            instance = new SProjectileEditorManager();
        }
        return instance;
    }

    public void reloadEditor(NewInteractionClickedGUIManager<SProjectileEditor> i) {
        i.gui.load();
        i.player.updateInventory();
    }


    @Override
    public void receiveMessage(NewInteractionClickedGUIManager<SProjectileEditor> i) {
        super.receiveMessage(i);
        reloadEditor(i);
    }

    @Override
    public void clicked(ItemStack item, NewInteractionClickedGUIManager<SProjectileEditor> interact, ClickType click) {
        super.clicked(item, interact, click);
        reloadEditor(interact);
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<SProjectileEditor> i) {
        boolean result = super.leftClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<SProjectileEditor> i) {
        boolean result = super.rightClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

}
