package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;

import com.ssomar.executableitems.executableitems.variables.VariableReal;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Projectile;
import org.jetbrains.annotations.Nullable;

public class StringPlaceholder extends PlaceholdersInterface implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* placeholders of the player */
	private final PlayerPlaceholders playerPlch = new PlayerPlaceholders();

	/* placeholders of the target player */
	private final TargetPlaceholders targetPlch = new TargetPlaceholders();

	/* placeholders of the owner */
	private final OwnerPlaceholders ownerPlch = new OwnerPlaceholders();

	/* placeholders of the owner */
	private final ProjectilePlaceholders projectilePlch = new ProjectilePlaceholders();

	/* placeholders of the time */
	@Getter
	private final TimePlaceholders timePlch = new TimePlaceholders();

	/* placeholders of the item */
	private String activator = "";
	private String item = "";
	private String quantity  = "";
	private String usage ="";
	private String usageLimit ="";
	private String maxUsePerDayItem = "";
	private String maxUsePerDayActivator = "";

	/* placeholders of the target entity */
	private final EntityPlaceholders entityPlch = new EntityPlaceholders();

	/* placeholders of the block */
	private final BlockPlaceholders blockPlch = new BlockPlaceholders();

	/* placeholders of the target block */
	private final TargetBlockPlaceholders targetBlockPlch = new TargetBlockPlaceholders();

	/* placeholders tools */
	private String launcher="";
	private String blockface= "";

	/* placeholders of the cooldown */
	private String cooldown= "";

	@Setter
	private List<VariableReal> variables = new ArrayList<>();

	/* placeholders of the around target player */
	AroundPlayerTargetPlaceholders aroundPlayerTargetPlch = new AroundPlayerTargetPlaceholders();

	/* placeholders of the around target entity */
	AroundEntityTargetPlaceholders aroundEntityTargetPlch = new AroundEntityTargetPlaceholders();

	public void setPlayerPlcHldr(UUID uuid) {
		playerPlch.setPlayerPlcHldr(uuid);
	}

	public void setPlayerPlcHldr(UUID uuid, int fixSlot) {
		playerPlch.setPlayerPlcHldr(uuid, fixSlot);
	}

	public void setTargetPlcHldr(UUID uuid) {
		targetPlch.setTargetPlcHldr(uuid);
	}

	public void setOwnerPlcHldr(UUID uuid) {
		ownerPlch.setOwnerPlcHldr(uuid);
	}

	public void setProjectilePlcHldr(Projectile proj, String blockFace) {
		projectilePlch.setProjectilePlcHldr(proj, blockFace);
	}

	public void setEntityPlcHldr(UUID uuid) {
		entityPlch.setEntityPlcHldr(uuid);
	}

	public void setBlockPlcHldr(Block block) {
		blockPlch.setBlockPlcHldr(block);
	}

	public void setBlockPlcHldr(Block block, Material fixType) {
		blockPlch.setBlockPlcHldr(block, fixType);
	}

	public void setTargetBlockPlcHldr(Block block) {
		targetBlockPlch.setTargetBlockPlcHldr(block);
	}

	public void setTargetBlockPlcHldr(Block block, Material fixType) {
		targetBlockPlch.setTargetBlockPlcHldr(block, fixType);
	}

	public void setAroundTargetPlayerPlcHldr(UUID uuid) {
		aroundPlayerTargetPlch.setAroundPlayerTargetPlcHldr(uuid);
	}

	public void setAroundTargetEntityPlcHldr(UUID uuid) {
		aroundEntityTargetPlch.setAroundEntityTargetPlcHldr(uuid);
	}

	public void reloadAllPlaceholders() {
		playerPlch.reloadPlayerPlcHldr();
		targetPlch.reloadTargetPlcHldr();
		ownerPlch.reloadOwnerPlcHldr();
		entityPlch.reloadEntityPlcHldr();
		blockPlch.reloadBlockPlcHldr();
		targetBlockPlch.reloadTargetBlockPlcHldr();
		aroundPlayerTargetPlch.reloadAroundPlayerTargetPlcHldr();
		aroundEntityTargetPlch.reloadAroundEntityTargetPlcHldr();
		/* delayed command with old version has this to null */
		if(projectilePlch != null ) projectilePlch.reloadProjectilePlcHldr();
	}

	public String replacePlaceholder(String str) {
		return replacePlaceholder(str, true);
	}

	public String replacePlaceholder(String str, boolean withPAPI) {
		this.reloadAllPlaceholders();
		String s = str;

		if(str.trim().length() == 0) return "";

		s = replaceRandomPlaceholders(s);
		
		if(this.hasActivator()) {
			s = s.replaceAll("%activator%", this.getActivator());
		}
		if(this.hasItem()) {
			s = s.replaceAll("%item%", Matcher.quoteReplacement(this.getItem()));
		}
		if(this.hasQuantity()) {
			s = replaceCalculPlaceholder(s, "%quantity%", quantity, true);
			s = replaceCalculPlaceholder(s, "%amount%", quantity, true);
		}
		if(this.hasCoolodwn()) {
			s = s.replaceAll("%cooldown%", this.getCooldown());
		}
		if(this.hasBlockFace()) {
			s = s.replaceAll("%blockface%", this.getBlockface());
		}
		if(this.hasUsage()) {
			s = replaceCalculPlaceholder(s, "%usage%", usage, true);
		}
		if(this.hasMaxUsePerDayActivator()) {
			s = s.replaceAll("%max_use_per_day_activator%", this.getMaxUsePerDayActivator());
		}
		if(this.hasMaxUsePerDayItem()) {
			s = s.replaceAll("%max_use_per_day_item%", this.getMaxUsePerDayItem());
		}

		if(variables != null) {
			for (VariableReal var : variables) {
				s = var.replaceVariablePlaceholder(s);
			}
		}

		s = playerPlch.replacePlaceholder(s);

		s = targetPlch.replacePlaceholder(s);

		s = ownerPlch.replacePlaceholder(s);

		s = entityPlch.replacePlaceholder(s);

		s = blockPlch.replacePlaceholder(s);

		s = targetBlockPlch.replacePlaceholder(s);

		s = aroundPlayerTargetPlch.replacePlaceholder(s);

		s = aroundEntityTargetPlch.replacePlaceholder(s);

		s = timePlch.replacePlaceholder(s);

		if(projectilePlch != null ) s = projectilePlch.replacePlaceholder(s);

		if(withPAPI) return replacePlaceholderOfPAPI(s);
		else return s;
	}

	public static String replaceRandomPlaceholders(String s) {
		String result = s;
		if(result.contains("%rand:")) {
			int part1;
			int part2;
			String [] decompRand = result.split("\\%rand:");
			boolean cont = true;
			for(String strRand : decompRand) {
				if(cont) {
					cont = false;
					continue;
				}

				if(strRand.contains("%")) {
					String [] decomp = strRand.split("\\%");
					if((decomp.length >= 2 || (strRand.endsWith("%") && decomp.length == 1) ) && decomp[0].contains("|")) {
						decomp = decomp[0].split("\\|");

						try {
							part1 = Integer.parseInt(decomp[0]);
							part2 = Integer.parseInt(decomp[1]);
						}catch(Exception e) {
							continue;
						}

						if(part1 < part2) {
							int random = part1 + (int)(Math.random() * ((part2 - part1) + 1));
							result = result.replace("%rand:"+part1+"|"+part2+"%", random+"");
						}
					}
				}
			}
		}

		return result;
	}

	public String replacePlaceholderOfPAPI(String s) {
		String replace = s;
		UUID uuid;
		if((uuid = playerPlch.getPlayerUUID()) != null) {
			Player p;
			//SsomarDev.testMsg("REPLACE PLACE 2 : "+((p = Bukkit.getPlayer(UUID.fromString(playerUUID)))!=null)+ " &&&&&&& "+ExecutableItems.hasPlaceholderAPI());
			if((p = Bukkit.getPlayer(uuid)) != null && SCore.hasPlaceholderAPI) replace = PlaceholderAPI.setPlaceholders(p, replace);
		}
		return replace;
	}

	//	public static void main(String[] args) {
	//		 StringPlaceholder sp = new StringPlaceholder();
	//		 sp.blockXInt = "10";
	//		 sp.blockYInt = "11";
	//		 sp.blockZInt = "12";
	//		 
	//		 String base = "SENDMESSAGE oops %block_x_int%,%block_y_int%+1,%block_z_int% youhouu";
	//		 
	//		 base = sp.replacePlaceholder(base);
	//		 
	//		 System.out.println(base);
	//	}

	public String getLauncher() {
		return launcher;
	}
	public void setLauncher(String launcher) {
		this.launcher = launcher;
	}
	public boolean hasLauncher() {
		return launcher.length()!=0;
	}
	public String getActivator() {
		return activator;
	}
	public void setActivator(String activator) {
		this.activator = activator;
	}
	public boolean hasActivator() {
		return activator.length()!=0;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public boolean hasItem() {
		return item.length()!=0;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public boolean hasQuantity() {
		return quantity.length()!=0;
	}
	public String getCooldown() {
		return cooldown;
	}
	public void setCooldown(String cooldown) {
		this.cooldown = cooldown;
	}
	public boolean hasCoolodwn() {
		return cooldown.length()!=0;
	}

	public String getUsageLimit() {
		return usageLimit;
	}

	public boolean hasUsageLimit() {
		return usageLimit.length() != 0;
	}

	public void setUsageLimit(String usageLimit) {
		this.usageLimit = usageLimit;
	}

	public String getBlockface() {
		return blockface;
	}
	public void setBlockface(String blockface) {
		this.blockface = blockface;
	}
	public boolean hasBlockFace() {
		return this.blockface.length()!=0;
	}

	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public boolean hasUsage() {
		return this.usage.length()!=0;
	}

	public String getMaxUsePerDayItem() {
		return maxUsePerDayItem;
	}

	public void setMaxUsePerDayItem(String maxUsePerDayItem) {
		this.maxUsePerDayItem = maxUsePerDayItem;
	}

	public boolean hasMaxUsePerDayItem() {
		return maxUsePerDayItem.length()!=0;
	}

	public String getMaxUsePerDayActivator() {
		return maxUsePerDayActivator;
	}

	public void setMaxUsePerDayActivator(String maxUsePerDayActivator) {
		this.maxUsePerDayActivator = maxUsePerDayActivator;
	}

	public boolean hasMaxUsePerDayActivator() {
		return maxUsePerDayActivator.length()!=0;
	}



}
