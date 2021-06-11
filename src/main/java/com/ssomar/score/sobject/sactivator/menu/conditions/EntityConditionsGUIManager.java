package com.ssomar.score.sobject.sactivator.menu.conditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.EntityConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;

public class EntityConditionsGUIManager extends GUIManager<EntityConditionsGUI>{

	private static EntityConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sAct, EntityConditions conditions, String detail) {
		cache.put(p, new EntityConditionsGUI(sPlugin, sObject, sAct, conditions, detail));
		cache.get(p).openGUISync(p);
	}

	public void clicked(Player p, ItemStack item) {
		if(item!=null) {
			if(item.hasItemMeta()) {
				SPlugin sPlugin = cache.get(p).getsPlugin();
				SObject sObject = cache.get(p).getSObject();
				SActivator sAct = cache.get(p).getSAct();
				String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
				String plName = sPlugin.getNameDesign();

				if(name.contains(EntityConditionsGUI.IF_GLOWING)) cache.get(p).changeBoolean(EntityConditionsGUI.IF_GLOWING);

				else if(name.contains(EntityConditionsGUI.IF_INVULNERABLE)) cache.get(p).changeBoolean(EntityConditionsGUI.IF_INVULNERABLE);

				else if(name.contains(EntityConditionsGUI.IF_ADULT)) cache.get(p).changeBoolean(EntityConditionsGUI.IF_ADULT);

				else if(name.contains(EntityConditionsGUI.IF_BABY)) cache.get(p).changeBoolean(EntityConditionsGUI.IF_BABY);

				else if(name.contains(EntityConditionsGUI.IF_POWERED)) cache.get(p).changeBoolean(EntityConditionsGUI.IF_POWERED);

				else if(name.contains(EntityConditionsGUI.IF_NAME)) {
					requestWriting.put(p, EntityConditionsGUI.IF_NAME);
					if(!currentWriting.containsKey(p)) {
						currentWriting.put(p, cache.get(p).getIfName());
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF NAME:"));
					this.showIfNameEditor(p);
					space(p);
				}

				else if(name.contains(EntityConditionsGUI.IF_NOT_ENTITY_TYPE)) {
					requestWriting.put(p, EntityConditionsGUI.IF_NOT_ENTITY_TYPE);
					if(!currentWriting.containsKey(p)) {
						currentWriting.put(p, cache.get(p).getIfName());
					}
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF NOT ENTITYTYPE:"));
					this.showIfNotEntityTypeEditor(p);
					space(p);
				}

				else if(name.contains(EntityConditionsGUI.IF_ENTITY_HEALTH)) {
					requestWriting.put(p, EntityConditionsGUI.IF_ENTITY_HEALTH);
					p.closeInventory();
					space(p);
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION IF ENTITY HEALTH:"));

					this.showCalculationGUI(p, "Health", cache.get(p).getIfEntityHealth());
					space(p);
				}
				
				else if(name.contains("Reset")) {
					p.closeInventory();
					cache.replace(p, new EntityConditionsGUI(sPlugin, sObject, sAct, new EntityConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
					p.closeInventory();
					saveEntityConditionsEI(p);
					sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
					ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sObject.getActivator(sAct.getID()));
				}

				else if(name.contains("Exit")) {
					p.closeInventory();
				}

				else if(name.contains("Back")) {
					ConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct);
				}
			}
		}
	}

	public void receivedMessage(Player p, String message) {
		boolean notExit= true;
		SPlugin sPlugin = cache.get(p).getsPlugin();
		//SObject sObject = cache.get(p).getSObject();
		//SActivator sAct = cache.get(p).getSAct();
		String plName = sPlugin.getNameDesign();

		if(message.contains("exit")) {
			boolean pass=false;
			if(StringConverter.decoloredString(message).equals("exit with delete")) {
				if(requestWriting.get(p).equals(EntityConditionsGUI.IF_ENTITY_HEALTH)) {
					cache.get(p).updateIfEntityHealth("");
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}
				pass=true;
			}
			if(StringConverter.decoloredString(message).equals("exit") || pass) {
				if(requestWriting.get(p).equals(EntityConditionsGUI.IF_NAME)) {
					List<String> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						result.add(str);
					}
					cache.get(p).updateIfName(result);
				}
				else if(requestWriting.get(p).equals(EntityConditionsGUI.IF_NOT_ENTITY_TYPE)) {
					List<EntityType> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						try {
							result.add(EntityType.valueOf(str.toUpperCase()));
						}catch(Exception e) {}

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
				int line = Integer.valueOf(message.split("delete line <")[1].split(">")[0]);
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

	public void saveEntityConditionsEI(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		EntityConditions eC = new EntityConditions();

		eC.setIfGlowing(cache.get(p).getBoolean(EntityConditionsGUI.IF_GLOWING));
		eC.setIfInvulnerable(cache.get(p).getBoolean(EntityConditionsGUI.IF_INVULNERABLE));
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

	public static EntityConditionsGUIManager getInstance() {
		if(instance == null) instance = new EntityConditionsGUIManager();
		return instance;
	}	
}
