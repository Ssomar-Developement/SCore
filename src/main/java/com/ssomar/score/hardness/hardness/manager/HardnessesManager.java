package com.ssomar.score.hardness.hardness.manager;

import com.ssomar.score.SCore;
import com.ssomar.score.hardness.hardness.Hardness;
import com.ssomar.score.sobject.SObjectWithFileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HardnessesManager extends SObjectWithFileManager<Hardness> {

    private static HardnessesManager instance;

    public HardnessesManager() {
        super(SCore.plugin, "hardnesses");
    }

    @Override
    public void actionOnObjectWhenLoading(Hardness sProjectile) {

    }

    @Override
    public void actionOnObjectWhenReloading(Hardness sProjectile) {

    }

    @Override
    public Optional<Hardness> methodObjectLoading(String s) {
        return Optional.empty();
    }

    public static HardnessesManager getInstance() {
        if (instance == null) {
            instance = new HardnessesManager();
        }
        return instance;
    }

    public boolean isValidID(String id) {
        for (Hardness item : this.getLoadedObjects()) {
            if (item.getId().equals(id)) return true;
        }
        return false;
    }

    public Optional<Hardness> getExecutableBlock(String s) {
        for (Hardness item : this.getLoadedObjects()) {
            if (item.getId().equals(s)) return Optional.of(item);
        }
        return Optional.empty();
    }

    public List<String> getExecutableBlockIdsList() {
        List<String> list = new ArrayList<>();
        for (Hardness item : this.getLoadedObjects()) {
            list.add(item.getId());
        }
        return list;
    }
}
