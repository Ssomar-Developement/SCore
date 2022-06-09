package com.ssomar.scoretestrecode.editor;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.commands.CommandsEditor;
import com.ssomar.score.menu.conditions.RequestMessageInfo;
import com.ssomar.score.utils.StringConverter;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

@Getter
public abstract class NewGUIManager<T extends GUI> {

	public HashMap<Player, T> cache;
	public HashMap<Player, String> requestWriting;
	public HashMap<Player, List<String>> currentWriting;

	public NewGUIManager () {
		cache = new HashMap<>();
		requestWriting = new HashMap<>();
		currentWriting = new HashMap<>();
	}

	public void reload() {
		cache = new HashMap<>();
		requestWriting = new HashMap<>();
		currentWriting = new HashMap<>();
	}

	public void clicked(Player p, ItemStack item, ClickType click) {
		NewInteractionClickedGUIManager<T> interact = new NewInteractionClickedGUIManager<>();
		interact.player = p;
		this.clicked(item, interact, click);
	}

	public void clicked(Player p, ItemStack item, String title, ClickType click) {
		NewInteractionClickedGUIManager<T> interact = new NewInteractionClickedGUIManager<>();
		interact.player = p;
		interact.title = title;
		this.clicked(item, interact, click);
	}

	public void clicked(ItemStack item, NewInteractionClickedGUIManager<T> interact, ClickType click) {
		if (item != null && item.hasItemMeta()) {
			interact.cache = this.getCache();
			interact.setName(item.getItemMeta().getDisplayName());
			interact.gui = cache.get(interact.player);

			RequestMessageInfo msgInfos = new RequestMessageInfo();
			msgInfos.player = interact.player;

			interact.msgInfos = msgInfos;

			if (interact.coloredDeconvertName.equals(GUI.RESET)) {
				reset(interact);
			}
			else if (interact.coloredDeconvertName.equals(GUI.BACK)) {
				back(interact);
			}
			else if (interact.coloredDeconvertName.equals(GUI.NEW)) {
				newObject(interact);
			}
			else if (interact.coloredDeconvertName.equals(GUI.EXIT)) {
				interact.player.closeInventory();
			}
			else if (interact.coloredDeconvertName.equals(GUI.SAVE)) {
				save(interact);
			}
			else if (interact.coloredDeconvertName.equals(GUI.NEXT_PAGE)) {
				nextPage(interact);
			}
			else if (interact.coloredDeconvertName.equals(GUI.PREVIOUS_PAGE)) {
				previousPage(interact);
			}
			else {
				if (click.equals(ClickType.SHIFT_LEFT) || click.equals(ClickType.SHIFT_RIGHT)) {
					if (click.equals(ClickType.SHIFT_LEFT)) {
						if (this.shiftLeftClicked(interact)) return;
					}
					else if (this.shiftRightClicked(interact)) return;

					if (this.shiftClicked(interact)) return;
				} else {
					if (click.equals(ClickType.RIGHT)) {
						if (this.noShiftRightclicked(interact)) return;
					}
					else if (this.noShiftLeftclicked(interact)) return;
					if (this.noShiftclicked(interact)) return;
				}

				if (click.equals(ClickType.RIGHT)) {
					if (this.rightClicked(interact)) return;
				}
				else if (this.leftClicked(interact)) return;

				if (this.allClicked(interact)) return;
			}
		}
	}

	public abstract boolean allClicked(NewInteractionClickedGUIManager<T> i);

	public abstract boolean noShiftclicked(NewInteractionClickedGUIManager<T> i);

	public abstract boolean noShiftLeftclicked(NewInteractionClickedGUIManager<T> i);

	public abstract boolean noShiftRightclicked(NewInteractionClickedGUIManager<T> i);

	public abstract boolean shiftClicked(NewInteractionClickedGUIManager<T> i);

	public abstract boolean shiftLeftClicked(NewInteractionClickedGUIManager<T> i);

	public abstract boolean shiftRightClicked(NewInteractionClickedGUIManager<T> i);

	public abstract boolean leftClicked(NewInteractionClickedGUIManager<T> i);

