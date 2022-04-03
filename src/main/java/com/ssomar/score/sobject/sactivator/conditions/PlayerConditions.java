package com.ssomar.score.sobject.sactivator.conditions;

import com.google.common.base.Charsets;
import com.ssomar.score.SCore;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.player.IfPlayerHasExecutableItem;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.WorldGuardAPI;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.messages.MessageDesign;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class PlayerConditions extends Conditions {

    // If the player must sneak to activate the activator
    private boolean ifSneaking;
    private static final String IF_SNEAKING_MSG = " &cYou must sneak to active the activator: &6%activator% &cof this item!";
    private String ifSneakingMsg;

    // If the player must not sneak to activate the activator
    private boolean ifNotSneaking;
    private static final String IF_NOT_SNEAKING_MSG = " &cYou must not sneak to active the activator: &6%activator% &cof this item!";
    private String ifNotSneakingMsg;

    // If the player must block to activate the activator
    private boolean ifBlocking;
    private static final String IF_BLOCKING_MSG = " &cYou must block damage with shield to active the activator: &6%activator% &cof this item!";
    private String ifBlockingMsg;

    // If the player must not block to activate the activator
    private boolean ifNotBlocking;
    private static final String IF_NOT_BLOCKING_MSG = " &cYou must not block damage with shield to active the activator: &6%activator% &cof this item!";
    private String ifNotBlockingMsg;

    // If the player must sprint to activate the activator
    private boolean ifSprinting;
    private static final String IF_SPRINTING_MSG = " &cYou must sprint to active the activator: &6%activator% &cof this item!";
    private String ifSprintingMsg;

    // If the player must swim to activate the activator
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

    private List<Material> ifIsNotOnTheBlock;
    private static final String IF_IS_NOT_ON_THE_BLOCK_MSG = " &cYou are not on the good type of block to active the activator: &6%activator% &cof this item!";
    private String ifIsNotOnTheBlockMsg;

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

    private List<IfPlayerHasExecutableItem> ifPlayerHasExecutableItem;
    private static final String IF_PLAYER_HAS_EXECUTABLE_ITEM_MSG = " &cYou don't have all correct ExecutableItems to active the activator: &6%activator% &cof this item!";
    private String ifPlayerHasExecutableItemMsg;

    private Map<Material, Integer> ifPlayerHasItem;
    private static final String IF_PLAYER_HAS_ITEM_MSG = " &cYou don't have all correct Items to active the activator: &6%activator% &cof this item!";
    private String ifPlayerHasItemMsg;

    private Map<PotionEffectType, Integer> ifPlayerHasEffect;
    private static final String IF_PLAYER_HAS_EFFECT_MSG = " &cYou don't have all correct effects to active the activator: &6%activator% &cof this item!";
    private String ifPlayerHasEffectMsg;

    private Map<PotionEffectType, Integer> ifPlayerHasEffectEquals;
    private String ifPlayerHasEffectEqualsMsg;

    private String ifCursorDistance;
    private static final String IF_CURSOR_DISTANCE_MSG = " &cCursor distance is not valid to active the activator: &6%activator% &cof this item!";
    private String ifCursorDistanceMsg;

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

        this.ifIsInTheAir = false;
        this.ifIsInTheAirMsg = IF_IS_IN_THE_AIR_MSG;

        this.ifIsOnTheBlock = new ArrayList<>();
        this.ifIsOnTheBlockMsg = IF_IS_ON_THE_BLOCK_MSG;

        this.ifIsNotOnTheBlock = new ArrayList<>();
        this.ifIsNotOnTheBlockMsg = IF_IS_NOT_ON_THE_BLOCK_MSG;

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
        this.ifPlayerFoodLevelMsg = IF_PLAYER_FOOD_LEVEL_MSG;

        this.ifPlayerEXP = "";
        this.ifPlayerEXPMsg = IF_PLAYER_EXP_MSG;

        this.ifPlayerLevel = "";
        this.ifPlayerLevelMsg = IF_PLAYER_LEVEL_MSG;

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

        this.ifPlayerHasExecutableItem = new ArrayList<>();
        this.ifPlayerHasExecutableItemMsg = IF_PLAYER_HAS_EXECUTABLE_ITEM_MSG;

        this.ifPlayerHasEffect = new HashMap<>();
        this.ifPlayerHasEffectMsg = IF_PLAYER_HAS_EFFECT_MSG;

        this.ifPlayerHasEffectEquals = new HashMap<>();
        this.ifPlayerHasEffectEqualsMsg = IF_PLAYER_HAS_EFFECT_MSG;

        this.ifCursorDistance = "";
        this.ifCursorDistanceMsg = IF_CURSOR_DISTANCE_MSG;
    }

    public boolean verifConditions(Player p, Player toMsg) {

        if (this.hasIfHasPermission()) {
            boolean valid = true;
            for (String perm : this.getIfHasPermission()) {
                if (!p.hasPermission(perm)) {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                this.getSm().sendMessage(toMsg, this.getIfHasPermissionMsg());
                return false;
            }
        }

        if (this.hasIfNotHasPermission()) {
            for (String perm : this.getIfNotHasPermission()) {
                if (p.hasPermission(perm)) {
                    this.getSm().sendMessage(toMsg, this.getIfNotHasPermissionMsg());
                    return false;
                }
            }
        }

        if (this.isIfSneaking() && ifSneaking && !p.isSneaking()) {
            this.getSm().sendMessage(toMsg, this.getIfSneakingMsg());
            return false;
        }

        if (this.isIfNotSneaking() && ifNotSneaking && p.isSneaking()) {
            this.getSm().sendMessage(toMsg, this.getIfNotSneakingMsg());
            return false;
        }

        if (ifBlocking && !p.isBlocking()) {
            this.getSm().sendMessage(toMsg, this.getIfBlockingMsg());
            return false;
        }

        if (ifNotBlocking && p.isBlocking()) {
            this.getSm().sendMessage(toMsg, this.getIfNotBlockingMsg());
            return false;
        }

        if (this.isIfSwimming() && ifSwimming && !p.isSwimming()) {
            this.getSm().sendMessage(toMsg, (this.getIfSwimmingMsg()));
            return false;
        }

        if (this.ifSprinting && !p.isSprinting()) {
            this.getSm().sendMessage(toMsg, (this.getIfSprintingMsg()));
            return false;
        }

        if (this.isIfGliding() && ifGliding && !p.isGliding()) {
            this.getSm().sendMessage(toMsg, this.getIfGlidingMsg());
            return false;
        }

        if (this.isIfFlying() && ifFlying && !p.isFlying()) {
            this.getSm().sendMessage(toMsg, this.getIfFlyingMsg());
            return false;
        }

        if (this.ifIsInTheAir || this.ifIsOnTheBlock.size() != 0 || this.ifIsNotOnTheBlock.size() != 0) {
            Location pLoc = p.getLocation();
            pLoc.subtract(0, 0.1, 0);

            Block block = pLoc.getBlock();
            Material type = block.getType();
            if (!type.equals(Material.AIR) && this.ifIsInTheAir) {
                this.getSm().sendMessage(toMsg, this.getIfIsInTheAirMsg());
                return false;
            }

            if (this.ifIsOnTheBlock.size() != 0 && !ifIsOnTheBlock.contains(type)) {
                this.getSm().sendMessage(toMsg, this.getIfIsOnTheBlockMsg());
                return false;
            }

            if (this.ifIsNotOnTheBlock.size() != 0 && ifIsNotOnTheBlock.contains(type)) {
                this.getSm().sendMessage(toMsg, this.getIfIsNotOnTheBlockMsg());
                return false;
            }
        }

        if (this.hasIfInWorld()) {
            boolean notValid = true;
            for (String s : this.ifInWorld) {
                if (p.getWorld().getName().equalsIgnoreCase(s)) {
                    notValid = false;
                    break;
                }
            }
            if (notValid) {
                this.getSm().sendMessage(toMsg, this.getIfInWorldMsg());
                return false;
            }
        }

        if (this.hasIfNotInWorld()) {
            boolean notValid = false;
            for (String s : this.ifNotInWorld) {
                if (p.getWorld().getName().equalsIgnoreCase(s)) {
                    notValid = true;
                    break;
                }
            }
            if (notValid) {
                this.getSm().sendMessage(toMsg, this.getIfNotInWorldMsg());
                return false;
            }
        }

        if (this.hasIfInBiome()) {
            boolean notValid = true;
            for (String s : this.ifInBiome) {
                if (p.getLocation().getBlock().getBiome().toString().equalsIgnoreCase(s)) {
                    notValid = false;
                    break;
                }
            }
            if (notValid) {
                this.getSm().sendMessage(toMsg, this.getIfInBiomeMsg());
                return false;
            }
        }

        if (this.hasIfNotInBiome()) {
            boolean notValid = false;
            for (String s : this.ifNotInBiome) {
                if (p.getLocation().getBlock().getBiome().toString().equalsIgnoreCase(s)) {
                    notValid = true;
                    break;
                }
            }
            if (notValid) {
                this.getSm().sendMessage(toMsg, this.getIfNotInBiomeMsg());
                return false;
            }
        }

        if (SCore.hasWorldGuard) {
            if (this.hasIfInRegion() && !new WorldGuardAPI().isInRegion(p, this.ifInRegion)) {
                this.getSm().sendMessage(toMsg, this.getIfInRegionMsg());
                return false;
            }

            if (this.hasIfNotInRegion() && new WorldGuardAPI().isInRegion(p, this.ifNotInRegion)) {
                this.getSm().sendMessage(toMsg, this.getIfNotInRegionMsg());
                return false;
            }
        }

        if (this.hasIfTargetBlock()) {
            Block block = p.getTargetBlock(null, 5);
            /* take only the fix block, not hte falling block */
            if ((block.getType().equals(Material.WATER) || block.getType().equals(Material.LAVA)) && !block.getBlockData().getAsString().contains("level=0")) {
                this.getSm().sendMessage(toMsg, this.getIfTargetBlockMsg());
                return false;
            }
            if (!this.getIfTargetBlock().contains(block.getType())) {
                this.getSm().sendMessage(toMsg, this.getIfTargetBlockMsg());
                return false;
            }
        }

        if (this.hasIfNotTargetBlock()) {
            Block block = p.getTargetBlock(null, 5);
            /* take only the fix block, not hte falling block */
            if ((block.getType().equals(Material.WATER) || block.getType().equals(Material.LAVA)) && !block.getBlockData().getAsString().contains("level=0")) {
                this.getSm().sendMessage(toMsg, this.getIfNotTargetBlockMsg());
                return false;
            }
            if (this.getIfNotTargetBlock().contains(block.getType())) {
                this.getSm().sendMessage(toMsg, this.getIfNotTargetBlockMsg());
                return false;
            }
        }

        if (this.hasIfPlayerHealth() && !StringCalculation.calculation(this.ifPlayerHealth, p.getHealth())) {
            this.getSm().sendMessage(toMsg, this.getIfPlayerHealthMsg());
            return false;
        }

        if (this.hasIfPlayerFoodLevel() && !StringCalculation.calculation(this.ifPlayerFoodLevel, p.getFoodLevel())) {
            this.getSm().sendMessage(toMsg, this.getIfPlayerFoodLevelMsg());
            return false;
        }

        if (this.hasIfPlayerEXP() && !StringCalculation.calculation(this.ifPlayerEXP, p.getTotalExperience())) {
            this.getSm().sendMessage(toMsg, this.getIfPlayerEXPMsg());
            return false;
        }

        if (this.hasIfPlayerLevel() && !StringCalculation.calculation(this.ifPlayerLevel, p.getLevel())) {
            this.getSm().sendMessage(toMsg, this.getIfPlayerLevelMsg());
            return false;
        }

        if (this.hasIfLightLevel() && !StringCalculation.calculation(this.ifLightLevel, p.getEyeLocation().getBlock().getLightLevel())) {
            this.getSm().sendMessage(toMsg, this.getIfLightLevelMsg());
            return false;
        }

        if (this.hasIfPosX() && !StringCalculation.calculation(this.ifPosX, p.getLocation().getX())) {
            this.getSm().sendMessage(toMsg, this.getIfPosXMsg());
            return false;
        }

        if (this.hasIfPosY() && !StringCalculation.calculation(this.ifPosY, p.getLocation().getY())) {
            this.getSm().sendMessage(toMsg, this.getIfPosYMsg());
            return false;
        }

        if (this.hasIfPosZ() && !StringCalculation.calculation(this.ifPosZ, p.getLocation().getZ())) {
            this.getSm().sendMessage(toMsg, this.getIfPosZMsg());
            return false;
        }

        if (this.hasIfPlayerHasExecutableItem() || this.hasIfPlayerHasItem()) {
            ItemStack[] content = p.getInventory().getContents();

            Map<Material, Integer> verifI = new HashMap<>(this.getIfPlayerHasItem());

            int cpt = -1;
            for (ItemStack is : content) {
                cpt++;
                if (is == null) continue;

                if (verifI.containsKey(is.getType()) && ((verifI.get(is.getType()) == cpt) || verifI.get(is.getType()) == -1 && cpt == p.getInventory().getHeldItemSlot())) {
                    verifI.remove(is.getType());
                }
            }

            if (!verifI.isEmpty()) {
                this.getSm().sendMessage(toMsg, this.getIfPlayerHasItemMsg());
                return false;
            }
        }

        if (this.hasIfPlayerHasExecutableItem()) {
            if (SCore.hasExecutableItems) {
                for (IfPlayerHasExecutableItem cdt : this.ifPlayerHasExecutableItem) {
                    if (!cdt.verify(p)) {
                        this.getSm().sendMessage(toMsg, this.getIfPlayerHasExecutableItemMsg());
                        return false;
                    }
                }
            }
        }

        if (this.ifPlayerHasEffect.size() > 0) {
            for (PotionEffectType pET : ifPlayerHasEffect.keySet()) {
                if (!p.hasPotionEffect(pET)) {
                    this.getSm().sendMessage(toMsg, this.getIfPlayerHasEffectMsg());
                    return false;
                } else {
                    if (p.getPotionEffect(pET).getAmplifier() < ifPlayerHasEffect.get(pET)) {
                        this.getSm().sendMessage(toMsg, this.getIfPlayerHasEffectMsg());
                        return false;
                    }
                }
            }
        }

        if (this.ifPlayerHasEffectEquals.size() > 0) {
            for (PotionEffectType pET : ifPlayerHasEffectEquals.keySet()) {
                if (!p.hasPotionEffect(pET)) {
                    this.getSm().sendMessage(toMsg, this.getIfPlayerHasEffectEqualsMsg());
                    return false;
                } else {
                    if (p.getPotionEffect(pET).getAmplifier() != ifPlayerHasEffectEquals.get(pET)) {
                        this.getSm().sendMessage(toMsg, this.getIfPlayerHasEffectEqualsMsg());
                        return false;
                    }
                }
            }
        }

        if (this.hasIfCursorDistance()) {
            Block block = p.getTargetBlock(null, 200);
            if(block.getType().equals(Material.AIR)) return false;

            if (!StringCalculation.calculation(this.ifCursorDistance, p.getLocation().distance(block.getLocation()))) {
                this.getSm().sendMessage(toMsg, this.getIfCursorDistanceMsg());
                return false;
            }
        }

        return true;
    }

    public static PlayerConditions getPlayerConditions(ConfigurationSection playerCdtSection, List<String> errorList, String pluginName) {

        PlayerConditions pCdt = new PlayerConditions();

        pCdt.setIfSneaking(playerCdtSection.getBoolean("ifSneaking", false));
        pCdt.setIfSneakingMsg(playerCdtSection.getString("ifSneakingMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_SNEAKING_MSG));

        pCdt.setIfNotSneaking(playerCdtSection.getBoolean("ifNotSneaking", false));
        pCdt.setIfNotSneakingMsg(playerCdtSection.getString("ifNotSneakingMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_NOT_SNEAKING_MSG));

        pCdt.setIfBlocking(playerCdtSection.getBoolean("ifBlocking", false));
        pCdt.setIfBlockingMsg(playerCdtSection.getString("ifBlockingMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_BLOCKING_MSG));

        pCdt.setIfNotBlocking(playerCdtSection.getBoolean("ifNotBlocking", false));
        pCdt.setIfNotBlockingMsg(playerCdtSection.getString("ifNotBlockingMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_NOT_BLOCKING_MSG));

        pCdt.setIfSprinting(playerCdtSection.getBoolean("ifSprinting", false));
        pCdt.setIfSprintingMsg(playerCdtSection.getString("ifSprintingMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_SPRINTING_MSG));

        pCdt.setIfSwimming(playerCdtSection.getBoolean("ifSwimming", false));
        pCdt.setIfSwimmingMsg(playerCdtSection.getString("ifSwimmingMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_SWIMMING_MSG));

        pCdt.setIfGliding(playerCdtSection.getBoolean("ifGliding", false));
        pCdt.setIfGlidingMsg(playerCdtSection.getString("ifGlidingMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_GLIDING_MSG));

        pCdt.setIfFlying(playerCdtSection.getBoolean("ifFlying", false));
        pCdt.setIfFlyingMsg(playerCdtSection.getString("ifFlyingMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_FLYING_MSG));

        pCdt.setIfIsInTheAir(playerCdtSection.getBoolean("ifIsInTheAir", false));
        pCdt.setIfIsInTheAirMsg(playerCdtSection.getString("ifIsInTheAirMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_IS_IN_THE_AIR_MSG));

        List<Material> mat = new ArrayList<>();
        for (String s : playerCdtSection.getStringList("ifIsOnTheBlock")) {
            try {
                mat.add(Material.valueOf(s.toUpperCase()));
            } catch (Exception ignored) {
            }
        }
        pCdt.setIfIsOnTheBlock(mat);
        pCdt.setIfIsOnTheBlockMsg(playerCdtSection.getString("ifIsOnTheBlockMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_IS_ON_THE_BLOCK_MSG));

        List<Material> mat2 = new ArrayList<>();
        for (String s : playerCdtSection.getStringList("ifIsNotOnTheBlock")) {
            try {
                mat2.add(Material.valueOf(s.toUpperCase()));
            } catch (Exception ignored) {
            }
        }
        pCdt.setIfIsNotOnTheBlock(mat2);
        pCdt.setIfIsNotOnTheBlockMsg(playerCdtSection.getString("ifIsNotOnTheBlockMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_IS_NOT_ON_THE_BLOCK_MSG));


        pCdt.setIfInWorld(playerCdtSection.getStringList("ifInWorld"));
        pCdt.setIfInWorldMsg(playerCdtSection.getString("ifInWorldMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_IN_WORLD_MSG));

        pCdt.setIfNotInWorld(playerCdtSection.getStringList("ifNotInWorld"));
        pCdt.setIfNotInWorldMsg(playerCdtSection.getString("ifNotInWorldMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_NOT_IN_WORLD_MSG));

        pCdt.setIfInBiome(playerCdtSection.getStringList("ifInBiome"));
        pCdt.setIfInBiomeMsg(playerCdtSection.getString("ifInBiomeMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_IN_BIOME_MSG));

        pCdt.setIfNotInBiome(playerCdtSection.getStringList("ifNotInBiome"));
        pCdt.setIfNotInBiomeMsg(playerCdtSection.getString("ifNotInBiomeMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_NOT_IN_BIOME_MSG));

        if (playerCdtSection.contains("ifInRegion") || playerCdtSection.contains("ifNotInRegion")) {

            if (SCore.is1v12()) {
                errorList.add(pluginName + " Error the conditions ifInRegion and ifNotInRegion are not available in 1.12 due to a changement of worldguard API ");
            } else {
                pCdt.setIfInRegion(playerCdtSection.getStringList("ifInRegion"));

                pCdt.setIfNotInRegion(playerCdtSection.getStringList("ifNotInRegion"));
            }
        }
        pCdt.setIfInRegionMsg(playerCdtSection.getString("ifInRegionMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_IN_REGION_MSG));
        pCdt.setIfNotInRegionMsg(playerCdtSection.getString("ifNotInRegionMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_NOT_IN_REGION_MSG));

        pCdt.setIfHasPermission(playerCdtSection.getStringList("ifHasPermission"));
        pCdt.setIfHasPermissionMsg(playerCdtSection.getString("ifHasPermissionMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_HAS_PERMISSION_MSG));

        pCdt.setIfNotHasPermission(playerCdtSection.getStringList("ifNotHasPermission"));
        pCdt.setIfNotHasPermissionMsg(playerCdtSection.getString("ifNotHasPermissionMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_NOT_HAS_PERMISSION_MSG));

        mat = new ArrayList<>();
        for (String s : playerCdtSection.getStringList("ifTargetBlock")) {
            try {
                mat.add(Material.valueOf(s.toUpperCase()));
            } catch (Exception ignored) {
            }
        }
        pCdt.setIfTargetBlock(mat);
        pCdt.setIfTargetBlockMsg(playerCdtSection.getString("ifTargetBlockMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_TARGET_BLOCK_MSG));

        mat = new ArrayList<>();
        for (String s : playerCdtSection.getStringList("ifNotTargetBlock")) {
            try {
                mat.add(Material.valueOf(s.toUpperCase()));
            } catch (Exception ignored) {
            }
        }
        pCdt.setIfNotTargetBlock(mat);
        pCdt.setIfNotTargetBlockMsg(playerCdtSection.getString("ifNotTargetBlockMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_NOT_TARGET_BLOCK_MSG));

        pCdt.setIfPlayerHealth(playerCdtSection.getString("ifPlayerHealth", ""));
        pCdt.setIfPlayerHealthMsg(playerCdtSection.getString("ifPlayerHealthMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_PLAYER_HEALTH_MSG));

        pCdt.setIfPlayerFoodLevel(playerCdtSection.getString("ifPlayerFoodLevel", ""));
        pCdt.setIfPlayerFoodLevelMsg(playerCdtSection.getString("ifPlayerFoodLevelMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_PLAYER_FOOD_LEVEL_MSG));

        pCdt.setIfPlayerEXP(playerCdtSection.getString("ifPlayerEXP", ""));
        pCdt.setIfPlayerEXPMsg(playerCdtSection.getString("ifPlayerEXPMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_PLAYER_EXP_MSG));

        pCdt.setIfPlayerLevel(playerCdtSection.getString("ifPlayerLevel", ""));
        pCdt.setIfPlayerLevelMsg(playerCdtSection.getString("ifPlayerLevelMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_PLAYER_LEVEL_MSG));

        pCdt.setIfLightLevel(playerCdtSection.getString("ifLightLevel", ""));
        pCdt.setIfLightLevelMsg(playerCdtSection.getString("ifLightLevelMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_LIGHT_LEVEL_MSG));

        pCdt.setIfPosX(playerCdtSection.getString("ifPosX", ""));
        pCdt.setIfPosXMsg(playerCdtSection.getString("ifPosXMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_POS_X_MSG));

        pCdt.setIfPosY(playerCdtSection.getString("ifPosY", ""));
        pCdt.setIfPosYMsg(playerCdtSection.getString("ifPosYMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_POS_Y_MSG));

        pCdt.setIfPosZ(playerCdtSection.getString("ifPosZ", ""));
        pCdt.setIfPosZMsg(playerCdtSection.getString("ifPosZMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_POS_Z_MSG));

        List<IfPlayerHasExecutableItem> verifEI = new ArrayList<>();
        if (playerCdtSection.contains("ifPlayerHasExecutableItem")) {
            ConfigurationSection sec = playerCdtSection.getConfigurationSection("ifPlayerHasExecutableItem");
            if (sec != null && sec.getKeys(false).size() > 0) {
                for (String s : sec.getKeys(false)) {
                    IfPlayerHasExecutableItem cdt = new IfPlayerHasExecutableItem(sec.getConfigurationSection(s));
                    if (cdt.isValid())
                        verifEI.add(cdt);
                    else
                        errorList.add(pluginName + " Invalid configuration of ifPlayerHasExecutableItems with id : " + s + " !");
                }
            } else if (playerCdtSection.getStringList("ifPlayerHasExecutableItem").size() > 0) {
                for (String s : playerCdtSection.getStringList("ifPlayerHasExecutableItem")) {
                    IfPlayerHasExecutableItem cdt = new IfPlayerHasExecutableItem(s);
                    if (cdt.isValid())
                        verifEI.add(cdt);
                    else
                        errorList.add(pluginName + " Invalid configuration of ifPlayerHasExecutableItems: " + s + " !");
                }
            }
        }

        pCdt.setIfPlayerHasExecutableItem(verifEI);
        pCdt.setIfPlayerHasExecutableItemMsg(playerCdtSection.getString("ifPlayerHasExecutableItemMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_PLAYER_HAS_EXECUTABLE_ITEM_MSG));

        Map<Material, Integer> verifI = new HashMap<>();
        for (String s : playerCdtSection.getStringList("ifPlayerHasItem")) {
            String[] spliter;
            if (s.contains(":") && (spliter = s.split(":")).length == 2) {
                int slot = 0;
                Material material = null;
                try {
                    material = Material.valueOf(spliter[0]);
                } catch (Exception e) {
                    errorList.add(pluginName + " Invalid argument for the ifPlayerHasItem condition: " + s + " correct form > MATERIAL:SLOT  example> DIAMOND:5 !");
                    continue;
                }
                try {
                    slot = Integer.parseInt(spliter[1]);
                } catch (Exception e) {
                    errorList.add(pluginName + " Invalid argument for the ifPlayerHasItem condition: " + s + " correct form > MATERIAL:SLOT  example> DIAMOND:5 !");
                    continue;
                }
                verifI.put(material, slot);
            }
        }
        pCdt.setIfPlayerHasItem(verifI);
        pCdt.setIfPlayerHasItemMsg(playerCdtSection.getString("ifPlayerHasItemMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_PLAYER_HAS_ITEM_MSG));

        Map<PotionEffectType, Integer> verifETP = new HashMap<>();
        for (String s : playerCdtSection.getStringList("ifPlayerHasEffect")) {
            String[] spliter;
            if (s.contains(":") && (spliter = s.split(":")).length == 2) {
                int value = 0;
                PotionEffectType type = PotionEffectType.getByName(spliter[0]);
                if (type == null) {
                    errorList.add(pluginName + " Invalid argument for the ifPlayerHasEffect condition: " + s + " correct form > EFFECT:MINIMUM_AMPLIFIER_REQUIRED  example> SPEED:0 !");
                    continue;
                }

                try {
                    value = Integer.parseInt(spliter[1]);
                } catch (Exception e) {
                    errorList.add(pluginName + " Invalid argument for the ifPlayerHasEffect condition: " + s + " correct form > EFFECT:MINIMUM_AMPLIFIER_REQUIRED  example> SPEED:0 !");
                    continue;
                }
                verifETP.put(type, value);
            }
        }

        pCdt.setIfPlayerHasEffect(verifETP);
        pCdt.setIfPlayerHasEffectMsg(playerCdtSection.getString("ifPlayerHasEffectMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_PLAYER_HAS_EFFECT_MSG));

        Map<PotionEffectType, Integer> verifETPE = new HashMap<>();
        for (String s : playerCdtSection.getStringList("ifPlayerHasEffectEquals")) {
            String[] spliter;
            if (s.contains(":") && (spliter = s.split(":")).length == 2) {
                int value = 0;
                PotionEffectType type = PotionEffectType.getByName(spliter[0]);
                if (type == null) {
                    errorList.add(pluginName + " Invalid argument for the ifPlayerHasEffectEquals condition: " + s + " correct form > EFFECT:AMPLIFIER_REQUIRED  example> SPEED:0 !");
                    continue;
                }

                try {
                    value = Integer.parseInt(spliter[1]);
                } catch (Exception e) {
                    errorList.add(pluginName + " Invalid argument for the ifPlayerHasEffectEquals condition: " + s + " correct form > EFFECT:AMPLIFIER_REQUIRED  example> SPEED:0 !");
                    continue;
                }
                verifETPE.put(type, value);
            }
        }

        pCdt.setIfPlayerHasEffectEquals(verifETPE);
        pCdt.setIfPlayerHasEffectEqualsMsg(playerCdtSection.getString("ifPlayerHasEffectEqualsMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_PLAYER_HAS_EFFECT_MSG));

        pCdt.setIfCursorDistance(playerCdtSection.getString("ifCursorDistance", ""));
        pCdt.setIfCursorDistanceMsg(playerCdtSection.getString("ifCursorDistanceMsg", MessageDesign.ERROR_CODE_FIRST + pluginName + IF_CURSOR_DISTANCE_MSG));

        return pCdt;

    }

    /*
     *  @param sPlugin The plugin of the conditions
     *  @param sObject The object
     *  @param sActivator The activator that contains the conditions
     *  @param pC the player conditions object
     */
    public static void savePlayerConditions(SPlugin sPlugin, SObject sObject, SActivator sActivator, PlayerConditions pC, String detail) {

        if (!new File(sObject.getPath()).exists()) {
            sPlugin.getPlugin().getLogger().severe(sPlugin.getNameDesign() + " Error can't find the file in the folder ! (" + sObject.getId() + ".yml)");
            return;
        }
        File file = new File(sObject.getPath());
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection activatorConfig = config.getConfigurationSection("activators." + sActivator.getID());
        activatorConfig.set("conditions." + detail + ".ifSneaking", false);

        String pluginName = sPlugin.getNameDesign();


        ConfigurationSection pCConfig = config.getConfigurationSection("activators." + sActivator.getID() + ".conditions." + detail);

        if (pC.isIfSneaking()) pCConfig.set("ifSneaking", true);
        else pCConfig.set("ifSneaking", null);
        if (pC.getIfSneakingMsg().contains(pC.IF_SNEAKING_MSG)) pCConfig.set("ifSneakingMsg", null);
        else pCConfig.set("ifSneakingMsg", pC.getIfSneakingMsg());

        if (pC.isIfNotSneaking()) pCConfig.set("ifNotSneaking", true);
        else pCConfig.set("ifNotSneaking", null);
        if (pC.getIfNotSneakingMsg().contains(pC.IF_NOT_SNEAKING_MSG)) pCConfig.set("ifNotSneakingMsg", null);
        else pCConfig.set("ifNotSneakingMsg", pC.getIfNotSneakingMsg());

        if (pC.ifBlocking) pCConfig.set("ifBlocking", true);
        else pCConfig.set("ifBlocking", null);
        if (pC.getIfBlockingMsg().contains(pC.IF_BLOCKING_MSG)) pCConfig.set("ifBlockingMsg", null);
        else pCConfig.set("ifBlockingMsg", pC.getIfBlockingMsg());

        if (pC.ifNotBlocking) pCConfig.set("ifNotBlocking", true);
        else pCConfig.set("ifNotBlocking", null);
        if (pC.getIfNotBlockingMsg().contains(pC.IF_NOT_BLOCKING_MSG)) pCConfig.set("ifNotBlockingMsg", null);
        else pCConfig.set("ifNotBlockingMsg", pC.getIfNotBlockingMsg());

        if (pC.isIfSwimming()) pCConfig.set("ifSwimming", true);
        else pCConfig.set("ifSwimming", null);
        if (pC.getIfSwimmingMsg().contains(pC.IF_SWIMMING_MSG)) pCConfig.set("ifSwimmingMsg", null);
        else pCConfig.set("ifSwimmingMsg", pC.getIfSwimmingMsg());

        if (pC.ifSprinting) pCConfig.set("ifSprinting", true);
        else pCConfig.set("ifSprinting", null);
        if (pC.getIfSprintingMsg().contains(pC.IF_SPRINTING_MSG)) pCConfig.set("ifSprintingMsg", null);
        else pCConfig.set("ifSprintingMsg", pC.getIfSprintingMsg());

        if (pC.isIfGliding()) pCConfig.set("ifGliding", true);
        else pCConfig.set("ifGliding", null);
        if (pC.getIfGlidingMsg().contains(pC.IF_GLIDING_MSG)) pCConfig.set("ifGlidingMsg", null);
        else pCConfig.set("ifGlidingMsg", pC.getIfGlidingMsg());

        if (pC.isIfFlying()) pCConfig.set("ifFlying", true);
        else pCConfig.set("ifFlying", null);
        if (pC.getIfFlyingMsg().contains(pC.IF_FLYING_MSG)) pCConfig.set("ifFlyingMsg", null);
        else pCConfig.set("ifFlyingMsg", pC.getIfFlyingMsg());

        if (pC.ifIsInTheAir) pCConfig.set("ifIsInTheAir", true);
        else pCConfig.set("ifIsInTheAir", null);
        if (pC.getIfIsInTheAirMsg().contains(pC.IF_IS_IN_THE_AIR_MSG)) pCConfig.set("ifIsInTheAirMsg", null);
        else pCConfig.set("ifIsInTheAirMsg", pC.getIfIsInTheAirMsg());

        List<String> convert = new ArrayList<>();
        for (Material mat : pC.getIfIsOnTheBlock()) {
            convert.add(mat.toString());
        }
        if (pC.getIfIsOnTheBlock().size() != 0) pCConfig.set("ifIsOnTheBlock", convert);
        else pCConfig.set("ifIsOnTheBlock", null);
        if (pC.getIfIsOnTheBlockMsg().contains(pC.IF_IS_ON_THE_BLOCK_MSG)) pCConfig.set("ifIsOnTheBlockMsg", null);
        else pCConfig.set("ifIsOnTheBlockMsg", pC.getIfIsOnTheBlockMsg());

        List<String> convert2 = new ArrayList<>();
        for (Material mat : pC.getIfIsNotOnTheBlock()) {
            convert2.add(mat.toString());
        }
        if (pC.getIfIsNotOnTheBlock().size() != 0) pCConfig.set("ifIsNotOnTheBlock", convert2);
        else pCConfig.set("ifIsNotOnTheBlock", null);
        if (pC.getIfIsNotOnTheBlockMsg().contains(pC.IF_IS_NOT_ON_THE_BLOCK_MSG))
            pCConfig.set("ifIsNotOnTheBlockMsg", null);
        else pCConfig.set("ifIsNotOnTheBlockMsg", pC.getIfIsNotOnTheBlockMsg());

        if (pC.hasIfInWorld()) pCConfig.set("ifInWorld", pC.getIfInWorld());
        else pCConfig.set("ifInWorld", null);
        if (pC.getIfInWorldMsg().contains(pC.IF_IN_WORLD_MSG)) pCConfig.set("ifInWorldMsg", null);
        else pCConfig.set("ifInWorldMsg", pC.getIfInWorldMsg());

        if (pC.hasIfNotInWorld()) pCConfig.set("ifNotInWorld", pC.getIfNotInWorld());
        else pCConfig.set("ifNotInWorld", null);
        if (pC.getIfNotInWorldMsg().contains(pC.IF_NOT_IN_WORLD_MSG)) pCConfig.set("ifNotInWorldMsg", null);
        else pCConfig.set("ifNotInWorldMsg", pC.getIfNotInWorldMsg());

        if (pC.hasIfInBiome()) pCConfig.set("ifInBiome", pC.getIfInBiome());
        else pCConfig.set("ifInBiome", null);
        if (pC.getIfInBiomeMsg().contains(pC.IF_IN_BIOME_MSG)) pCConfig.set("ifInBiomeMsg", null);
        else pCConfig.set("ifInBiomeMsg", pC.getIfInBiomeMsg());

        if (pC.hasIfNotInBiome()) pCConfig.set("ifNotInBiome", pC.getIfNotInBiome());
        else pCConfig.set("ifNotInBiome", null);
        if (pC.getIfNotInBiomeMsg().contains(pC.IF_NOT_IN_BIOME_MSG)) pCConfig.set("ifNotInBiomeMsg", null);
        else pCConfig.set("ifNotInBiomeMsg", pC.getIfNotInBiomeMsg());

        if (pC.hasIfInRegion()) pCConfig.set("ifInRegion", pC.getIfInRegion());
        else pCConfig.set("ifInRegion", null);
        if (pC.getIfInRegionMsg().contains(pC.IF_IN_REGION_MSG)) pCConfig.set("ifInRegionMsg", null);
        else pCConfig.set("ifInRegionMsg", pC.getIfInRegionMsg());

        if (pC.hasIfNotInRegion()) pCConfig.set("ifNotInRegion", pC.getIfNotInRegion());
        else pCConfig.set("ifNotInRegion", null);
        if (pC.getIfNotInRegionMsg().contains(pC.IF_NOT_IN_REGION_MSG)) pCConfig.set("ifNotInRegionMsg", null);
        else pCConfig.set("ifNotInRegionMsg", pC.getIfNotInRegionMsg());

        if (pC.hasIfHasPermission()) pCConfig.set("ifHasPermission", pC.getIfHasPermission());
        else pCConfig.set("ifHasPermission", null);
        if (pC.getIfHasPermissionMsg().contains(pC.IF_HAS_PERMISSION_MSG)) pCConfig.set("ifHasPermissionMsg", null);
        else pCConfig.set("ifHasPermissionMsg", pC.getIfHasPermissionMsg());

        if (pC.hasIfNotHasPermission()) pCConfig.set("ifNotHasPermission", pC.getIfNotHasPermission());
        else pCConfig.set("ifNotHasPermission", null);
        if (pC.getIfNotHasPermissionMsg().contains(pC.IF_NOT_HAS_PERMISSION_MSG))
            pCConfig.set("ifNotHasPermissionMsg", null);
        else pCConfig.set("ifNotHasPermissionMsg", pC.getIfNotHasPermissionMsg());

        convert = new ArrayList<>();
        for (Material mat : pC.getIfTargetBlock()) {
            convert.add(mat.toString());
        }
        if (pC.hasIfTargetBlock()) pCConfig.set("ifTargetBlock", convert);
        else pCConfig.set("ifTargetBlock", null);
        if (pC.getIfTargetBlockMsg().contains(pC.IF_TARGET_BLOCK_MSG)) pCConfig.set("ifTargetBlockMsg", null);
        else pCConfig.set("ifTargetBlockMsg", pC.getIfTargetBlockMsg());

        convert = new ArrayList<>();
        for (Material mat : pC.getIfNotTargetBlock()) {
            convert.add(mat.toString());
        }
        if (pC.hasIfNotTargetBlock()) pCConfig.set("ifNotTargetBlock", convert);
        else pCConfig.set("ifNotTargetBlock", null);
        if (pC.getIfNotTargetBlockMsg().contains(pC.IF_NOT_TARGET_BLOCK_MSG)) pCConfig.set("ifNotTargetBlockMsg", null);
        else pCConfig.set("ifNotTargetBlockMsg", pC.getIfNotTargetBlockMsg());

        if (pC.hasIfPlayerHealth()) pCConfig.set("ifPlayerHealth", pC.getIfPlayerHealth());
        else pCConfig.set("ifPlayerHealth", null);
        if (pC.getIfPlayerHealthMsg().contains(pC.IF_PLAYER_HEALTH_MSG)) pCConfig.set("ifPlayerHealthMsg", null);
        else pCConfig.set("ifPlayerHealthMsg", pC.getIfPlayerHealthMsg());

        if (pC.hasIfLightLevel()) pCConfig.set("ifLightLevel", pC.getIfLightLevel());
        else pCConfig.set("ifLightLevel", null);
        if (pC.getIfLightLevelMsg().contains(pC.IF_LIGHT_LEVEL_MSG)) pCConfig.set("ifLightLevelMsg", null);
        else pCConfig.set("ifLightLevelMsg", pC.getIfLightLevelMsg());

        if (pC.hasIfPlayerFoodLevel()) pCConfig.set("ifPlayerFoodLevel", pC.getIfPlayerFoodLevel());
        else pCConfig.set("ifPlayerFoodLevel", null);
        if (pC.getIfPlayerFoodLevelMsg().contains(pC.IF_PLAYER_FOOD_LEVEL_MSG))
            pCConfig.set("ifPlayerFoodLevelMsg", null);
        else pCConfig.set("ifPlayerFoodLevelMsg", pC.getIfPlayerFoodLevelMsg());

        if (pC.hasIfPlayerEXP()) pCConfig.set("ifPlayerEXP", pC.getIfPlayerEXP());
        else pCConfig.set("ifPlayerEXP", null);
        if (pC.getIfPlayerEXPMsg().contains(pC.IF_PLAYER_EXP_MSG)) pCConfig.set("ifPlayerEXPMsg", null);
        else pCConfig.set("ifPlayerEXPMsg", pC.getIfPlayerEXPMsg());

        if (pC.hasIfPlayerLevel()) pCConfig.set("ifPlayerLevel", pC.getIfPlayerLevel());
        else pCConfig.set("ifPlayerLevel", null);
        if (pC.getIfPlayerLevelMsg().contains(pC.IF_PLAYER_LEVEL_MSG)) pCConfig.set("ifPlayerLevelMsg", null);
        else pCConfig.set("ifPlayerLevelMsg", pC.getIfPlayerLevelMsg());

        if (pC.hasIfPosX()) pCConfig.set("ifPosX", pC.getIfPosX());
        else pCConfig.set("ifPosX", null);
        if (pC.getIfPosXMsg().contains(pC.IF_POS_X_MSG)) pCConfig.set("ifPosXMsg", null);
        else pCConfig.set("ifPosXMsg", pC.getIfPosXMsg());

        if (pC.hasIfPosY()) pCConfig.set("ifPosY", pC.getIfPosY());
        else pCConfig.set("ifPosY", null);
        if (pC.getIfPosYMsg().contains(pC.IF_POS_Y_MSG)) pCConfig.set("ifPosYMsg", null);
        else pCConfig.set("ifPosYMsg", pC.getIfPosYMsg());

        if (pC.hasIfPosZ()) pCConfig.set("ifPosZ", pC.getIfPosZ());
        else pCConfig.set("ifPosZ", null);
        if (pC.getIfPosZMsg().contains(pC.IF_POS_Z_MSG)) pCConfig.set("ifPosZMsg", null);
        else pCConfig.set("ifPosZMsg", pC.getIfPosZMsg());

        if (pC.ifPlayerHasEffect.size() > 0) {
            List<String> result = new ArrayList<>();
            for (PotionEffectType pET : pC.ifPlayerHasEffect.keySet()) {
                result.add(pET.getName() + ":" + pC.ifPlayerHasEffect.get(pET));
            }
            pCConfig.set("ifPlayerHasEffect", result);
            if (pC.getIfPlayerHasEffectMsg().contains(pC.IF_PLAYER_HAS_EFFECT_MSG))
                pCConfig.set("ifPlayerHasEffectMsg", null);
            else pCConfig.set("ifPlayerHasEffectMsg", pC.getIfPlayerHasEffectMsg());
        } else pCConfig.set("ifPlayerHasEffect", null);

        if (pC.ifPlayerHasEffectEquals.size() > 0) {
            List<String> result = new ArrayList<>();
            for (PotionEffectType pET : pC.ifPlayerHasEffectEquals.keySet()) {
                result.add(pET.getName() + ":" + pC.ifPlayerHasEffectEquals.get(pET));
            }
            pCConfig.set("ifPlayerHasEffectEquals", result);
            if (pC.getIfPlayerHasEffectEqualsMsg().contains(pC.IF_PLAYER_HAS_EFFECT_MSG))
                pCConfig.set("ifPlayerHasEffectEqualsMsg", null);
            else pCConfig.set("ifPlayerHasEffectEqualsMsg", pC.getIfPlayerHasEffectEqualsMsg());
        } else pCConfig.set("ifPlayerHasEffectEquals", null);

        if (pC.hasIfCursorDistance()) pCConfig.set("ifCursorDistance", pC.getIfCursorDistance());
        else pCConfig.set("ifCursorDistance", null);
        if (pC.getIfCursorDistanceMsg().contains(pC.IF_CURSOR_DISTANCE_MSG)) pCConfig.set("ifCursorDistanceMsg", null);
        else pCConfig.set("ifCursorDistanceMsg", pC.getIfCursorDistanceMsg());

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

    public boolean hasIfInWorld() {
        return ifInWorld != null && ifInWorld.size() != 0;
    }

    public boolean hasIfNotInWorld() {
        return ifNotInWorld != null && ifNotInWorld.size() != 0;
    }

    public boolean hasIfInBiome() {
        return ifInBiome != null && ifInBiome.size() != 0;
    }

    public boolean hasIfNotInBiome() {
        return ifNotInBiome != null && ifNotInBiome.size() != 0;
    }

    public boolean hasIfInRegion() {
        return ifInRegion != null && ifInRegion.size() != 0;
    }

    public boolean hasIfNotInRegion() {
        return ifNotInRegion != null && ifNotInRegion.size() != 0;
    }

    public boolean hasIfHasPermission() {
        return ifHasPermission != null && ifHasPermission.size() != 0;
    }

    public boolean hasIfNotHasPermission() {
        return ifNotHasPermission != null && ifNotHasPermission.size() != 0;
    }

    public boolean hasIfTargetBlock() {
        return ifTargetBlock != null && ifTargetBlock.size() != 0;
    }

    public boolean hasIfNotTargetBlock() {
        return ifNotTargetBlock != null && ifNotTargetBlock.size() != 0;
    }

    public boolean hasIfPlayerHealth() {
        return ifPlayerHealth != null && ifPlayerHealth.length() != 0;
    }

    public boolean hasIfPlayerFoodLevel() {
        return ifPlayerFoodLevel != null && ifPlayerFoodLevel.length() != 0;
    }

    public boolean hasIfPlayerEXP() {
        return ifPlayerEXP != null && ifPlayerEXP.length() != 0;
    }

    public boolean hasIfPlayerLevel() {
        return ifPlayerLevel != null && ifPlayerLevel.length() != 0;
    }

    public boolean hasIfLightLevel() {
        return ifLightLevel != null && ifLightLevel.length() != 0;
    }

    public boolean hasIfPosX() {
        return ifPosX != null && ifPosX.length() != 0;
    }

    public boolean hasIfPosY() {
        return ifPosY != null && ifPosY.length() != 0;
    }

    public boolean hasIfPosZ() {
        return ifPosZ != null && ifPosZ.length() != 0;
    }

    public boolean hasIfPlayerHasExecutableItem() {
        return ifPlayerHasExecutableItem != null && !ifPlayerHasExecutableItem.isEmpty();
    }

    public boolean hasIfPlayerHasItem() {
        return ifPlayerHasItem != null && !ifPlayerHasItem.isEmpty();
    }

    public boolean hasIfCursorDistance() {
        return ifCursorDistance != null && ifCursorDistance.length() != 0;
    }
}
