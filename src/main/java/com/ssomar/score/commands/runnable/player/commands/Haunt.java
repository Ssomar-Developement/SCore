package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/* HAUNT duration:{duration} */
public class Haunt extends PlayerCommand {

    public Haunt() {
        CommandSetting duration = new CommandSetting("duration", 0, Integer.class, 100);
        List<CommandSetting> settings = getSettings();
        settings.add(duration);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        int duration = (int) sCommandToExec.getSettingValue("duration");

        // Apply invisibility effect to make player ghostly
        receiver.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, duration, 0, false, false));

        // Spawn ghost-like particles around the player
        receiver.getWorld().spawnParticle(
            Particle.SOUL,
            receiver.getLocation(),
            30,
            0.5,
            1.0,
            0.5,
            0.05
        );

        // Play spooky ghost sound
        receiver.getWorld().playSound(receiver.getLocation(), Sound.ENTITY_PHANTOM_AMBIENT, 1.0f, 0.8f);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("HAUNT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "HAUNT duration:100";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.DARK_PURPLE;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.LIGHT_PURPLE;
    }
}
