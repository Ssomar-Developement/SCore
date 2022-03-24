package com.ssomar.score.sobject.sactivator.conditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.ssomar.score.utils.messages.MessageDesign;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Powerable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.base.Charsets;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.MyCoreProtectAPI;
import com.ssomar.score.utils.StringCalculation;
import org.jetbrains.annotations.Nullable;

@Getter @Setter
public class BlockConditions extends Conditions{

	private boolean ifPlantFullyGrown;
	public static final String IF_PLANT_FULLY_GROWN_MSG = " &cThe plant must be fully grown to active the activator: &6%activator% &cof this item!";
	private String ifPlantFullyGrownMsg;

	private String ifBlockAge;
	public static final String IF_BLOCK_AGE_MSG = " &cThe block age is invalid to active the activator: &6%activator% &cof this item!";
	private String ifBlockAgeMsg;

	private boolean ifIsPowered;
	public static final String IF_IS_POWERED_MSG = " &cThe block must be powered by redstone to active the activator: &6%activator% &cof this item!";
	private String ifIsPoweredMsg;

	private boolean ifMustBeNotPowered;
	public static final String IF_MUST_BE_NOT_POWERED_MSG = " &cThe block must be not powered by redstone to active the activator: &6%activator% &cof this item!";
	private String ifMustBeNotPoweredMsg;

	private boolean ifMustBeNatural;
	public static final String IF_MUST_BE_NATURAL_MSG = " &cThe block must be natural to active the activator: &6%activator% &cof this item!";
	private String ifMustBeNaturalMsg;
	
	private String ifBlockLocationX;
	public static final String IF_BLOCK_LOCATION_X_MSG = " &cThe block location X is invalid to active the activator: &6%activator% &cof this item!";
	private String ifBlockLocationXMsg;
	
	private String ifBlockLocationX2;
	public static final String IF_BLOCK_LOCATION_X2_MSG = " &cThe block location X is invalid to active the activator: &6%activator% &cof this item!";
	private String ifBlockLocationX2Msg;
	
	private String ifBlockLocationY;
	public static final String IF_BLOCK_LOCATION_Y_MSG = " &cThe block location Y is invalid to active the activator: &6%activator% &cof this item!";
	private String ifBlockLocationYMsg;
	
	private String ifBlockLocationY2;
	public static final String IF_BLOCK_LOCATION_Y2_MSG = " &cThe block location Y is invalid to active the activator: &6%activator% &cof this item!";
	private String ifBlockLocationY2Msg;
	
	private String ifBlockLocationZ;
	public static final String IF_BLOCK_LOCATION_Z_MSG = " &cThe block location Z is invalid to active the activator: &6%activator% &cof this item!";
	private String ifBlockLocationZMsg;
	
	private String ifBlockLocationZ2;
	public static final String IF_BLOCK_LOCATION_Z2_MSG = " &cThe block location Z is invalid to active the activator: &6%activator% &cof this item!";
	private String ifBlockLocationZ2Msg;

	List<AroundBlockCondition> blockAroundConditions;

	private boolean ifPlayerMustBeOnTheBlock;
	public static final String IF_PLAYER_MUST_BE_ON_THE_MSG = " &cA player must be on the block to active the activator: &6%activator% &cof this item!";
	private String ifPlayerMustBeOnTheBlockMsg;

	private boolean ifNoPlayerMustBeOnTheBlock;
	public static final String IF_NO_PLAYER_MUST_BE_ON_THE_MSG = " &cA player must be on the block to active the activator: &6%activator% &cof this item!";
	private String ifNoPlayerMustBeOnTheBlockMsg;

	public BlockConditions(){
		init();
	}

