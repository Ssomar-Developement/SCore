package com.ssomar.score.projectiles.manager;

import com.ssomar.score.SCore;
import com.ssomar.score.projectiles.SProjectile;
import com.ssomar.score.sobject.SObjectWithFileManager;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SProjectilesManager extends SObjectWithFileManager<SProjectile> {

    private static SProjectilesManager instance;

    private NamespacedKey key;

    public SProjectilesManager() {
        super(SCore.plugin, "projectiles");

        if (!SCore.is1v13Less()) key = new NamespacedKey(SCore.plugin, "SCORE-ID");
    }

    @Override
    public void actionOnObjectWhenLoading(SProjectile sProjectile) {

    }

    @Override
    public void actionOnObjectWhenReloading(SProjectile sProjectile) {

    }

    @Override
    public Optional<SProjectile> methodObjectLoading(String s) {
        return Optional.empty();
    }

    public static SProjectilesManager getInstance() {
        if (instance == null) {
            instance = new SProjectilesManager();
        }
        return instance;
    }

    public boolean isValidID(String id) {
        for (SProjectile item : this.getLoadedObjects()) {
            if (item.getId().equals(id)) return true;
        }
        return false;
    }

    public Optional<SProjectile> getExecutableBlock(String s) {
        for (SProjectile item : this.getLoadedObjects()) {
            if (item.getId().equals(s)) return Optional.of(item);
        }
        return Optional.empty();
    }

    public List<String> getExecutableBlockIdsList() {
        List<String> list = new ArrayList<>();
        for (SProjectile item : this.getLoadedObjects()) {
            list.add(item.getId());
        }
        return list;
    }
}
