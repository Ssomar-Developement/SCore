package com.ssomar.score.sobject.sactivator.conditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.google.common.base.Charsets;
import com.ssomar.executableitems.api.ExecutableItemsAPI;
import com.ssomar.executableitems.items.Item;
import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.WorldGuardAPI;
import com.ssomar.score.utils.StringCalculation;

public class PlayerConditions extends Conditions{

	private boolean ifSneaking;
	private static final String IF_SNEAKING_MSG = " &cYou must sneak to active the activator: &6%activator% &cof this item!";
	private String ifSneakingMsg;

	private boolean ifNotSneaking;
	private static final String IF_NOT_SNEAKING_MSG = " &cYou must not sneak to active the activator: &6%activator% &cof this item!";
	private String ifNotSneakingMsg;

	private boolean ifBlocking;
	private static final String IF_BLOCKING_MSG = " &cYou must block damage with shield to active the activator: &6%activator% &cof this item!";
	private String ifBlockingMsg;

	private boolean ifNotBlocking;
	private static final String IF_NOT_BLOCKING_MSG = " &cYou must not block damage with shield to active the activator: &6%activator% &cof this item!";
	private String ifNotBlockingMsg;

	private boolean ifSprinting;
	private static final String IF_SPRINTING_MSG = " &cYou must sprint to active the activator: &6%activator% &cof this item!";
	private String ifSprintingMsg;

	private boolean ifSwimming;
	private static final String IF_SWIMMING_MSG = " &cYou must swin to active the activator: &6%activator% &cof this item!";
	private String ifSwimmingMsg;

	private boolean ifGliding;
	private static final String IF_GLIDING_MSG = " &cYou must glide to active the activator: &6%activator% &cof this item!";
	private String ifGlidingMsg;

	private boolean ifFlying;
	private static final String IF_FLYING_MSG = " &cYou must fly to active the activator: &6%activator% &cof this item!";
	private String ifFlyingMsg;

	private boolean ifIsInTheAir;
	private static final String IF_IS_IN_THE_AIR_MSG = " &cYou must be in the air to active the activator: &6%activator% &cof this item!";
	private String ifIsInTheAirMsg;

	private List<Material> ifIsOnTheBlock;
	private static final String IF_IS_ON_THE_BLOCK_MSG = " &cYou are not on the good type of block to active the activator: &6%activator% &cof this item!";
	private String ifIsOnTheBlockMsg;

	private List<String> ifInWorld;
	private static final String IF_IN_WORLD_MSG = " &cYou aren't in the good world to active the activator: &6%activator% &cof this item!";
	private String ifInWorldMsg;

	private List<String> ifNotInWorld;
	private static final String IF_NOT_IN_WORLD_MSG = " &cYou aren't in the good world to active the activator: &6%activator% &cof this item!";
	private String ifNotInWorldMsg;

	private List<String> ifInBiome;
	private static final String IF_IN_BIOME_MSG = " &cYou aren't in the good biome to active the activator: &6%activator% &cof this item!";
	private String ifInBiomeMsg;

	private List<String> ifNotInBiome;
	private static final String IF_NOT_IN_BIOME_MSG = " &cYou aren't in the good biome to active the activator: &6%activator% &cof this item!";
	private String ifNotInBiomeMsg;

	private List<String> ifInRegion;
	private static final String IF_IN_REGION_MSG = " &cYou aren't in the good region to active the activator: &6%activator% &cof this item!";
	private String ifInRegionMsg;

	private List<String> ifNotInRegion;
	private static final String IF_NOT_IN_REGION_MSG = " &cYou are in blacklisted region to active the activator: &6%activator% &cof this item!";
	private String ifNotInRegionMsg;

	private List<String> ifHasPermission;
	private static final String IF_HAS_PERMISSION_MSG = " &cYou doesn't have the permission to active the activator: &6%activator% &cof this item!";
	private String ifHasPermissionMsg;

	private List<String> ifNotHasPermission;
	private static final String IF_NOT_HAS_PERMISSION_MSG = " &cYou have a blacklisted permission to active the activator: &6%activator% &cof this item!";
	private String ifNotHasPermissionMsg;

	private List<Material> ifTargetBlock;
	private static final String IF_TARGET_BLOCK_MSG = " &cYou don't target the good type of block to active the activator: &6%activator% &cof this item!";
	private String ifTargetBlockMsg;

	private List<Material> ifNotTargetBlock;
	private static final String IF_NOT_TARGET_BLOCK_MSG = " &cYou don't target the good type of block to active the activator: &6%activator% &cof this item!";
	private String ifNotTargetBlockMsg;

	private String ifPlayerHealth;
	private static final String IF_PLAYER_HEALTH_MSG = " &cYour health is not valid to active the activator: &6%activator% &cof this item!";
	private String ifPlayerHealthMsg;

	private String ifPlayerFoodLevel;
	private static final String IF_PLAYER_FOOD_LEVEL_MSG = " &cYour food is not valid to active the activator: &6%activator% &cof this item!";
	private String ifPlayerFoodLevelMsg;

	private String ifPlayerEXP;
	private static final String IF_PLAYER_EXP_MSG = " &cYour EXP is not valid to active the activator: &6%activator% &cof this item!";
	private String ifPlayerEXPMsg;

	private String ifPlayerLevel;
	private static final String IF_PLAYER_LEVEL_MSG = " &cYour level is not valid to active the activator: &6%activator% &cof this item!";
	private String ifPlayerLevelMsg;

	private String ifLightLevel;
	private static final String IF_LIGHT_LEVEL_MSG = " &cLight level is not valid to active the activator: &6%activator% &cof this item!";
	private String ifLightLevelMsg;

	private String ifPosX;
	private static final String IF_POS_X_MSG = " &cCoordinate X is not valid to active the activator: &6%activator% &cof this item!";
	private String ifPosXMsg;

	private String ifPosY;
	private static final String IF_POS_Y_MSG = " &cCoordinate Y is not valid to active the activator: &6%activator% &cof this item!";
	private String ifPosYMsg;

	private String ifPosZ;
	private static final String IF_POS_Z_MSG = " &cCoordinate Z is not valid to active the activator: &6%activator% &cof this item!";
	private String ifPosZMsg;

	private Map<String, Integer> ifPlayerHasExecutableItem;
	private static final String IF_PLAYER_HAS_EXECUTABLE_ITEM_MSG = " &cYou don't have all correct ExecutableItems to active the activator: &6%activator% &cof this item!";
	private String ifPlayerHasExecutableItemMsg;

	private Map<Material, Integer> ifPlayerHasItem;
	private static final String IF_PLAYER_HAS_ITEM_MSG = " &cYou don't have all correct Items to active the activator: &6%activator% &cof this item!";
	private String ifPlayerHasItemMsg;

	private Map<PotionEffectType, Integer> ifPlayerHasEffect;
	private static final String IF_PLAYER_HAS_EFFECT_MSG = " &cYou don't have all correct effects to active the activator: &6%activator% &cof this item!";
	private String ifPlayerHasEffectMsg;

