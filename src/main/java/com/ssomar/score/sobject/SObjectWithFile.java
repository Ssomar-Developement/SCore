package com.ssomar.score.sobject;


import com.ssomar.executableitems.configs.Message;
import com.ssomar.score.SCore;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.utils.messages.SendMessage;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class SObjectWithFile<X extends FeatureInterface<X, X>, Y extends GUI, Z extends NewGUIManager<Y>> extends SObject<X, Y, Z> {

    private String path;
    private SObjectWithFileLoader sObjectWithFileLoader;
    private List<String> parentFoldersNames;

    public SObjectWithFile(SPlugin sPlugin, String id, FeatureSettingsInterface featureSettings, String path, SObjectWithFileLoader sObjectWithFileLoader) {
        super(sPlugin, id, null, featureSettings);
        this.path = path;
        this.sObjectWithFileLoader = sObjectWithFileLoader;
        this.parentFoldersNames = null;
    }

    /**
     * Useful to have an option to set a parent for the clone option
     **/
    public SObjectWithFile(SPlugin sPlugin, String id,FeatureParentInterface parent, FeatureSettingsInterface featureSettings, String path, SObjectWithFileLoader sObjectWithFileLoader) {
        super(sPlugin, id, parent, featureSettings);
        this.path = path;
        this.sObjectWithFileLoader = sObjectWithFileLoader;
        this.parentFoldersNames = null;
    }

    @Override
    public boolean delete() {
        File file = null;
        //SsomarDev.testMsg("DELETE path: "+getPath(), true);
        if ((file = new File(getPath())) != null) {
            file.delete();
            return true;
        }
        return false;

    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        File file = getFile();
        FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        return config;
    }

    @Override
    public File getFile() {
        File file = new File(path);
        if (!file.exists()) {
            try {
                if(file.getParentFile() != null) file.getParentFile().mkdirs();
                new File(path).createNewFile();
                file = sObjectWithFileLoader.searchFileOfObject(getId());
            } catch (IOException e) {
                Utils.sendConsoleMsg(SCore.plugin,"Error while creating a file: " + path);
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }

    public List<String> getParentFoldersNames(){
        if(parentFoldersNames != null) return parentFoldersNames;
        List<String> folders = new ArrayList<>();
        File file = getFile();
        File parent = file.getParentFile();
        SPlugin sPlugin = this.getPlugin();
        while(parent != null) {
            if(parent.getName().equalsIgnoreCase(sPlugin.getName())) break;
            folders.add(parent.getName());
            parent = parent.getParentFile();
        }
        // copy the list to avoid modification
        parentFoldersNames = new ArrayList<>(folders);
        return folders;
    }

    @Override
    public boolean hasPermission(@NotNull Player player, boolean showError) {
        if (player.isOp()) return true;

        SPlugin sPlugin = this.getPlugin();

        //SsomarDev.testMsg("CHECK PERM sPlugin: "+sPlugin.getName(), true);
        
        String particle = sPlugin.getObjectNameForPermission(this);

        if (sPlugin.isLotOfWork()) {

            List<String> permissionsToCheck = new ArrayList<>();
            permissionsToCheck.add(sPlugin.getName()+"."+particle+"." + getId());
            permissionsToCheck.add(sPlugin.getName()+"."+particle+".*");
            permissionsToCheck.add(sPlugin.getShortName().toLowerCase()+"."+particle+"." + getId());
            permissionsToCheck.add(sPlugin.getShortName().toLowerCase()+"."+particle+".*");
            permissionsToCheck.add(sPlugin.getName()+"."+particle+".folder.*");
            permissionsToCheck.add(sPlugin.getShortName().toLowerCase()+"."+particle+".folder.*");
            for (String folder : getParentFoldersNames()) {
                permissionsToCheck.add(sPlugin.getName()+"."+particle+".folder." + folder);
                permissionsToCheck.add(sPlugin.getShortName().toLowerCase()+"."+particle+".folder." + folder);
            }
            permissionsToCheck.add("*");

            boolean hasPermission = false;
            for(String perm : permissionsToCheck) {
                if(player.hasPermission(perm)) {
                    hasPermission = true;
                    break;
                }
            }

            if (!hasPermission) {
                if (showError)
                    new SendMessage().sendMessage(player, StringConverter.replaceVariable(MessageMain.getInstance().getMessage(sPlugin.getPlugin(), Message.REQUIRE_PERMISSION), player.getName(), getId(), "", 0));
                return false;
            }
        } else {
            if (player.hasPermission("*")) return true;

            //SsomarDev.testMsg("CHECK PERM 2 sPlugin: "+sPlugin.getName(), true);

            List<String> permissionsToCheck = new ArrayList<>();
            permissionsToCheck.add(sPlugin.getName()+"."+particle+"." + getId());
            permissionsToCheck.add(sPlugin.getName()+"."+particle+".*");
            permissionsToCheck.add(sPlugin.getShortName().toLowerCase()+"."+particle+"." + getId());
            permissionsToCheck.add(sPlugin.getShortName().toLowerCase()+"."+particle+".*");
            permissionsToCheck.add(sPlugin.getName()+"."+particle+".folder.*");
            permissionsToCheck.add(sPlugin.getShortName().toLowerCase()+"."+particle+".folder.*");
            for (String folder : getParentFoldersNames()) {
                //SsomarDev.testMsg("folder: "+folder, true);
                permissionsToCheck.add(sPlugin.getName()+"."+particle+".folder." + folder);
                permissionsToCheck.add(sPlugin.getShortName().toLowerCase()+"."+particle+".folder." + folder);
            }

            boolean hasPermission = false;
            for(String perm : permissionsToCheck) {
                if(player.hasPermission(perm)) {
                    hasPermission = true;
                    break;
                }
            }

            List<String> invertedPermissionsToCheck = new ArrayList<>();
            invertedPermissionsToCheck.add("-"+sPlugin.getName()+"."+particle+"." + getId());
            invertedPermissionsToCheck.add("-"+sPlugin.getName()+"."+particle+".*");
            invertedPermissionsToCheck.add("-"+sPlugin.getShortName().toLowerCase()+"."+particle+"." + getId());
            invertedPermissionsToCheck.add("-"+sPlugin.getShortName().toLowerCase()+"."+particle+".*");
            invertedPermissionsToCheck.add("-"+sPlugin.getName()+"."+particle+".folder.*");
            invertedPermissionsToCheck.add("-"+sPlugin.getShortName().toLowerCase()+"."+particle+".folder.*");
            for (String folder : getParentFoldersNames()) {
                invertedPermissionsToCheck.add("-"+sPlugin.getName()+"."+particle+".folder." + folder);
                invertedPermissionsToCheck.add("-"+sPlugin.getShortName().toLowerCase()+"."+particle+".folder." + folder);
            }

            for(String perm : invertedPermissionsToCheck) {
                if(player.hasPermission(perm)) {
                    hasPermission = false;
                    break;
                }
            }

            if (!hasPermission) {
                if (showError)
                    new SendMessage().sendMessage(player, StringConverter.replaceVariable(MessageMain.getInstance().getMessage(sPlugin.getPlugin(), Message.REQUIRE_PERMISSION), player.getName(), getId(), "", 0));
                return false;
            }
        }

        return true;
    }
}
