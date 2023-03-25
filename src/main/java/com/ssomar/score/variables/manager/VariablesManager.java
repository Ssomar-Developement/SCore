package com.ssomar.score.variables.manager;

import com.ssomar.score.SCore;
import com.ssomar.score.sobject.NewSObjectManager;
import com.ssomar.score.variables.Variable;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;

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

    public String getVariableIdsListStr() {
        StringBuilder sb = new StringBuilder();
        boolean pass = false;
        for (Variable item : this.getLoadedObjects()) {
            sb.append(item.getId()).append(" | ");
            pass = true;
        }
        if (pass) sb.delete(sb.length() - 3, sb.length());
        return sb.toString();
    }

    public Optional<String> onRequestPlaceholder(OfflinePlayer player, String params) {
        //System.out.println("params: "+params);

        boolean variables = false;
        boolean variablesContains = false;
        boolean variablesSize = false;

        String check = params;
        if (params.startsWith("variables-contains_")) {
            variablesContains = true;
            check = params.substring(19);
        } else if (params.startsWith("variables-size_")) {
            variablesSize = true;
            check = params.substring(15);
        } else if (params.startsWith("variables_")) {
            variables = true;
            check = params.substring(10);
        }

        if (!variables && !variablesContains && !variablesSize) return Optional.empty();

        String variableID = null;

        for (String portentialID : getVariableIdsList()) {
            if (check.startsWith(portentialID)) {
                variableID = portentialID;
                break;
            }
        }

        if (variableID == null) return Optional.of("Variable not found");

        check = check.substring(variableID.length());

        Optional<Variable> var = VariablesManager.getInstance().getVariable(variableID);

        if (!var.isPresent()) return Optional.of("Variable not found");

        // score_variables-contains_<variable-name>_<value>
        if (variablesContains) {
            if (check.startsWith("_")) check = check.substring(1);
            String value = check;
            return Optional.of(var.get().containsValue(Optional.ofNullable(player.getPlayer()), value) + "");
        } else if (variablesSize) {

            Optional<Integer> indexOpt = Optional.empty();
            if (check.startsWith("_")) {
                check = check.substring(1);
                if (!check.isEmpty()) {
                    try {
                        indexOpt = Optional.of(Integer.parseInt(check));
                    } catch (Exception ignored) {}
                }
            }

            return Optional.of(var.get().sizeValue(Optional.ofNullable(player.getPlayer()), indexOpt) + "");
        }
        // score_variables_<variable-name>[_<index>][_int]
        else if (variables) {

            boolean castInt = false;
            if (check.endsWith("_int")) {
                castInt = true;
                check = check.substring(0, check.length() - 4);
            }

            Optional<Integer> indexOpt = Optional.empty();
            if (check.startsWith("_")) {
                check = check.substring(1);
                if (!check.isEmpty()) {
                    try {
                        indexOpt = Optional.of(Integer.parseInt(check));
                    } catch (Exception ignored) {
                    }
                }
            }

            String value = var.get().getValue(Optional.ofNullable(player), indexOpt);
            //SsomarDev.testMsg("value: "+value, true);
            if (castInt) {
                try {
                    return Optional.of(Double.valueOf(value).intValue() + "");
                } catch (NumberFormatException e) {
                    return Optional.of("Variable can't be converted to int");
                }
            } else return Optional.of(value);

        }

        return Optional.empty(); // Placeholder is unknown
    }
}
