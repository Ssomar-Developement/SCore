package com.ssomar.score.variables;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.sobject.menu.SObjectsWithFileEditor;
import com.ssomar.score.variables.loader.VariablesLoader;
import com.ssomar.score.variables.manager.VariablesManager;

public class VariablesEditor extends SObjectsWithFileEditor {

    public VariablesEditor() {
        super(SCore.plugin, FeatureSettingsSCore.VARIABLE, "/variables", VariablesManager.getInstance(), VariablesLoader.getInstance());
    }

    @Override
    public void initSettings() {
        this.setDeleteArg("variables-delete");
        this.setCreateArg("variables-create");
        setGiveButton(false);
        setDefaultObjectsButton(false);
    }
}
