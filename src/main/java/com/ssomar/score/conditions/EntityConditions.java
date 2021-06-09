package com.ssomar.score.conditions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;

public class EntityConditions extends Conditions{

	private boolean ifGlowing = false;
	private static final String IF_GLOWING_MSG = " &cThe entity must glowing to active the activator: &6%activator% &cof this item!";
	private String ifGlowingMsg = "";

	private boolean ifInvulnerable = false;
	private static final String IF_INVULNERABLE_MSG = " &cThe entity must being invulnerable to active the activator: &6%activator% &cof this item!";
	private String ifInvulnerableMsg = "";

	private List<String> ifName = new ArrayList<>();
	private static final String IF_NAME_MSG = " &cThe entity doesn't have a valid name to active the activator: &6%activator% &cof this item!";
	private String ifNameMsg = "";

	private List<EntityType> ifNotEntityType = new ArrayList<>();
	private static final String IF_NOT_ENTITY_TYPE_MSG = " &cThe entity hasn't the good type to active the activator: &6%activator% &cof this item!";
	private String ifNotEntityTypeMsg = "";

	private String ifEntityHealth = "";
	private static final String IF_ENTITY_HEALTH_MSG = " &cThe health of the entity is not valid to active the activator: &6%activator% &cof this item!";
	private String ifEntityHealthMsg = "";

	//Creeper
	private boolean ifPowered = false;
	private static final String IF_POWERED_MSG = " &cThe entity must being powered to active the activator: &6%activator% &cof this item!";
	private String ifPoweredMsg = "";

	//Ageable
	private boolean ifAdult = false;
	private static final String IF_ADULT_MSG = " &cThe entity must being adult to active the activator: &6%activator% &cof this item!";
	private String ifAdultMsg = "";

	private boolean ifBaby = false;
	private static final String IF_BABY_MSG = " &cThe entity must being baby to active the activator: &6%activator% &cof this item!";
	private String ifBabyMsg = "";