	@Override
	public void init() {
		this.ifSneaking = false;
		this.ifSneakingMsg = IF_SNEAKING_MSG;

		this.ifNotSneaking = false;
		this.ifNotSneakingMsg = IF_NOT_SNEAKING_MSG;

		this.ifBlocking = false;
		this.ifBlockingMsg = IF_BLOCKING_MSG;

		this.ifNotBlocking = false;
		this.ifNotBlockingMsg = IF_NOT_BLOCKING_MSG;

		this.ifSprinting = false;
		this.ifSprintingMsg = IF_SPRINTING_MSG;

		this.ifSwimming = false;
		this.ifSwimmingMsg = IF_SWIMMING_MSG;

		this.ifGliding = false;
		this.ifGlidingMsg = IF_GLIDING_MSG;

		this.ifFlying = false;
		this.ifFlyingMsg = IF_FLYING_MSG;

		ifIsInTheAir = false;
		ifIsInTheAirMsg = IF_IS_IN_THE_AIR_MSG;

		ifIsOnTheBlock = new ArrayList<>();
		ifIsOnTheBlockMsg = IF_IS_ON_THE_BLOCK_MSG;

		this.ifInWorld = new ArrayList<>();
		this.ifInWorldMsg = IF_IN_WORLD_MSG;

		this.ifNotInWorld = new ArrayList<>();
		this.ifNotInWorldMsg = IF_NOT_IN_WORLD_MSG;

		this.ifInBiome = new ArrayList<>();
		this.ifInBiomeMsg = IF_IN_BIOME_MSG;

		this.ifNotInBiome = new ArrayList<>();
		this.ifNotInBiomeMsg = IF_NOT_IN_BIOME_MSG;

		this.ifInRegion = new ArrayList<>();
		this.ifInRegionMsg = IF_IN_REGION_MSG;

		this.ifNotInRegion = new ArrayList<>();
		this.ifNotInRegionMsg = IF_NOT_IN_REGION_MSG;

		this.ifHasPermission = new ArrayList<>();
		this.ifHasPermissionMsg = IF_HAS_PERMISSION_MSG;

		this.ifNotHasPermission = new ArrayList<>();
		this.ifNotHasPermissionMsg = IF_NOT_HAS_PERMISSION_MSG;

		this.ifTargetBlock = new ArrayList<>();
		this.ifTargetBlockMsg = IF_TARGET_BLOCK_MSG;

		this.ifNotTargetBlock = new ArrayList<>();
		this.ifNotTargetBlockMsg = IF_NOT_TARGET_BLOCK_MSG;

		this.ifPlayerHealth = "";
		this.ifPlayerHealthMsg = IF_PLAYER_HEALTH_MSG;

		this.ifPlayerFoodLevel = "";
		this.ifPlayerFoodLevelMsg= IF_PLAYER_FOOD_LEVEL_MSG;

		this.ifPlayerEXP = "";
		this.ifPlayerEXPMsg = IF_PLAYER_EXP_MSG;

		this.ifPlayerLevel = "";
		this.ifPlayerLevelMsg = IF_PLAYER_LEVEL_MSG;;

		this.ifLightLevel = "";
		this.ifLightLevelMsg = IF_LIGHT_LEVEL_MSG;

		this.ifPosX = "";
		this.ifPosXMsg = IF_POS_X_MSG;

		this.ifPosY = "";
		this.ifPosYMsg = IF_POS_Y_MSG;

		this.ifPosZ = "";
		this.ifPosZMsg = IF_POS_Z_MSG;

		this.ifPlayerHasItem = new HashMap<>();
		this.ifPlayerHasItemMsg = IF_PLAYER_HAS_ITEM_MSG;

		this.ifPlayerHasExecutableItem = new HashMap<>();
		this.ifPlayerHasExecutableItemMsg = IF_PLAYER_HAS_EXECUTABLE_ITEM_MSG;

		this.ifPlayerHasEffect = new HashMap<>();
		this.ifPlayerHasEffectMsg = IF_PLAYER_HAS_EFFECT_MSG;
	}

