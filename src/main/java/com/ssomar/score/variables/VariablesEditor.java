package com.ssomar.score.variables;

import com.ssomar.score.SCore;
import com.ssomar.score.sobject.menu.NewSObjectsEditorAbstract;
import com.ssomar.score.variables.loader.VariablesLoader;
import com.ssomar.score.variables.manager.VariablesManager;

public class VariablesEditor extends NewSObjectsEditorAbstract {

    public VariablesEditor() {
        super(SCore.plugin, "&lVariables", "/variables", VariablesManager.getInstance(), VariablesLoader.getInstance());
        this.setDeleteArg("variables-delete");
    }
}
