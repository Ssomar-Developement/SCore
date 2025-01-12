package com.ssomar.score.hardness.hardness;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.hardness.hardness.loader.HardnessLoader;
import com.ssomar.score.hardness.hardness.manager.HardnessesManager;
import com.ssomar.score.sobject.menu.SObjectsWithFileEditor;

public class HardnessesEditor extends SObjectsWithFileEditor {

    public HardnessesEditor() {
        super(SCore.plugin, FeatureSettingsSCore.HARDNESS, "/hardnesses", HardnessesManager.getInstance(), HardnessLoader.getInstance());
    }

    @Override
    public void initSettings() {
        this.setDeleteArg("hardnesses-delete");
        this.setCreateArg("hardnesses-create");
        setGiveButton(false);
    }
}