	public boolean verifConditions(Player p, Player toMsg) {

		if(this.hasIfHasPermission()) {
			boolean valid= true;
			for(String perm : this.getIfHasPermission()) {
				if(!p.hasPermission(perm)) {
					valid=false;
					break;
				}
			}
			if(!valid) {
				this.getSm().sendMessage(toMsg, this.getIfHasPermissionMsg());
				return false;
			}
		}

		if(this.hasIfNotHasPermission()) {
			for(String perm : this.getIfNotHasPermission()) {
				if(p.hasPermission(perm)) {
					this.getSm().sendMessage(toMsg, this.getIfNotHasPermissionMsg());
					return false;
				}
			}
		}

		if(this.hasIfSneaking() && ifSneaking && !p.isSneaking()) {
			this.getSm().sendMessage(toMsg, this.getIfSneakingMsg());
			return false;
		}

		if(this.hasIfNotSneaking() && ifNotSneaking && p.isSneaking()) {
			this.getSm().sendMessage(toMsg, this.getIfNotSneakingMsg());
			return false;
		}

		if(ifBlocking && !p.isBlocking()) {
			this.getSm().sendMessage(toMsg, this.getIfBlockingMsg());
			return false;
		}

		if(ifNotBlocking && p.isBlocking()) {
			this.getSm().sendMessage(toMsg, this.getIfNotBlockingMsg());
			return false;
		}

		if(this.hasIfSwimming() && ifSwimming && !p.isSwimming()) {
			this.getSm().sendMessage(toMsg, (this.getIfSwimmingMsg()));
			return false;
		}

		if(this.ifSprinting && !p.isSprinting()) {
			this.getSm().sendMessage(toMsg, (this.getIfSprintingMsg()));
			return false;
		}

		if(this.hasIfGliding() && ifGliding && !p.isGliding()) {
			this.getSm().sendMessage(toMsg, this.getIfGlidingMsg());
			return false;
		}

		if(this.hasIfFlying() && ifFlying && !p.isFlying()) {
			this.getSm().sendMessage(toMsg, this.getIfFlyingMsg());
			return false;
		}

		if(this.ifIsInTheAir || this.ifIsOnTheBlock.size() != 0) {
			Location pLoc = p.getLocation();
			pLoc.subtract(0, 1, 0);

			Block block = pLoc.getBlock();
			Material type = block.getType();
			if(!type.equals(Material.AIR) && this.ifIsInTheAir) {
				this.getSm().sendMessage(toMsg, this.getIfIsInTheAirMsg());
				return false;
			}

			if(this.ifIsOnTheBlock.size() != 0 && !ifIsOnTheBlock.contains(type)) {
				this.getSm().sendMessage(toMsg, this.getIfIsOnTheBlockMsg());
				return false;
			}
		}

		if(this.hasIfInWorld()) {
			boolean notValid=true;
			for(String s: this.ifInWorld) {
				if(p.getWorld().getName().equalsIgnoreCase(s)) {
					notValid=false;
					break;
				}
			}
			if(notValid) {
				this.getSm().sendMessage(toMsg, this.getIfInWorldMsg());
				return false;
			}
		}

		if(this.hasIfNotInWorld()) {
			boolean notValid=false;
			for(String s: this.ifNotInWorld) {
				if(p.getWorld().getName().equalsIgnoreCase(s)) {
					notValid=true;
					break;
				}
			}
			if(notValid) {
				this.getSm().sendMessage(toMsg, this.getIfNotInWorldMsg());
				return false;
			}
		}

		if(this.hasIfInBiome()) {
			boolean notValid=true;
			for(String s: this.ifInBiome) {
				if(p.getLocation().getBlock().getBiome().toString().equalsIgnoreCase(s)) {
					notValid=false;
					break;
				}
			}
			if(notValid) {
				this.getSm().sendMessage(toMsg, this.getIfInBiomeMsg());
				return false;
			}
		}

		if(this.hasIfNotInBiome()) {
			boolean notValid=false;
			for(String s: this.ifNotInBiome) {
				if(p.getLocation().getBlock().getBiome().toString().equalsIgnoreCase(s)) {
					notValid=true;
					break;
				}
			}
			if(notValid) {
				this.getSm().sendMessage(toMsg, this.getIfNotInBiomeMsg());
				return false;
			}
		}		

		if(SCore.hasWorldGuard) {
			if(this.hasIfInRegion() && !new WorldGuardAPI().isInRegion(p, this.ifInRegion)) {
				this.getSm().sendMessage(toMsg, this.getIfInRegionMsg());
				return false;
			}

			if(this.hasIfNotInRegion() && new WorldGuardAPI().isInRegion(p, this.ifNotInRegion)) {
				this.getSm().sendMessage(toMsg, this.getIfNotInRegionMsg());
				return false;
			}
		}

		if(this.hasIfTargetBlock()) {
			Block block = p.getTargetBlock(null, 5);
			/* take only the fix block, not hte falling block */
			if((block.getType().equals(Material.WATER) || block.getType().equals(Material.LAVA)) && !block.getBlockData().getAsString().contains("level=0")) {
				this.getSm().sendMessage(toMsg, this.getIfTargetBlockMsg());
				return false;
			}
			if(!this.getIfTargetBlock().contains(block.getType())) {
				this.getSm().sendMessage(toMsg, this.getIfTargetBlockMsg());
				return false;
			}
		}

		if(this.hasIfNotTargetBlock()) {
			Block block = p.getTargetBlock(null, 5);
			/* take only the fix block, not hte falling block */
			if((block.getType().equals(Material.WATER) || block.getType().equals(Material.LAVA)) && !block.getBlockData().getAsString().contains("level=0")) {
				this.getSm().sendMessage(toMsg, this.getIfNotTargetBlockMsg());
				return false;
			}
			if(this.getIfNotTargetBlock().contains(block.getType())) {
				this.getSm().sendMessage(toMsg, this.getIfNotTargetBlockMsg());
				return false;
			}
		}

		if(this.hasIfPlayerHealth() && !StringCalculation.calculation(this.ifPlayerHealth, p.getHealth())) {
			this.getSm().sendMessage(toMsg, this.getIfPlayerHealthMsg());
			return false;
		}

		if(this.hasIfPlayerFoodLevel() && !StringCalculation.calculation(this.ifPlayerFoodLevel, p.getFoodLevel())) {
			this.getSm().sendMessage(toMsg, this.getIfPlayerFoodLevelMsg());
			return false;
		}

		if(this.hasIfPlayerEXP() && !StringCalculation.calculation(this.ifPlayerEXP, p.getExp())) {
			this.getSm().sendMessage(toMsg, this.getIfPlayerEXPMsg());
			return false;
		}

		if(this.hasIfPlayerLevel() && !StringCalculation.calculation(this.ifPlayerLevel, p.getLevel())) {
			this.getSm().sendMessage(toMsg, this.getIfPlayerLevelMsg());
			return false;
		}	

		if(this.hasIfLightLevel() && !StringCalculation.calculation(this.ifLightLevel, p.getEyeLocation().getBlock().getLightLevel())) {
			this.getSm().sendMessage(toMsg, this.getIfLightLevelMsg());
			return false;
		}	

		if(this.hasIfPosX() && !StringCalculation.calculation(this.ifPosX, p.getLocation().getX())) {
			this.getSm().sendMessage(toMsg, this.getIfPosXMsg());
			return false;
		}

		if(this.hasIfPosY() && !StringCalculation.calculation(this.ifPosY, p.getLocation().getY())) {
			this.getSm().sendMessage(toMsg, this.getIfPosYMsg());
			return false;
		}

		if(this.hasIfPosZ() && !StringCalculation.calculation(this.ifPosZ, p.getLocation().getZ())) {
			this.getSm().sendMessage(toMsg, this.getIfPosZMsg());
			return false;
		}

		if(this.hasIfPlayerHasExecutableItem() || this.hasIfPlayerHasItem()) {
			ItemStack[] content = p.getInventory().getContents();
			Map<String, Integer> verifEI = new HashMap<>();
			verifEI.putAll(this.getIfPlayerHasExecutableItem());

			Map<Material, Integer> verifI = new HashMap<>();
			verifI.putAll(this.getIfPlayerHasItem());

			int cpt = -1;
			for(ItemStack is : content) {
				cpt++;
				if(is == null) continue;

				if(SCore.hasExecutableItems) {
					Item item;
					if((item = ExecutableItemsAPI.getExecutableItemConfig(is))!=null && !verifEI.isEmpty() && verifEI.containsKey(item.getIdentification()) && verifEI.get(item.getIdentification())==cpt) 
						verifEI.remove(item.getIdentification());
				}

				if(verifI.containsKey(is.getType()) && verifI.get(is.getType())==cpt) {
					verifI.remove(is.getType());
				}
			}

			if(!verifEI.isEmpty()) {
				this.getSm().sendMessage(toMsg, this.getIfPlayerHasExecutableItemMsg());
				return false;
			}

			if(!verifI.isEmpty()) {
				this.getSm().sendMessage(toMsg, this.getIfPlayerHasItemMsg());
				return false;
			}
		}

		if(this.ifPlayerHasEffect.size() > 0) {
			for(PotionEffectType pET : ifPlayerHasEffect.keySet()) {
				if(!p.hasPotionEffect(pET)) {
					this.getSm().sendMessage(toMsg, this.getIfPlayerHasEffectMsg());
					return false;
				}
				else {
					if(p.getPotionEffect(pET).getAmplifier() < ifPlayerHasEffect.get(pET)) {
						this.getSm().sendMessage(toMsg, this.getIfPlayerHasEffectMsg());
						return false;
					}
				}
			}
		}
		return true;
	}

