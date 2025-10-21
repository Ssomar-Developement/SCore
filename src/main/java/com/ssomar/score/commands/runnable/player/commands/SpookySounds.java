package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* SPOOKY_SOUNDS soundType:{soundType} volume:{volume} pitch:{pitch} */
public class SpookySounds extends PlayerCommand {

    private static final Random random = new Random();

    public SpookySounds() {
        CommandSetting soundType = new CommandSetting("soundType", 0, String.class, "RANDOM");
        CommandSetting volume = new CommandSetting("volume", 1, Double.class, 1.0);
        CommandSetting pitch = new CommandSetting("pitch", 2, Double.class, 1.0);
        List<CommandSetting> settings = getSettings();
        settings.add(soundType);
        settings.add(volume);
        settings.add(pitch);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        String soundType = ((String) sCommandToExec.getSettingValue("soundType")).toUpperCase();
        double volume = (double) sCommandToExec.getSettingValue("volume");
        double pitch = (double) sCommandToExec.getSettingValue("pitch");

        Sound selectedSound;

        switch (soundType) {
            case "WITCH":
                selectedSound = Sound.ENTITY_WITCH_AMBIENT;
                spawnParticles(receiver, Particle.WITCH);
                break;
            case "GHOST":
                selectedSound = Sound.ENTITY_PHANTOM_AMBIENT;
                spawnParticles(receiver, Particle.SOUL);
                break;
            case "ZOMBIE":
                selectedSound = Sound.ENTITY_ZOMBIE_AMBIENT;
                spawnParticles(receiver, Particle.ASH);
                break;
            case "ENDERMAN":
                selectedSound = Sound.ENTITY_ENDERMAN_SCREAM;
                spawnParticles(receiver, Particle.REVERSE_PORTAL);
                break;
            case "GHAST":
                selectedSound = Sound.ENTITY_GHAST_SCREAM;
                spawnParticles(receiver, Particle.FLAME);
                break;
            case "WITHER":
                selectedSound = Sound.ENTITY_WITHER_AMBIENT;
                spawnParticles(receiver, Particle.SMOKE);
                break;
            case "THUNDER":
                selectedSound = Sound.ENTITY_LIGHTNING_BOLT_THUNDER;
                spawnParticles(receiver, Particle.ELECTRIC_SPARK);
                break;
            case "RANDOM":
            default:
                Sound[] spookySounds = {
                    Sound.ENTITY_WITCH_AMBIENT,
                    Sound.ENTITY_PHANTOM_AMBIENT,
                    Sound.ENTITY_ZOMBIE_AMBIENT,
                    Sound.ENTITY_ENDERMAN_SCREAM,
                    Sound.ENTITY_GHAST_SCREAM,
                    Sound.ENTITY_WITHER_AMBIENT,
                    Sound.ENTITY_EVOKER_PREPARE_SUMMON,
                    Sound.AMBIENT_CAVE
                };
                selectedSound = spookySounds[random.nextInt(spookySounds.length)];
                spawnParticles(receiver, Particle.SMOKE);
                break;
        }

        // Play the spooky sound
        receiver.getWorld().playSound(receiver.getLocation(), selectedSound, (float) volume, (float) pitch);
    }

    private void spawnParticles(Player player, Particle particleType) {
        player.getWorld().spawnParticle(
            particleType,
            player.getLocation().add(0, 1, 0),
            20,
            0.5,
            0.5,
            0.5,
            0.05
        );
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SPOOKY_SOUNDS");
        names.add("SPOOKYSOUNDS");
        names.add("HALLOWEEN_SOUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SPOOKY_SOUNDS soundType:RANDOM volume:1.0 pitch:1.0";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.DARK_GRAY;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.RED;
    }
}
