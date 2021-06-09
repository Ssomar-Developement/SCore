package com.ssomar.score.conditions;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.ssomar.score.utils.StringCalculation;

public class TargetConditions extends Conditions{

	private String ifTargetHealth;
	private static final String IF_TARGET_HEALTH_MSG = " &cYour target has not valid health to active the activator: &6%activator% &cof this item!";
	private String ifTargetHealthMsg;

	private String ifTargetFoodLevel;
	private static final String IF_TARGET_FOOD_LEVEL_MSG = " &cYour target has not valid food level to active the activator: &6%activator% &cof this item!";
	private String ifTargetFoodLevelMsg;

	private String ifTargetEXP;
	private static final String IF_TARGET_EXP_MSG = " &cYour target has not valid EXP to active the activator: &6%activator% &cof this item!";
	private String ifTargetEXPMsg;

	private String ifTargetLevel;
	private static final String IF_TARGET_LEVEL_MSG = " &cYour target has not valid Level to active the activator: &6%activator% &cof this item!";
	private String ifTargetLevelMsg;


	public boolean verifConditions(Player target, Player p) {

		if(this.hasIfTargetHealth() && !StringCalculation.calculation(this.ifTargetHealth, target.getHealth())) {
			this.getSm().sendMessage(p, this.getIfTargetHealthMsg());
			return false;
		}

		if(this.hasIfTargetFoodLevel() && !StringCalculation.calculation(this.ifTargetFoodLevel, target.getFoodLevel())) {
			this.getSm().sendMessage(p, this.getIfTargetFoodLevelMsg());
			return false;
		}

		if(this.hasIfTargetEXP() && !StringCalculation.calculation(this.ifTargetEXP, target.getExp())) {
			this.getSm().sendMessage(p, this.getIfTargetEXPMsg());
			return false;
		}

		if(this.hasIfTargetLevel() && !StringCalculation.calculation(this.ifTargetLevel, target.getLevel())) {
			this.getSm().sendMessage(p, this.getIfTargetLevelMsg());
			return false;
		}	

		return true;
	}
	
	public static TargetConditions getTargetConditions(ConfigurationSection targetCdtSection, List<String> errorList, String pluginName) {

		TargetConditions tCdt = new TargetConditions();

		tCdt.setIfTargetHealth(targetCdtSection.getString("ifTargetHealth", ""));
		tCdt.setIfTargetHealthMsg(targetCdtSection.getString("ifTargetHealthMsg", "&4&l"+pluginName+IF_TARGET_HEALTH_MSG));

		tCdt.setIfTargetFoodLevel(targetCdtSection.getString("ifTargetFoodLevel", ""));
		tCdt.setIfTargetFoodLevelMsg(targetCdtSection.getString("ifTargetFoodLevelMsg", "&4&l"+pluginName+IF_TARGET_FOOD_LEVEL_MSG));

		tCdt.setIfTargetEXP(targetCdtSection.getString("ifTargetEXP", ""));
		tCdt.setIfTargetEXPMsg(targetCdtSection.getString("ifTargetEXPMsg", "&4&l"+pluginName+IF_TARGET_EXP_MSG));

		tCdt.setIfTargetLevel(targetCdtSection.getString("ifTargetLevel", ""));
		tCdt.setIfTargetLevelMsg(targetCdtSection.getString("ifTargetLevelMsg", "&4&l"+pluginName+IF_TARGET_LEVEL_MSG));

		return tCdt;

	}

	public String getIfTargetHealth() {
		return ifTargetHealth;
	}

	public void setIfTargetHealth(String ifTargetHealth) {
		this.ifTargetHealth = ifTargetHealth;
	}

	public boolean hasIfTargetHealth() {
		return this.ifTargetHealth.length()!=0;
	}

	public String getIfTargetFoodLevel() {
		return ifTargetFoodLevel;
	}

	public void setIfTargetFoodLevel(String ifTargetFoodLevel) {
		this.ifTargetFoodLevel = ifTargetFoodLevel;
	}
	public boolean hasIfTargetFoodLevel() {
		return this.ifTargetFoodLevel.length()!=0;
	}

	public String getIfTargetEXP() {
		return ifTargetEXP;
	}

	public void setIfTargetEXP(String ifTargetEXP) {
		this.ifTargetEXP = ifTargetEXP;
	}

	public boolean hasIfTargetEXP() {
		return this.ifTargetEXP.length()!=0;
	}

	public String getIfTargetLevel() {
		return ifTargetLevel;
	}

	public void setIfTargetLevel(String ifTargetLevel) {
		this.ifTargetLevel = ifTargetLevel;
	}

	public boolean hasIfTargetLevel() {
		return this.ifTargetLevel.length()!=0;
	}







	public String getIfTargetHealthMsg() {
		return ifTargetHealthMsg;
	}

	public void setIfTargetHealthMsg(String ifTargetHealthMsg) {
		this.ifTargetHealthMsg = ifTargetHealthMsg;
	}

	public String getIfTargetFoodLevelMsg() {
		return ifTargetFoodLevelMsg;
	}

	public void setIfTargetFoodLevelMsg(String ifTargetFoodLevelMsg) {
		this.ifTargetFoodLevelMsg = ifTargetFoodLevelMsg;
	}

	public String getIfTargetEXPMsg() {
		return ifTargetEXPMsg;
	}

	public void setIfTargetEXPMsg(String ifTargetEXPMsg) {
		this.ifTargetEXPMsg = ifTargetEXPMsg;
	}

	public String getIfTargetLevelMsg() {
		return ifTargetLevelMsg;
	}

	public void setIfTargetLevelMsg(String ifTargetLevelMsg) {
		this.ifTargetLevelMsg = ifTargetLevelMsg;
	}








}