	public static PlayerConditions getPlayerConditions(ConfigurationSection playerCdtSection, List<String> errorList, String pluginName) {

		PlayerConditions pCdt = new PlayerConditions();

		pCdt.setIfSneaking(playerCdtSection.getBoolean("ifSneaking", false));
		pCdt.setIfSneakingMsg(playerCdtSection.getString("ifSneakingMsg", "&4&l"+pluginName+IF_SNEAKING_MSG));

		pCdt.setIfNotSneaking(playerCdtSection.getBoolean("ifNotSneaking", false));
		pCdt.setIfNotSneakingMsg(playerCdtSection.getString("ifNotSneakingMsg", "&4&l"+pluginName+IF_NOT_SNEAKING_MSG));

		pCdt.setIfBlocking(playerCdtSection.getBoolean("ifBlocking", false));
		pCdt.setIfBlockingMsg(playerCdtSection.getString("ifBlockingMsg", "&4&l"+pluginName+IF_BLOCKING_MSG));

		pCdt.setIfNotBlocking(playerCdtSection.getBoolean("ifNotBlocking", false));
		pCdt.setIfNotBlockingMsg(playerCdtSection.getString("ifNotBlockingMsg", "&4&l"+pluginName+IF_NOT_BLOCKING_MSG));

		pCdt.setIfSprinting(playerCdtSection.getBoolean("ifSprinting", false));
		pCdt.setIfSprintingMsg(playerCdtSection.getString("ifSprintingMsg", "&4&l"+pluginName+IF_SPRINTING_MSG));

		pCdt.setIfSwimming(playerCdtSection.getBoolean("ifSwimming", false));
		pCdt.setIfSwimmingMsg(playerCdtSection.getString("ifSwimmingMsg", "&4&l"+pluginName+IF_SWIMMING_MSG));

		pCdt.setIfGliding(playerCdtSection.getBoolean("ifGliding", false));
		pCdt.setIfGlidingMsg(playerCdtSection.getString("ifGlidingMsg", "&4&l"+pluginName+IF_GLIDING_MSG));

		pCdt.setIfFlying(playerCdtSection.getBoolean("ifFlying", false));
		pCdt.setIfFlyingMsg(playerCdtSection.getString("ifFlyingMsg", "&4&l"+pluginName+IF_FLYING_MSG));

		pCdt.setIfIsInTheAir(playerCdtSection.getBoolean("ifIsInTheAir", false));
		pCdt.setIfIsInTheAirMsg(playerCdtSection.getString("ifIsInTheAirMsg", "&4&l"+pluginName+IF_IS_IN_THE_AIR_MSG));

		List<Material> mat = new ArrayList<>();
		for (String s : playerCdtSection.getStringList("ifIsOnTheBlock")) {
			try {
				mat.add(Material.valueOf(s.toUpperCase()));
			} catch (Exception e) {}
		}
		pCdt.setIfIsOnTheBlock(mat);
		pCdt.setIfIsOnTheBlockMsg(playerCdtSection.getString("ifIsOnTheBlockMsg", "&4&l"+pluginName+IF_IS_ON_THE_BLOCK_MSG));

		pCdt.setIfInWorld(playerCdtSection.getStringList("ifInWorld"));
		pCdt.setIfInWorldMsg(playerCdtSection.getString("ifInWorldMsg", "&4&l"+pluginName+IF_IN_WORLD_MSG));

		pCdt.setIfNotInWorld(playerCdtSection.getStringList("ifNotInWorld"));
		pCdt.setIfNotInWorldMsg(playerCdtSection.getString("ifNotInWorldMsg", "&4&l"+pluginName+IF_NOT_IN_WORLD_MSG));

		pCdt.setIfInBiome(playerCdtSection.getStringList("ifInBiome"));
		pCdt.setIfInBiomeMsg(playerCdtSection.getString("ifInBiomeMsg", "&4&l"+pluginName+IF_IN_BIOME_MSG));

		pCdt.setIfNotInBiome(playerCdtSection.getStringList("ifNotInBiome"));
		pCdt.setIfNotInBiomeMsg(playerCdtSection.getString("ifNotInBiomeMsg", "&4&l"+pluginName+IF_NOT_IN_BIOME_MSG));

		if (playerCdtSection.contains("ifInRegion") || playerCdtSection.contains("ifNotInRegion")) {

			if (SCore.is1v12()) {
				errorList.add(pluginName+" Error the conditions ifInRegion and ifNotInRegion are not available in 1.12 due to a changement of worldguard API ");
			} 
			else {
				pCdt.setIfInRegion(playerCdtSection.getStringList("ifInRegion"));
				pCdt.setIfInRegionMsg(playerCdtSection.getString("ifInRegionMsg", "&4&l"+pluginName+IF_IN_REGION_MSG));

				pCdt.setIfNotInRegion(playerCdtSection.getStringList("ifNotInRegion"));
				pCdt.setIfNotInRegionMsg(playerCdtSection.getString("ifNotInRegionMsg", "&4&l"+pluginName+IF_NOT_IN_REGION_MSG));
			}
		}

		pCdt.setIfHasPermission(playerCdtSection.getStringList("ifHasPermission"));
		pCdt.setIfHasPermissionMsg(playerCdtSection.getString("ifHasPermissionMsg", "&4&l"+pluginName+IF_HAS_PERMISSION_MSG));

		pCdt.setIfNotHasPermission(playerCdtSection.getStringList("ifNotHasPermission"));
		pCdt.setIfNotHasPermissionMsg(playerCdtSection.getString("ifNotHasPermissionMsg", "&4&l"+pluginName+IF_NOT_HAS_PERMISSION_MSG));

		mat = new ArrayList<>();
		for (String s : playerCdtSection.getStringList("ifTargetBlock")) {
			try {
				mat.add(Material.valueOf(s.toUpperCase()));
			} catch (Exception e) {}
		}
		pCdt.setIfTargetBlock(mat);
		pCdt.setIfTargetBlockMsg(playerCdtSection.getString("ifTargetBlockMsg", "&4&l"+pluginName+IF_TARGET_BLOCK_MSG));

		mat = new ArrayList<>();
		for (String s : playerCdtSection.getStringList("ifNotTargetBlock")) {
			try {
				mat.add(Material.valueOf(s.toUpperCase()));
			} catch (Exception e) {}
		}
		pCdt.setIfNotTargetBlock(mat);
		pCdt.setIfNotTargetBlockMsg(playerCdtSection.getString("ifNotTargetBlockMsg", "&4&l"+pluginName+IF_NOT_TARGET_BLOCK_MSG));

		pCdt.setIfPlayerHealth(playerCdtSection.getString("ifPlayerHealth", ""));
		pCdt.setIfPlayerHealthMsg(playerCdtSection.getString("ifPlayerHealthMsg", "&4&l"+pluginName+IF_PLAYER_HEALTH_MSG));

		pCdt.setIfPlayerFoodLevel(playerCdtSection.getString("ifPlayerFoodLevel", ""));
		pCdt.setIfPlayerFoodLevelMsg(playerCdtSection.getString("ifPlayerFoodLevelMsg", "&4&l"+pluginName+IF_PLAYER_FOOD_LEVEL_MSG));

		pCdt.setIfPlayerEXP(playerCdtSection.getString("ifPlayerEXP", ""));
		pCdt.setIfPlayerEXPMsg(playerCdtSection.getString("ifPlayerEXPMsg", "&4&l"+pluginName+IF_PLAYER_EXP_MSG));

		pCdt.setIfPlayerLevel(playerCdtSection.getString("ifPlayerLevel", ""));
		pCdt.setIfPlayerLevelMsg(playerCdtSection.getString("ifPlayerLevelMsg", "&4&l"+pluginName+IF_PLAYER_LEVEL_MSG));

		pCdt.setIfLightLevel(playerCdtSection.getString("ifLightLevel", ""));
		pCdt.setIfLightLevelMsg(playerCdtSection.getString("ifLightLevelMsg", "&4&l"+pluginName+IF_LIGHT_LEVEL_MSG));

		pCdt.setIfPosX(playerCdtSection.getString("ifPosX", ""));
		pCdt.setIfPosXMsg(playerCdtSection.getString("ifPosXMsg", "&4&l"+pluginName+IF_POS_X_MSG));

		pCdt.setIfPosY(playerCdtSection.getString("ifPosY", ""));
		pCdt.setIfPosYMsg(playerCdtSection.getString("ifPosYMsg", "&4&l"+pluginName+IF_POS_Y_MSG));

		pCdt.setIfPosZ(playerCdtSection.getString("ifPosZ", ""));
		pCdt.setIfPosZMsg(playerCdtSection.getString("ifPosZMsg", "&4&l"+pluginName+IF_POS_Z_MSG));

		Map<String, Integer> verifEI = new HashMap<>();
		for (String s : playerCdtSection.getStringList("ifPlayerHasExecutableItem")) {
			String[] spliter;
			if (s.contains(":") && (spliter = s.split(":")).length == 2) {
				int slot = 0;
				try {
					slot = Integer.valueOf(spliter[1]);
				} catch (Exception e) {
					errorList.add(pluginName+" Invalid argument for the ifPlayerHasExecutableItem condition: " + s + " correct form > ID:SLOT  example> test:5 !");
					continue;
				}
				verifEI.put(spliter[0], slot);
			}
		}
		pCdt.setIfPlayerHasExecutableItem(verifEI);
		pCdt.setIfPlayerHasExecutableItemMsg(playerCdtSection.getString("ifPlayerHasExecutableItemMsg", "&4&l"+pluginName+IF_PLAYER_HAS_EXECUTABLE_ITEM_MSG));

		Map<Material, Integer> verifI = new HashMap<>();
		for (String s : playerCdtSection.getStringList("ifPlayerHasItem")) {
			String[] spliter;
			if (s.contains(":") && (spliter = s.split(":")).length == 2) {
				int slot = 0;
				Material material = null;
				try {
					material = Material.valueOf(spliter[0]);
				} catch (Exception e) {
					errorList.add(pluginName+" Invalid argument for the ifPlayerHasItem condition: " + s+ " correct form > MATERIAL:SLOT  example> DIAMOND:5 !");
					continue;
				}
				try {
					slot = Integer.valueOf(spliter[1]);
				} catch (Exception e) {
					errorList.add(pluginName+" Invalid argument for the ifPlayerHasItem condition: " + s+ " correct form > MATERIAL:SLOT  example> DIAMOND:5 !");
					continue;
				}
				verifI.put(material, slot);
			}
		}
		pCdt.setIfPlayerHasItem(verifI);
		pCdt.setIfPlayerHasItemMsg(playerCdtSection.getString("ifPlayerHasItemMsg", "&4&l"+pluginName+IF_PLAYER_HAS_ITEM_MSG));

		Map<PotionEffectType, Integer> verifETP = new HashMap<>();
		for (String s : playerCdtSection.getStringList("ifPlayerHasEffect")) {
			String[] spliter;
			if (s.contains(":") && (spliter = s.split(":")).length == 2) {
				int value = 0;
				PotionEffectType type = PotionEffectType.getByName(spliter[0]);
				if(type == null) {
					errorList.add(pluginName+" Invalid argument for the ifPlayerHasEffect condition: " + s+ " correct form > EFFECT:MINIMUM_AMPLIFIER_REQUIRED  example> SPEED:0 !");
					continue;
				}

				try {
					value = Integer.valueOf(spliter[1]);
				} catch (Exception e) {
					errorList.add(pluginName+" Invalid argument for the ifPlayerHasEffect condition: " + s+ " correct form > EFFECT:MINIMUM_AMPLIFIER_REQUIRED  example> SPEED:0 !");
					continue;
				}
				verifETP.put(type, value);
			}
		}

		pCdt.setIfPlayerHasEffect(verifETP);
		pCdt.setIfPlayerHasEffectMsg(playerCdtSection.getString("ifPlayerHasEffectMsg", "&4&l"+pluginName+IF_PLAYER_HAS_EFFECT_MSG));

		return pCdt;

	}

