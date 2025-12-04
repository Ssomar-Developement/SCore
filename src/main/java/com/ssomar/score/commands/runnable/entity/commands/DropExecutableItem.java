package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class DropExecutableItem extends EntityCommand {

    public static final Boolean DEBUG = false;

    public DropExecutableItem() {
        CommandSetting id = new CommandSetting("id", 0, String.class, "null");
        CommandSetting amount = new CommandSetting("amount", 1, Integer.class, 1);
        CommandSetting owner = new CommandSetting("owner", 2, String.class, null);
        List<CommandSetting> settings = getSettings();
        settings.add(id);
        settings.add(amount);
        settings.add(owner);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        String id = (String) sCommandToExec.getSettingValue("id");
        int amount = (int) sCommandToExec.getSettingValue("amount");
        String owner = (String) sCommandToExec.getSettingValue("owner");

        if (!(SCore.hasExecutableItems && ExecutableItemsAPI.getExecutableItemsManager().isValidID(id))) {
            SCore.plugin.getLogger().info(ChatColor.RED+"Invalid ID was provided for a DROPEXECUTABLEITEM command. Please double check your DROPEXECUTABLEITEM commands.");
            return;
        }

        Player playerOwner;
        playerOwner = Bukkit.getPlayer(owner); // first attempt by getting player details via ign
        if (playerOwner == null)
            playerOwner = Bukkit.getPlayer(UUID.fromString(owner)); // second attempt by getting player details via uuid
        if (playerOwner == null)
            playerOwner = p; // if all fails, rely on the player details of the one who executed the cmd

        //SsomarDev.testMsg("DropExecutableItem.run()", DEBUG);
        if (SCore.hasExecutableItems && ExecutableItemsAPI.getExecutableItemsManager().isValidID(id)) {
            //SsomarDev.testMsg("DropExecutableItem.run() - hasExecutableItems", DEBUG);
            if (amount > 0) {
                //SsomarDev.testMsg("DropExecutableItem.run() - amount > 0", DEBUG);
                Optional<ExecutableItemInterface> eiOpt = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(id);
                if (eiOpt.isPresent()) {
                    //SsomarDev.testMsg(">> loc: " + entity.getLocation());
                    entity.getWorld().dropItem(entity.getLocation(), eiOpt.get().buildItem(amount, Optional.empty(), Optional.ofNullable(playerOwner)));
                }
            }
        }
    }


    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DROPEXECUTABLEITEM");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DROPEXECUTABLEITEM id:{id} amount:{number} owner:{ign/uuid}";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

}
