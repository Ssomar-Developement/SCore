package com.ssomar.score.commands.runnable;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.ssomar.executableitems.items.Item;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.utils.placeholders.StringPlaceholder;

public class ActionInfo {
	
	private String name;
	
	/* The slot where the action was activated */
	private Integer slot;
	
	/* The item (ExecutableItems) that actives the action */
	private Item item;
	
	/* The activator that actives the action */
	private SActivator sActivator;
	
	private boolean isEventCallByMineInCube = false;
	
	/* Important info */
	private UUID launcherUUID;
	
	private UUID receiverUUID;
	
	/* ------------------ */
	private Block block;
	
	private Material oldBlockMaterial;
	
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
		this.item = null;
		this.sActivator = null;
		this.isEventCallByMineInCube = false;
		this.launcherUUID = null;
		this.receiverUUID = null;
		this.block = null;
		this.oldBlockMaterial = null;
		this.entityUUID = null;
		this.silenceOutput = false;
	}
	
	public ActionInfo clone() {
		ActionInfo result = new ActionInfo(this.name, this.slot, this.sp);
		result.setItem(item);
		result.setsActivator(sActivator);
		result.setEventCallByMineInCube(isEventCallByMineInCube);
		result.setLauncherUUID(launcherUUID);
		result.setReceiverUUID(receiverUUID);
		result.setBlock(block);
		result.setOldBlockMaterial(oldBlockMaterial);
		result.setEntityUUID(entityUUID);
		result.setSilenceOutput(silenceOutput);
		
		return result;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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

	public SActivator getsActivator() {
		return sActivator;
	}

	public void setsActivator(SActivator sActivator) {
		this.sActivator = sActivator;
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

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Material getOldBlockMaterial() {
		return oldBlockMaterial;
	}

	public void setOldBlockMaterial(Material oldBlockMaterial) {
		this.oldBlockMaterial = oldBlockMaterial;
	}

	public UUID getEntityUUID() {
		return entityUUID;
	}

	public void setEntityUUID(UUID entityUUID) {
		this.entityUUID = entityUUID;
	}
	
}