	/*
	 *  @param sPlugin The plugin of the conditions
	 *  @param sObject The object
	 *  @param sActivator The activator that contains the conditions
	 *  @param pC the player conditions object
	 */
	public static void savePlayerConditions(SPlugin sPlugin, SObject sObject, SActivator sActivator, PlayerConditions pC, String detail) {

		if(!new File(sObject.getPath()).exists()) {
			sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign()+" Error can't find the file in the folder ! ("+sObject.getID()+".yml)");
			return;
		}
		File file = new File(sObject.getPath());
		FileConfiguration config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		ConfigurationSection activatorConfig = config.getConfigurationSection("activators."+sActivator.getID());
		activatorConfig.set("conditions."+detail+".ifSneaking", false);


		ConfigurationSection pCConfig = config.getConfigurationSection("activators."+sActivator.getID()+".conditions."+detail);

		if(pC.hasIfSneaking()) pCConfig.set("ifSneaking", true); 
		else pCConfig.set("ifSneaking", null);
		pCConfig.set("ifSneakingMsg", pC.getIfSneakingMsg()); 

		if(pC.hasIfNotSneaking()) pCConfig.set("ifNotSneaking", true); 
		else pCConfig.set("ifNotSneaking", null);
		pCConfig.set("ifNotSneakingMsg", pC.getIfNotSneakingMsg()); 

		if(pC.ifBlocking) pCConfig.set("ifBlocking", true); 
		else pCConfig.set("ifBlocking", null);
		pCConfig.set("ifBlockingMsg", pC.getIfBlockingMsg()); 

		if(pC.ifNotBlocking) pCConfig.set("ifNotBlocking", true); 
		else pCConfig.set("ifNotBlocking", null);
		pCConfig.set("ifNotBlockingMsg", pC.getIfNotBlockingMsg()); 

		if(pC.hasIfSwimming()) pCConfig.set("ifSwimming", true); 
		else pCConfig.set("ifSwimming", null);
		pCConfig.set("ifSwimmingMsg", pC.getIfSwimmingMsg()); 

		if(pC.ifSprinting) pCConfig.set("ifSprinting", true); 
		else pCConfig.set("ifSprinting", null);
		pCConfig.set("ifSprintingMsg", pC.getIfSprintingMsg()); 

		if(pC.hasIfGliding()) pCConfig.set("ifGliding", true); 
		else pCConfig.set("ifGliding", null);
		pCConfig.set("ifGlidingMsg", pC.getIfGlidingMsg()); 

		if(pC.hasIfFlying()) pCConfig.set("ifFlying", true); 
		else pCConfig.set("ifFlying", null);
		pCConfig.set("ifFlyingMsg", pC.getIfFlyingMsg()); 

		if(pC.ifIsInTheAir) pCConfig.set("ifIsInTheAir", true); 
		else pCConfig.set("ifIsInTheAir", null);
		pCConfig.set("ifIsInTheAirMsg", pC.getIfIsInTheAirMsg()); 

		List<String> convert = new ArrayList<>();
		for(Material mat : pC.getIfIsOnTheBlock()) {
			convert.add(mat.toString());
		}
		if(pC.getIfIsOnTheBlock().size() != 0) pCConfig.set("ifIsOnTheBlock",convert); 
		else pCConfig.set("ifIsOnTheBlock", null);
		pCConfig.set("ifIsOnTheBlockMsg", pC.getIfIsOnTheBlockMsg()); 

		if(pC.hasIfInWorld()) pCConfig.set("ifInWorld", pC.getIfInWorld()); 
		else pCConfig.set("ifInWorld", null);
		pCConfig.set("ifInWorldMsg", pC.getIfInWorldMsg()); 

		if(pC.hasIfNotInWorld()) pCConfig.set("ifNotInWorld", pC.getIfNotInWorld()); 
		else pCConfig.set("ifNotInWorld", null);
		pCConfig.set("ifNotInWorldMsg", pC.getIfNotInWorldMsg()); 

		if(pC.hasIfInBiome()) pCConfig.set("ifInBiome", pC.getIfInBiome()); 
		else pCConfig.set("ifInBiome", null);
		pCConfig.set("ifInBiomeMsg", pC.getIfInBiomeMsg()); 

