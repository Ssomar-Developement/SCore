package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DamageNoKnockback extends PlayerCommand {

    @SuppressWarnings("deprecation")
    public static double getDamage(Player launcher, LivingEntity receiver, List<String> args, ActionInfo actionInfo) {
        double amount;
        String damage = args.get(0);

        boolean potionAmplification = false;
        if (args.size() >= 2) {
            potionAmplification = Boolean.valueOf(args.get(1));
        }
        boolean attributeAmplification = false;
        if (args.size() >= 3) {
            attributeAmplification = Boolean.valueOf(args.get(2));
        }


        /* percentage damage */
        if (damage.contains("%")) {
            String[] decomp = damage.split("%");
            damage = decomp[0];
            damage = damage.trim();
            if (damage.length() == 1) {
                damage = "0" + damage;
            }

            double percentage = damage.equals("100") ? 1 : Double.parseDouble("0." + damage);
            amount = receiver.getMaxHealth() * percentage;
            amount = NTools.reduceDouble(amount, 2);
        } else amount = Double.parseDouble(damage);

        if (launcher != null) {
            if (potionAmplification) {
                PotionEffect pE = launcher.getPotionEffect(PotionEffectType.INCREASE_DAMAGE);
                if (pE != null) {
                    amount = amount + (pE.getAmplifier() + 1) * 1.5;
                }
            }

            //SsomarDev.testMsg("boost attribute: "+ attributeAmplification);
            if (attributeAmplification) {
                AttributeInstance aI = launcher.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                double bonusAmount = 0;
                if (aI != null) {
                    //SsomarDev.testMsg("damage value: "+aI.getValue());
                    for (AttributeModifier aM : aI.getModifiers()) {
                        //SsomarDev.testMsg("passe 2:  "+aM.getOperation());
                        if (aM.getOperation().equals(AttributeModifier.Operation.MULTIPLY_SCALAR_1)) {
                            //SsomarDev.testMsg("passe 3: "+(amount * aM.getAmount())+ " >> "+aM.getAmount());
                            bonusAmount = bonusAmount + amount * aM.getAmount();
                        }
                    }
                }
                //SsomarDev.testMsg("boost attribute bonus: "+ bonusAmount);
                amount = amount + bonusAmount;
            }
        }
        return amount;
    }

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        /* When target a NPC it can occurs */
        if (receiver == null || receiver.getGameMode().equals(GameMode.CREATIVE)) return;

        double damage = getDamage(p, receiver, args, aInfo);

        if (damage > 0 && !receiver.isDead()) {
            int maximumNoDmg = receiver.getMaximumNoDamageTicks();
            receiver.setMaximumNoDamageTicks(0);
            if (p != null) {
                boolean doDamage = true;
                if (SCore.hasWorldGuard) doDamage = WorldGuardAPI.isInPvpZone(receiver, receiver.getLocation());
                if (doDamage) {
                    p.setMetadata("cancelDamageEvent", (MetadataValue) new FixedMetadataValue((Plugin) SCore.plugin, Integer.valueOf(7772)));
                    receiver.setLastDamageCause(new EntityDamageEvent((Entity) p, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage));
                    receiver.playEffect(EntityEffect.HURT);
                    receiver.setHealth(Math.max(receiver.getHealth() -damage, 0));
                }
            } else {
                boolean doDamage = true;
                if (SCore.hasWorldGuard) doDamage = WorldGuardAPI.isInPvpZone(receiver, receiver.getLocation());
                if (doDamage) {
                    receiver.playEffect(EntityEffect.HURT);
                    receiver.setHealth(Math.max(receiver.getHealth() -damage, 0));
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