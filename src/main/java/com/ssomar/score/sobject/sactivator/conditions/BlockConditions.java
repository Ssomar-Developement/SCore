package com.ssomar.score.sobject.sactivator.conditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

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

public class BlockConditions extends Conditions{

	private boolean ifPlantFullyGrown;
	public static final String IF_PLANT_FULLY_GROWN_MSG = " &cThe plant must be fully grown to active the activator: &6%activator% &cof this item!";
	private String ifPlantFullyGrownMsg;

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

	public BlockConditions(){
		init();
	}

	@Override
	public void init() {
		this.ifPlantFullyGrown = false;
		this.ifPlantFullyGrownMsg = IF_PLANT_FULLY_GROWN_MSG;	

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

		//		List<Material> typeCdt = new ArrayList<>();
		//		typeCdt.add(Material.RAW_IRON_BLOCK);
		//		
		//		List<Material> typeCdt2 = new ArrayList<>();
		//		typeCdt2.add(Material.RAW_GOLD_BLOCK);
		//		
		//		AroundBlockCondition cdt1 = new AroundBlockCondition(0, 0, 0, 0, 1, 0, new ArrayList<>(), typeCdt, "error block 1");
		//		AroundBlockCondition cdt2 = new AroundBlockCondition(0, 0, 0, 1, 0, 0, new ArrayList<>(), typeCdt, "error block 1");
		//		AroundBlockCondition cdt3 = new AroundBlockCondition(0, 0, 1, 0, 0, 0, new ArrayList<>(), typeCdt, "error block 1");
		//		AroundBlockCondition cdt4 = new AroundBlockCondition(0, 0, 0, 0, 0, 1, new ArrayList<>(), typeCdt, "error block 1");
		//		
		//		AroundBlockCondition cdt5 = new AroundBlockCondition(0, 0, 0, 0, 2, 0, new ArrayList<>(), typeCdt2, "error block 1");
		//		
		//		blockAroundConditions.add(cdt1);
		//		blockAroundConditions.add(cdt2);
		//		blockAroundConditions.add(cdt3);
		//		blockAroundConditions.add(cdt4);
		//		blockAroundConditions.add(cdt5);
	}

	public boolean verifConditions(Block b, Player p) {

		if(this.isIfPlantFullyGrown() && b.getState().getBlockData() instanceof Ageable) {
			Ageable ageable = (Ageable) b.getState().getBlockData();
			if(ageable.getAge() != ageable.getMaximumAge()) {
				this.getSm().sendMessage(p, this.getIfPlantFullyGrownMsg());
				return false;
			}
		}

		if(this.ifIsPowered) {
			boolean notPowered = !b.isBlockPowered();

			if(b.getBlockData() instanceof Powerable) {
				Powerable power = (Powerable)b.getBlockData();
				if(!power.isPowered()) notPowered = true;
			}

			if(notPowered) {
				this.getSm().sendMessage(p, this.getIfIsPoweredMsg());
				return false;
			}
		}

		if(this.ifMustBeNotPowered && b.getBlockData() instanceof Powerable) {
			Powerable power = (Powerable)b.getBlockData();
			if(power.isPowered()) {
				this.getSm().sendMessage(p, this.getIfMustBeNotPoweredMsg());
				return false;
			}
		}

		if(this.ifMustBeNatural) {
			if(!MyCoreProtectAPI.isNaturalBlock(b)) {	
				this.getSm().sendMessage(p, this.getIfMustBeNaturalMsg());
				return false;
			}
		}

		if(this.blockAroundConditions.size() > 0) {
			for(AroundBlockCondition bAC : this.blockAroundConditions) {
				if(!bAC.verif(b, p)) return false;
			}
		}
		
		if(!ifBlockLocationX.equals("") && !StringCalculation.calculation(ifBlockLocationX, b.getLocation().getX())) {
			this.getSm().sendMessage(p, ifBlockLocationXMsg);
			return false;
		}
		
		if(!ifBlockLocationX2.equals("") && !StringCalculation.calculation(ifBlockLocationX2, b.getLocation().getX())) {
			this.getSm().sendMessage(p, ifBlockLocationX2Msg);
			return false;
		}
		
		if(!ifBlockLocationY.equals("") && !StringCalculation.calculation(ifBlockLocationY, b.getLocation().getY())) {
			this.getSm().sendMessage(p, ifBlockLocationYMsg);
			return false;
		}
		
		if(!ifBlockLocationY2.equals("") && !StringCalculation.calculation(ifBlockLocationY2, b.getLocation().getY())) {
			this.getSm().sendMessage(p, ifBlockLocationY2Msg);
			return false;
		}
		
		if(!ifBlockLocationZ.equals("") && !StringCalculation.calculation(ifBlockLocationZ, b.getLocation().getZ())) {
			this.getSm().sendMessage(p, ifBlockLocationZMsg);
			return false;
		}
		
		if(!ifBlockLocationZ2.equals("") && !StringCalculation.calculation(ifBlockLocationZ2, b.getLocation().getZ())) {
			this.getSm().sendMessage(p, ifBlockLocationZ2Msg);
			return false;
		}

		return true;
	}

