package com.ssomar.score.hardness.hardness.loader;

import com.ssomar.score.SCore;
import com.ssomar.score.hardness.hardness.Hardness;
import com.ssomar.score.hardness.hardness.manager.HardnessesManager;
import com.ssomar.score.sobject.SObjectWithFileLoader;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.util.*;

public class HardnessLoader extends SObjectWithFileLoader<Hardness> {

    private static HardnessLoader instance;

    private static final String DEFAULT = "Default";


    public HardnessLoader() {
        super(SCore.plugin, "/com/ssomar/score/configs/hardnesses/", HardnessesManager.getInstance(), 1000);
    }

    @Override
    public void load() {
        HardnessesManager.getInstance().setDefaultObjects(new ArrayList<>());
        /* // TODO if (!GeneralConfig.getInstance().isDisableTestItems()) {*/
        //if (PlaceholderAPI.isLotOfWork()) {
            this.loadDefaultPremiumObjects();
        //}
        //this.loadDefaultEncodedPremiumObjects(this.getPremiumPackObjectsName());
        //}

        // ITEMS CONFIG
        HardnessesManager.getInstance().setLoadedObjects(new ArrayList<>());

        this.resetCpt();

        File itemsDirectory;
        if ((itemsDirectory = new File(SCore.plugin.getDataFolder() + "/hardnesses")).exists()) {
            this.loadObjectsInFolder(itemsDirectory, true);
        } else {
            this.createDefaultObjectsFile( true);
            this.load();
        }
    }



    public Map<String, List<String>> getNotEditableProjectilesName() {
        Map<String, List<String>> defaultBlocks = new HashMap<>();

        List<String> defaultProj = new ArrayList<>();


        defaultBlocks.put(DEFAULT, defaultProj);

        return defaultBlocks;
    }

    @Override
    public void configVersionsConverter(File file) {
        ConfigConverter.update(file);
    }

    @Override
    public Optional<Hardness> getObject(FileConfiguration itemConfig, String id, boolean showError, boolean isPremiumLoading, String path) {

        List<String> errors = new ArrayList<>();
        Hardness item = new Hardness(id, path);
        errors.addAll(item.load(SCore.plugin, itemConfig, isPremiumLoading));

        if (showError) {
            for (String s : errors) {
                SCore.plugin.getServer().getLogger().severe(s);
            }
        }
        return Optional.ofNullable(item);
    }

    public static HardnessLoader getInstance() {
        if (instance == null) instance = new HardnessLoader();
        return instance;
    }

}
