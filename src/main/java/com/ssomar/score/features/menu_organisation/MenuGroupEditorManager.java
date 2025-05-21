package com.ssomar.score.features.menu_organisation;


import com.ssomar.score.features.editor.FeatureEditorManagerAbstract;

public class MenuGroupEditorManager extends FeatureEditorManagerAbstract<MenuGroupEditor, MenuGroup> {

    private static MenuGroupEditorManager instance;

    public static MenuGroupEditorManager getInstance() {
        if (instance == null) {
            instance = new MenuGroupEditorManager();
        }
        return instance;
    }

    @Override
    public MenuGroupEditor buildEditor(MenuGroup parent) {
        return new MenuGroupEditor(parent);
    }

}