	public static BlockConditions getBlockConditions(ConfigurationSection blockCdtSection, List<String> errorList, String pluginName) {

		BlockConditions bCdt = new BlockConditions();

		bCdt.setIfPlantFullyGrown(blockCdtSection.getBoolean("ifPlantFullyGrown", false));
		bCdt.setIfPlantFullyGrownMsg(blockCdtSection.getString("ifPlantFullyGrownMsg", "&4&l"+pluginName+IF_PLANT_FULLY_GROWN_MSG));

		bCdt.setIfIsPowered(blockCdtSection.getBoolean("ifIsPowered", false));
		bCdt.setIfIsPoweredMsg(blockCdtSection.getString("ifIsPoweredMsg", "&4&l"+pluginName+IF_IS_POWERED_MSG));

		bCdt.setIfMustBeNotPowered(blockCdtSection.getBoolean("ifMustBeNotPowered", false));
		bCdt.setIfMustBeNotPoweredMsg(blockCdtSection.getString("ifMustBeNotPoweredMsg", "&4&l"+pluginName+IF_MUST_BE_NOT_POWERED_MSG));

		bCdt.setIfMustBeNatural(blockCdtSection.getBoolean("ifMustBeNatural", false));
		bCdt.setIfIsNaturalMsg(blockCdtSection.getString("ifMustBeNaturalMsg", "&4&l"+pluginName+IF_MUST_BE_NATURAL_MSG));

		bCdt.setIfBlockLocationX(blockCdtSection.getString("ifBlockLocationX", ""));
		bCdt.setIfBlockLocationXMsg(blockCdtSection.getString("ifBlockLocationXMsg", "&4&l"+pluginName+IF_BLOCK_LOCATION_X_MSG));
		
		bCdt.setIfBlockLocationX2(blockCdtSection.getString("ifBlockLocationX2", ""));
		bCdt.setIfBlockLocationX2Msg(blockCdtSection.getString("ifBlockLocationX2Msg", "&4&l"+pluginName+IF_BLOCK_LOCATION_X2_MSG));
		
		bCdt.setIfBlockLocationY(blockCdtSection.getString("ifBlockLocationY", ""));
		bCdt.setIfBlockLocationYMsg(blockCdtSection.getString("ifBlockLocationYMsg", "&4&l"+pluginName+IF_BLOCK_LOCATION_Y_MSG));
		
		bCdt.setIfBlockLocationY2(blockCdtSection.getString("ifBlockLocationY2", ""));
		bCdt.setIfBlockLocationY2Msg(blockCdtSection.getString("ifBlockLocationY2Msg", "&4&l"+pluginName+IF_BLOCK_LOCATION_Y2_MSG));
		
		bCdt.setIfBlockLocationZ(blockCdtSection.getString("ifBlockLocationZ", ""));
		bCdt.setIfBlockLocationZMsg(blockCdtSection.getString("ifBlockLocationZMsg", "&4&l"+pluginName+IF_BLOCK_LOCATION_Z_MSG));
		
		bCdt.setIfBlockLocationZ2(blockCdtSection.getString("ifBlockLocationZ2", ""));
		bCdt.setIfBlockLocationZ2Msg(blockCdtSection.getString("ifBlockLocationZ2Msg", "&4&l"+pluginName+IF_BLOCK_LOCATION_Z2_MSG));

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
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file in the folder ! ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions."+detail+".fPlantFullyGrown", false);


		ConfigurationSection pCConfig = config.getConfigurationSection("activators."+sActivator.getID()+".conditions."+detail);

		if(bC.isIfPlantFullyGrown()) pCConfig.set("ifPlantFullyGrown", true); 
		else pCConfig.set("ifPlantFullyGrown", null);
		pCConfig.set("ifPlantFullyGrownMsg", bC.getIfPlantFullyGrownMsg()); 

		if(bC.isIfIsPowered()) pCConfig.set("ifIsPowered", true); 
		else pCConfig.set("ifIsPowered", null);
		pCConfig.set("ifIsPoweredMsg", bC.getIfIsPoweredMsg());

		if(bC.isIfMustBeNotPowered()) pCConfig.set("ifMustBeNotPowered", true); 
		else pCConfig.set("ifMustBeNotPowered", null);
		pCConfig.set("ifMustBeNotPoweredMsg", bC.getIfMustBeNotPoweredMsg());

		if(bC.isIfMustbeNatural()) pCConfig.set("ifMustBeNatural", true); 
		else pCConfig.set("ifMustBeNatural", null);
		pCConfig.set("ifMustBeNaturalMsg", bC.getIfMustBeNaturalMsg());
		
		if(!bC.ifBlockLocationX.equals("")) pCConfig.set("ifBlockLocationX", bC.ifBlockLocationX);
		else pCConfig.set("ifBlockLocationX", null);
		pCConfig.set("ifBlockLocationXMsg", bC.ifBlockLocationXMsg);
		
		if(!bC.ifBlockLocationX2.equals("")) pCConfig.set("ifBlockLocationX2", bC.ifBlockLocationX2);
		else pCConfig.set("ifBlockLocationX2", null);
		pCConfig.set("ifBlockLocationX2Msg", bC.ifBlockLocationX2Msg);
		
		if(!bC.ifBlockLocationY.equals("")) pCConfig.set("ifBlockLocationY", bC.ifBlockLocationY);
		else pCConfig.set("ifBlockLocationY", null);
		pCConfig.set("ifBlockLocationYMsg", bC.ifBlockLocationYMsg);
		
		if(!bC.ifBlockLocationY2.equals("")) pCConfig.set("ifBlockLocationY2", bC.ifBlockLocationY2);
		else pCConfig.set("ifBlockLocationY2", null);
		pCConfig.set("ifBlockLocationY2Msg", bC.ifBlockLocationY2Msg);
		
		if(!bC.ifBlockLocationZ.equals("")) pCConfig.set("ifBlockLocationZ", bC.ifBlockLocationZ);
		else pCConfig.set("ifBlockLocationZ", null);
		pCConfig.set("ifBlockLocationZMsg", bC.ifBlockLocationZMsg);
		
		if(!bC.ifBlockLocationZ2.equals("")) pCConfig.set("ifBlockLocationZ2", bC.ifBlockLocationZ2);
		else pCConfig.set("ifBlockLocationZ2", null);
		pCConfig.set("ifBlockLocationZ2Msg", bC.ifBlockLocationZ2Msg);

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

	public boolean isIfIsPowered() {
		return ifIsPowered;
	}

	public void setIfIsPowered(boolean ifIsPowered) {
		this.ifIsPowered = ifIsPowered;
	}

	public String getIfIsPoweredMsg() {
		return ifIsPoweredMsg;
	}

	public void setIfIsPoweredMsg(String ifIsPoweredMsg) {
		this.ifIsPoweredMsg = ifIsPoweredMsg;
	}

	public boolean isIfMustbeNatural() {
		return ifMustBeNatural;
	}

	public void setIfMustBeNatural(boolean ifIsNatural) {
		this.ifMustBeNatural = ifIsNatural;
	}

	public String getIfMustBeNaturalMsg() {
		return ifMustBeNaturalMsg;
	}

	public void setIfIsNaturalMsg(String ifIsNaturalMsg) {
		this.ifMustBeNaturalMsg = ifIsNaturalMsg;
	}

	public List<AroundBlockCondition> getBlockAroundConditions() {
		return blockAroundConditions;
	}

	public void setBlockAroundConditions(List<AroundBlockCondition> blockAroundConditions) {
		this.blockAroundConditions = blockAroundConditions;
	}

	public boolean isIfMustBeNotPowered() {
		return ifMustBeNotPowered;
	}

	public void setIfMustBeNotPowered(boolean ifMustBeNotPowered) {
		this.ifMustBeNotPowered = ifMustBeNotPowered;
	}

	public String getIfMustBeNotPoweredMsg() {
		return ifMustBeNotPoweredMsg;
	}

	public void setIfMustBeNotPoweredMsg(String ifMustBeNotPoweredMsg) {
		this.ifMustBeNotPoweredMsg = ifMustBeNotPoweredMsg;
	}

	public String getIfBlockLocationX() {
		return ifBlockLocationX;
	}

	public void setIfBlockLocationX(String ifBlockLocationX) {
		this.ifBlockLocationX = ifBlockLocationX;
	}

	public String getIfBlockLocationXMsg() {
		return ifBlockLocationXMsg;
	}

	public void setIfBlockLocationXMsg(String ifBlockLocationXMsg) {
		this.ifBlockLocationXMsg = ifBlockLocationXMsg;
	}

	public String getIfBlockLocationX2() {
		return ifBlockLocationX2;
	}

	public void setIfBlockLocationX2(String ifBlockLocationX2) {
		this.ifBlockLocationX2 = ifBlockLocationX2;
	}

	public String getIfBlockLocationX2Msg() {
		return ifBlockLocationX2Msg;
	}

	public void setIfBlockLocationX2Msg(String ifBlockLocationX2Msg) {
		this.ifBlockLocationX2Msg = ifBlockLocationX2Msg;
	}

	public String getIfBlockLocationY() {
		return ifBlockLocationY;
	}

	public void setIfBlockLocationY(String ifBlockLocationY) {
		this.ifBlockLocationY = ifBlockLocationY;
	}

	public String getIfBlockLocationYMsg() {
		return ifBlockLocationYMsg;
	}

	public void setIfBlockLocationYMsg(String ifBlockLocationYMsg) {
		this.ifBlockLocationYMsg = ifBlockLocationYMsg;
	}

	public String getIfBlockLocationY2() {
		return ifBlockLocationY2;
	}

	public void setIfBlockLocationY2(String ifBlockLocationY2) {
		this.ifBlockLocationY2 = ifBlockLocationY2;
	}

	public String getIfBlockLocationY2Msg() {
		return ifBlockLocationY2Msg;
	}

	public void setIfBlockLocationY2Msg(String ifBlockLocationY2Msg) {
		this.ifBlockLocationY2Msg = ifBlockLocationY2Msg;
	}

	public String getIfBlockLocationZ() {
		return ifBlockLocationZ;
	}

	public void setIfBlockLocationZ(String ifBlockLocationZ) {
		this.ifBlockLocationZ = ifBlockLocationZ;
	}

	public String getIfBlockLocationZ2() {
		return ifBlockLocationZ2;
	}

	public void setIfBlockLocationZ2(String ifBlockLocationZ2) {
		this.ifBlockLocationZ2 = ifBlockLocationZ2;
	}

	public String getIfBlockLocationZMsg() {
		return ifBlockLocationZMsg;
	}

	public void setIfBlockLocationZMsg(String ifBlockLocationZMsg) {
		this.ifBlockLocationZMsg = ifBlockLocationZMsg;
	}

	public String getIfBlockLocationZ2Msg() {
		return ifBlockLocationZ2Msg;
	}

	public void setIfBlockLocationZ2Msg(String ifBlockLocationZ2Msg) {
		this.ifBlockLocationZ2Msg = ifBlockLocationZ2Msg;
	}

	public boolean isIfMustBeNatural() {
		return ifMustBeNatural;
	}

	public void setIfMustBeNaturalMsg(String ifMustBeNaturalMsg) {
		this.ifMustBeNaturalMsg = ifMustBeNaturalMsg;
	}

}
