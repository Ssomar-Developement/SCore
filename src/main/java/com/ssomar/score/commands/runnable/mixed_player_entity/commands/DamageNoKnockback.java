package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import com.ssomar.score.damagewithoutknockback.DamageWithoutKnockbackManager;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DamageNoKnockback extends MixedCommand {

    @Override
    public void run(Player p, LivingEntity receiver, List<String> args, ActionInfo aInfo) {
        /* When target a NPC it can occurs */
        if (receiver == null) return;

        double damage = Damage.getDamage(p, receiver, args, aInfo);

        if (damage > 0 && !receiver.isDead()) {
            int maximumNoDmg = receiver.getMaximumNoDamageTicks();
            receiver.setMaximumNoDamageTicks(0);
            boolean doDamage = true;
            if (SCore.hasWorldGuard && receiver instanceof Player) doDamage = WorldGuardAPI.isInPvpZone((Player) receiver, receiver.getLocation());
            if(doDamage) {
                if (p != null) {
                        p.setMetadata("cancelDamageEvent", (MetadataValue) new FixedMetadataValue((Plugin) SCore.plugin, Integer.valueOf(7772)));
                        DamageWithoutKnockbackManager.getInstance().addDamageWithoutKnockback(receiver);
                        receiver.damage(damage, p);
                } else {
                        DamageWithoutKnockbackManager.getInstance().addDamageWithoutKnockback(receiver);
                        receiver.damage(damage);
                }
            }
            receiver.setMaximumNoDamageTicks(maximumNoDmg);
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return staticVerif(args, isFinalVerification, getTemplate());
    }

    public static Optional<String> staticVerif(List<String> args, boolean isFinalVerification, String template) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + template);

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, template, true);
        if (!ac.isValid()) return Optional.of(ac.getError());

        if (args.size() >= 2) {
            String value = args.get(1);
            ArgumentChecker ac2 = checkBoolean(value, isFinalVerification, template);
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        if (args.size() >= 3) {
            String value = args.get(2);
            ArgumentChecker ac2 = checkBoolean(value, isFinalVerification, template);
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("DAMAGE_NO_KNOCKBACK");
        return names;
    }

    @Override
    public String getTemplate() {
        return "DAMAGE_NO_KNOCKBACK {number} [amplified If Strength Effect, true or false] [amplified with attack attribute, true or false]";
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