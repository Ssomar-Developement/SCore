package com.ssomar.score.variables.manager;

import com.ssomar.score.SCore;
import com.ssomar.score.sobject.NewSObjectManager;
import com.ssomar.score.variables.Variable;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VariablesManager extends NewSObjectManager<Variable> {

    private static VariablesManager instance;

    private NamespacedKey key;

    public VariablesManager() {
        super(SCore.plugin, "variables");

        if (!SCore.is1v13Less()) key = new NamespacedKey(SCore.plugin, "SCORE-ID");
    }

    @Override
    public void actionOnObjectWhenLoading(Variable sProjectile) {

    }

    @Override
    public void actionOnObjectWhenReloading(Variable sProjectile) {

    }

    @Override
    public Optional<Variable> methodObjectLoading(String s) {
        return Optional.empty();
    }

    public static VariablesManager getInstance() {
        if (instance == null) {
            instance = new VariablesManager();
        }
        return instance;
    }

    public boolean isValidID(String id) {
        for (Variable item : this.getLoadedObjects()) {
            if (item.getId().equals(id)) return true;
        }
        return false;
    }

    public Optional<Variable> getVariable(String s) {
        for (Variable item : this.getLoadedObjects()) {
            if (item.getId().equals(s)) return Optional.of(item);
        }
        return Optional.empty();
    }

    public List<String> getVariableIdsList() {
        List<String> list = new ArrayList<>();
        for (Variable item : this.getLoadedObjects()) {
            list.add(item.getId());
        }
        return list;
    }

    public String getVariableIdsListStr(){
        StringBuilder sb = new StringBuilder();
        boolean pass = false;
        for (Variable item : this.getLoadedObjects()) {
            sb.append(item.getId()).append(" | ");
            pass = true;
        }
        if(pass) sb.delete(sb.length()-3, sb.length());
        return sb.toString();
    }
}
