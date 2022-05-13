package com.ssomar.score.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.ssomar.score.utils.StringConverter;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public abstract class GUIManager<T extends GUI> {

	public HashMap<Player, T> cache;
	public HashMap<Player, String> requestWriting;
	public HashMap<Player, List<String>> currentWriting;
	
	public GUIManager () {
		cache = new HashMap<>();
		requestWriting = new HashMap<>();
		currentWriting = new HashMap<>();
	}
	
	public void reload() {
		cache = new HashMap<>();
		requestWriting = new HashMap<>();
		currentWriting = new HashMap<>();
	}

	public abstract void saveTheConfiguration(Player p);
	
	@SuppressWarnings("deprecation")
	public void showCalculationGUI(Player p, String variable, String current) {
		p.sendMessage(StringConverter.coloredString("&8➤ &7&oReplace {number} by the number of your choice !"));
		TextComponent message = new TextComponent( StringConverter.coloredString("&8➤ &7Choose an option: "));
		
		TextComponent edit = new TextComponent( StringConverter.coloredString("&e&l[EDIT]"));
		edit.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, current));
		edit.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&eClick here to edit the current condition") ).create()));

		message.addExtra(edit);
		message.addExtra(new TextComponent(StringConverter.coloredString(" &7Or create new condition: ")));
		
		TextComponent inf = new TextComponent( StringConverter.coloredString("&a&l[<]"));
		inf.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "<{number}"));
		inf.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick to add condition "+variable+" < {number}") ).create()));
		
		TextComponent infE = new TextComponent( StringConverter.coloredString("&a&l[<=]"));
		infE.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "<={number}"));
		infE.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick to add condition "+variable+" <= {number}") ).create()));
		
		TextComponent egal = new TextComponent( StringConverter.coloredString("&a&l[==]"));
		egal.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "=={number}"));
		egal.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick to add condition "+variable+" == {number}") ).create()));
		
		TextComponent sup = new TextComponent( StringConverter.coloredString("&a&l[>]"));
		sup.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, ">{number}"));
		sup.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick to add condition "+variable+" > {number}") ).create()));
		
		TextComponent supE = new TextComponent( StringConverter.coloredString("&a&l[>=]"));
		supE.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, ">={number}"));
		supE.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick to add condition "+variable+" >= {number}") ).create()));
		
		TextComponent noC = new TextComponent( StringConverter.coloredString("&c&l[NO CONDITION]"));
		noC.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "exit with delete"));
		noC.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick to don't set condition") ).create()));
		
		message.addExtra(inf);
		message.addExtra(new TextComponent(" "));
		message.addExtra(infE);
		message.addExtra(new TextComponent(" "));
		message.addExtra(egal);
		message.addExtra(new TextComponent(" "));
		message.addExtra(sup);
		message.addExtra(new TextComponent(" "));
		message.addExtra(supE);	
		message.addExtra(new TextComponent(" "));
		message.addExtra(noC);	

		p.spigot().sendMessage(message);
	}
	
	public void deleteLine(String message, Player p) {
		space(p);
		space(p);
		int line = Integer.parseInt(message.split("delete line <")[1].split(">")[0]);
		deleteLine(p, line);
		p.sendMessage(StringConverter.coloredString("&a&l[ExecutableItems] &2&lEDITION &aYou have delete the line: "+line+" !"));
		space(p);
		space(p);		
	}
	
	public void deleteLine(Player p, int nb) {
		if(currentWriting.containsKey(p)) {
			currentWriting.get(p).remove(nb);
		}
	}

	public void editLine(Player p, int nb, String edition) {
		
		if(currentWriting.containsKey(p)) {
			if(nb >= currentWriting.get(p).size()) {
				currentWriting.get(p).add(edition);
			}
			else currentWriting.get(p).set(nb, edition);
		}
	}
	
	public void space(Player p) {
		p.sendMessage("");
	}
	
	public String getStringBeforeEnd(String insert) {
		StringBuilder sb= new StringBuilder();
		for(char c : insert.toCharArray()) {
			if(c==',' || c=='}') return sb.toString();
			else sb.append(c);
		}
		return "";
	}
	
	public HashMap<Player, String> getRequestWriting() {
		return requestWriting;
	}

	public void setRequestWriting(HashMap<Player, String> requestWriting) {
		this.requestWriting = requestWriting;
	}

	public HashMap<Player, T> getCache() {
		return cache;
	}

	public void setCache(HashMap<Player, T> cache) {
		this.cache = cache;
	}

	public HashMap<Player, List<String>> getCurrentWriting() {
		return currentWriting;
	}

	public void setCurrentWriting(HashMap<Player, List<String>> currentWriting) {
		this.currentWriting = currentWriting;
	}
	
}
