package com.ssomar.score.conditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.ssomar.score.utils.messages.MessageDesign;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.google.common.base.Charsets;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;

@Getter @Setter
public class EntityConditions extends Conditions{

	private boolean ifGlowing;
	private static final String IF_GLOWING_MSG = " &cThe entity must glowing to active the activator: &6%activator% &cof this item!";
	private String ifGlowingMsg;

	private boolean ifInvulnerable;
	private static final String IF_INVULNERABLE_MSG = " &cThe entity must being invulnerable to active the activator: &6%activator% &cof this item!";
	private String ifInvulnerableMsg;

	private boolean ifOnFire;
	private static final String IF_ON_FIRE_MSG = " &cThe entity must be on fire to active the activator: &6%activator% &cof this item!";
	private String ifOnFireMsg;

	private List<String> ifName;
	private static final String IF_NAME_MSG = " &cThe entity doesn't have a valid name to active the activator: &6%activator% &cof this item!";
	private String ifNameMsg;

	private List<EntityType> ifNotEntityType;
	private static final String IF_NOT_ENTITY_TYPE_MSG = " &cThe entity hasn't the good type to active the activator: &6%activator% &cof this item!";
	private String ifNotEntityTypeMsg;

	private String ifEntityHealth;
	private static final String IF_ENTITY_HEALTH_MSG = " &cThe health of the entity is not valid to active the activator: &6%activator% &cof this item!";
	private String ifEntityHealthMsg;

	//Creeper
	private boolean ifPowered;
	private static final String IF_POWERED_MSG = " &cThe entity must being powered to active the activator: &6%activator% &cof this item!";
	private String ifPoweredMsg;

	//Ageable
	private boolean ifAdult;
	private static final String IF_ADULT_MSG = " &cThe entity must being adult to active the activator: &6%activator% &cof this item!";
	private String ifAdultMsg;

	private boolean ifBaby;
	private static final String IF_BABY_MSG = " &cThe entity must being baby to active the activator: &6%activator% &cof this item!";
	private String ifBabyMsg;
	
	@Override
	public void init() {
		this.ifGlowing = false;
		this.ifGlowingMsg = IF_GLOWING_MSG;

		this.ifInvulnerable = false;
		this.ifInvulnerableMsg = IF_INVULNERABLE_MSG;

		this.ifOnFire = false;
		this.ifOnFireMsg = IF_ON_FIRE_MSG;

		this.ifName = new ArrayList<>();
		this.ifNameMsg = IF_NAME_MSG;

		this.ifNotEntityType = new ArrayList<>();
		this.ifNotEntityTypeMsg = IF_NOT_ENTITY_TYPE_MSG;

		this.ifEntityHealth = "";
		this.ifEntityHealthMsg = IF_ENTITY_HEALTH_MSG;

		this.ifPowered = false;
		this.ifPoweredMsg = IF_POWERED_MSG;

		this.ifAdult = false;
		this.ifAdultMsg = IF_ADULT_MSG;

		this.ifBaby = false;
		this.ifBabyMsg = IF_BABY_MSG;
	}

