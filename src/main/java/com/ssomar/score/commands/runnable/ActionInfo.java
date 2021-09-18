package com.ssomar.score.commands.runnable;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.ssomar.score.sobject.sactivator.DetailedBlocks;
import com.ssomar.score.utils.placeholders.StringPlaceholder;

public class ActionInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	/* The slot where the action was activated */
	private Integer slot;

	/* The item (ExecutableItems) that actives the action */
	private String itemID;

	private boolean isEventCallByMineInCube;

	/* Important info */
	private UUID launcherUUID;

	private UUID receiverUUID;

	/* ------------------ */
	private int blockLocationX;
	private int blockLocationY;
	private int blockLocationZ;
	private UUID blockLocationWorld;

	private DetailedBlocks detailedBlocks;

	private String oldBlockMaterialName;

	/* ------------------ */
	private UUID entityUUID;

	/* ------------------ */

	private boolean silenceOutput;

	private StringPlaceholder sp;

	public ActionInfo(String name, Integer slot, StringPlaceholder sp) {
		this.name = name;
		this.slot = slot;
		this.sp = sp;
		this.silenceOutput = false;
		this.itemID = null;
		this.isEventCallByMineInCube = false;
		this.launcherUUID = null;
		this.receiverUUID = null;
		this.blockLocationX = -1;
		this.blockLocationY = -1;
		this.blockLocationZ = -1;
		this.blockLocationWorld = null;
		this.oldBlockMaterialName = null;
		this.entityUUID = null;
		this.silenceOutput = false;
	}

	public ActionInfo clone() {
		ActionInfo result = new ActionInfo(this.name, this.slot, this.sp);
		result.setItemID(itemID);
		result.setEventCallByMineInCube(isEventCallByMineInCube);
		result.setLauncherUUID(launcherUUID);
		result.setReceiverUUID(receiverUUID);
		try {
			result.setOldBlockMaterial(Material.valueOf(oldBlockMaterialName));
		}
		catch(Exception ignored) {}
		result.setEntityUUID(entityUUID);
		result.setSilenceOutput(silenceOutput);
		result.setBlockLocationX(blockLocationX);
		result.setBlockLocationY(blockLocationY);
		result.setBlockLocationZ(blockLocationZ);
		result.setBlockLocationWorld(blockLocationWorld);		

		return result;
	}

	public void setBlock(Block block) {
		Location bLoc = block.getLocation();
		this.blockLocationX = bLoc.getBlockX();
		this.blockLocationY = bLoc.getBlockY();
		this.blockLocationZ = bLoc.getBlockZ();
		this.blockLocationWorld =  bLoc.getWorld().getUID();
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSlot() {
		return slot;
	}

	public void setSlot(Integer slot) {
		this.slot = slot;
	}

	public boolean isEventCallByMineInCube() {
		return isEventCallByMineInCube;
	}

	public void setEventCallByMineInCube(boolean isEventCallByMineInCube) {
		this.isEventCallByMineInCube = isEventCallByMineInCube;
	}

	public UUID getLauncherUUID() {
		return launcherUUID;
	}

	public void setLauncherUUID(UUID launcherUUID) {
		this.launcherUUID = launcherUUID;
	}

	public UUID getReceiverUUID() {
		return receiverUUID;
	}

	public void setReceiverUUID(UUID receiverUUID) {
		this.receiverUUID = receiverUUID;
	}

	public boolean isSilenceOutput() {
		return silenceOutput;
	}

	public void setSilenceOutput(boolean silenceOutput) {
		this.silenceOutput = silenceOutput;
	}

	public StringPlaceholder getSp() {
		return sp;
	}

	public void setSp(StringPlaceholder sp) {
		this.sp = sp;
	}

	public Material getOldBlockMaterial() {
		return  Material.valueOf(oldBlockMaterialName);
	}

	public void setOldBlockMaterial(Material oldBlockMaterial) {
		this.oldBlockMaterialName = oldBlockMaterial.toString();
	}

	public UUID getEntityUUID() {
		return entityUUID;
	}

	public void setEntityUUID(UUID entityUUID) {
		this.entityUUID = entityUUID;
	}

	public DetailedBlocks getDetailedBlocks() {
		return detailedBlocks;
	}

	public void setDetailedBlocks(DetailedBlocks detailedBlocks) {
		this.detailedBlocks = detailedBlocks;
	}

	public int getBlockLocationX() {
		return blockLocationX;
	}

	public void setBlockLocationX(int blockLocationX) {
		this.blockLocationX = blockLocationX;
	}

	public int getBlockLocationY() {
		return blockLocationY;
	}

	public void setBlockLocationY(int blockLocationY) {
		this.blockLocationY = blockLocationY;
	}

	public int getBlockLocationZ() {
		return blockLocationZ;
	}

	public void setBlockLocationZ(int blockLocationZ) {
		this.blockLocationZ = blockLocationZ;
	}

	public UUID getBlockLocationWorld() {
		return blockLocationWorld;
	}

	public void setBlockLocationWorld(UUID blockLocationWorld) {
		this.blockLocationWorld = blockLocationWorld;
	}

	public String getOldBlockMaterialName() {
		return oldBlockMaterialName;
	}

	public void setOldBlockMaterialName(String oldBlockMaterialName) {
		this.oldBlockMaterialName = oldBlockMaterialName;
	}



}