		if(pC.hasIfNotInBiome()) pCConfig.set("ifNotInBiome", pC.getIfNotInBiome()); 
		else pCConfig.set("ifNotInBiome", null);
		pCConfig.set("ifNotInBiomeMsg", pC.getIfNotInBiomeMsg()); 

		if(pC.hasIfInRegion()) pCConfig.set("ifInRegion", pC.getIfInRegion()); 
		else pCConfig.set("ifInRegion", null);
		pCConfig.set("ifInRegionMsg", pC.getIfInRegionMsg()); 

		if(pC.hasIfNotInRegion()) pCConfig.set("ifNotInRegion", pC.getIfNotInRegion()); 
		else pCConfig.set("ifNotInRegion", null);
		pCConfig.set("ifNotInRegionMsg", pC.getIfNotInRegionMsg()); 

		if(pC.hasIfHasPermission()) pCConfig.set("ifHasPermission", pC.getIfHasPermission()); 
		else pCConfig.set("ifHasPermission", null);
		pCConfig.set("ifHasPermissionMsg", pC.getIfHasPermissionMsg()); 

		if(pC.hasIfNotHasPermission()) pCConfig.set("ifNotHasPermission", pC.getIfNotHasPermission()); 
		else pCConfig.set("ifNotHasPermission", null);
		pCConfig.set("ifNotHasPermissionMsg", pC.getIfNotHasPermissionMsg()); 

		convert = new ArrayList<>();
		for(Material mat : pC.getIfTargetBlock()) {
			convert.add(mat.toString());
		}
		if(pC.hasIfTargetBlock()) pCConfig.set("ifTargetBlock",convert); 
		else pCConfig.set("ifTargetBlock", null);
		pCConfig.set("ifTargetBlockMsg", pC.getIfTargetBlockMsg()); 

		convert = new ArrayList<>();
		for(Material mat : pC.getIfNotTargetBlock()) {
			convert.add(mat.toString());
		}
		if(pC.hasIfNotTargetBlock()) pCConfig.set("ifNotTargetBlock", convert); 
		else pCConfig.set("ifNotTargetBlock", null);
		pCConfig.set("ifNotTargetBlockMsg", pC.getIfNotTargetBlockMsg()); 

		if(pC.hasIfPlayerHealth()) pCConfig.set("ifPlayerHealth", pC.getIfPlayerHealth()); 
		else pCConfig.set("ifPlayerHealth", null);
		pCConfig.set("ifPlayerHealthMsg", pC.getIfPlayerHealthMsg()); 

		if(pC.hasIfLightLevel()) pCConfig.set("ifLightLevel", pC.getIfLightLevel()); 
		else pCConfig.set("ifLightLevel", null);
		pCConfig.set("ifLightLevelMsg", pC.getIfLightLevelMsg()); 

		if(pC.hasIfPlayerFoodLevel()) pCConfig.set("ifPlayerFoodLevel", pC.getIfPlayerFoodLevel());
		else pCConfig.set("ifPlayerFoodLevel", null);
		pCConfig.set("ifPlayerFoodLevelMsg", pC.getIfPlayerFoodLevelMsg());

		if(pC.hasIfPlayerEXP()) pCConfig.set("ifPlayerEXP", pC.getIfPlayerEXP()); 
		else pCConfig.set("ifPlayerEXP", null);
		pCConfig.set("ifPlayerEXPMsg", pC.getIfPlayerEXPMsg()); 

		if(pC.hasIfPlayerLevel()) pCConfig.set("ifPlayerLevel", pC.getIfPlayerLevel()); 
		else pCConfig.set("ifPlayerLevel", null);
		pCConfig.set("ifPlayerLevelMsg", pC.getIfPlayerLevelMsg()); 

		if(pC.hasIfPosX()) pCConfig.set("ifPosX", pC.getIfPosX()); 
		else pCConfig.set("ifPosX", null);
		pCConfig.set("ifPosXMsg", pC.getIfPosXMsg());

		if(pC.hasIfPosY()) pCConfig.set("ifPosY", pC.getIfPosY()); 
		else pCConfig.set("ifPosY", null);
		pCConfig.set("ifPosYMsg", pC.getIfPosYMsg());

		if(pC.hasIfPosZ()) pCConfig.set("ifPosZ", pC.getIfPosZ()); 
		else pCConfig.set("ifPosZ", null);
		pCConfig.set("ifPosZMsg", pC.getIfPosZMsg());
		
		if(pC.ifPlayerHasEffect.size() > 0) {
			List<String> result = new ArrayList<>();
			for(PotionEffectType pET : pC.ifPlayerHasEffect.keySet()) {
				result.add(pET.getName().toString()+":"+pC.ifPlayerHasEffect.get(pET));
			}
			pCConfig.set("ifPlayerHasEffect", result);
			pCConfig.set("ifPlayerHasEffectMsg", pC.getIfPlayerHasEffectMsg());
		}
		else pCConfig.set("ifPlayerHasEffect", null);

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

	public boolean isIfSneaking() {
		return ifSneaking;
	}

	public void setIfSneaking(boolean ifSneaking) {
		this.ifSneaking = ifSneaking;
	}

	public boolean hasIfSneaking() {
		return ifSneaking;
	}

	public boolean isIfNotSneaking() {
		return ifNotSneaking;
	}

	public void setIfNotSneaking(boolean ifNotSneaking) {
		this.ifNotSneaking = ifNotSneaking;
	}

	public boolean hasIfNotSneaking() {
		return ifNotSneaking;
	}

	public boolean isIfSwimming() {
		return ifSwimming;
	}

	public void setIfSwimming(boolean ifSwimming) {
		this.ifSwimming = ifSwimming;
	}

	public boolean hasIfSwimming() {
		return ifSwimming;
	}

	public boolean isIfGliding() {
		return ifGliding;
	}

	public void setIfGliding(boolean ifGliding) {
		this.ifGliding = ifGliding;
	}

	public boolean hasIfGliding() {
		return ifGliding;
	}

	public boolean isIfFlying() {
		return ifFlying;
	}

	public void setIfFlying(boolean ifFlying) {
		this.ifFlying = ifFlying;
	}

	public boolean hasIfFlying() {
		return ifFlying; 
	}

	public List<String> getIfInWorld() {
		return ifInWorld;
	}

	public void setIfInWorld(List<String> ifInWorld) {
		this.ifInWorld = ifInWorld;
	}

	public boolean hasIfInWorld() {
		return ifInWorld != null && ifInWorld.size()!=0;
	}

	public List<String> getIfNotInWorld() {
		return ifNotInWorld;
	}

	public void setIfNotInWorld(List<String> ifNotInWorld) {
		this.ifNotInWorld = ifNotInWorld;
	}

	public boolean hasIfNotInWorld() {
		return ifNotInWorld != null && ifNotInWorld.size()!=0;
	}

	public List<String> getIfInBiome() {
		return ifInBiome;
	}

	public void setIfInBiome(List<String> ifInBiome) {
		this.ifInBiome = ifInBiome;
	}

	public boolean hasIfInBiome() {
		return ifInBiome != null && ifInBiome.size()!=0;
	}

	public List<String> getIfNotInBiome() {
		return ifNotInBiome;
	}

	public void setIfNotInBiome(List<String> ifNotInBiome) {
		this.ifNotInBiome = ifNotInBiome;
	}

	public boolean hasIfNotInBiome() {
		return ifNotInBiome != null && ifNotInBiome.size()!=0;
	}

