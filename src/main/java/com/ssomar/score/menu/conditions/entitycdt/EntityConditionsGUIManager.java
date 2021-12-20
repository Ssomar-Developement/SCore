package com.ssomar.score.menu.conditions.entitycdt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.EntityConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;

public class EntityConditionsGUIManager extends GUIManagerConditions<EntityConditionsGUI>{

	private static EntityConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sAct, EntityConditions conditions, String detail) {
		cache.put(p, new EntityConditionsGUI(sPlugin, sObject, sAct, conditions, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<EntityConditionsGUI> i) {
		return this.saveOrBackOrNothing(i);
	}

	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<EntityConditionsGUI> i) {
		if(i.name.contains(EntityConditionsGUI.IF_GLOWING)) i.gui.changeBoolean(EntityConditionsGUI.IF_GLOWING);

		else if(i.name.contains(EntityConditionsGUI.IF_INVULNERABLE)) i.gui.changeBoolean(EntityConditionsGUI.IF_INVULNERABLE);

		else if(i.name.contains(EntityConditionsGUI.IF_ON_FIRE)) i.gui.changeBoolean(EntityConditionsGUI.IF_ON_FIRE);

		else if(i.name.contains(EntityConditionsGUI.IF_ADULT)) i.gui.changeBoolean(EntityConditionsGUI.IF_ADULT);

		else if(i.name.contains(EntityConditionsGUI.IF_BABY)) i.gui.changeBoolean(EntityConditionsGUI.IF_BABY);

		else if(i.name.contains(EntityConditionsGUI.IF_POWERED)) i.gui.changeBoolean(EntityConditionsGUI.IF_POWERED);

		else if(i.name.contains(EntityConditionsGUI.IF_NAME)) {
			requestWriting.put(i.player, EntityConditionsGUI.IF_NAME);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfName());
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF NAME:"));
			this.showIfNameEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(EntityConditionsGUI.IF_NOT_ENTITY_TYPE)) {
			requestWriting.put(i.player, EntityConditionsGUI.IF_NOT_ENTITY_TYPE);
			if(!currentWriting.containsKey(i.player)) {
				currentWriting.put(i.player, cache.get(i.player).getIfName());
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF NOT ENTITYTYPE:"));
			this.showIfNotEntityTypeEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains(EntityConditionsGUI.IF_ENTITY_HEALTH)) {
			requestWriting.put(i.player, EntityConditionsGUI.IF_ENTITY_HEALTH);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF ENTITY HEALTH:"));

			this.showCalculationGUI(i.player, "Health", cache.get(i.player).getIfEntityHealth());
			space(i.player);
		}
		else return false;
		
		return true;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<EntityConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<EntityConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<EntityConditionsGUI> i) {
		String detail = cache.get(i.player).getDetail();
		this.saveTheConfiguration(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
		EntityConditionsMessagesGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sObject.getActivator(i.sActivator.getID()).getTargetEntityConditions(), detail);
		return true;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<EntityConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<EntityConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<EntityConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<EntityConditionsGUI> interact) {
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
				if(requestWriting.get(p).equals(EntityConditionsGUI.IF_ENTITY_HEALTH)) {
					cache.get(p).updateIfEntityHealth("");
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}
				pass = true;
			}
			if(StringConverter.decoloredString(message).equals("exit") || pass) {
				if(requestWriting.get(p).equals(EntityConditionsGUI.IF_NAME)) {
					List<String> result = new ArrayList<>(currentWriting.get(p));
					cache.get(p).updateIfName(result);
				}
				else if(requestWriting.get(p).equals(EntityConditionsGUI.IF_NOT_ENTITY_TYPE)) {
					List<EntityType> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						try {
							result.add(EntityType.valueOf(str.toUpperCase()));
						}catch(Exception ignored) {}

					}
					cache.get(p).updateIfNotEntityType(result);
				}
				currentWriting.remove(p);
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				notExit=false;
			}
		}
		if(notExit) {
			if(message.contains("delete line <")) {	
				space(p);
				space(p);
				int line = Integer.parseInt(message.split("delete line <")[1].split(">")[0]);
				deleteLine(p, line);
				p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have delete the line: "+line+" !"));
				if(requestWriting.get(p).equals(EntityConditionsGUI.IF_NAME)) this.showIfNameEditor(p);
				space(p);
				space(p);			
			}
			else if(requestWriting.get(p).equals(EntityConditionsGUI.IF_NAME)) {
				if(message.isEmpty() || message.equals(" ")) {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid name please !"));
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
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new name!"));
				}
				this.showIfNameEditor(p);
			}

			else if(requestWriting.get(p).equals(EntityConditionsGUI.IF_NOT_ENTITY_TYPE)) {
				boolean error =false;
				if(message.isEmpty() || message.equals(" ")) {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid entityType please !"));
					error=true;
				}
				try {
					EntityType.valueOf(message.toUpperCase());
				}catch(Exception ec) {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid entityType please !"));
					error=true;
				}
				if(!error) {
					if(currentWriting.containsKey(p)) {
						currentWriting.get(p).add(message.toUpperCase());
					}
					else {
						ArrayList<String> list = new ArrayList<>();
						list.add(message.toUpperCase());
						currentWriting.put(p, list);
					}
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have added new blacklisted entityType!"));
				}
				this.showIfNotEntityTypeEditor(p);
			}

			else if(requestWriting.get(p).equals(EntityConditionsGUI.IF_ENTITY_HEALTH)) {
				if(StringCalculation.isStringCalculation(message)) {
					cache.get(p).updateIfEntityHealth(message);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for health please !"));
					this.showCalculationGUI(p, "Health", cache.get(p).getIfEntityHealth());
				}
			}
		}

	}

	public void showIfNameEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifName: &7&0(No color)");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Namne", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public void showIfNotEntityTypeEditor(Player p) {
		List<String> beforeMenu = new ArrayList<>();
		beforeMenu.add("&7➤ ifNotEntityType: ");

		HashMap<String, String> suggestions = new HashMap<>();

		EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Not entity type", false, false, false, true, true, true, false, "", suggestions);		
		editor.generateTheMenuAndSendIt(p);
	}

	public static EntityConditionsGUIManager getInstance() {
		if(instance == null) instance = new EntityConditionsGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		EntityConditions eC = (EntityConditions) cache.get(p).getConditions();

		eC.setIfGlowing(cache.get(p).getBoolean(EntityConditionsGUI.IF_GLOWING));
		eC.setIfInvulnerable(cache.get(p).getBoolean(EntityConditionsGUI.IF_INVULNERABLE));
		eC.setIfOnFire(cache.get(p).getBoolean(EntityConditionsGUI.IF_ON_FIRE));
		eC.setIfAdult(cache.get(p).getBoolean(EntityConditionsGUI.IF_ADULT));
		eC.setIfBaby(cache.get(p).getBoolean(EntityConditionsGUI.IF_BABY));
		eC.setIfPowered(cache.get(p).getBoolean(EntityConditionsGUI.IF_POWERED));
		eC.setIfName(cache.get(p).getIfName());
		eC.setIfNotEntityType(cache.get(p).getIfNotEntityType());
		eC.setIfEntityHealth(cache.get(p).getIfEntityHealth());

		EntityConditions.saveEntityConditions(sPlugin, sObject, sActivator, eC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}
}