	public boolean verifConditions(Entity e, @Nullable Player p) {

		if(this.isIfGlowing()) {
			boolean hasError= !e.isGlowing();
			LivingEntity lE = (LivingEntity)e;
			try {
				hasError= !lE.hasPotionEffect(PotionEffectType.GLOWING);
			}catch(Exception ignored) {}
			if(hasError) {
				if(p != null) this.getSm().sendMessage(p, this.getIfGlowingMsg());
				return false;
			}
		}

		if(this.hasIfEntityHealth() && e instanceof LivingEntity) {
			LivingEntity lE = (LivingEntity) e;
			if(!StringCalculation.calculation(this.ifEntityHealth, lE.getHealth())) {
				if(p != null) this.getSm().sendMessage(p, this.getIfEntityHealthMsg());
				return false;
			}
		}

		if(this.isIfInvulnerable() && !e.isInvulnerable()) {
			if(p != null) this.getSm().sendMessage(p, this.getIfInvulnerableMsg());
			return false;
		}

		if(this.isIfOnFire() && !e.isVisualFire()) {
			if(p != null) this.getSm().sendMessage(p, this.getIfOnFireMsg());
			return false;
		}

		if(this.hasIfName()) {
			boolean notValid=true;
			for(String name: this.getIfName()) {
				if(StringConverter.decoloredString(e.getName()).equalsIgnoreCase(name)) {
					notValid=false;
					break;
				}
			}
			if(notValid) {
				if(p != null) this.getSm().sendMessage(p, this.getIfNameMsg());
				return false;
			}
		}

		if(this.hasIfNotEntityType()) {
			for(EntityType et: this.getIfNotEntityType()) {
				if(e.getType().equals(et)) return false;
			}
		}


		if(this.isIfAdult() && e instanceof Ageable && !((Ageable)e).isAdult()) {
			if(p != null) this.getSm().sendMessage(p, this.getIfAdultMsg());
			return false;
		}

		if(this.isIfBaby() && e instanceof Ageable && ((Ageable)e).isAdult()) {
			if(p != null) this.getSm().sendMessage(p, this.getIfBabyMsg());
			return false;
		}

		if(this.isIfPowered() && e instanceof Creeper && !((Creeper)e).isPowered()) {
			if(p != null) this.getSm().sendMessage(p, this.getIfPoweredMsg());
			return false;
		}

		return true;
	}

