package com.ssomar.score.sobject.sactivator.conditions;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BlockConditions extends Conditions{

	private boolean ifPlantFullyGrown;
	public static final String IF_PLANT_FULLY_GROWN_MSG = " &cThe plant must be fully grown to active the activator: &6%activator% &cof this item!";
	private String ifPlantFullyGrownMsg;

	public BlockConditions() {
		this.ifPlantFullyGrown = false;
		this.ifPlantFullyGrownMsg = "";
	}

	public boolean verifConditions(Block b, Player p) {

		if(this.isIfPlantFullyGrown() && b.getState().getBlockData() instanceof Ageable) {
			Ageable ageable = (Ageable)b.getState().getBlockData();
			if(ageable.getAge()!=ageable.getMaximumAge()) {
				this.getSm().sendMessage(p, this.getIfPlantFullyGrownMsg());
				return false;
			}
		}

		return true;
	}
	
	public static BlockConditions getBlockConditions(ConfigurationSection blockCdtSection, List<String> errorList, String pluginName) {

		BlockConditions bCdt = new BlockConditions();

		bCdt.setIfPlantFullyGrown(blockCdtSection.getBoolean("ifPlantFullyGrown", false));
		bCdt.setIfPlantFullyGrownMsg(blockCdtSection.getString("ifPlantFullyGrownMsg", "&4&l"+pluginName+IF_PLANT_FULLY_GROWN_MSG));

		return bCdt;
	}


	public boolean isIfPlantFullyGrown() {
		return ifPlantFullyGrown;
	}


	public void setIfPlantFullyGrown(boolean ifPlantFullyGrown) {
		this.ifPlantFullyGrown = ifPlantFullyGrown;
	}


	public String getIfPlantFullyGrownMsg() {
		return ifPlantFullyGrownMsg;
	}


	public void setIfPlantFullyGrownMsg(String ifPlantFullyGrownMsg) {
		this.ifPlantFullyGrownMsg = ifPlantFullyGrownMsg;
	}


}
