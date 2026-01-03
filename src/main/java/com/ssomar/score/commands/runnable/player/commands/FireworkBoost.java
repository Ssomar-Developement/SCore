package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FireworkBoost extends PlayerCommand {
    public FireworkBoost() {
        CommandSetting duration = new CommandSetting("duration", 0, Integer.class, 1);
        List<CommandSetting> settings = getSettings();
        settings.add(duration);
        setNewSettingsMode(true);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("FIREWORK_BOOST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "FIREWORK_BOOST {duration in seconds}";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

    @Override
    public void run(@Nullable Player launcher, Player receiver, SCommandToExec sCommandToExec) {
        if (launcher.isGliding()) {
            ItemStack itemStack = new ItemStack(Material.FIREWORK_ROCKET, 1);
            FireworkMeta itemMeta = (FireworkMeta) itemStack.getItemMeta();
            itemMeta.setPower((int) sCommandToExec.getSettingValue("duration"));

            itemStack.setItemMeta(itemMeta);

            launcher.fireworkBoost(itemStack);
        }
    }
}
