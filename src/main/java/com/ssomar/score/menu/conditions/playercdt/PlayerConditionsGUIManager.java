package com.ssomar.score.menu.conditions.playercdt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.ssomar.score.SCore;
import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.PlayerConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;


public class PlayerConditionsGUIManager extends GUIManagerConditions<PlayerConditionsGUI>{

	private static PlayerConditionsGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, PlayerConditions pC, String detail) {
		cache.put(p, new PlayerConditionsGUI(sPlugin, sObject, sActivator, pC, detail));
		cache.get(p).openGUISync(p);
	}

	@Override
	public boolean allClicked(InteractionClickedGUIManager<PlayerConditionsGUI> i) {
		return this.saveOrBackOrNothing(i);
	}

	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<PlayerConditionsGUI> i) {
		if(i.name.contains(PlayerConditionsGUI.IF_SNEAKING)) cache.get(i.player).changeBoolean(PlayerConditionsGUI.IF_SNEAKING);

		else if(i.name.contains(PlayerConditionsGUI.IF_NOT_SNEAKING)) cache.get(i.player).changeBoolean(PlayerConditionsGUI.IF_NOT_SNEAKING);

		else if(i.name.contains(PlayerConditionsGUI.IF_BLOCKING)) cache.get(i.player).changeBoolean(PlayerConditionsGUI.IF_BLOCKING);

		else if(i.name.contains(PlayerConditionsGUI.IF_NOT_BLOCKING)) cache.get(i.player).changeBoolean(PlayerConditionsGUI.IF_NOT_BLOCKING);

		else if(i.name.contains(PlayerConditionsGUI.IF_SPRINTING)) cache.get(i.player).changeBoolean(PlayerConditionsGUI.IF_SPRINTING);

		else if(i.name.contains(PlayerConditionsGUI.IF_SWIMMING)) cache.get(i.player).changeBoolean(PlayerConditionsGUI.IF_SWIMMING);

		else if(i.name.contains(PlayerConditionsGUI.IF_GLIDING)) cache.get(i.player).changeBoolean(PlayerConditionsGUI.IF_GLIDING);

		else if(i.name.contains(PlayerConditionsGUI.IF_FLYING)) cache.get(i.player).changeBoolean(PlayerConditionsGUI.IF_FLYING);

		else if(i.name.contains(PlayerConditionsGUI.IF_IS_IN_THE_AIR)) cache.get(i.player).changeBoolean(PlayerConditionsGUI.IF_IS_IN_THE_AIR);

		else if(i.name.contains(PlayerConditionsGUI.IF_HAS_EFFECT)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_HAS_EFFECT);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfHasEffectStr(false));
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF HAS EFFECT &e&o(EFFECT:MINIMUM_REQUIRED_AMPLIFIER):"));
			this.showIfHasEffectEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_HAS_EFFECT_EQUALS)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_HAS_EFFECT_EQUALS);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfHasEffectStr(true));
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF HAS EFFECT EQUALS &e&o(EFFECT:REQUIRED_AMPLIFIER):"));
			this.showIfHasEffectEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_IN_WORLD)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_IN_WORLD);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfInWorld());
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF IN WORLD:"));
			this.showIfInWorldEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_NOT_IN_WORLD)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_NOT_IN_WORLD);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfNotInWorld());
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF IN NOT WORLD:"));
			this.showIfNotInWorldEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_IN_BIOME)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_IN_BIOME);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfInBiome());
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF IN BIOME:"));
			this.showIfInBiomeEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_NOT_IN_BIOME)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_NOT_IN_BIOME);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfNotInBiome());
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF IN NOT BIOME:"));
			this.showIfNotInBiomeEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_IN_REGION)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_IN_REGION);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfInRegion());
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF IN REGION:"));
			this.showIfInRegionEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_NOT_IN_REGION)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_NOT_IN_REGION);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfNotInRegion());
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF NOT IN REGION:"));
			this.showIfNotInRegionEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_HAS_PERMISSION)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_HAS_PERMISSION);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfHasPermission());
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF HAS PERMISSION:"));
			this.showIfHasPermissionEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_NOT_HAS_PERMISSION)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_NOT_HAS_PERMISSION);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfNotHasPermission());
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF NOT HAS PERMISSION:"));
			this.showIfNotHasPermissionEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_TARGET_BLOCK)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_TARGET_BLOCK);
			if(!currentWriting.containsKey(i.player)) {
				List<String> convert = new ArrayList<>();
				for(Material mat : cache.get(i.player).getIfTargetBlock()) {
					convert.add(mat.toString());
				}
				currentWriting.put(i.player, convert);
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF TARGET BLOCK:"));
			this.showIfTargetBlockEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_IS_ON_THE_BLOCK)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_IS_ON_THE_BLOCK);
			if(!currentWriting.containsKey(i.player)) {
				List<String> convert = new ArrayList<>();
				for(Material mat : cache.get(i.player).getIfIsOnTheBlock()) {
					convert.add(mat.toString());
				}
				currentWriting.put(i.player, convert);
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF IS ON THE BLOCK:"));
			this.showIfIsOnTheBlockEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_NOT_TARGET_BLOCK)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_NOT_TARGET_BLOCK);
			if(!currentWriting.containsKey(i.player)) {	
				List<String> convert = new ArrayList<>();
				for(Material mat : cache.get(i.player).getIfNotTargetBlock()) {
					convert.add(mat.toString());
				}
				currentWriting.put(i.player, convert);
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF NOT TARGET BLOCK:"));
			this.showIfNotTargetBlockEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_PLAYER_HEALTH)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_PLAYER_HEALTH);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF PLAYER HEALTH:"));

			this.showCalculationGUI(i.player, "Health", cache.get(i.player).getIfPlayerHealth());
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_LIGHT_LEVEL)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_LIGHT_LEVEL);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF LIGHT LEVEL:"));

			this.showCalculationGUI(i.player, "Light level", cache.get(i.player).getIfLightLevel());
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_POS_X)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_POS_X);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF POS X:"));

			this.showCalculationGUI(i.player, "Pos X", cache.get(i.player).getIfPosX());
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_POS_Y)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_POS_Y);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF POS Y:"));

			this.showCalculationGUI(i.player, "Pos Y", cache.get(i.player).getIfPosY());
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_POS_Z)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_POS_Z);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF POS Z:"));

			this.showCalculationGUI(i.player, "Pos Z", cache.get(i.player).getIfPosZ());
			space(i.player);
		}

		else if(i.name.contains(PlayerConditionsGUI.IF_PLAYER_FOOD_LEVEL)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_PLAYER_FOOD_LEVEL);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF PLAYER FOOD LEVEL:"));

			this.showCalculationGUI(i.player, "Food level", cache.get(i.player).getIfPlayerFoodLevel());
			space(i.player);
		}
		else if(i.name.contains(PlayerConditionsGUI.IF_PLAYER_EXP)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_PLAYER_EXP);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF PLAYER EXP:"));

			this.showCalculationGUI(i.player, "EXP", cache.get(i.player).getIfPlayerEXP());
			space(i.player);
		}
		else if(i.name.contains(PlayerConditionsGUI.IF_PLAYER_LEVEL)) {
			requestWriting.put(i.player, PlayerConditionsGUI.IF_PLAYER_LEVEL);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF PLAYER LEVEL:"));

			this.showCalculationGUI(i.player, "Level", cache.get(i.player).getIfPlayerLevel());
			space(i.player);
		}	
		else return false;

		return true;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<PlayerConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<PlayerConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<PlayerConditionsGUI> i) {
		String detail = cache.get(i.player).getDetail();
		this.saveTheConfiguration(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
		PlayerConditions pC = null;
		if(detail.contains("owner")) pC = i.sObject.getActivator(i.sActivator.getID()).getOwnerConditions();
		else if(detail.contains("target")) pC = i.sObject.getActivator(i.sActivator.getID()).getTargetPlayerConditions();
		else if(detail.contains("player")) pC = i.sObject.getActivator(i.sActivator.getID()).getPlayerConditions();
		PlayerConditionsMessagesGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, pC, detail);

		return true;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<PlayerConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<PlayerConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<PlayerConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<PlayerConditionsGUI> interact) {
		return false;
	}

	public void receivedMessage(Player p, String message) {
		boolean notExit = true;
		SPlugin sPlugin = cache.get(p).getsPlugin();
		//SObject sObject = cache.get(p).getSObject();
		//SActivator sAct = cache.get(p).getSAct();
		String plName = sPlugin.getNameDesign();

		if(message.contains("exit")) {
			boolean pass = false;
			if(StringConverter.decoloredString(message).equals("exit with delete")) {
				if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_HEALTH)) {
					cache.get(p).updateIfPlayerHealth("");
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_LIGHT_LEVEL)) {
					cache.get(p).updateIfLightLevel("");
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_POS_X)) {
					cache.get(p).updateIfPosX("");
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_POS_Y)) {
					cache.get(p).updateIfPosY("");
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_POS_Z)) {
					cache.get(p).updateIfPosZ("");
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_FOOD_LEVEL)) {
					cache.get(p).updateIfPlayerFoodLevel("");
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_EXP)) {
					cache.get(p).updateIfPlayerEXP("");
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_LEVEL)) {
					cache.get(p).updateIfPlayerLevel("");
				}
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				pass = true;
			}
			if(StringConverter.decoloredString(message).equals("exit") || pass) {
				if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_HAS_EFFECT)) {
					List<String> result = new ArrayList<>(currentWriting.get(p));
					cache.get(p).updateIfHasEffect(result, false);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_HAS_EFFECT_EQUALS)) {
					List<String> result = new ArrayList<>(currentWriting.get(p));
					cache.get(p).updateIfHasEffect(result, true);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IN_WORLD)) {
					List<String> result = new ArrayList<>(currentWriting.get(p));
					cache.get(p).updateIfInWorld(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_IN_WORLD)) {
					List<String> result = new ArrayList<>(currentWriting.get(p));
					cache.get(p).updateIfNotInWorld(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IN_BIOME)) {
					List<String> result = new ArrayList<>(currentWriting.get(p));
					cache.get(p).updateIfInBiome(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_IN_BIOME)) {
					List<String> result = new ArrayList<>(currentWriting.get(p));
					cache.get(p).updateIfNotInBiome(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IN_REGION)) {
					List<String> result = new ArrayList<>(currentWriting.get(p));
					cache.get(p).updateIfInRegion(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_IN_REGION)) {
					List<String> result = new ArrayList<>(currentWriting.get(p));
					cache.get(p).updateIfNotInRegion(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_HAS_PERMISSION)) {
					List<String> result = new ArrayList<>(currentWriting.get(p));
					cache.get(p).updateIfHasPermission(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_TARGET_BLOCK)) {
					List<Material> result = new ArrayList<>();
					try {
						for(String str : currentWriting.get(p)) {
							result.add(Material.valueOf(str));
						}
					}catch(Exception ignored) {}
					cache.get(p).updateIfTargetBlock(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IS_ON_THE_BLOCK)) {
					List<Material> result = new ArrayList<>();
					try {
						for(String str : currentWriting.get(p)) {
							result.add(Material.valueOf(str));
						}
					}catch(Exception ignored) {}
					cache.get(p).updateIfIsOnTheBlock(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_TARGET_BLOCK)) {
					List<Material> result = new ArrayList<>();
					try {
						for(String str : currentWriting.get(p)) {
							result.add(Material.valueOf(str));
						}
					}catch(Exception ignored) {}
					cache.get(p).updateIfNotTargetBlock(result);
				}
				else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_HAS_PERMISSION)) {
					List<String> result = new ArrayList<>(currentWriting.get(p));
					cache.get(p).updateIfNotHasPermission(result);
				}
				currentWriting.remove(p);
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				notExit=false;
			}
		}
		if(notExit) {
			if(message.contains("delete line <")) {	
				this.deleteLine(message, p);
				this.showTheGoodEditor(requestWriting.get(p), p);
				space(p);
				space(p);			
			}

			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_HAS_EFFECT)) {

				message = StringConverter.decoloredString(message);
				String[] decomp;
				PotionEffectType type;
				int value;

				if(!message.contains(":") || (decomp = message.split(":")).length != 2) {
					p.sendMessage(StringConverter.coloredString("&4&l"+plName+" &4&lERROR &cInvalid form pls follow this example: SPEED:0  (EFFECT:MINIMUM_AMPLIFIER_REQUIRED)"));
					this.showTheGoodEditor(requestWriting.get(p), p);
					return;
				}

				type = PotionEffectType.getByName(decomp[0]);

				if(type == null) {
					p.sendMessage(StringConverter.coloredString("&4&l"+plName+" &4&lERROR &cInvalid Effect type. List of the effects: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionEffectType.html"));
					this.showTheGoodEditor(requestWriting.get(p), p);
					return;
				}

				try {
					value = Integer.parseInt(decomp[1]);
				}
				catch(Exception e) {
					p.sendMessage(StringConverter.coloredString("&4&l"+plName+" &4&lERROR &cInvalid MINIMUM_AMPLIFIER_REQUIRED, set an integer >= 0"));
					this.showTheGoodEditor(requestWriting.get(p), p);
					return;
				}

				if(value < 0) {
					p.sendMessage(StringConverter.coloredString("&4&l"+plName+" &4&lERROR &cInvalid MINIMUM_AMPLIFIER_REQUIRED, set an integer >= 0"));
					this.showTheGoodEditor(requestWriting.get(p), p);
					return;
				}


				if(currentWriting.containsKey(p)) {
					currentWriting.get(p).add(message);
				}
				else {
					ArrayList<String> list = new ArrayList<>();
					list.add(message);
					currentWriting.put(p, list);
				}
				p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new required effect!"));

				this.showTheGoodEditor(requestWriting.get(p), p);
			}

			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_HAS_EFFECT_EQUALS)) {

				message = StringConverter.decoloredString(message);
				String[] decomp;
				PotionEffectType type;
				int value;

				if(!message.contains(":") || (decomp = message.split(":")).length != 2) {
					p.sendMessage(StringConverter.coloredString("&4&l"+plName+" &4&lERROR &cInvalid form pls follow this example: SPEED:0  (EFFECT:AMPLIFIER_REQUIRED)"));
					this.showTheGoodEditor(requestWriting.get(p), p);
					return;
				}

				type = PotionEffectType.getByName(decomp[0]);

				if(type == null) {
					p.sendMessage(StringConverter.coloredString("&4&l"+plName+" &4&lERROR &cInvalid Effect type. List of the effects: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionEffectType.html"));
					this.showTheGoodEditor(requestWriting.get(p), p);
					return;
				}

				try {
					value = Integer.parseInt(decomp[1]);
				}
				catch(Exception e) {
					p.sendMessage(StringConverter.coloredString("&4&l"+plName+" &4&lERROR &cInvalid AMPLIFIER_REQUIRED, set an integer >= 0"));
					this.showTheGoodEditor(requestWriting.get(p), p);
					return;
				}

				if(value < 0) {
					p.sendMessage(StringConverter.coloredString("&4&l"+plName+" &4&lERROR &cInvalid AMPLIFIER_REQUIRED, set an integer >= 0"));
					this.showTheGoodEditor(requestWriting.get(p), p);
					return;
				}


				if(currentWriting.containsKey(p)) {
					currentWriting.get(p).add(message);
				}
				else {
					ArrayList<String> list = new ArrayList<>();
					list.add(message);
					currentWriting.put(p, list);
				}
				p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new required effect equals!"));

				this.showTheGoodEditor(requestWriting.get(p), p);
			}

			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IN_WORLD) || requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_IN_WORLD)) {
				if(Bukkit.getServer().getWorld(message)!=null) {
					if(currentWriting.containsKey(p)) {
						currentWriting.get(p).add(message);
					}
					else {
						ArrayList<String> list = new ArrayList<>();
						list.add(message);
						currentWriting.put(p, list);
					}
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new world!"));
				}
				else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid world please !"));
				}
				this.showTheGoodEditor(requestWriting.get(p), p);
			}

			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IN_BIOME) ||  requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_IN_BIOME)) {

				if(currentWriting.containsKey(p)) {
					currentWriting.get(p).add(message);
				}
				else {
					ArrayList<String> list = new ArrayList<>();
					list.add(message);
					currentWriting.put(p, list);
				}
				p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new biome!"));

				this.showTheGoodEditor(requestWriting.get(p), p);
			}

			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IN_REGION) || requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_IN_REGION)) {
				if(SCore.is1v12()) {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lError the conditions ifInRegion and ifNotInRegion are not available in 1.12 due to a changement of worldguard API "));
				}else {
					if(message.isEmpty() || message.equals(" ")) {
						p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid region please !"));
					}
					else {
						if(currentWriting.containsKey(p)) {
							currentWriting.get(p).add(message);
						}
						else {
							ArrayList<String> list = new ArrayList<>();
							list.add(message);
							currentWriting.put(p, list);
						}
						p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new region!"));
					}
					this.showTheGoodEditor(requestWriting.get(p), p);
				}
			}

			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_HAS_PERMISSION) || requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_HAS_PERMISSION)) {
				if(message.isEmpty() || message.equals(" ")) {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid permission please !"));
				}
				else {
					if(currentWriting.containsKey(p)) {
						currentWriting.get(p).add(message);
					}
					else {
						ArrayList<String> list = new ArrayList<>();
						list.add(message);
						currentWriting.put(p, list);
					}
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new permission!"));
				}
				this.showTheGoodEditor(requestWriting.get(p), p);
			}

			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_TARGET_BLOCK) || requestWriting.get(p).equals(PlayerConditionsGUI.IF_NOT_TARGET_BLOCK)) {
				if(message.isEmpty() || message.equals(" ")) {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid block please !"));
				}
				else {
					try {
						Material.valueOf(message.toUpperCase());
					}catch(Exception e) {
						p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid block please !"));
						p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&l> https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html !"));
						return;
					}
					if(currentWriting.containsKey(p)) {
						currentWriting.get(p).add(message.toUpperCase());
					}
					else {
						ArrayList<String> list = new ArrayList<>();
						list.add(message);
						currentWriting.put(p, list);
					}
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new target block!"));
				}
				this.showTheGoodEditor(requestWriting.get(p), p);
			}

			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_IS_ON_THE_BLOCK)) {
				if(message.isEmpty() || message.equals(" ")) {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid block please !"));
				}
				else {
					try {
						Material.valueOf(message.toUpperCase());
					}catch(Exception e) {
						p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid block please !"));
						p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&l> https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html !"));
						return;
					}
					if(currentWriting.containsKey(p)) {
						currentWriting.get(p).add(message.toUpperCase());
					}
					else {
						ArrayList<String> list = new ArrayList<>();
						list.add(message);
						currentWriting.put(p, list);
					}
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new block!"));
				}
				this.showTheGoodEditor(requestWriting.get(p), p);
			}


			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_HEALTH)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPlayerHealth(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for health please !"));
					this.showCalculationGUI(p, "Health", cache.get(p).getIfPlayerHealth());
				}
			}

			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_LIGHT_LEVEL)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfLightLevel(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for light level please !"));
					this.showCalculationGUI(p, "Light level", cache.get(p).getIfLightLevel());
				}
			}
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_POS_X)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPosX(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for pos X please !"));
					this.showCalculationGUI(p, "Pos X", cache.get(p).getIfPosX());
				}
			}
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_POS_Y)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPosY(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for pos Y please !"));
					this.showCalculationGUI(p, "Pos Y", cache.get(p).getIfPosY());
				}
			}
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_POS_Z)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPosZ(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for pos Z please !"));
					this.showCalculationGUI(p, "Pos Z", cache.get(p).getIfPosZ());
				}
			}
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_FOOD_LEVEL)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPlayerFoodLevel(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for food level please !"));
					this.showCalculationGUI(p, "Food level", cache.get(p).getIfPlayerFoodLevel());
				}
			}
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_EXP)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPlayerEXP(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for EXP please !"));
					this.showCalculationGUI(p, "EXP", cache.get(p).getIfPlayerEXP());
				}
			}
			else if(requestWriting.get(p).equals(PlayerConditionsGUI.IF_PLAYER_LEVEL)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfPlayerLevel(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for level please !"));
					this.showCalculationGUI(p, "Level", cache.get(p).getIfPlayerLevel());
				}
			}
		}

	}

	public void showIfHasEffectEditor(Player p) {
		p.sendMessage(StringConverter.coloredString("&8&o>>> &7 Effets list: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionEffectType.html"));
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifHasEffect:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Has effect:", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfInWorldEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifInWorld:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "World:", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfNotInWorldEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifNotInWorld:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Not world:", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfInBiomeEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifInBiome:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Biome:", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfNotInBiomeEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifNotInBiome:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Not biome:", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfNotInRegionEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifNotInRegion:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Not region", false, true, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfInRegionEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifInRegion:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Region", false, true, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfNotHasPermissionEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifNotHasPermission:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Not has permission", false, true, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfHasPermissionEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifHasPermission:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Has permission", false, true, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfTargetBlockEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifTargetBlock:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Target block", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfIsOnTheBlockEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifIsOnTheBlock:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Is on the block", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfNotTargetBlockEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifNotTargetBlock:");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Not target block", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showTheGoodEditor(String value, Player p) {

		switch(value) {

		case PlayerConditionsGUI.IF_NOT_TARGET_BLOCK:
			showIfNotTargetBlockEditor(p);
			break;

		case PlayerConditionsGUI.IF_TARGET_BLOCK:
			showIfTargetBlockEditor(p);
			break;

		case PlayerConditionsGUI.IF_IS_ON_THE_BLOCK:
			showIfIsOnTheBlockEditor(p);
			break;

		case PlayerConditionsGUI.IF_HAS_PERMISSION:
			showIfHasPermissionEditor(p);
			break;

		case PlayerConditionsGUI.IF_NOT_HAS_PERMISSION:
			showIfNotHasPermissionEditor(p);
			break;

		case PlayerConditionsGUI.IF_IN_REGION:
			showIfInRegionEditor(p);
			break;

		case PlayerConditionsGUI.IF_NOT_IN_REGION:
			showIfNotInRegionEditor(p);
			break;

		case PlayerConditionsGUI.IF_NOT_IN_BIOME:
			showIfNotInBiomeEditor(p);
			break;

		case PlayerConditionsGUI.IF_IN_BIOME:
			showIfInBiomeEditor(p);
			break;

		case PlayerConditionsGUI.IF_IN_WORLD:
			showIfInWorldEditor(p);
			break;
			
		case PlayerConditionsGUI.IF_HAS_EFFECT: case PlayerConditionsGUI.IF_HAS_EFFECT_EQUALS:
			showIfHasEffectEditor(p);
			break;

		case PlayerConditionsGUI.IF_NOT_IN_WORLD:
			showIfNotInWorldEditor(p);
			break;

		default:
			break;
		}
	}

	public static PlayerConditionsGUIManager getInstance() {
		if(instance == null) instance = new PlayerConditionsGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		PlayerConditions pC = (PlayerConditions)cache.get(p).getConditions();

		pC.setIfSneaking(cache.get(p).getBoolean(PlayerConditionsGUI.IF_SNEAKING));
		pC.setIfNotSneaking(cache.get(p).getBoolean(PlayerConditionsGUI.IF_NOT_SNEAKING));
		pC.setIfBlocking(cache.get(p).getBoolean(PlayerConditionsGUI.IF_BLOCKING));
		pC.setIfNotBlocking(cache.get(p).getBoolean(PlayerConditionsGUI.IF_NOT_BLOCKING));
		pC.setIfSwimming(cache.get(p).getBoolean(PlayerConditionsGUI.IF_SWIMMING));
		pC.setIfSprinting(cache.get(p).getBoolean(PlayerConditionsGUI.IF_SPRINTING));
		pC.setIfGliding(cache.get(p).getBoolean(PlayerConditionsGUI.IF_GLIDING));
		pC.setIfFlying(cache.get(p).getBoolean(PlayerConditionsGUI.IF_FLYING));
		pC.setIfIsInTheAir(cache.get(p).getBoolean(PlayerConditionsGUI.IF_IS_IN_THE_AIR));
		pC.setIfInWorld(cache.get(p).getIfInWorld());
		pC.setIfNotInWorld(cache.get(p).getIfNotInWorld());
		pC.setIfInBiome(cache.get(p).getIfInBiome());
		pC.setIfNotInBiome(cache.get(p).getIfNotInBiome());
		pC.setIfInRegion(cache.get(p).getIfInRegion());
		pC.setIfNotInRegion(cache.get(p).getIfNotInRegion());
		pC.setIfHasPermission(cache.get(p).getIfHasPermission());
		pC.setIfNotHasPermission(cache.get(p).getIfNotHasPermission());
		pC.setIfPlayerHealth(cache.get(p).getIfPlayerHealth());
		pC.setIfLightLevel(cache.get(p).getIfLightLevel());
		pC.setIfPlayerFoodLevel(cache.get(p).getIfPlayerFoodLevel());
		pC.setIfPlayerEXP(cache.get(p).getIfPlayerEXP());
		pC.setIfPlayerLevel(cache.get(p).getIfPlayerLevel());
		pC.setIfTargetBlock(cache.get(p).getIfTargetBlock());
		pC.setIfNotTargetBlock(cache.get(p).getIfNotTargetBlock());
		pC.setIfPosX(cache.get(p).getIfPosX());
		pC.setIfPosY(cache.get(p).getIfPosY());
		pC.setIfPosZ(cache.get(p).getIfPosZ());
		pC.setIfIsOnTheBlock(cache.get(p).getIfIsOnTheBlock());
		pC.setIfPlayerHasEffect(cache.get(p).getIfHasEffect(false));
		pC.setIfPlayerHasEffectEquals(cache.get(p).getIfHasEffect(true));

		PlayerConditions.savePlayerConditions(sPlugin, sObject, sActivator, pC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}

}