	@Override
	public void init() {
		this.ifPlantFullyGrown = false;
		this.ifPlantFullyGrownMsg = IF_PLANT_FULLY_GROWN_MSG;

		ifBlockAge = "";
		ifBlockAgeMsg = IF_BLOCK_AGE_MSG;

		this.ifIsPowered = false;
		this.ifIsPoweredMsg = IF_IS_POWERED_MSG;	

		this.ifMustBeNotPowered = false;
		this.ifMustBeNotPoweredMsg = IF_MUST_BE_NOT_POWERED_MSG;

		this.ifMustBeNatural = false;
		this.ifMustBeNaturalMsg = IF_MUST_BE_NATURAL_MSG;	
		
		ifBlockLocationX = "";
		ifBlockLocationXMsg =IF_BLOCK_LOCATION_X_MSG;
		
		ifBlockLocationX2 = "";
		ifBlockLocationX2Msg =IF_BLOCK_LOCATION_X2_MSG;
		
		ifBlockLocationY = "";
		ifBlockLocationYMsg =IF_BLOCK_LOCATION_Y_MSG;
		
		ifBlockLocationY2 = "";
		ifBlockLocationY2Msg =IF_BLOCK_LOCATION_Y2_MSG;
		
		ifBlockLocationZ = "";
		ifBlockLocationZMsg =IF_BLOCK_LOCATION_Z_MSG;
		
		ifBlockLocationZ2 = "";
		ifBlockLocationZ2Msg =IF_BLOCK_LOCATION_Z2_MSG;

		blockAroundConditions = new ArrayList<>();

		ifPlayerMustBeOnTheBlock = false;
		ifPlayerMustBeOnTheBlockMsg = IF_PLAYER_MUST_BE_ON_THE_MSG;

		ifNoPlayerMustBeOnTheBlock = false;
		ifNoPlayerMustBeOnTheBlockMsg = IF_NO_PLAYER_MUST_BE_ON_THE_MSG;
	}

	public boolean verifConditions(Block b, @Nullable Player p) {

		boolean hasPlayer = (p != null);

		if(this.isIfPlantFullyGrown() && b.getState().getBlockData() instanceof Ageable) {
			Ageable ageable = (Ageable) b.getState().getBlockData();
			int age = ageable.getAge();
			if(!ifBlockAge.equals("") && !StringCalculation.calculation(ifBlockAge, age)) {
				if(hasPlayer) this.getSm().sendMessage(p, this.getIfBlockAgeMsg());
				return false;
			}
			if(age != ageable.getMaximumAge()) {
				this.getSm().sendMessage(p, this.getIfPlantFullyGrownMsg());
				return false;
			}
		}

		if(this.ifPlayerMustBeOnTheBlock){
			boolean onBlock = false;
			Location bLoc = b.getLocation();
			bLoc = bLoc.add(0.5,1,0.5);
			for(Player pl : Bukkit.getServer().getOnlinePlayers()){
				Location pLoc = pl.getLocation();
				if(bLoc.getWorld().getUID().equals(pLoc.getWorld().getUID())) {
					if (bLoc.distance(pLoc) < 1.135) {
						onBlock = true;
						break;
					}
				}
			}
			if(!onBlock) return false;
		}

		if(this.ifNoPlayerMustBeOnTheBlock){
			boolean onBlock = false;
			Location bLoc = b.getLocation();
			bLoc = bLoc.add(0.5,1,0.5);
			for(Player pl : Bukkit.getServer().getOnlinePlayers()){
				Location pLoc = pl.getLocation();
				if(bLoc.getWorld().getUID().equals(pLoc.getWorld().getUID())) {
					if (bLoc.distance(pl.getLocation()) < 1.135) {
						onBlock = true;
						break;
					}
				}
			}
			if(onBlock) return false;
		}

		if(this.ifIsPowered) {
			//SsomarDev.testMsg("block: "+b.getType()+ "   isBlockpowered: "+b.isBlockPowered()+ " is Powerable: "+(b.getBlockData() instanceof Powerable)+ "power: "+b.getBlockPower());
			boolean notPowered = !b.isBlockPowered() && b.getBlockPower() == 0;

			if(b.getBlockData() instanceof Powerable) {
				Powerable power = (Powerable)b.getBlockData();
				notPowered = !power.isPowered();
			}

			if(notPowered) {
				if(hasPlayer) this.getSm().sendMessage(p, this.getIfIsPoweredMsg());
				return false;
			}
		}

		if(this.ifMustBeNotPowered && b.getBlockData() instanceof Powerable) {
			Powerable power = (Powerable)b.getBlockData();
			if(power.isPowered()) {
				if(hasPlayer) this.getSm().sendMessage(p, this.getIfMustBeNotPoweredMsg());
				return false;
			}
		}

		if(this.ifMustBeNatural) {
			if(!MyCoreProtectAPI.isNaturalBlock(b)) {
				if(hasPlayer) this.getSm().sendMessage(p, this.getIfMustBeNaturalMsg());
				return false;
			}
		}

		if(this.blockAroundConditions.size() > 0) {
			for(AroundBlockCondition bAC : this.blockAroundConditions) {
				if(!bAC.verif(b, p)) return false;
			}
		}
		
		if(!ifBlockLocationX.equals("") && !StringCalculation.calculation(ifBlockLocationX, b.getLocation().getX())) {
			if(hasPlayer) this.getSm().sendMessage(p, ifBlockLocationXMsg);
			return false;
		}
		
		if(!ifBlockLocationX2.equals("") && !StringCalculation.calculation(ifBlockLocationX2, b.getLocation().getX())) {
			if(hasPlayer) this.getSm().sendMessage(p, ifBlockLocationX2Msg);
			return false;
		}
		
		if(!ifBlockLocationY.equals("") && !StringCalculation.calculation(ifBlockLocationY, b.getLocation().getY())) {
			if(hasPlayer) this.getSm().sendMessage(p, ifBlockLocationYMsg);
			return false;
		}
		
		if(!ifBlockLocationY2.equals("") && !StringCalculation.calculation(ifBlockLocationY2, b.getLocation().getY())) {
			if(hasPlayer) this.getSm().sendMessage(p, ifBlockLocationY2Msg);
			return false;
		}
		
		if(!ifBlockLocationZ.equals("") && !StringCalculation.calculation(ifBlockLocationZ, b.getLocation().getZ())) {
			if(hasPlayer) this.getSm().sendMessage(p, ifBlockLocationZMsg);
			return false;
		}
		
		if(!ifBlockLocationZ2.equals("") && !StringCalculation.calculation(ifBlockLocationZ2, b.getLocation().getZ())) {
			if(hasPlayer) this.getSm().sendMessage(p, ifBlockLocationZ2Msg);
			return false;
		}

		return true;
	}

