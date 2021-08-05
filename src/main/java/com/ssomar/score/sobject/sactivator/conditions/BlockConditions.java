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
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.base.Charsets;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.MyCoreProtectAPI;

public class BlockConditions extends Conditions{

	private boolean ifPlantFullyGrown;
	public static final String IF_PLANT_FULLY_GROWN_MSG = " &cThe plant must be fully grown to active the activator: &6%activator% &cof this item!";
	private String ifPlantFullyGrownMsg;

	private boolean ifIsPowered;
	public static final String IF_IS_POWERED_MSG = " &cThe block ust be powered by redstone to active the activator: &6%activator% &cof this item!";
	private String ifIsPoweredMsg;
	
	private boolean ifMustBeNatural;
	public static final String IF_MUST_BE_NATURAL_MSG = " &cThe block must be natural to active the activator: &6%activator% &cof this item!";
	private String ifMustBeNaturalMsg;

	List<AroundBlockCondition> blockAroundConditions;

	@Override
	public void init() {
		this.ifPlantFullyGrown = false;
		this.ifPlantFullyGrownMsg = IF_PLANT_FULLY_GROWN_MSG;	

		this.ifIsPowered = false;
		this.ifIsPoweredMsg = IF_IS_POWERED_MSG;	
		
		this.ifMustBeNatural = false;
		this.ifMustBeNaturalMsg = IF_MUST_BE_NATURAL_MSG;	
		

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
			if(!b.isBlockPowered()) {
				this.getSm().sendMessage(p, this.getIfIsPoweredMsg());
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

		return true;
	}

	public static BlockConditions getBlockConditions(ConfigurationSection blockCdtSection, List<String> errorList, String pluginName) {

		BlockConditions bCdt = new BlockConditions();

		bCdt.setIfPlantFullyGrown(blockCdtSection.getBoolean("ifPlantFullyGrown", false));
		bCdt.setIfPlantFullyGrownMsg(blockCdtSection.getString("ifPlantFullyGrownMsg", "&4&l"+pluginName+IF_PLANT_FULLY_GROWN_MSG));

		bCdt.setIfIsPowered(blockCdtSection.getBoolean("ifIsPowered", false));
		bCdt.setIfIsPoweredMsg(blockCdtSection.getString("ifIsPoweredMsg", "&4&l"+pluginName+IF_IS_POWERED_MSG));
		
		bCdt.setIfMustBeNatural(blockCdtSection.getBoolean("ifMustBeNatural", false));
		bCdt.setIfIsNaturalMsg(blockCdtSection.getString("ifMustBeNaturalMsg", "&4&l"+pluginName+IF_MUST_BE_NATURAL_MSG));
		
		
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
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions."+detail+".fPlantFullyGrown", false);


		ConfigurationSection pCConfig = config.getConfigurationSection("activators."+sActivator.getID()+".conditions."+detail);

		if(bC.isIfPlantFullyGrown()) pCConfig.set("ifPlantFullyGrown", true); 
		else pCConfig.set("ifPlantFullyGrown", null);
		pCConfig.set("ifPlantFullyGrownMsg", bC.getIfPlantFullyGrownMsg()); 

		if(bC.isIfIsPowered()) pCConfig.set("ifIsPowered", true); 
		else pCConfig.set("ifIsPowered", null);
		pCConfig.set("ifIsPoweredMsg", bC.getIfIsPoweredMsg());
		
		if(bC.isIfMustbeNatural()) pCConfig.set("ifMustBeNatural", true); 
		else pCConfig.set("ifMustBeNatural", null);
		pCConfig.set("ifMustBeNaturalMsg", bC.getIfMustBeNaturalMsg());

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

}
