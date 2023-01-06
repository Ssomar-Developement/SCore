package com.ssomar.score.variables;

import com.ssomar.score.editor.NewInteractionClickedGUIManager;
import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class VariableEditorManager extends FeatureEditorManagerAbstract<VariableEditor, Variable> {

    private static VariableEditorManager instance;

    @Override
    public VariableEditor buildEditor(Variable featureParentInterface) {
        return new VariableEditor(featureParentInterface.clone(featureParentInterface.getParent()));
    }

    public static VariableEditorManager getInstance() {
        if (instance == null) {
            instance = new VariableEditorManager();
        }
        return instance;
    }

    public void reloadEditor(NewInteractionClickedGUIManager<VariableEditor> i) {
        i.gui.load();
        i.player.updateInventory();
    }


    @Override
    public void receiveMessage(NewInteractionClickedGUIManager<VariableEditor> i) {
        super.receiveMessage(i);
        reloadEditor(i);
    }

    @Override
    public void clicked(ItemStack item, NewInteractionClickedGUIManager<VariableEditor> interact, ClickType click) {
        super.clicked(item, interact, click);
        reloadEditor(interact);
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<VariableEditor> i) {
        boolean result = super.leftClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<VariableEditor> i) {
        boolean result = super.rightClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

}
