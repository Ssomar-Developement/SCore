package com.ssomar.score.features.custom.toolrules.group;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class ToolRulesGroupFeatureEditorManager extends FeatureEditorManagerAbstract<ToolRulesGroupFeatureEditor, ToolRulesGroupFeature> {

    private static ToolRulesGroupFeatureEditorManager instance;

    public static ToolRulesGroupFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new ToolRulesGroupFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public ToolRulesGroupFeatureEditor buildEditor(ToolRulesGroupFeature parent) {
        return new ToolRulesGroupFeatureEditor(parent);
    }

}
