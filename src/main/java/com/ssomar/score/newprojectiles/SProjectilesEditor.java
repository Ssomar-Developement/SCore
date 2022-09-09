package com.ssomar.score.newprojectiles;

import com.ssomar.score.SCore;
import com.ssomar.score.newprojectiles.loader.SProjectileLoader;
import com.ssomar.score.newprojectiles.manager.SProjectilesManager;
import com.ssomar.score.sobject.menu.NewSObjectsEditorAbstract;

public class SProjectilesEditor extends NewSObjectsEditorAbstract {

    public SProjectilesEditor() {
        super(SCore.plugin, "&lSProjectiles", "/projectiles", SProjectilesManager.getInstance(), SProjectileLoader.getInstance());
        this.setDeleteArg("projectiles-delete");
    }
}
