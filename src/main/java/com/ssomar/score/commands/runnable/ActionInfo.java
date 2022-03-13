package com.ssomar.score.commands.runnable;

import java.io.Serializable;
import java.util.UUID;

import com.ssomar.executableitems.items.ExecutableItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.ssomar.score.sobject.sactivator.DetailedBlocks;
import com.ssomar.score.utils.placeholders.StringPlaceholder;

@Getter @Setter
public class ActionInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	/* The slot where the action was activated */
	private Integer slot;

	/* The executableItem that actives the action (to know from which ei a custom projectile has been launched) */
	private ExecutableItem executableItem;

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

	public ActionInfo(String name, StringPlaceholder sp) {
		this.name = name;
		this.slot = -1;
		this.sp = sp;
		this.executableItem = null;
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
		ActionInfo result = new ActionInfo(this.name, this.sp);
		result.setExecutableItem(executableItem);
		result.setEventCallByMineInCube(isEventCallByMineInCube);
		result.setLauncherUUID(launcherUUID);
		result.setReceiverUUID(receiverUUID);
		result.setOldBlockMaterialName(oldBlockMaterialName);
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
}
