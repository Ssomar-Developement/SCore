package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.utils.strings.StringSetting;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;


public class DropExecutableItem extends EntityCommand {

    public static final Boolean DEBUG = false;

    public DropExecutableItem() {
        CommandSetting id = new CommandSetting("id", 0, String.class, "null");
        CommandSetting amount = new CommandSetting("amount", 1, Integer.class, 1);
        CommandSetting owner = new CommandSetting("owner", 2, String.class, null);
        CommandSetting itemdata = new CommandSetting("itemdata", 3, String.class, null);
        List<CommandSetting> settings = getSettings();
        settings.add(id);
        settings.add(amount);
        settings.add(owner);
        settings.add(itemdata);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity entity, SCommandToExec sCommandToExec) {
        String id = (String) sCommandToExec.getSettingValue("id");
        int amount = (int) sCommandToExec.getSettingValue("amount");
        String owner = (String) sCommandToExec.getSettingValue("owner");
        Map<String, Object> settings = StringSetting.getSettings((String) sCommandToExec.getSettingValue("itemdata"));

        if (!(SCore.hasExecutableItems && ExecutableItemsAPI.getExecutableItemsManager().isValidID(id))) {
            SCore.plugin.getLogger().info(ChatColor.RED+"Invalid ID was provided for a DROPEXECUTABLEITEM command. Please double check your DROPEXECUTABLEITEM commands.");
            return;
        }

        Optional<Player> playerOwner;
        playerOwner = Optional.ofNullable(Bukkit.getPlayer(owner)); // first attempt by getting player details via ign
        if (playerOwner == null)
            playerOwner = Optional.ofNullable(Bukkit.getPlayer(UUID.fromString(owner))); // second attempt by getting player details via uuid
        if (playerOwner == null)
            playerOwner = Optional.ofNullable(p); // if all fails, rely on the player details of the one who executed the cmd

        if (SCore.hasExecutableItems && ExecutableItemsAPI.getExecutableItemsManager().isValidID(id)) {

            if (amount > 0) {
                Optional<ExecutableItemInterface> eiOpt = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(id);
                if (eiOpt.isPresent()) {
                    ExecutableItemInterface ei = eiOpt.get();
                    entity.getWorld().dropItem(entity.getLocation(), ei.buildItem(amount, playerOwner, settings));
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
        return "DROPEXECUTABLEITEM id:{id} amount:{number} owner:{ign/uuid} itemdata:{usage/variables/durability w/o no outer side brackets}";
        // ex: DROPEXECUTABLEITEM id:drop_stick amount:1 owner:Special70 itemdata:Usage:50,Variables:{keg:deng}
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
