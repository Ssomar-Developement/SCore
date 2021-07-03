package com.ssomar.score.menu.conditions.itemcdt;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.GUIManager;
import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.ItemConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class ItemConditionsMessagesGUIManager extends GUIManager<ItemConditionsMessagesGUI>{

	private static ItemConditionsMessagesGUIManager instance;	

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, ItemConditions iC, String detail) {
		cache.put(p, new ItemConditionsMessagesGUI(sPlugin, sObject, sActivator, iC, detail));
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
					cache.replace(p, new ItemConditionsMessagesGUI(sPlugin, sObject, sAct, new ItemConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
					saveItemConditionsEI(p);
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

					if(name.contains(ItemConditionsMessagesGUI.IF_DURABILITY_MSG)) {
						requestWriting.put(p, ItemConditionsMessagesGUI.IF_DURABILITY_MSG);
						this.sendRequestMessage(sPlugin, p, cache.get(p).getActuallyWithColor(ItemConditionsMessagesGUI.IF_DURABILITY_MSG));
					}
					
					else if(name.contains(ItemConditionsMessagesGUI.IF_USAGE_MSG)) {
						requestWriting.put(p, ItemConditionsMessagesGUI.IF_USAGE_MSG);
						this.sendRequestMessage(sPlugin, p, cache.get(p).getActuallyWithColor(ItemConditionsMessagesGUI.IF_USAGE_MSG));
					}
					
					else if(name.contains(ItemConditionsMessagesGUI.IF_USAGE2_MSG)) {
						requestWriting.put(p, ItemConditionsMessagesGUI.IF_USAGE2_MSG);
						this.sendRequestMessage(sPlugin, p, cache.get(p).getActuallyWithColor(ItemConditionsMessagesGUI.IF_USAGE2_MSG));
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
					cache.replace(p, new ItemConditionsMessagesGUI(sPlugin, sObject, sAct, new ItemConditions(), cache.get(p).getDetail()));
					cache.get(p).openGUISync(p);
				}

				else if(name.contains("Save")) {
					saveItemConditionsEI(p);
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
					saveItemConditionsEI(p);
					sObject = LinkedPlugins.getSObject(sPlugin, sObject.getID());
					ItemConditionsGUIManager.getInstance().startEditing(p, sPlugin, sObject, sAct, sObject.getActivator(sAct.getID()).getItemConditions(), detail);
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

	public void saveItemConditionsEI(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		ItemConditions iC = cache.get(p).getConditions();

		iC.setIfDurabilityMsg(cache.get(p).getMessage(ItemConditionsMessagesGUI.IF_DURABILITY_MSG));
		iC.setIfUsageMsg(cache.get(p).getMessage(ItemConditionsMessagesGUI.IF_USAGE_MSG));
		iC.setIfUsage2Msg(cache.get(p).getMessage(ItemConditionsMessagesGUI.IF_USAGE2_MSG));
		
		ItemConditions.saveItemConditions(sPlugin, sObject, sActivator, iC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}


	public static ItemConditionsMessagesGUIManager getInstance() {
		if(instance == null) instance = new ItemConditionsMessagesGUIManager();
		return instance;
	}
}
