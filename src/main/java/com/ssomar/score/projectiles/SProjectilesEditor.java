package com.ssomar.score.projectiles;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.projectiles.loader.SProjectileLoader;
import com.ssomar.score.projectiles.manager.SProjectilesManager;
import com.ssomar.score.sobject.menu.SObjectsWithFileEditor;

public class SProjectilesEditor extends SObjectsWithFileEditor {

    public SProjectilesEditor() {
        super(SCore.plugin, FeatureSettingsSCore.SPROJECTILE, "/projectiles", SProjectilesManager.getInstance(), SProjectileLoader.getInstance());
    }

    @Override
    public void initSettings() {
        this.setDeleteArg("projectiles-delete");
        this.setCreateArg("projectiles-create");
        setGiveButton(false);
        setDefaultObjectsButton(false);
    }
}
