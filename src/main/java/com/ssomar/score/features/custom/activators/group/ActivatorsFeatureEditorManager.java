package com.ssomar.score.features.custom.activators.group;


import com.ssomar.score.editor.NewInteractionClickedGUIManager;
import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class ActivatorsFeatureEditorManager extends FeatureEditorManagerAbstract<ActivatorsFeatureEditor, ActivatorsFeature> {

    private static ActivatorsFeatureEditorManager instance;

    public static ActivatorsFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new ActivatorsFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public ActivatorsFeatureEditor buildEditor(ActivatorsFeature parent) {
        return new ActivatorsFeatureEditor(parent);
    }

    @Override
    public void nextPage(NewInteractionClickedGUIManager<ActivatorsFeatureEditor> interact) {
        interact.gui.nextPage();
    }

    @Override
    public void previousPage(NewInteractionClickedGUIManager<ActivatorsFeatureEditor> interact) {
        interact.gui.prevPage();
    }

}
