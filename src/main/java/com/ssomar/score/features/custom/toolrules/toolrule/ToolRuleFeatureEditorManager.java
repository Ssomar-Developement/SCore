package com.ssomar.score.features.custom.toolrules.toolrule;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class ToolRuleFeatureEditorManager extends FeatureEditorManagerAbstract<ToolRuleFeatureEditor, ToolRuleFeature> {

    private static ToolRuleFeatureEditorManager instance;

    public static ToolRuleFeatureEditorManager getInstance() {
        if (instance == null) {
            instance = new ToolRuleFeatureEditorManager();
        }
        return instance;
    }

    @Override
    public ToolRuleFeatureEditor buildEditor(ToolRuleFeature parent) {
        return new ToolRuleFeatureEditor(parent.clone(parent.getParent()));
    }

}
