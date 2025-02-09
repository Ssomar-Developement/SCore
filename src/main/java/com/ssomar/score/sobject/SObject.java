package com.ssomar.score.sobject;

import com.ssomar.executableitems.configs.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.FeatureWithHisOwnEditor;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.messages.SendMessage;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Setter
public abstract class SObject<X extends FeatureInterface<X, X>, Y extends GUI, Z extends NewGUIManager<Y>> extends FeatureWithHisOwnEditor<X, X, Y, Z> implements SObjectInterface{

    private SPlugin sPlugin;
    private String id;

    public SObject(SPlugin sPlugin, String id, FeatureSettingsInterface featureSettings) {
        super(null, featureSettings);
        this.sPlugin = sPlugin;
        this.id = id;
    }

    /**
     * Useful to have an option to set a parent for the clone option
     **/
    public SObject(SPlugin sPlugin, String id, FeatureParentInterface parent, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.sPlugin = sPlugin;
        this.id = id;
    }

    public abstract boolean delete();

    @Override
    public String getId() {
        return id;
    }

    public SPlugin getPlugin() {
        return sPlugin;
    }


    public boolean hasPermission(@NotNull Player player, boolean showError) {
        if (player.isOp()) return true;

        //SsomarDev.testMsg("CHECK PERM SOBJECT : ", true);
        
        String particle = sPlugin.getObjectNameForPermission(this);

        if (sPlugin.isLotOfWork()) {

            List<String> permissionsToCheck = new ArrayList<>();
            permissionsToCheck.add(sPlugin.getName()+"."+particle+"." + getId());
            permissionsToCheck.add(sPlugin.getName()+"."+particle+".*");
            permissionsToCheck.add(sPlugin.getShortName().toLowerCase()+"."+particle+"." + getId());
            permissionsToCheck.add(sPlugin.getShortName().toLowerCase()+"."+particle+".*");
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


            List<String> permissionsToCheck = new ArrayList<>();
            permissionsToCheck.add(sPlugin.getName()+"."+particle+"." + getId());
            permissionsToCheck.add(sPlugin.getName()+"."+particle+".*");
            permissionsToCheck.add(sPlugin.getShortName().toLowerCase()+"."+particle+"." + getId());
            permissionsToCheck.add(sPlugin.getShortName().toLowerCase()+"."+particle+".*");

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