	public List<String> getIfInRegion() {
		return ifInRegion;
	}

	public void setIfInRegion(List<String> ifInRegion) {
		this.ifInRegion = ifInRegion;
	}

	public boolean hasIfInRegion() {
		return ifInRegion != null && ifInRegion.size()!=0;
	}

	public List<String> getIfNotInRegion() {
		return ifNotInRegion;
	}

	public void setIfNotInRegion(List<String> ifNotInRegion) {
		this.ifNotInRegion = ifNotInRegion;
	}

	public boolean hasIfNotInRegion() {
		return ifNotInRegion != null && ifNotInRegion.size()!=0;
	}

	public List<String> getIfHasPermission() {
		return ifHasPermission;
	}

	public void setIfHasPermission(List<String> ifHasPermission) {
		this.ifHasPermission = ifHasPermission;
	}

	public boolean hasIfHasPermission() {
		return ifHasPermission != null && ifHasPermission.size()!=0;
	}

	public List<String> getIfNotHasPermission() {
		return ifNotHasPermission;
	}

	public void setIfNotHasPermission(List<String> ifNotHasPermission) {
		this.ifNotHasPermission = ifNotHasPermission;
	}

	public boolean hasIfNotHasPermission() {
		return ifNotHasPermission != null && ifNotHasPermission.size()!=0;
	}

	public List<Material> getIfTargetBlock() {
		return ifTargetBlock;
	}
	public void setIfTargetBlock(List<Material> ifTargetBlock) {
		this.ifTargetBlock = ifTargetBlock;
	}
	public boolean hasIfTargetBlock() {
		return ifTargetBlock != null && ifTargetBlock.size()!=0;
	}

	public List<Material> getIfNotTargetBlock() {
		return ifNotTargetBlock;
	}
	public void setIfNotTargetBlock(List<Material> ifNotTargetBlock) {
		this.ifNotTargetBlock = ifNotTargetBlock;
	}
	public boolean hasIfNotTargetBlock() {
		return ifNotTargetBlock != null && ifNotTargetBlock.size()!=0;
	}

	public String getIfPlayerHealth() {
		return ifPlayerHealth;
	}

	public void setIfPlayerHealth(String ifPlayerHealth) {
		this.ifPlayerHealth = ifPlayerHealth;
	}

	public boolean hasIfPlayerHealth() {
		return  ifPlayerHealth != null && ifPlayerHealth.length()!=0;
	}

	public String getIfPlayerFoodLevel() {
		return ifPlayerFoodLevel;
	}

	public void setIfPlayerFoodLevel(String ifPlayerFoodLevel) {
		this.ifPlayerFoodLevel = ifPlayerFoodLevel;
	}

	public boolean hasIfPlayerFoodLevel() {
		return  ifPlayerFoodLevel != null && ifPlayerFoodLevel.length()!=0;
	}

	public String getIfPlayerEXP() {
		return ifPlayerEXP;
	}

	public void setIfPlayerEXP(String ifPlayerEXP) {
		this.ifPlayerEXP = ifPlayerEXP;
	}

	public boolean hasIfPlayerEXP() {
		return  ifPlayerEXP != null && ifPlayerEXP.length()!=0;
	}

	public String getIfPlayerLevel() {
		return ifPlayerLevel;
	}
	public void setIfPlayerLevel(String ifPlayerLevel) {
		this.ifPlayerLevel = ifPlayerLevel;
	}

	public boolean hasIfPlayerLevel() {
		return  ifPlayerLevel != null && ifPlayerLevel.length()!=0;
	}

	public boolean hasIfLightLevel() {
		return ifLightLevel != null && ifLightLevel.length()!=0;
	}

	public String getIfLightLevel() {
		return ifLightLevel;
	}

	public void setIfLightLevel(String x) {
		this.ifLightLevel = x;
	}

	public boolean hasIfPosX() {
		return ifPosX != null && ifPosX.length()!=0;
	}

	public String getIfPosX() {
		return ifPosX;
	}

	public void setIfPosX(String ifPosX) {
		this.ifPosX = ifPosX;
	}

	public boolean hasIfPosY() {
		return ifPosY != null && ifPosY.length()!=0;
	}

	public String getIfPosY() {
		return ifPosY;
	}

	public void setIfPosY(String ifPosY) {
		this.ifPosY = ifPosY;
	}

	public boolean hasIfPosZ() {
		return ifPosZ != null && ifPosZ.length()!=0;
	}

	public String getIfPosZ() {
		return ifPosZ;
	}

	public void setIfPosZ(String ifPosZ) {
		this.ifPosZ = ifPosZ;
	}

	public Map<String, Integer> getIfPlayerHasExecutableItem() {
		return ifPlayerHasExecutableItem;
	}
	public void setIfPlayerHasExecutableItem(Map<String, Integer> ifPlayerHasExecutableItem) {
		this.ifPlayerHasExecutableItem = ifPlayerHasExecutableItem;
	}
	public boolean hasIfPlayerHasExecutableItem() {
		return ifPlayerHasExecutableItem != null && !ifPlayerHasExecutableItem.isEmpty();
	}

	public Map<Material, Integer> getIfPlayerHasItem() {
		return ifPlayerHasItem;
	}
	public void setIfPlayerHasItem(Map<Material, Integer> ifPlayerHasItem) {
		this.ifPlayerHasItem = ifPlayerHasItem;
	}
	public boolean hasIfPlayerHasItem() {
		return ifPlayerHasItem != null && !ifPlayerHasItem.isEmpty();
	}







	public synchronized String getIfSneakingMsg() {
		return ifSneakingMsg;
	}

	public synchronized void setIfSneakingMsg(String ifSneakingMsg) {
		this.ifSneakingMsg = ifSneakingMsg;
	}

	public synchronized String getIfNotSneakingMsg() {
		return ifNotSneakingMsg;
	}

	public synchronized void setIfNotSneakingMsg(String ifNotSneakingMsg) {
		this.ifNotSneakingMsg = ifNotSneakingMsg;
	}

	public synchronized String getIfSwimmingMsg() {
		return ifSwimmingMsg;
	}

	public synchronized void setIfSwimmingMsg(String ifSwimmingMsg) {
		this.ifSwimmingMsg = ifSwimmingMsg;
	}

	public synchronized String getIfGlidingMsg() {
		return ifGlidingMsg;
	}

	public synchronized void setIfGlidingMsg(String ifGlidingMsg) {
		this.ifGlidingMsg = ifGlidingMsg;
	}

	public synchronized String getIfFlyingMsg() {
		return ifFlyingMsg;
	}

	public synchronized void setIfFlyingMsg(String ifFlyingMsg) {
		this.ifFlyingMsg = ifFlyingMsg;
	}

	public synchronized String getIfInWorldMsg() {
		return ifInWorldMsg;
	}

	public synchronized void setIfInWorldMsg(String ifInWorldMsg) {
		this.ifInWorldMsg = ifInWorldMsg;
	}

	public synchronized void setIfNotInWorldMsg(String ifNotInWorldMsg) {
		this.ifNotInWorldMsg = ifNotInWorldMsg;
	}

	public synchronized String getIfNotInWorldMsg() {
		return ifNotInWorldMsg;
	}

	public synchronized String getIfInBiomeMsg() {
		return ifInBiomeMsg;
	}

