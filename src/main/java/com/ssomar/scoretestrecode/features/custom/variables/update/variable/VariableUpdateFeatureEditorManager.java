package com.ssomar.scoretestrecode.features.custom.variables.update.variable;


import com.ssomar.scoretestrecode.editor.NewInteractionClickedGUIManager;
import com.ssomar.scoretestrecode.features.FeatureInterface;
import com.ssomar.scoretestrecode.features.editor.FeatureEditorManagerAbstract;
import com.ssomar.scoretestrecode.features.types.UncoloredStringFeature;
import com.ssomar.scoretestrecode.features.types.VariableUpdateTypeFeature;

public class VariableUpdateFeatureEditorManager extends FeatureEditorManagerAbstract<VariableUpdateFeatureEditor, VariableUpdateFeature> {

    private static VariableUpdateFeatureEditorManager instance;

    public static VariableUpdateFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new VariableUpdateFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public VariableUpdateFeatureEditor buildEditor(VariableUpdateFeature parent) {
        return new VariableUpdateFeatureEditor(parent.clone(parent.getParent()));
    }

    public void reloadEditor(NewInteractionClickedGUIManager<VariableUpdateFeatureEditor> i) {
        for (FeatureInterface feature : i.gui.getParent().getFeatures()) {
            if (feature instanceof VariableUpdateTypeFeature || feature instanceof UncoloredStringFeature) {
                i.gui.load();
            }
        }
    }


    @Override
    public boolean shiftLeftClicked(NewInteractionClickedGUIManager<VariableUpdateFeatureEditor> i) {
        boolean result = super.shiftLeftClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean shiftRightClicked(NewInteractionClickedGUIManager<VariableUpdateFeatureEditor> i) {
        boolean result = super.shiftRightClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean leftClicked(NewInteractionClickedGUIManager<VariableUpdateFeatureEditor> i) {
        boolean result = super.leftClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public boolean rightClicked(NewInteractionClickedGUIManager<VariableUpdateFeatureEditor> i) {
        boolean result = super.rightClicked(i);
        if (result) {
            reloadEditor(i);
        }
        return result;
    }

    @Override
    public void receiveMessageNoValue(NewInteractionClickedGUIManager<VariableUpdateFeatureEditor> interact) {
        super.receiveMessageNoValue(interact);
        reloadEditor(interact);
    }


    @Override
    public void receiveMessageValue(NewInteractionClickedGUIManager<VariableUpdateFeatureEditor> interact) {
        super.receiveMessageValue(interact);
        reloadEditor(interact);
    }

}
