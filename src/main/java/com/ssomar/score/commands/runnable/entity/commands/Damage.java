package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;

import com.ssomar.score.SsomarDev;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;

/* DAMAGE {amount} */
public class Damage extends EntityCommand{

	@SuppressWarnings("deprecation")
	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
		//SsomarDev.testMsg("Passe damage 1");
		try {
			double amount;
			String damage = args.get(0);
			/* percentage damage */
			if(damage.contains("%") && entity instanceof LivingEntity) {
				String [] decomposition = damage.split("\\%");
				damage = decomposition[0];
				
				double percentage = damage.equals("100") ? 1 : Double.parseDouble("0."+damage);
				amount = ((LivingEntity) entity).getMaxHealth() * percentage;
				
				amount = Double.parseDouble((amount+"").substring(0, 3));
			}
			else amount = Double.parseDouble(damage);
			//SsomarDev.testMsg("Passe damage 2");
			if(amount > 0 && !entity.isDead() && entity instanceof LivingEntity) {
				LivingEntity e = (LivingEntity) entity;
				if(e instanceof EnderDragon){
					//SsomarDev.testMsg("Passe enderdrag");
					double newHealth = e.getHealth()-amount;
					if(newHealth <= 0) newHealth = 0;
					else e.setHealth(newHealth);
					e.playEffect(EntityEffect.HURT);
					e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 100, 1);
				}

				if(p != null) {
					p.setMetadata("cancelDamageEvent", new FixedMetadataValue(SCore.plugin, 7772));
					e.damage(amount, p);
				}
				else e.damage(amount);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String damage = "DAMAGE {amount}";
		if(args.size() < 1) error = notEnoughArgs+damage;
		else if(args.size() != 1) error= tooManyArgs+damage;

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
		return "DAMAGE {amount}";
	}

	@Override
	public ChatColor getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		// TODO Auto-generated method stub
		return null;
	}

}