	public static EntityConditions getEntityConditions(ConfigurationSection entityCdtSection, List<String> errorList, String pluginName) {

		EntityConditions eCdt = new EntityConditions();

		eCdt.setIfGlowing(entityCdtSection.getBoolean("ifGlowing", false));
		eCdt.setIfGlowingMsg(entityCdtSection.getString("ifGlowingMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_GLOWING_MSG));

		eCdt.setIfInvulnerable(entityCdtSection.getBoolean("ifInvulnerable", false));
		eCdt.setIfInvulnerableMsg(entityCdtSection.getString("ifInvulnerableMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_INVULNERABLE_MSG));

		eCdt.setIfOnFire(entityCdtSection.getBoolean("ifOnFire", false));
		eCdt.setIfOnFireMsg(entityCdtSection.getString("ifOnFireMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_ON_FIRE_MSG));

		eCdt.setIfAdult(entityCdtSection.getBoolean("ifAdult", false));
		eCdt.setIfAdultMsg(entityCdtSection.getString("ifAdultMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_ADULT_MSG));

		eCdt.setIfBaby(entityCdtSection.getBoolean("ifBaby", false));
		eCdt.setIfBabyMsg(entityCdtSection.getString("ifBabyMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_BABY_MSG));

		eCdt.setIfPowered(entityCdtSection.getBoolean("ifPowered", false));
		eCdt.setIfPoweredMsg(entityCdtSection.getString("ifPoweredMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_POWERED_MSG));

		eCdt.setIfName(entityCdtSection.getStringList("ifName"));
		eCdt.setIfNameMsg(entityCdtSection.getString("ifNameMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_NAME_MSG));

		List<EntityType> list = new ArrayList<>();
		for (String s : entityCdtSection.getStringList("ifNotEntityType")) {
			try {
				list.add(EntityType.valueOf(s));
			} catch (Exception ignored) {}
		}
		eCdt.setIfNotEntityType(list);
		eCdt.setIfNotEntityTypeMsg(entityCdtSection.getString("ifNotEntityTypeMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_NOT_ENTITY_TYPE_MSG));

		eCdt.setIfEntityHealth(entityCdtSection.getString("ifEntityHealth", ""));
		eCdt.setIfEntityHealthMsg(entityCdtSection.getString("ifEntityHealthMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_ENTITY_HEALTH_MSG));

		return eCdt;
	}

	/*
	 *  @param sPlugin The plugin of the conditions
	 *  @param sObject The object
	 *  @param sActivator The activator that contains the conditions
	 *  @param eC the custom conditions object
	 */
	public static void saveEntityConditions(SPlugin sPlugin, SObject sObject, SActivator sActivator, EntityConditions eC, String detail) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file in the folder ! ("+sObject.getId()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions."+detail+".ifGlowing", false);

		String pluginName = sPlugin.getNameDesign();

		ConfigurationSection eCConfig = config.getConfigurationSection("activators."+sActivator.getID()+".conditions."+detail);

		if(eC.isIfGlowing()) eCConfig.set("ifGlowing", true);
		else eCConfig.set("ifGlowing", null);
		if(eC.getIfGlowingMsg().contains(eC.IF_GLOWING_MSG)) eCConfig.set("ifGlowingMsg", null);
		else eCConfig.set("ifGlowingMsg", eC.getIfGlowingMsg());

		if(eC.isIfInvulnerable()) eCConfig.set("ifInvulnerable", true);
		else eCConfig.set("ifInvulnerable", null);
		if(eC.getIfInvulnerableMsg().contains(eC.IF_INVULNERABLE_MSG))  eCConfig.set("ifInvulnerableMsg", null);
		else eCConfig.set("ifInvulnerableMsg", eC.getIfInvulnerableMsg());

		if(eC.isIfOnFire()) eCConfig.set("ifOnFire", true);
		else eCConfig.set("ifOnFire", null);
		if(eC.getIfOnFireMsg().contains(eC.IF_ON_FIRE_MSG)) eCConfig.set("ifOnFireMsg", null);
		else eCConfig.set("ifOnFireMsg", eC.getIfOnFireMsg());

		if(eC.isIfAdult()) eCConfig.set("ifAdult", true);
		else eCConfig.set("ifAdult", null);
		if(eC.getIfAdultMsg().contains(eC.IF_ADULT_MSG))  eCConfig.set("ifAdultMsg", null);
		else eCConfig.set("ifAdultMsg", eC.getIfAdultMsg());

		if(eC.isIfBaby()) eCConfig.set("ifBaby", true);
		else eCConfig.set("ifBaby", null);
		if(eC.getIfBabyMsg().contains(eC.IF_BABY_MSG)) eCConfig.set("ifBabyMsg", null);
		else eCConfig.set("ifBabyMsg", eC.getIfBabyMsg());

		if(eC.isIfPowered()) eCConfig.set("ifPowered", true);
		else eCConfig.set("ifPowered", null);
		if(eC.getIfPoweredMsg().contains(eC.IF_POWERED_MSG)) eCConfig.set("ifPoweredMsg", null);
		else eCConfig.set("ifPoweredMsg", eC.getIfPoweredMsg());

		if(eC.hasIfName()) eCConfig.set("ifName", eC.getIfName()); 
		else eCConfig.set("ifName", null);
		if(eC.getIfNameMsg().contains(eC.IF_NAME_MSG)) eCConfig.set("ifNameMsg", null);
		else eCConfig.set("ifNameMsg", eC.getIfNameMsg());

		List<String> convert= new ArrayList<>();
		for(EntityType eT : eC.getIfNotEntityType()) {
			convert.add(eT.toString());
		}

		if(eC.hasIfNotEntityType()) eCConfig.set("ifNotEntityType", convert); 
		else eCConfig.set("ifNotEntityType", null);
		if(eC.getIfNotEntityTypeMsg().contains(eC.IF_NOT_ENTITY_TYPE_MSG)) eCConfig.set("ifNotEntityTypeMsg", null);
		else eCConfig.set("ifNotEntityTypeMsg", eC.getIfNotEntityTypeMsg());

		if(eC.hasIfEntityHealth()) eCConfig.set("ifEntityHealth", eC.getIfEntityHealth()); 
		else eCConfig.set("ifEntityHealth", null);
		if(eC.getIfEntityHealthMsg().contains(eC.IF_ENTITY_HEALTH_MSG)) eCConfig.set("ifEntityHealthMsg", null);
		else eCConfig.set("ifEntityHealthMsg", eC.getIfEntityHealthMsg());

		try {
			Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

			try {
				writer.write(config.saveToString());
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean hasIfName() {
		return ifName.size() != 0;
	}

	public boolean hasIfNotEntityType() {
		return ifNotEntityType.size() != 0;
	}

	public boolean hasIfEntityHealth() {
		return ifEntityHealth.length() != 0;
	}
}
