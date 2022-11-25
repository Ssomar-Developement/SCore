package com.ssomar.score.projectiles;

import com.ssomar.score.SCore;
import com.ssomar.score.projectiles.loader.SProjectileLoader;
import com.ssomar.score.projectiles.manager.SProjectilesManager;
import com.ssomar.score.sobject.menu.NewSObjectsEditorAbstract;

public class SProjectilesEditor extends NewSObjectsEditorAbstract {

    public SProjectilesEditor() {
        super(SCore.plugin, "&lSProjectiles", "/projectiles", SProjectilesManager.getInstance(), SProjectileLoader.getInstance());
        this.setDeleteArg("projectiles-delete");
    }
}
