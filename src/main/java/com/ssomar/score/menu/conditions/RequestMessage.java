package com.ssomar.score.menu.conditions;

import org.bukkit.entity.Player;

import com.ssomar.score.utils.StringConverter;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public interface RequestMessage {
	
	@SuppressWarnings("deprecation")
	public static void sendRequestMessage(RequestMessageInfo infos) {
		infos.player.closeInventory();
		space(infos.player);

		TextComponent message = new TextComponent(
				StringConverter.coloredString("&a&l"+infos.sPlugin.getNameDesign()+" &aEnter a new message or &aedit &athe &amessage: "));

		TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
		edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(infos.actualMsg)));
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

		infos.player.spigot().sendMessage(message);
		space(infos.player);
	}
	
	public static void space(Player p) {
		p.sendMessage("");
	}

}
