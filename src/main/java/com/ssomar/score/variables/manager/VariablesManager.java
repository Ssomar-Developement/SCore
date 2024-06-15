package com.ssomar.score.variables.manager;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.data.Database;
import com.ssomar.score.data.VariablesQuery;
import com.ssomar.score.sobject.SObjectWithFileManager;
import com.ssomar.score.variables.Variable;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class VariablesManager extends SObjectWithFileManager<Variable> {

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
        updateLoadedMySQL(s, MODE.IMPORT);
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

        List<String> valids = new ArrayList<>();
        for (String portentialID : getVariableIdsList()) {
            if (check.startsWith(portentialID)) {
                valids.add(portentialID);
            }
        }
        for (String valid : valids) {
            if (variableID == null || variableID.length() < valid.length()) {
                variableID = valid;
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
            // There still be placeholder value inside we will replace it later ex : %score_variables-contains_test%around_target_uuid%%
            if(value.isEmpty()) return Optional.empty();
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

    public void updateAllLoadedMySQL(MODE mode){
        if(GeneralConfig.getInstance().isUseMySQL()) {
            if(mode.equals(MODE.IMPORT)) {
                VariablesQuery.insertVariableNotExists(Database.getInstance().connect(), VariablesManager.getInstance().getAllObjects());
                VariablesManager.getInstance().deleteAllLoadedObjects();
                /* Not generate issue because  https://discord.com/channels/701066025516531753/1126693934064730112/1126693934064730112
                * when using MYSQL we reload the variables continuously and sometimes async, the event cant be generated async*/
                VariablesManager.getInstance().addLoadedObjects(VariablesQuery.selectAllVariables(Database.getInstance().connect()), false);
                VariablesManager.getInstance().saveAllLoadedObjects();
            }
        }
    }

    public void updateLoadedMySQL(String id, MODE mode){
        if(GeneralConfig.getInstance().isUseMySQL()) {
            if(mode.equals(MODE.IMPORT)) {
                VariablesManager.getInstance().deleteObject(id);
                Optional<Variable> var = VariablesQuery.selectVariable(Database.getInstance().connect(), id);
                if (var.isPresent()) {
                    VariablesManager.getInstance().addLoadedObject(var.get(), false);
                    var.get().save();
                }
            }
            else if(mode.equals(MODE.EXPORT)) {
                Optional<Variable> varOpt = VariablesManager.getInstance().getLoadedObjectWithID(id);
                if (varOpt.isPresent()) {
                    VariablesQuery.insertVariablesAndDeleteIfExists(Database.getInstance().connect(), Arrays.asList(varOpt.get()));
                }
            }
        }
    }

    public void deleteLoadedMYSQL(String id) {
        if(GeneralConfig.getInstance().isUseMySQL()) {
            VariablesQuery.deleteVariable(Database.getInstance().connect(), id);
        }
    }

    public enum MODE {
        IMPORT,
        EXPORT;
    }
}