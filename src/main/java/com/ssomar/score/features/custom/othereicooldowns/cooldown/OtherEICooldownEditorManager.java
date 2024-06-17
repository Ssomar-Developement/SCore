package com.ssomar.score.features.custom.othereicooldowns.cooldown;


import com.ssomar.score.editor.NewInteractionClickedGUIManager;
import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class OtherEICooldownEditorManager extends FeatureEditorManagerAbstract<OtherEICooldownEditor, OtherEICooldown> {

    private static OtherEICooldownEditorManager instance;

    public static OtherEICooldownEditorManager getInstance() {
        if (instance == null) {
            instance = new OtherEICooldownEditorManager();
        }
        return instance;
    }

    @Override
    public OtherEICooldownEditor buildEditor(OtherEICooldown parent) {
        return new OtherEICooldownEditor(parent.clone(parent.getParent()));
    }

    public void reloadEditor(NewInteractionClickedGUIManager<OtherEICooldownEditor> i) {
        i.gui.getParent().reloadActivatorsSuggestions();
    }


    @Override
    public boolean shiftLeftClicked(NewInteractionClickedGUIManager<OtherEICooldownEditor> i) {
        boolean result = super.shiftLeftClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean shiftRightClicked(NewInteractionClickedGUIManager<OtherEICooldownEditor> i) {
        boolean result = super.shiftRightClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<OtherEICooldownEditor> i) {
        boolean result = super.leftClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<OtherEICooldownEditor> i) {
        boolean result = super.rightClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

}
