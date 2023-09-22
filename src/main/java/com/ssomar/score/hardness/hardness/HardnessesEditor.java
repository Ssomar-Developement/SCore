package com.ssomar.score.hardness.hardness;

import com.ssomar.score.SCore;
import com.ssomar.score.hardness.hardness.loader.HardnessLoader;
import com.ssomar.score.hardness.hardness.manager.HardnessesManager;
import com.ssomar.score.sobject.menu.NewSObjectsEditorAbstract;

public class HardnessesEditor extends NewSObjectsEditorAbstract {

    public HardnessesEditor() {
        super(SCore.plugin, "&lHardnesses", "/hardnesses", HardnessesManager.getInstance(), HardnessLoader.getInstance());
        this.setDeleteArg("hardnesses-delete");
        this.setCreateArg("hardnesses-create");
    }
}