	public static BlockConditions getBlockConditions(ConfigurationSection blockCdtSection, List<String> errorList, String pluginName) {

		BlockConditions bCdt = new BlockConditions();

		bCdt.setIfPlantFullyGrown(blockCdtSection.getBoolean("ifPlantFullyGrown", false));
		bCdt.setIfPlantFullyGrownMsg(blockCdtSection.getString("ifPlantFullyGrownMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_PLANT_FULLY_GROWN_MSG));

		bCdt.setIfBlockAge(blockCdtSection.getString("ifBlockAge", ""));
		bCdt.setIfBlockAgeMsg(blockCdtSection.getString("ifBlockAgeMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_BLOCK_AGE_MSG));

		bCdt.setIfIsPowered(blockCdtSection.getBoolean("ifIsPowered", false));
		bCdt.setIfIsPoweredMsg(blockCdtSection.getString("ifIsPoweredMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_IS_POWERED_MSG));

		bCdt.setIfMustBeNotPowered(blockCdtSection.getBoolean("ifMustBeNotPowered", false));
		bCdt.setIfMustBeNotPoweredMsg(blockCdtSection.getString("ifMustBeNotPoweredMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_MUST_BE_NOT_POWERED_MSG));

		bCdt.setIfMustBeNatural(blockCdtSection.getBoolean("ifMustBeNatural", false));
		bCdt.setIfMustBeNaturalMsg(blockCdtSection.getString("ifMustBeNaturalMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_MUST_BE_NATURAL_MSG));

		bCdt.setIfBlockLocationX(blockCdtSection.getString("ifBlockLocationX", ""));
		bCdt.setIfBlockLocationXMsg(blockCdtSection.getString("ifBlockLocationXMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_BLOCK_LOCATION_X_MSG));
		
		bCdt.setIfBlockLocationX2(blockCdtSection.getString("ifBlockLocationX2", ""));
		bCdt.setIfBlockLocationX2Msg(blockCdtSection.getString("ifBlockLocationX2Msg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_BLOCK_LOCATION_X2_MSG));
		
		bCdt.setIfBlockLocationY(blockCdtSection.getString("ifBlockLocationY", ""));
		bCdt.setIfBlockLocationYMsg(blockCdtSection.getString("ifBlockLocationYMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_BLOCK_LOCATION_Y_MSG));
		
		bCdt.setIfBlockLocationY2(blockCdtSection.getString("ifBlockLocationY2", ""));
		bCdt.setIfBlockLocationY2Msg(blockCdtSection.getString("ifBlockLocationY2Msg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_BLOCK_LOCATION_Y2_MSG));
		
		bCdt.setIfBlockLocationZ(blockCdtSection.getString("ifBlockLocationZ", ""));
		bCdt.setIfBlockLocationZMsg(blockCdtSection.getString("ifBlockLocationZMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_BLOCK_LOCATION_Z_MSG));
		
		bCdt.setIfBlockLocationZ2(blockCdtSection.getString("ifBlockLocationZ2", ""));
		bCdt.setIfBlockLocationZ2Msg(blockCdtSection.getString("ifBlockLocationZ2Msg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_BLOCK_LOCATION_Z2_MSG));

		bCdt.setIfPlayerMustBeOnTheBlock(blockCdtSection.getBoolean("ifPlayerMustBeOnTheBlock", false));
		bCdt.setIfPlayerMustBeOnTheBlockMsg(blockCdtSection.getString("ifPlayerMustBeOnTheBlockMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_PLAYER_MUST_BE_ON_THE_MSG));

		bCdt.setIfNoPlayerMustBeOnTheBlock(blockCdtSection.getBoolean("ifNoPlayerMustBeOnTheBlock", false));
		bCdt.setIfNoPlayerMustBeOnTheBlockMsg(blockCdtSection.getString("ifNoPlayerMustBeOnTheBlockMsg", MessageDesign.ERROR_CODE_FIRST+pluginName+IF_NO_PLAYER_MUST_BE_ON_THE_MSG));

		if(blockCdtSection.contains("blockAroundCdts")) {
			for(String s : blockCdtSection.getConfigurationSection("blockAroundCdts").getKeys(false)) {
				ConfigurationSection section = blockCdtSection.getConfigurationSection("blockAroundCdts."+s);
				bCdt.blockAroundConditions.add(AroundBlockCondition.get(section));
			}
		}

		return bCdt;
	}

	/*
	 *  @param sPlugin The plugin of the conditions
	 *  @param sObject The object
	 *  @param sActivator The activator that contains the conditions
	 *  @param bC the blockm conditions object
	 */
	public static void saveBlockConditions(SPlugin sPlugin, SObject sObject, SActivator sActivator, BlockConditions bC, String detail) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file in the folder ! ("+sObject.getId()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions."+detail+".ifPlantFullyGrown", false);

		String pluginName = sPlugin.getNameDesign();

		ConfigurationSection pCConfig = config.getConfigurationSection("activators."+sActivator.getID()+".conditions."+detail);

		if(bC.isIfPlantFullyGrown()) pCConfig.set("ifPlantFullyGrown", true); 
		else pCConfig.set("ifPlantFullyGrown", null);
		if(bC.getIfPlantFullyGrownMsg().contains(bC.IF_PLANT_FULLY_GROWN_MSG)) pCConfig.set("ifPlantFullyGrownMsg", null);
		else pCConfig.set("ifPlantFullyGrownMsg", bC.getIfPlantFullyGrownMsg());

		if(!bC.ifBlockAge.equals("")) pCConfig.set("ifBlockAge", bC.ifBlockAge);
		else pCConfig.set("ifBlockAge", null);
		if(bC.ifBlockAgeMsg.contains(bC.IF_BLOCK_AGE_MSG)) pCConfig.set("ifBlockAgeMsg", null);
		else pCConfig.set("ifBlockAgeMsg", bC.ifBlockAgeMsg);

		if(bC.isIfIsPowered()) pCConfig.set("ifIsPowered", true); 
		else pCConfig.set("ifIsPowered", null);
		if(bC.getIfIsPoweredMsg().contains(bC.IF_IS_POWERED_MSG)) pCConfig.set("ifIsPoweredMsg", null);
		else pCConfig.set("ifIsPoweredMsg", bC.getIfIsPoweredMsg());

		if(bC.isIfMustBeNotPowered()) pCConfig.set("ifMustBeNotPowered", true); 
		else pCConfig.set("ifMustBeNotPowered", null);
		if(bC.getIfMustBeNotPoweredMsg().contains(bC.IF_MUST_BE_NOT_POWERED_MSG))  pCConfig.set("ifMustBeNotPoweredMsg", null);
		else pCConfig.set("ifMustBeNotPoweredMsg", bC.getIfMustBeNotPoweredMsg());

		if(bC.isIfMustBeNatural()) pCConfig.set("ifMustBeNatural", true);
		else pCConfig.set("ifMustBeNatural", null);
		if(bC.getIfMustBeNaturalMsg().contains(bC.IF_MUST_BE_NATURAL_MSG)) pCConfig.set("ifMustBeNaturalMsg", null);
		else pCConfig.set("ifMustBeNaturalMsg", bC.getIfMustBeNaturalMsg());
		
		if(!bC.ifBlockLocationX.equals("")) pCConfig.set("ifBlockLocationX", bC.ifBlockLocationX);
		else pCConfig.set("ifBlockLocationX", null);
		if(bC.ifBlockLocationXMsg.contains(bC.IF_BLOCK_LOCATION_X_MSG)) pCConfig.set("ifBlockLocationXMsg", null);
		else pCConfig.set("ifBlockLocationXMsg", bC.ifBlockLocationXMsg);
		
		if(!bC.ifBlockLocationX2.equals("")) pCConfig.set("ifBlockLocationX2", bC.ifBlockLocationX2);
		else pCConfig.set("ifBlockLocationX2", null);
		if(bC.ifBlockLocationX2Msg.contains(bC.IF_BLOCK_LOCATION_X2_MSG)) pCConfig.set("ifBlockLocationX2Msg", null);
		else pCConfig.set("ifBlockLocationX2Msg", bC.ifBlockLocationX2Msg);
		
		if(!bC.ifBlockLocationY.equals("")) pCConfig.set("ifBlockLocationY", bC.ifBlockLocationY);
		else pCConfig.set("ifBlockLocationY", null);
		if(bC.ifBlockLocationYMsg.contains(bC.IF_BLOCK_LOCATION_Y_MSG)) pCConfig.set("ifBlockLocationYMsg", null);
		else pCConfig.set("ifBlockLocationYMsg", bC.ifBlockLocationYMsg);
		
		if(!bC.ifBlockLocationY2.equals("")) pCConfig.set("ifBlockLocationY2", bC.ifBlockLocationY2);
		else pCConfig.set("ifBlockLocationY2", null);
		if(bC.ifBlockLocationY2Msg.contains(bC.IF_BLOCK_LOCATION_Y2_MSG)) pCConfig.set("ifBlockLocationY2Msg", null);
		else pCConfig.set("ifBlockLocationY2Msg", bC.ifBlockLocationY2Msg);
		
		if(!bC.ifBlockLocationZ.equals("")) pCConfig.set("ifBlockLocationZ", bC.ifBlockLocationZ);
		else pCConfig.set("ifBlockLocationZ", null);
		if(bC.ifBlockLocationZMsg.contains(bC.IF_BLOCK_LOCATION_Z_MSG)) pCConfig.set("ifBlockLocationZMsg", null);
		else pCConfig.set("ifBlockLocationZMsg", bC.ifBlockLocationZMsg);
		
		if(!bC.ifBlockLocationZ2.equals("")) pCConfig.set("ifBlockLocationZ2", bC.ifBlockLocationZ2);
		else pCConfig.set("ifBlockLocationZ2", null);
		if(bC.ifBlockLocationZ2Msg.contains(bC.IF_BLOCK_LOCATION_Z2_MSG)) pCConfig.set("ifBlockLocationZ2Msg", null);
		else pCConfig.set("ifBlockLocationZ2Msg", bC.ifBlockLocationZ2Msg);

		if(bC.isIfPlayerMustBeOnTheBlock()) pCConfig.set("ifPlayerMustBeOnTheBlock", true);
		else pCConfig.set("ifPlayerMustBeOnTheBlock", null);
		if(bC.getIfPlayerMustBeOnTheBlockMsg().contains(bC.IF_PLAYER_MUST_BE_ON_THE_MSG)) pCConfig.set("ifPlayerMustBeOnTheBlockMsg", null);
		else pCConfig.set("ifPlayerMustBeOnTheBlockMsg", bC.getIfPlayerMustBeOnTheBlockMsg());

		if(bC.isIfNoPlayerMustBeOnTheBlock()) pCConfig.set("ifNoPlayerMustBeOnTheBlock", true);
		else pCConfig.set("ifNoPlayerMustBeOnTheBlock", null);
		if(bC.getIfNoPlayerMustBeOnTheBlockMsg().contains(bC.IF_NO_PLAYER_MUST_BE_ON_THE_MSG)) pCConfig.set("ifNoPlayerMustBeOnTheBlockMsg", null);
		else pCConfig.set("ifNoPlayerMustBeOnTheBlockMsg", bC.getIfNoPlayerMustBeOnTheBlockMsg());

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
}