	public synchronized void setIfInBiomeMsg(String ifInBiomeMsg) {
		this.ifInBiomeMsg = ifInBiomeMsg;
	}

	public synchronized void setIfNotInBiomeMsg(String ifNotInBiomeMsg) {
		this.ifNotInBiomeMsg = ifNotInBiomeMsg;
	}

	public synchronized String getIfNotInBiomeMsg() {
		return ifNotInBiomeMsg;
	}

	public synchronized String getIfInRegionMsg() {
		return ifInRegionMsg;
	}

	public synchronized void setIfInRegionMsg(String ifInRegionMsg) {
		this.ifInRegionMsg = ifInRegionMsg;
	}

	public synchronized String getIfNotInRegionMsg() {
		return ifNotInRegionMsg;
	}

	public synchronized void setIfNotInRegionMsg(String ifNotInRegionMsg) {
		this.ifNotInRegionMsg = ifNotInRegionMsg;
	}

	public synchronized String getIfHasPermissionMsg() {
		return ifHasPermissionMsg;
	}

	public synchronized void setIfHasPermissionMsg(String ifHasPermissionMsg) {
		this.ifHasPermissionMsg = ifHasPermissionMsg;
	}

	public synchronized String getIfNotHasPermissionMsg() {
		return ifNotHasPermissionMsg;
	}

	public synchronized void setIfNotHasPermissionMsg(String ifNotHasPermissionMsg) {
		this.ifNotHasPermissionMsg = ifNotHasPermissionMsg;
	}

	public synchronized String getIfPlayerHealthMsg() {
		return ifPlayerHealthMsg;
	}

	public synchronized void setIfPlayerHealthMsg(String ifPlayerHealthMsg) {
		this.ifPlayerHealthMsg = ifPlayerHealthMsg;
	}

	public synchronized String getIfPlayerFoodLevelMsg() {
		return ifPlayerFoodLevelMsg;
	}

	public synchronized void setIfPlayerFoodLevelMsg(String ifPlayerFoodLevelMsg) {
		this.ifPlayerFoodLevelMsg = ifPlayerFoodLevelMsg;
	}

	public synchronized String getIfPlayerEXPMsg() {
		return ifPlayerEXPMsg;
	}

	public synchronized void setIfPlayerEXPMsg(String ifPlayerExpMsg) {
		this.ifPlayerEXPMsg = ifPlayerExpMsg;
	}

	public synchronized String getIfPlayerLevelMsg() {
		return ifPlayerLevelMsg;
	}

	public synchronized void setIfPlayerLevelMsg(String ifPlayerLevelMsg) {
		this.ifPlayerLevelMsg = ifPlayerLevelMsg;
	}


	public String getIfTargetBlockMsg() {
		return ifTargetBlockMsg;
	}


	public void setIfTargetBlockMsg(String ifTargetBlockMsg) {
		this.ifTargetBlockMsg = ifTargetBlockMsg;
	}


	public String getIfPosXMsg() {
		return ifPosXMsg;
	}


	public void setIfPosXMsg(String ifPosXMsg) {
		this.ifPosXMsg = ifPosXMsg;
	}


	public String getIfPosYMsg() {
		return ifPosYMsg;
	}


	public void setIfPosYMsg(String ifPosYMsg) {
		this.ifPosYMsg = ifPosYMsg;
	}


	public String getIfPosZMsg() {
		return ifPosZMsg;
	}


	public void setIfPosZMsg(String ifPosZMsg) {
		this.ifPosZMsg = ifPosZMsg;
	}


	public String getIfNotTargetBlockMsg() {
		return ifNotTargetBlockMsg;
	}


	public void setIfNotTargetBlockMsg(String ifNotTargetBlockMsg) {
		this.ifNotTargetBlockMsg = ifNotTargetBlockMsg;
	}


	public String getIfPlayerHasExecutableItemMsg() {
		return ifPlayerHasExecutableItemMsg;
	}


	public void setIfPlayerHasExecutableItemMsg(String ifPlayerHasExecutableItemMsg) {
		this.ifPlayerHasExecutableItemMsg = ifPlayerHasExecutableItemMsg;
	}


	public String getIfPlayerHasItemMsg() {
		return ifPlayerHasItemMsg;
	}


	public void setIfPlayerHasItemMsg(String ifPlayerHasItemMsg) {
		this.ifPlayerHasItemMsg = ifPlayerHasItemMsg;
	}

	public String getIfLightLevelMsg() {
		return ifLightLevelMsg;
	}

	public void setIfLightLevelMsg(String ifLightLevelMsg) {
		this.ifLightLevelMsg = ifLightLevelMsg;
	}

	public boolean isIfBlocking() {
		return ifBlocking;
	}

	public void setIfBlocking(boolean ifBlocking) {
		this.ifBlocking = ifBlocking;
	}

	public String getIfBlockingMsg() {
		return ifBlockingMsg;
	}

	public void setIfBlockingMsg(String ifBlockingMsg) {
		this.ifBlockingMsg = ifBlockingMsg;
	}

	public boolean isIfNotBlocking() {
		return ifNotBlocking;
	}

	public void setIfNotBlocking(boolean ifNotBlocking) {
		this.ifNotBlocking = ifNotBlocking;
	}

	public String getIfNotBlockingMsg() {
		return ifNotBlockingMsg;
	}

	public void setIfNotBlockingMsg(String ifNotBlockingMsg) {
		this.ifNotBlockingMsg = ifNotBlockingMsg;
	}

	public boolean isIfIsInTheAir() {
		return ifIsInTheAir;
	}

	public void setIfIsInTheAir(boolean ifIsInTheAir) {
		this.ifIsInTheAir = ifIsInTheAir;
	}

	public String getIfIsInTheAirMsg() {
		return ifIsInTheAirMsg;
	}

	public void setIfIsInTheAirMsg(String ifIsInTheAirMsg) {
		this.ifIsInTheAirMsg = ifIsInTheAirMsg;
	}

	public List<Material> getIfIsOnTheBlock() {
		return ifIsOnTheBlock;
	}

	public void setIfIsOnTheBlock(List<Material> ifIsOnTheBlock) {
		this.ifIsOnTheBlock = ifIsOnTheBlock;
	}

	public String getIfIsOnTheBlockMsg() {
		return ifIsOnTheBlockMsg;
	}

	public void setIfIsOnTheBlockMsg(String ifIsOnTheBlockMsg) {
		this.ifIsOnTheBlockMsg = ifIsOnTheBlockMsg;
	}

	public boolean isIfSprinting() {
		return ifSprinting;
	}

	public void setIfSprinting(boolean ifSprinting) {
		this.ifSprinting = ifSprinting;
	}

	public String getIfSprintingMsg() {
		return ifSprintingMsg;
	}

	public void setIfSprintingMsg(String ifSprintingMsg) {
		this.ifSprintingMsg = ifSprintingMsg;
	}

	public Map<PotionEffectType, Integer> getIfPlayerHasEffect() {
		return ifPlayerHasEffect;
	}

	public void setIfPlayerHasEffect(Map<PotionEffectType, Integer> ifPlayerHasEffect) {
		this.ifPlayerHasEffect = ifPlayerHasEffect;
	}

	public String getIfPlayerHasEffectMsg() {
		return ifPlayerHasEffectMsg;
	}

	public void setIfPlayerHasEffectMsg(String ifPlayerHasEffectMsg) {
		this.ifPlayerHasEffectMsg = ifPlayerHasEffectMsg;
	}

}