	public abstract boolean rightClicked(NewInteractionClickedGUIManager<T> interact);

	public void receiveMessage(Player p, String message) {
		NewInteractionClickedGUIManager<T> interact = new NewInteractionClickedGUIManager<>();
		interact.player = p;
		interact.setMessage(message);
		interact.gui = cache.get(interact.player);
		receiveMessage(interact);
	}


	public void receiveMessage(NewInteractionClickedGUIManager<T> interact) {
		String message = interact.decoloredMessage;
		boolean pass = false;
		/* The commands editor has the priority */
		if(!CommandsEditor.getInstance().isAsking(interact.player)) {
			if (message.equals("PREVIOUS PAGE")) {
				this.receiveMessagePreviousPage(interact);
				pass = true;
			} else if (message.equals("NEXT PAGE")) {
				this.receiveMessageNextPage(interact);
				pass = true;
			} else if (message.contains("delete line <")) {
				this.receiveMessageDeleteline(interact);
				pass = true;
			} else if (message.contains("up line <")) {
				this.receiveMessageUpLine(interact);
				pass = true;
			} else if (message.contains("down line <")) {
				this.receiveMessageDownLine(interact);
				pass = true;
			}
		}
		if(message.equals("exit")) {
			this.receiveMessageFinish(interact);
			pass = true;
		}
		if(!pass) {
			this.receiveMessageValue(interact);
		}
	}

	public abstract void receiveMessagePreviousPage(NewInteractionClickedGUIManager<T> interact);

	public abstract void receiveMessageNextPage(NewInteractionClickedGUIManager<T> interact);

	public abstract void receiveMessageFinish(NewInteractionClickedGUIManager<T> interact);

	public void receiveMessageDeleteline(NewInteractionClickedGUIManager<T> interact){
		Player p = interact.player;
		space(p);
		space(p);
		int line = Integer.parseInt(interact.decoloredMessage.split("delete line <")[1].split(">")[0]);
		currentWriting.get(p).remove(line);
		p.sendMessage(StringConverter.coloredString("&a&l>> &2&lEDITION &aYou have delete the line: "+line+" !"));
		space(p);
		space(p);
	}

	public void receiveMessageUpLine(NewInteractionClickedGUIManager<T> interact){
		Player p = interact.player;
		space(p);
		space(p);
		int line = Integer.valueOf(interact.decoloredMessage.split("up line <")[1].split(">")[0]);
		if (line != 0) {
			String current = currentWriting.get(p).get(line);
			currentWriting.get(p).set(line, currentWriting.get(p).get(line-1));
			currentWriting.get(p).set(line-1, current);
		}
		p.sendMessage(StringConverter.coloredString("&a&l>> &2&lEDITION &aYou have up the line: "+line+" to "+(line-1)+" !"));
		space(p);
		space(p);
	}

	public void receiveMessageDownLine(NewInteractionClickedGUIManager<T> interact){
		Player p = interact.player;
		space(p);
		space(p);
		int line = Integer.valueOf(interact.decoloredMessage.split("down line <")[1].split(">")[0]);
		if (line != 0) {
			String current = currentWriting.get(p).get(line);
			currentWriting.get(p).set(line, currentWriting.get(p).get(line+1));
			currentWriting.get(p).set(line+1, current);
		}
		p.sendMessage(StringConverter.coloredString("&a&l>> &2&lEDITION &aYou have down the line: "+line+" to "+(line+1)+" !"));
		space(p);
		space(p);
	}

	public abstract void receiveMessageValue(NewInteractionClickedGUIManager<T> interact);

	public abstract void newObject(NewInteractionClickedGUIManager<T> interact);

	public abstract void reset(NewInteractionClickedGUIManager<T> interact);

	public abstract void back(NewInteractionClickedGUIManager<T> interact);

	public abstract void nextPage(NewInteractionClickedGUIManager<T> interact);

	public abstract void previousPage(NewInteractionClickedGUIManager<T> interact);

	public abstract void save(NewInteractionClickedGUIManager<T> interact);

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
}