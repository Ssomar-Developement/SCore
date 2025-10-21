package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* TRICK_OR_TREAT */
public class TrickOrTreat extends PlayerCommand {

    private static final Random random = new Random();

    public TrickOrTreat() {
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        // 50% chance for treat, 50% for trick
        boolean isTreat = random.nextBoolean();

        if (isTreat) {
            // TREAT - Give candy and positive effects
            giveTreat(receiver);
        } else {
            // TRICK - Apply spooky negative effects
            performTrick(receiver);
        }
    }

    private void giveTreat(Player player) {
        // Give random Halloween treats
        Material[] treats = {
            Material.COOKIE,
            Material.CAKE,
            Material.PUMPKIN_PIE,
            Material.GOLDEN_APPLE,
            Material.ENCHANTED_GOLDEN_APPLE
        };

        Material selectedTreat = treats[random.nextInt(treats.length)];
        int amount = random.nextInt(3) + 1; // 1-3 items

        ItemStack treat = new ItemStack(selectedTreat, amount);
        player.getInventory().addItem(treat);

        // Apply positive effect
        PotionEffectType[] positiveEffects = {
            PotionEffectType.SPEED,
            PotionEffectType.REGENERATION,
            PotionEffectType.SATURATION,
            PotionEffectType.LUCK
        };

        PotionEffectType selectedEffect = positiveEffects[random.nextInt(positiveEffects.length)];
        player.addPotionEffect(new PotionEffect(selectedEffect, 200, 0, false, true));

        // Spawn happy particles
        player.getWorld().spawnParticle(
            Particle.HAPPY_VILLAGER,
            player.getLocation().add(0, 1, 0),
            30,
            0.5,
            0.5,
            0.5,
            0.1
        );

        // Play success sound
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1.5f);

        sm.sendMessage(player, ChatColor.GREEN + "TREAT! You received " + amount + "x " + selectedTreat.name() + "!", false);
    }

    private void performTrick(Player player) {
        // Apply spooky effects
        List<PotionEffectType> negativeEffects = new ArrayList<>();
        negativeEffects.add(PotionEffectType.BLINDNESS);
        negativeEffects.add(PotionEffectType.SLOWNESS);
        negativeEffects.add(PotionEffectType.WEAKNESS);

        // Add version-specific effects
        PotionEffectType nausea = PotionEffectType.getByName("NAUSEA");
        if (nausea != null) negativeEffects.add(nausea);

        PotionEffectType darkness = PotionEffectType.getByName("DARKNESS");
        if (darkness != null) negativeEffects.add(darkness);

        PotionEffectType selectedEffect = negativeEffects.get(random.nextInt(negativeEffects.size()));
        int duration = random.nextInt(100) + 60; // 60-160 ticks (3-8 seconds)

        player.addPotionEffect(new PotionEffect(selectedEffect, duration, 0, false, true));

        // Spawn spooky particles
        player.getWorld().spawnParticle(
            Particle.SMOKE,
            player.getLocation().add(0, 1, 0),
            40,
            0.5,
            0.5,
            0.5,
            0.05
        );

        // Play spooky sound
        Sound[] spookySounds = {
            Sound.ENTITY_WITCH_AMBIENT,
            Sound.ENTITY_GHAST_SCREAM,
            Sound.ENTITY_ENDERMAN_SCREAM,
            Sound.ENTITY_PHANTOM_AMBIENT
        };

        Sound selectedSound = spookySounds[random.nextInt(spookySounds.length)];
        player.getWorld().playSound(player.getLocation(), selectedSound, 1.0f, 0.8f);

        sm.sendMessage(player, ChatColor.RED + "TRICK! You've been spooked!", false);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("TRICK_OR_TREAT");
        names.add("TRICKORTREAT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "TRICK_OR_TREAT";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.DARK_RED;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.GOLD;
    }
}
