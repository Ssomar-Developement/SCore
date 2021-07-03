package com.ssomar.score.menu.conditions.entitycdt;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.EntityConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class EntityConditionsMessagesGUIManager extends GUIManager<EntityConditionsMessagesGUI>{

	private static EntityConditionsMessagesGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sAct, EntityConditions conditions, String detail) {
		cache.put(p, new EntityConditionsMessagesGUI(sPlugin, sObject, sAct, conditions, detail));
		cache.get(p).openGUISync(p);
	}

	public void clicked(Player p, ItemStack item) {
		if(item != null) {
			if(item.hasItemMeta()) {
				SPlugin sPlugin = cache.get(p).getsPlugin();
				SObject sObject = cache.get(p).getSObject();
				SActivator sAct = cache.get(p).getSAct();
				String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
				//String plName = sPlugin.getNameDesign();

				if(name.contains("Reset")) {
					cache.replace(p, new EntityConditionsMessagesGUI(sPlugin, sObject, sAct, new EntityConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
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
				else if(!name.isEmpty()) {

					if(name.contains(EntityConditionsMessagesGUI.IF_ADULT_MSG)) {
						requestWriting.put(p, EntityConditionsMessagesGUI.IF_ADULT_MSG);
						this.sendRequestMessage(sPlugin, p, cache.get(p).getActuallyWithColor(EntityConditionsMessagesGUI.IF_ADULT_MSG));
					}

					else if(name.contains(EntityConditionsMessagesGUI.IF_BABY_MSG)) {
						requestWriting.put(p, EntityConditionsMessagesGUI.IF_BABY_MSG);
						this.sendRequestMessage(sPlugin, p, cache.get(p).getActuallyWithColor(EntityConditionsMessagesGUI.IF_BABY_MSG));
					}

					else if(name.contains(EntityConditionsMessagesGUI.IF_ENTITY_HEALTH_MSG)) {
						requestWriting.put(p, EntityConditionsMessagesGUI.IF_ENTITY_HEALTH_MSG);
						this.sendRequestMessage(sPlugin, p, cache.get(p).getActuallyWithColor(EntityConditionsMessagesGUI.IF_ENTITY_HEALTH_MSG));
					}

					else if(name.contains(EntityConditionsMessagesGUI.IF_GLOWING_MSG)) {
						requestWriting.put(p, EntityConditionsMessagesGUI.IF_GLOWING_MSG);
						this.sendRequestMessage(sPlugin, p, cache.get(p).getActuallyWithColor(EntityConditionsMessagesGUI.IF_GLOWING_MSG));
					}

					else if(name.contains(EntityConditionsMessagesGUI.IF_INVULNERABLE_MSG)) {
						requestWriting.put(p, EntityConditionsMessagesGUI.IF_INVULNERABLE_MSG);
						this.sendRequestMessage(sPlugin, p, cache.get(p).getActuallyWithColor(EntityConditionsMessagesGUI.IF_INVULNERABLE_MSG));
					}

					else if(name.contains(EntityConditionsMessagesGUI.IF_NAME_MSG)) {
						requestWriting.put(p, EntityConditionsMessagesGUI.IF_NAME_MSG);
						this.sendRequestMessage(sPlugin, p, cache.get(p).getActuallyWithColor(EntityConditionsMessagesGUI.IF_NAME_MSG));
					}

					else if(name.contains(EntityConditionsMessagesGUI.IF_NOT_ENTITY_TYPE_MSG)) {
						requestWriting.put(p, EntityConditionsMessagesGUI.IF_NOT_ENTITY_TYPE_MSG);
						this.sendRequestMessage(sPlugin, p, cache.get(p).getActuallyWithColor(EntityConditionsMessagesGUI.IF_NOT_ENTITY_TYPE_MSG));
					}

					else if(name.contains(EntityConditionsMessagesGUI.IF_POWERED_MSG)) {
						requestWriting.put(p, EntityConditionsMessagesGUI.IF_POWERED_MSG);
						this.sendRequestMessage(sPlugin, p, cache.get(p).getActuallyWithColor(EntityConditionsMessagesGUI.IF_POWERED_MSG));
					}	
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void sendRequestMessage(SPlugin sPlugin, Player p, String actualMsg) {
		p.closeInventory();
		space(p);

		TextComponent message = new TextComponent(
				StringConverter.coloredString("&a&l"+sPlugin.getNameDesign()+" &aEnter a new message or &aedit &athe &amessage: "));

		TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
		edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(actualMsg)));
		edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current message")).create()));

		TextComponent newName = new TextComponent(StringConverter.coloredString("&a&l[NEW]"));
		newName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type the new message here.."));
		newName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder(StringConverter.coloredString("&aClick here to set new message")).create()));

		TextComponent noMsg = new TextComponent(StringConverter.coloredString("&c&l[NO MSG]"));
		noMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "NO MESSAGE"));
		noMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder(StringConverter.coloredString("&cClick here to set no msg")).create()));

		
		message.addExtra(new TextComponent(" "));
		message.addExtra(edit);
		message.addExtra(new TextComponent(" "));
		message.addExtra(newName);
		message.addExtra(new TextComponent(" "));
		message.addExtra(noMsg);

		p.spigot().sendMessage(message);
		space(p);
	}

	public void shiftClicked(Player p, ItemStack item) {
		if(item != null) {
			if(item.hasItemMeta()) {
				SPlugin sPlugin = cache.get(p).getsPlugin();
				SObject sObject = cache.get(p).getSObject();
				SActivator sAct = cache.get(p).getSAct();
				String name = StringConverter.decoloredString(item.getItemMeta().getDisplayName());
				//String plName = sPlugin.getNameDesign();
				if(name.contains("Reset")) {
					p.closeInventory();
					cache.replace(p, new EntityConditionsMessagesGUI(sPlugin, sObject, sAct, new EntityConditions(), cache.get(p).getDetail()));
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
				else {
					String detail = cache.get(p).getDetail();
					saveEntityConditionsEI(p);
					sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
					EntityConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getTargetEntityConditions(), detail);
				}
			}
		}
	}

	public void receivedMessage(Player p, String message) {
		//SPlugin sPlugin = cache.get(p).getsPlugin();
		//SObject sObject = cache.get(p).getSObject();
		//SActivator sAct = cache.get(p).getSAct();
		//String plName = sPlugin.getNameDesign();

		String request = requestWriting.get(p);

		if(message .contains("NO MESSAGE")) cache.get(p).updateMessage(request, "");
		else cache.get(p).updateMessage(request, message);
		requestWriting.remove(p);
		cache.get(p).openGUISync(p);
	}

	public void saveEntityConditionsEI(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		
		EntityConditions eC = cache.get(p).getConditions();
		eC.setIfAdultMsg(cache.get(p).getMessage(EntityConditionsMessagesGUI.IF_ADULT_MSG));
		eC.setIfBabyMsg(cache.get(p).getMessage(EntityConditionsMessagesGUI.IF_BABY_MSG));
		eC.setIfEntityHealthMsg(cache.get(p).getMessage(EntityConditionsMessagesGUI.IF_ENTITY_HEALTH_MSG));
		eC.setIfGlowingMsg(cache.get(p).getMessage(EntityConditionsMessagesGUI.IF_GLOWING_MSG));
		eC.setIfInvulnerableMsg(cache.get(p).getMessage(EntityConditionsMessagesGUI.IF_INVULNERABLE_MSG));
		eC.setIfNameMsg(cache.get(p).getMessage(EntityConditionsMessagesGUI.IF_NAME_MSG));
		eC.setIfNotEntityTypeMsg(cache.get(p).getMessage(EntityConditionsMessagesGUI.IF_NOT_ENTITY_TYPE_MSG));
		eC.setIfPoweredMsg(cache.get(p).getMessage(EntityConditionsMessagesGUI.IF_POWERED_MSG));

		EntityConditions.saveEntityConditions(sPlugin, sObject, sActivator, eC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}


	public static EntityConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new EntityConditionsMessagesGUIManager();
		return instance;
	}	
}
