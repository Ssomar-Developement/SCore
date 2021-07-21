package com.ssomar.score.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendMessage {
	
	private StringPlaceholder sp = new StringPlaceholder();

	public void sendMessage(Player p, String s) {
		CommandSender cs = (CommandSender)p;
		this.sendMessage(cs, s);
	}
	
	public void sendMessage(CommandSender cs, String s) {
		String prepareMsg = s;
		prepareMsg = sp.replacePlaceholder(prepareMsg);
		prepareMsg = StringConverter.coloredString(prepareMsg);
		if(!(prepareMsg.isEmpty() || StringConverter.decoloredString(prepareMsg).isEmpty())) cs.sendMessage(prepareMsg);
	}
	
	public static void sendMessageNoPlch(Player p, String s) {
		CommandSender cs = (CommandSender)p;
		sendMessageNoPlch(cs, s);
	}
	
	public static void sendMessageNoPlch(CommandSender cs, String s) {
		String prepareMsg = s;
		prepareMsg = StringConverter.coloredString(prepareMsg);
		if(!(prepareMsg.isEmpty() || StringConverter.decoloredString(prepareMsg).isEmpty())) cs.sendMessage(prepareMsg);
	}

	public StringPlaceholder getSp() {
		return sp;
	}

	public void setSp(StringPlaceholder sp) {
		this.sp = sp;
	}
	
	public void resetSp() {
		this.sp = new StringPlaceholder();
	}
	
}
