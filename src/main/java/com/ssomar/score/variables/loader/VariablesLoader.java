package com.ssomar.score.variables.loader;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.sobject.SObjectWithFileLoader;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.variables.Variable;
import com.ssomar.score.variables.manager.VariablesManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.*;

public class VariablesLoader extends SObjectWithFileLoader<Variable> {

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
        //this.loadDefaultPremiumObjects();
        //}
        //this.loadDefaultEncodedPremiumObjects(this.getPremiumPackObjectsName());
        //}

        // ITEMS CONFIG
        VariablesManager.getInstance().setLoadedObjects(new ArrayList<>());

        this.resetCpt();

        File variablesDirectory;
        if ((variablesDirectory = new File(SCore.plugin.getDataFolder() + "/variables")).exists()) {
            this.loadObjectsInFolder(variablesDirectory, true);

            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7SCore loaded &6"+VariablesManager.getInstance().getLoadedObjects().size()+" &7variables from local files !");

            if(GeneralConfig.getInstance().isUseMySQL()){
                VariablesManager.getInstance().updateAllLoadedMySQL(VariablesManager.MODE.IMPORT);
                Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7SCore loaded &6"+VariablesManager.getInstance().getLoadedObjects().size()+" &7variables from your MySQL Database !");
            }

        } else {
            this.createDefaultObjectsFile(true);
            this.load();
        }

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
