package com.ssomar.score.commands.score;

import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.SObjectManager;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Optional;

public abstract class CustomCommandWithManagerAbstract<T extends SPlugin, Y extends SObjectManager<X>, X extends SObject> extends CustomCommandAbstract<T>{

    @Getter
    private Y sObjectManager;

    public CustomCommandWithManagerAbstract(T sPlugin,Y sObjectManager) {
        super(sPlugin);
        this.sObjectManager = sObjectManager;
    }

    public Optional<X> checkSObject(CommandSender sender, String sObjectId) {
        Optional<X> oOpt = sObjectManager.getLoadedObjectWithID(sObjectId);
        if (!oOpt.isPresent())
            getSm().sendMessage(sender, ChatColor.RED + getSPlugin().getNameWithBrackets() + " " + sObjectManager.getObjectName() + " " + sObjectId + " not found");
        return oOpt;
    }
}
