package com.ssomar.score.variables.loader;

import com.ssomar.score.SCore;
import com.ssomar.score.sobject.NewSObjectLoader;
import com.ssomar.score.variables.Variable;
import com.ssomar.score.variables.manager.VariablesManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.util.*;

public class VariablesLoader extends NewSObjectLoader<Variable> {

    private static VariablesLoader instance;

    private static final String DEFAULT = "Default";


    public VariablesLoader() {
        super(SCore.plugin, "/com/ssomar/score/configs/variables/", VariablesManager.getInstance(), 1000);
    }

    @Override
    public void load() {
        VariablesManager.getInstance().setDefaultObjects(new ArrayList<>());
        /* // TODO if (!GeneralConfig.getInstance().isDisableTestItems()) {*/
        //if (PlaceholderAPI.isLotOfWork()) {
            this.loadDefaultPremiumObjects(this.getPremiumDefaultObjectsName());
        //}
        //this.loadDefaultEncodedPremiumObjects(this.getPremiumPackObjectsName());
        //}

        // ITEMS CONFIG
        VariablesManager.getInstance().setLoadedObjects(new ArrayList<>());

        this.resetCpt();

        File itemsDirectory;
        if ((itemsDirectory = new File(SCore.plugin.getDataFolder() + "/variables")).exists()) {
            this.loadObjectsInFolder(itemsDirectory, true);
        } else {
            this.createDefaultObjectsFile(true);
            this.load();
        }

    }

    public Map<String, List<String>> getPremiumPackObjectsName() {
        Map<String, List<String>> defaultItems = new HashMap<>();

        return defaultItems;
    }

    @Override
    public Map<String, List<String>> getPremiumDefaultObjectsName() {
        Map<String, List<String>> defaultBlocks = new HashMap<>();

        List<String> defaultProj = new ArrayList<>();
        //defaultProj.add("arrow1");

        defaultBlocks.put(DEFAULT, defaultProj);

        return defaultBlocks;
    }

    @Override
    public Map<String, List<String>> getFreeDefaultObjectsName() {
        Map<String, List<String>> defaultBlocks = new HashMap<>();

        List<String> defaultProj = new ArrayList<>();
        defaultProj.add("example");

        defaultBlocks.put(DEFAULT, defaultProj);

        return defaultBlocks;
    }

    @Override
    public void configVersionsConverter(File file) {
        ConfigConverter.update(file);
    }

    @Override
    public Optional<Variable> getObject(FileConfiguration itemConfig, String id, boolean showError, boolean isPremiumLoading, String path) {

        List<String> errors = new ArrayList<>();
        Variable item = new Variable(id, path);
        errors.addAll(item.load(SCore.plugin, itemConfig, isPremiumLoading));

        if (showError) {
            for (String s : errors) {
                SCore.plugin.getServer().getLogger().severe(s);
            }
        }
        return Optional.ofNullable(item);
    }

    public static VariablesLoader getInstance() {
        if (instance == null) instance = new VariablesLoader();
        return instance;
    }

}