	public boolean verifConditions(Entity e, @Nullable Player p) {

		if(this.hasIfGlowing()) {
			boolean hasError= false;
			if(!e.isGlowing()) {
				hasError=true;
			}
			LivingEntity lE = (LivingEntity)e;
			try {
				if(!lE.hasPotionEffect(PotionEffectType.GLOWING)) {
					hasError=true;
				}
				else hasError=false;
			}catch(Exception err) {}
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

		if(this.hasIfInvulnerable() && !e.isInvulnerable()) {
			if(p != null) this.getSm().sendMessage(p, this.getIfInvulnerableMsg());
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


		if(this.hasIfAdult() && e instanceof Ageable && !((Ageable)e).isAdult()) {
			if(p != null) this.getSm().sendMessage(p, this.getIfAdultMsg());
			return false;
		}

		if(this.hasIfBaby() && e instanceof Ageable && ((Ageable)e).isAdult()) {
			if(p != null) this.getSm().sendMessage(p, this.getIfBabyMsg());
			return false;
		}

		if(this.hasIfPowered() && e instanceof Creeper && !((Creeper)e).isPowered()) {
			if(p != null) this.getSm().sendMessage(p, this.getIfPoweredMsg());
			return false;
		}

		return true;
	}

	public static EntityConditions getEntityConditions(ConfigurationSection entityCdtSection, List<String> errorList, String pluginName) {

		EntityConditions eCdt = new EntityConditions();

		eCdt.setIfGlowing(entityCdtSection.getBoolean("ifGlowing", false));
		eCdt.setIfGlowingMsg(entityCdtSection.getString("ifGlowingMsg", "&4&l"+pluginName+IF_GLOWING_MSG));

		eCdt.setIfInvulnerable(entityCdtSection.getBoolean("ifInvulnerable", false));
		eCdt.setIfInvulnerableMsg(entityCdtSection.getString("ifInvulnerableMsg", "&4&l"+pluginName+IF_INVULNERABLE_MSG));

		eCdt.setIfAdult(entityCdtSection.getBoolean("ifAdult", false));
		eCdt.setIfAdultMsg(entityCdtSection.getString("ifAdultMsg", "&4&l"+pluginName+IF_ADULT_MSG));

		eCdt.setIfBaby(entityCdtSection.getBoolean("ifBaby", false));
		eCdt.setIfBabyMsg(entityCdtSection.getString("ifBabyMsg", "&4&l"+pluginName+IF_BABY_MSG));

		eCdt.setIfPowered(entityCdtSection.getBoolean("ifPowered", false));
		eCdt.setIfPoweredMsg(entityCdtSection.getString("ifPoweredMsg", "&4&l"+pluginName+IF_POWERED_MSG));

		eCdt.setIfName(entityCdtSection.getStringList("ifName"));
		eCdt.setIfNameMsg(entityCdtSection.getString("ifNameMsg", "&4&l"+pluginName+IF_NAME_MSG));

		List<EntityType> list = new ArrayList<>();
		for (String s : entityCdtSection.getStringList("ifNotEntityType")) {
			try {
				list.add(EntityType.valueOf(s));
			} catch (Exception e) {}
		}
		eCdt.setIfNotEntityType(list);
		eCdt.setIfNotEntityTypeMsg(entityCdtSection.getString("ifNotEntityTypeMsg", "&4&l"+pluginName+IF_NOT_ENTITY_TYPE_MSG));

		eCdt.setIfEntityHealth(entityCdtSection.getString("ifEntityHealth", ""));
		eCdt.setIfEntityHealthMsg(entityCdtSection.getString("ifEntityHealthMsg", "&4&l"+pluginName+IF_ENTITY_HEALTH_MSG));

		return eCdt;
	}


	public boolean isIfAdult() {
		return ifAdult;
	}

	public void setIfAdult(boolean ifAdult) {
		this.ifAdult = ifAdult;
	}

	public boolean hasIfAdult() {
		return ifAdult;
	}

	public boolean isIfBaby() {
		return ifBaby;
	}

	public void setIfBaby(boolean ifBaby) {
		this.ifBaby = ifBaby;
	}

	public boolean hasIfBaby() {
		return ifBaby;
	}

	public boolean isIfGlowing() {
		return ifGlowing;
	}

	public void setIfGlowing(boolean ifGlowing) {
		this.ifGlowing = ifGlowing;
	}

	public boolean hasIfGlowing() {
		return ifGlowing;
	}

	public boolean isIfInvulnerable() {
		return ifInvulnerable;
	}

	public void setIfInvulnerable(boolean ifInvulnerable) {
		this.ifInvulnerable = ifInvulnerable;
	}

	public boolean hasIfInvulnerable() {
		return ifInvulnerable;
	}

	public List<String> getIfName() {
		return ifName;
	}

	public void setIfName(List<String> ifName) {
		this.ifName = ifName;
	}

	public boolean hasIfName() {
		return ifName.size()!=0;
	}

	public List<EntityType> getIfNotEntityType() {
		return ifNotEntityType;
	}

	public void setIfNotEntityType(List<EntityType> ifNotEntityType) {
		this.ifNotEntityType = ifNotEntityType;
	}

	public boolean hasIfNotEntityType() {
		return ifNotEntityType.size()!=0;
	}

	public String getIfEntityHealth() {
		return ifEntityHealth;
	}

	public void setIfEntityHealth(String ifEntityHealth) {
		this.ifEntityHealth = ifEntityHealth;
	}

	public boolean hasIfEntityHealth() {
		return ifEntityHealth.length()!=0;
	}

	public boolean isIfPowered() {
		return ifPowered;
	}

	public void setIfPowered(boolean ifPowered) {
		this.ifPowered = ifPowered;
	}

	public boolean hasIfPowered() {
		return ifPowered;
	}


	public String getIfAdultMsg() {
		return ifAdultMsg;
	}


	public void setIfAdultMsg(String ifAdultMsg) {
		this.ifAdultMsg = ifAdultMsg;
	}


	public String getIfGlowingMsg() {
		return ifGlowingMsg;
	}


	public void setIfGlowingMsg(String ifGlowingMsg) {
		this.ifGlowingMsg = ifGlowingMsg;
	}


	public String getIfInvulnerableMsg() {
		return ifInvulnerableMsg;
	}


	public void setIfInvulnerableMsg(String ifInvulnerableMsg) {
		this.ifInvulnerableMsg = ifInvulnerableMsg;
	}


	public String getIfNameMsg() {
		return ifNameMsg;
	}


	public void setIfNameMsg(String ifNameMsg) {
		this.ifNameMsg = ifNameMsg;
	}


	public String getIfEntityHealthMsg() {
		return ifEntityHealthMsg;
	}


	public void setIfEntityHealthMsg(String ifEntityHealthMsg) {
		this.ifEntityHealthMsg = ifEntityHealthMsg;
	}


	public String getIfBabyMsg() {
		return ifBabyMsg;
	}


	public void setIfBabyMsg(String ifBabyMsg) {
		this.ifBabyMsg = ifBabyMsg;
	}


	public String getIfPoweredMsg() {
		return ifPoweredMsg;
	}


	public void setIfPoweredMsg(String ifPoweredMsg) {
		this.ifPoweredMsg = ifPoweredMsg;
	}


	public String getIfNotEntityTypeMsg() {
		return ifNotEntityTypeMsg;
	}


	public void setIfNotEntityTypeMsg(String ifNotEntityTypeMsg) {
		this.ifNotEntityTypeMsg = ifNotEntityTypeMsg;
	}




}
