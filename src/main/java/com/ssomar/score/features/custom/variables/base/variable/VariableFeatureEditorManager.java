package com.ssomar.score.features.custom.variables.base.variable;


import com.ssomar.score.editor.NewInteractionClickedGUIManager;
import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;
import com.ssomar.score.features.types.VariableTypeFeature;

public class VariableFeatureEditorManager extends FeatureEditorManagerAbstract<VariableFeatureEditor, VariableFeature> {

    private static VariableFeatureEditorManager instance;

    public static VariableFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new VariableFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public VariableFeatureEditor buildEditor(VariableFeature parent) {
        return new VariableFeatureEditor(parent.clone(parent.getParent()));
    }

    public void reloadEditor(NewInteractionClickedGUIManager<VariableFeatureEditor> i) {
        for (Object feature : i.gui.getParent().getFeatures()) {
            if (feature instanceof VariableTypeFeature) {
                i.gui.load();
            }
        }
    }


    @Override
    public boolean shiftLeftClicked(NewInteractionClickedGUIManager<VariableFeatureEditor> i) {
        boolean result = super.shiftLeftClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean shiftRightClicked(NewInteractionClickedGUIManager<VariableFeatureEditor> i) {
        boolean result = super.shiftRightClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<VariableFeatureEditor> i) {
        boolean result = super.leftClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<VariableFeatureEditor> i) {
        boolean result = super.rightClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

}
