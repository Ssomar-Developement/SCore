package com.ssomar.score.hardness.hardness;

import com.ssomar.score.editor.NewInteractionClickedGUIManager;
import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class HardnessEditorManager extends FeatureEditorManagerAbstract<HardnessEditor, Hardness> {

    private static HardnessEditorManager instance;

    @Override
    public HardnessEditor buildEditor(Hardness featureParentInterface) {
        return new HardnessEditor(featureParentInterface.clone(featureParentInterface.getParent()));
    }

    public static HardnessEditorManager getInstance() {
        if (instance == null) {
            instance = new HardnessEditorManager();
        }
        return instance;
    }

    public void reloadEditor(NewInteractionClickedGUIManager<HardnessEditor> i) {
        i.gui.load();
        i.player.updateInventory();
    }


    @Override
    public void receiveMessage(NewInteractionClickedGUIManager<HardnessEditor> i) {
        super.receiveMessage(i);
        reloadEditor(i);
    }

    @Override
    public void clicked(ItemStack item, NewInteractionClickedGUIManager<HardnessEditor> interact, ClickType click) {
        super.clicked(item, interact, click);
        reloadEditor(interact);
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<HardnessEditor> i) {
        boolean result = super.leftClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<HardnessEditor> i) {
        boolean result = super.rightClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

}
