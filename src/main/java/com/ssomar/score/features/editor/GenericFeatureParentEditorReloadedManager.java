package com.ssomar.score.features.editor;


import com.ssomar.score.editor.NewInteractionClickedGUIManager;
import com.ssomar.score.features.FeatureParentInterface;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class GenericFeatureParentEditorReloadedManager extends FeatureEditorManagerAbstract<GenericFeatureParentEditorReloaded, FeatureParentInterface> {

    private static GenericFeatureParentEditorReloadedManager instance;

    public static GenericFeatureParentEditorReloadedManager getInstance() {
        if (instance == null) {
            instance = new GenericFeatureParentEditorReloadedManager();
        }
        return instance;
    }

    @Override
    public GenericFeatureParentEditorReloaded buildEditor(FeatureParentInterface parent) {
        return new GenericFeatureParentEditorReloaded(parent.cloneParent(parent.getParent()));
    }

    public void reloadEditor(NewInteractionClickedGUIManager<GenericFeatureParentEditorReloaded> i) {
        i.gui.load();
        i.player.updateInventory();
    }


    @Override
    public void receiveMessage(NewInteractionClickedGUIManager<GenericFeatureParentEditorReloaded> i) {
        super.receiveMessage(i);
        reloadEditor(i);
    }

    @Override
    public void clicked(ItemStack item, NewInteractionClickedGUIManager<GenericFeatureParentEditorReloaded> interact, ClickType click) {
        super.clicked(item, interact, click);
        reloadEditor(interact);
    }

}
