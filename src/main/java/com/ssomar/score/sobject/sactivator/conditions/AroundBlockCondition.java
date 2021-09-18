package com.ssomar.score.sobject.sactivator.conditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.base.Charsets;
import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlacedManager;
import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.SendMessage;

public class AroundBlockCondition extends Conditions{
	
	private String id;

	private int southValue;
	private int northValue;
	private int westValue;
	private int eastValue;
	private int aboveValue;
	private int underValue;

	private String errorMsg;

	private List<String> blockMustBeExecutableBlock;

	private List<Material> blockTypeMustBe;

	/*
	 * 
	 * @param southValue the south value
	 * @param northValue the north value
	 * @param westValue the west value
	 * @param eastValue the east value
	 * @param blockMustBeExecutableBlock all ExecutableBlock ids that the block can be
	 * @param blockTypeMustBe all type of block that the block can be
	 * @param errorMsg the message sended when the condition is not valid
	 * 
	 */
	public AroundBlockCondition(String id, int southValue, int northValue, int westValue, int eastValue, int aboveValue, int underValue,
			List<String> blockMustBeExecutableBlock, List<Material> blockTypeMustBe, String errorMsg) {
		super();
		this.id = id;
		this.southValue = southValue;
		this.northValue = northValue;
		this.westValue = westValue;
		this.eastValue = eastValue;
		this.aboveValue = aboveValue;
		this.underValue = underValue;
		this.blockMustBeExecutableBlock = blockMustBeExecutableBlock;
		this.blockTypeMustBe = blockTypeMustBe;
		this.errorMsg = errorMsg;
	}
	
	public AroundBlockCondition(String id) {
		super();
		this.id = id;
		init();
	}

	public boolean verif(Block block, @Nullable Player p) {

		Location targetLoc;
		Block targetBlock;

		targetLoc = block.getLocation();
		targetLoc.add(-westValue+eastValue, -underValue+aboveValue, -northValue+southValue);

		targetBlock = targetLoc.getBlock();

		if(!this.blockTypeMustBe.isEmpty() && this.blockTypeMustBe.contains(targetBlock.getType())) return true;

		if(SCore.hasExecutableBlocks && !this.blockMustBeExecutableBlock.isEmpty()) {
			ExecutableBlockPlaced eBP;
			if((eBP = ExecutableBlockPlacedManager.getInstance().getExecutableBlockPlaced(targetLoc)) != null
					&& this.blockMustBeExecutableBlock.contains(eBP.getEB_ID())) {
				return true;
			}
		}

		if(p != null) SendMessage.sendMessageNoPlch(p, errorMsg);
		return false;
	}
	
	public static void saveAroundBlockCdt(SPlugin sPlugin, SObject sObject, SActivator sActivator, AroundBlockCondition aBC, String detail) {
		
		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file in the folder ! ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions."+detail+".blockAroundCdts."+aBC.getId()+".southValue", "0");
		ConfigurationSection section = activatorConfig.getConfigurationSection("conditions."+detail+".blockAroundCdts."+aBC.getId());
		
		section.set("southValue", aBC.getSouthValue());
		section.set("northValue", aBC.getNorthValue());
		section.set("westValue", aBC.getWestValue());
		section.set("eastValue", aBC.getEastValue());
		section.set("aboveValue", aBC.getAboveValue());
		section.set("underValue", aBC.getUnderValue());
		section.set("blockMustBeExecutableBlock", aBC.getBlockMustBeExecutableBlock());

		List<String> convert = new ArrayList<>();
		for(Material mat : aBC.getBlockTypeMustBe()) {
			convert.add(mat.toString());
		}

		section.set("blockTypeMustBe", convert);
		section.set("errorMsg", aBC.getErrorMsg());
		
		
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

	public static AroundBlockCondition get(ConfigurationSection section) {
		
		String id = section.getName();

		int southValue = section.getInt("southValue", 0);
		int northValue = section.getInt("northValue", 0);
		int westValue = section.getInt("westValue", 0);
		int eastValue = section.getInt("eastValue", 0);
		int aboveValue = section.getInt("aboveValue", 0);
		int underValue = section.getInt("underValue", 0);
		List<String> blockMustBeExecutableBlock = section.getStringList("blockMustBeExecutableBlock");
		List<Material> blockTypeMustBe = new ArrayList<>();

		for(String s : section.getStringList("blockTypeMustBe")) {
			try {
				blockTypeMustBe.add(Material.valueOf(s));
			}catch(Exception ignored) {}
		}

		String errorMsg = section.getString("errorMsg", "");

		return new AroundBlockCondition(id, southValue, northValue, westValue, eastValue, aboveValue, underValue, blockMustBeExecutableBlock, blockTypeMustBe, errorMsg);
	}
	
	/*
	 *  @param sPlugin The plugin of the conditions
	 *  @param sObject The object
	 *  @param sActivator The activator that contains the conditions
	 *  @param id the block around cdt id
	 */
	public static void deleteBACCdt(SPlugin sPlugin, SObject sObject, SActivator sActivator, String id, String detail) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file the folder ! ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions."+detail+"."+id, null);

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSouthValue() {
		return southValue;
	}

	public void setSouthValue(int southValue) {
		this.southValue = southValue;
	}

	public int getNorthValue() {
		return northValue;
	}

	public void setNorthValue(int northValue) {
		this.northValue = northValue;
	}

	public int getWestValue() {
		return westValue;
	}

	public void setWestValue(int westValue) {
		this.westValue = westValue;
	}

	public int getEastValue() {
		return eastValue;
	}

	public void setEastValue(int eastValue) {
		this.eastValue = eastValue;
	}

	public int getAboveValue() {
		return aboveValue;
	}

	public void setAboveValue(int aboveValue) {
		this.aboveValue = aboveValue;
	}

	public int getUnderValue() {
		return underValue;
	}

	public void setUnderValue(int underValue) {
		this.underValue = underValue;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<String> getBlockMustBeExecutableBlock() {
		return blockMustBeExecutableBlock;
	}

	public void setBlockMustBeExecutableBlock(List<String> blockMustBeExecutableBlock) {
		this.blockMustBeExecutableBlock = blockMustBeExecutableBlock;
	}

	public List<Material> getBlockTypeMustBe() {
		return blockTypeMustBe;
	}

	public void setBlockTypeMustBe(List<Material> blockTypeMustBe) {
		this.blockTypeMustBe = blockTypeMustBe;
	}

	@Override
	public void init() {
		this.southValue = 0;
		this.northValue = 0;
		this.westValue = 0;
		this.eastValue = 0;
		this.aboveValue = 0;
		this.underValue = 0;
		this.blockMustBeExecutableBlock = new ArrayList<>();
		this.blockTypeMustBe = new ArrayList<>();
		this.errorMsg = "";
	}

}
