package com.ssomar.score.menu.activator.requiredei;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.ssomar.executableitems.items.Item;
import com.ssomar.executableitems.items.ItemManager;
import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEI;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEIManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class RequiredEIGUIManager extends GUIManagerSCore<RequiredEIGUI>{

	private static RequiredEIGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator activator) {
		cache.put(p, new RequiredEIGUI(sPlugin,sObject, activator));
		cache.get(p).openGUISync(p);
	}

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator activator, RequiredEI rEI) {
		cache.put(p, new RequiredEIGUI(sPlugin, sObject, activator, rEI));
		cache.get(p).openGUISync(p);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean allClicked(InteractionClickedGUIManager<RequiredEIGUI> i) {
		
		if(i.name.contains(RequiredEIGUI.CONSUME)) cache.get(i.player).changeBoolean(RequiredEIGUI.CONSUME);

		else if(i.name.contains(RequiredEIGUI.EI_ID)) {
			requestWriting.put(i.player, RequiredEIGUI.EI_ID);
			i.player.closeInventory();
			space(i.player);

			TextComponent message = new TextComponent( StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &aSelect an ID below: "));
			i.player.spigot().sendMessage(message);

			List<TextComponent> listItems = new ArrayList<>();
			for(Item _i : ItemManager.getInstance().getLoadedItems()) {
				TextComponent newText;

				newText = new TextComponent( StringConverter.coloredString("&5&l[&d&l"+_i.getIdentification()+"&5&l]"));
				newText.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, _i.getIdentification() ));
				newText.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aSelect the EI with ID: &e"+_i.getIdentification()) ).create() ) );

				listItems.add(newText);
			}

			for(int j = 0 ; j < listItems.size(); j++) {
				TextComponent result;
				if(j+1 != listItems.size()) {
					(result = listItems.get(j)).addExtra("  ");
					result.addExtra(listItems.get(j+1));
					j++;
				}
				else {
					result= listItems.get(j);
				}
				i.player.spigot().sendMessage(result);
			}
			space(i.player);
		}

		else if(i.name.contains(RequiredEIGUI.AMOUNT)) {
			requestWriting.put(i.player, RequiredEIGUI.AMOUNT);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &aEnter a cooldown (min 0, in seconds):"));
			space(i.player);
		}

		else if(i.name.contains(RequiredEIGUI.VALID_USAGES)) {
			requestWriting.put(i.player, RequiredEIGUI.VALID_USAGES);
			if(!currentWriting.containsKey(i.player)) {

				List<String> lore= cache.get(i.player).getByName(RequiredEIGUI.VALID_USAGES).getItemMeta().getLore().subList(3, cache.get(i.player).getByName(RequiredEIGUI.VALID_USAGES).getItemMeta().getLore().size());
				List<String> convert = new ArrayList<>();

				for(String str: lore) {
					if(str.contains("ALL USAGE IS VALID")) break;
					else {
						convert.add(str);	
					}
				}
				currentWriting.put(i.player, convert);
			}
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION VALID USAGES:"));
			this.showValidUsagesEditor(i.player);
			space(i.player);
		}

		else if(i.name.contains("Save") || i.name.contains("Create this required EI")) {
			this.saveTheConfiguration(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getId());
			RequiredEIsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
		}

		else if(i.name.contains("Back")) {
			RequiredEIsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
		}
		else return false;
		
		return true;
	}

	public void receivedMessage(Player p, String message) {
		boolean notExit = true;
		
		SPlugin sPlugin = cache.get(p).getsPlugin();
		//SObject sObject = cache.get(p).getSObject();
		//SActivator sAct = cache.get(p).getSAct();
		String plName = sPlugin.getNameDesign();

		if(message.contains("exit")) {
			boolean pass=false;
			if(StringConverter.decoloredString(message).equals("exit with delete")) {
				currentWriting.get(p).clear();
				pass=true;
			}
			if(StringConverter.decoloredString(message).equals("exit") || pass) {
				if(requestWriting.get(p).equals(RequiredEIGUI.VALID_USAGES)) {
					List<Integer> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						result.add(Integer.valueOf(StringConverter.decoloredString(str).split("➤ ")[1]));
					}
					cache.get(p).updateValidUsages(result);
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
				if(requestWriting.get(p).equals(RequiredEIGUI.VALID_USAGES)) this.showValidUsagesEditor(p);
				space(p);
				space(p);			
			}

			else if(message.contains("up line <")) {
				space(p);
				space(p);
				int line= Integer.parseInt(message.split("up line <")[1].split(">")[0]);
				if (line!=0) {
					String current = currentWriting.get(p).get(line);
					currentWriting.get(p).set(line, currentWriting.get(p).get(line-1));
					currentWriting.get(p).set(line-1, current);
				}
				if(requestWriting.get(p).equals(RequiredEIGUI.VALID_USAGES)) this.showValidUsagesEditor(p);
				space(p);
				space(p);
			}

			else if(message.contains("down line <")) {
				space(p);
				space(p);
				int line= Integer.parseInt(message.split("down line <")[1].split(">")[0]);
				if (line!=currentWriting.get(p).size()-1) {
					String current = currentWriting.get(p).get(line);
					currentWriting.get(p).set(line, currentWriting.get(p).get(line+1));
					currentWriting.get(p).set(line+1, current);
				}
				if(requestWriting.get(p).equals(RequiredEIGUI.VALID_USAGES)) this.showValidUsagesEditor(p);
				space(p);
				space(p);
			}

			else if(message.contains("edit line <")) {
				space(p);
				space(p);
				int line=0;
				boolean error=false;
				try {
					line = Integer.parseInt(message.split("edit line <")[1].split(">")[0]);
					String newLine;
					if(message.split("edit line <")[1].split("->").length==1) newLine="&7";
					else newLine=  message.split("edit line <")[1].split("->")[1];
					editLine(p, line, newLine);	
				}
				catch(NumberFormatException e) {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &cError incorrect number line, dont edit the message before the '->' !"));
					error=true;
				}
				if(!error) {
					p.sendMessage(StringConverter.coloredString("&a&l"+plName+" &2&lEDITION &aYou have edit the line: "+line+" !"));
					if(requestWriting.get(p).equals(RequiredEIGUI.VALID_USAGES)) this.showValidUsagesEditor(p);
				}
				space(p);
				space(p);
			}
			else if(requestWriting.get(p).equals(RequiredEIGUI.EI_ID)) {
				boolean error=true;
				for(Item it : ItemManager.getInstance().getLoadedItems()) {
					if(it.getIdentification().equalsIgnoreCase(message.replaceAll(" ", ""))) {
						requestWriting.remove(p);
						cache.get(p).updateActually(RequiredEIGUI.EI_ID, message);
						cache.get(p).openGUISync(p);
						error=false;
					}

				}
				if(error) p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &cError incorrect EI ID !"));
			}
			else if (requestWriting.get(p).equals(RequiredEIGUI.AMOUNT)) {

				boolean error=true;
				if(!message.replaceAll(" ", "").isEmpty()) {
					try {
						if(Integer.parseInt(message)>0) {
							cache.get(p).updateInt(RequiredEIGUI.AMOUNT, Integer.parseInt(message));
							cache.get(p).openGUISync(p);
							requestWriting.remove(p);
							error=false;
						}
					} catch (Exception ignored) {}
				}
				if(error) p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &cError invalid amount pls select an amount > 0 !"));
			}
			else if(requestWriting.get(p).equals(RequiredEIGUI.VALID_USAGES)) {
				boolean error=true;
				if(!message.replaceAll(" ", "").isEmpty()) {
					try {
						if(Integer.parseInt(message)>0) {
							if(currentWriting.containsKey(p)) {
								currentWriting.get(p).add("&6➤ &e"+Integer.valueOf(message));
							}
							else {
								ArrayList<String> list = new ArrayList<>();
								list.add(StringConverter.coloredString("&6➤ &e"+Integer.valueOf(message)));
								currentWriting.put(p, list);
							}
							error=false;
						}
					} catch (Exception ignored) {}
					this.showValidUsagesEditor(p);
				}
				if(error) p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &cError invalid valid usage pls select a valid usage > 0 !"));
			}
		}

	}

	public void showValidUsagesEditor(Player p) {
		p.sendMessage(StringConverter.coloredString("&7➤ Valid usages:"));
		space(p);
		space(p);
		showEditor(p , "validusage");
	}


	@SuppressWarnings("deprecation")
	public void showEditor(Player p, String type) {
		int cpt=0;
		if(currentWriting.containsKey(p) && currentWriting.get(p)!=null ) {
			if(!currentWriting.get(p).isEmpty()) {
				for(String s: currentWriting.get(p)) {

					TextComponent message;
					if(type.equals("lore")) {
						message = new TextComponent( StringConverter.coloredString("&7"+cpt+"."+StringConverter.coloredString("&5"+s)));
					}
					else message = new TextComponent( StringConverter.coloredString("&7"+cpt+"."+StringConverter.coloredString(s)));

					TextComponent edit=null;
					if(!type.equals("validusage")) {
						edit = new TextComponent( StringConverter.coloredString("&e&l[EDIT]"));
						edit.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "edit line <"+cpt+"> ->"+s.replaceAll("§", "&") ));
						edit.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&eClick Here to edit line: "+ cpt) ).create() ) );
					}

					TextComponent delete = new TextComponent( StringConverter.coloredString("&c&l[X]"));
					delete.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "delete line <"+cpt+">"));
					delete.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&cClick here to delete the line: "+ cpt) ).create() ) );

					TextComponent downLine = new TextComponent( StringConverter.coloredString("&a&l[↓]"));
					downLine.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "down line <"+cpt+">"));
					downLine.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&cClick here to go down the line: "+ cpt) ).create() ) );

					TextComponent upLine = new TextComponent( StringConverter.coloredString("&a&l[↑]"));
					upLine.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "up line <"+cpt+">"));
					upLine.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&cClick here to go up the line: "+ cpt) ).create() ) );
					if(!type.equals("validusage")) {
						message.addExtra(new TextComponent(" "));
						message.addExtra(edit);
					}
					message.addExtra(new TextComponent(" "));
					message.addExtra(delete);
					message.addExtra(new TextComponent(" "));
					message.addExtra(downLine);
					message.addExtra(upLine);

					p.spigot().sendMessage(message);		
					cpt++;		
				}
			}
			else p.sendMessage(StringConverter.coloredString("&7EMPTY"));

		}else p.sendMessage(StringConverter.coloredString("&7EMPTY"));

		space(p);
		space(p);

		TextComponent message = new TextComponent(StringConverter.coloredString("&7➤Options: "));

		TextComponent finish = new TextComponent( StringConverter.coloredString("&4&l[FINISH]"));
		finish.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "exit"));
		finish.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&4Click Here when you have finish to edit the "+ type) ).create() ) );

		TextComponent finishwithdelete = new TextComponent( StringConverter.coloredString("&a&l[ACCEPT ALL USAGES]"));
		finishwithdelete.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "exit with delete"));
		finishwithdelete.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&aClick Here if you want accept all usages") ).create() ) );

		TextComponent addLine;
		addLine = new TextComponent( StringConverter.coloredString("&2&l[ADD USAGE]"));
		addLine.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "Type the valid usage here.."));
		addLine.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( StringConverter.coloredString("&2Click Here if you want add new " +type) ).create() ) );



		message.addExtra(finish);
		message.addExtra(new TextComponent(" "));
		message.addExtra(finishwithdelete);
		message.addExtra(new TextComponent(" "));
		message.addExtra(addLine);
		p.spigot().sendMessage(message);

	}

	public static RequiredEIGUIManager getInstance() {
		if(instance == null) instance = new RequiredEIGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sAct = cache.get(p).getSAct();
		//String plName = sPlugin.getNameDesign();
		
		RequiredEI rEI = new RequiredEI(cache.get(p).getActually(RequiredEIGUI.ID));
		rEI.setAmount(cache.get(p).getInt(RequiredEIGUI.AMOUNT));
		rEI.setEI_ID(cache.get(p).getActually(RequiredEIGUI.EI_ID));
		rEI.setValidUsages(cache.get(p).getValidUsages());
		rEI.setConsume(cache.get(p).getBoolean(RequiredEIGUI.CONSUME));

		RequiredEIManager.saveRequiredEI(sPlugin, sObject, sAct, rEI);
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getId());
	}

	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<RequiredEIGUI> i) {
		return false;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<RequiredEIGUI> i) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<RequiredEIGUI> i) {
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<RequiredEIGUI> i) {
		return false;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<RequiredEIGUI> i) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<RequiredEIGUI> i) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<RequiredEIGUI> i) {
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<RequiredEIGUI> interact) {
		return false;
	}
}
