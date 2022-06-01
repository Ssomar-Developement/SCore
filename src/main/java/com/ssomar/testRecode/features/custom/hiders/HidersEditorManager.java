package com.ssomar.testRecode.features.custom.hiders;


import com.ssomar.testRecode.features.editor.FeatureEditorManagerAbstract;

public class HidersEditorManager extends FeatureEditorManagerAbstract<HidersEditor, Hiders> {

    private static HidersEditorManager instance;

    public static HidersEditorManager getInstance(){
        if(instance == null){
            instance = new HidersEditorManager();
        }
        return instance;
    }

    @Override
    public HidersEditor buildEditor(Hiders parent) {
        return new HidersEditor(parent.clone());
    }

}
