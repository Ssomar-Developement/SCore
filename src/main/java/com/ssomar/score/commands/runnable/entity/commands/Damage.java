package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.utils.NTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/* DAMAGE {amount} */
public class Damage extends EntityCommand{

	@SuppressWarnings("deprecation")
	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
		/* When target a NPC it can occurs */
		if(entity == null || !(entity instanceof LivingEntity)) return;
		LivingEntity receiver = (LivingEntity)  entity;

		double damage = com.ssomar.score.commands.runnable.player.commands.Damage.getDamage(p, receiver, args, aInfo);

		if(damage > 0 && !entity.isDead()) {
			if(receiver instanceof EnderDragon){
				//SsomarDev.testMsg("Passe enderdrag");
				double newHealth = receiver.getHealth()-damage;
				if(newHealth <= 0){
					((EnderDragon) receiver).setPhase(EnderDragon.Phase.DYING);
				}
				else{
					receiver.setHealth(newHealth);
					receiver.playEffect(EntityEffect.HURT);
					receiver.getWorld().playSound(receiver.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 100, 1);
				}
			}

			int maximumNoDmg = receiver.getMaximumNoDamageTicks();
			receiver.setMaximumNoDamageTicks(0);
			if(p != null) {
				p.setMetadata("cancelDamageEvent", (MetadataValue)new FixedMetadataValue((Plugin)SCore.plugin, Integer.valueOf(7772)));
				receiver.damage(damage, (Entity)p);
			}
			else receiver.damage(damage);
			receiver.setMaximumNoDamageTicks(maximumNoDmg);
		}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String damage = "DAMAGE {amount} {amplified If Strength Effect, true or false} {amplified with attack attribute, true or false}";
		if(args.size() < 1) error = notEnoughArgs+damage;
		else if(args.size() > 3) error= tooManyArgs+damage;

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("DAMAGE");
		return names;
	}

	@Override
	public String getTemplate() {
		return "DAMAGE {amount} {amplified If Strength Effect, true or false} {amplified with attack attribute, true or false}";
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
