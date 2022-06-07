package com.ssomar.testRecode.features.custom.required.money;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerAbstract;

public class RequiredMoneyGUIManager extends FeatureEditorManagerAbstract<RequiredMoneyGUI, RequiredMoney> {

    private static RequiredMoneyGUIManager instance;

    public static RequiredMoneyGUIManager getInstance(){
        if(instance == null){
            instance = new RequiredMoneyGUIManager();
        }
        return instance;
    }

    @Override
    public RequiredMoneyGUI buildEditor(RequiredMoney parent) {
        return new RequiredMoneyGUI(parent.clone());
    }
}
