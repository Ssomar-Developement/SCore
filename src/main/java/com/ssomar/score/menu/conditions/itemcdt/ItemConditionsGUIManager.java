package com.ssomar.score.menu.conditions.itemcdt;

import org.bukkit.entity.Player;

import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.ConditionsGUIManager;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.ItemConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;

public class ItemConditionsGUIManager extends GUIManagerSCore<ItemConditionsGUI>{

	private static ItemConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, ItemConditions iC, String detail) {
		cache.put(p, new ItemConditionsGUI(sPlugin, sObject, sActivator, iC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<ItemConditionsGUI> i) {
		if(i.name.contains("Save")) {
			saveItemConditionsEI(i.player);
			i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sObject.getActivator(i.sActivator.getID()));
		}

		else if(i.name.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
		}
		else return false;
		
		return true;
	}

	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<ItemConditionsGUI> i) {
		/*======================= A AMELIORER CEST NIMPORTE QUOI YA TROP DE REPETITION =========================*/
		if(i.name.contains(ItemConditionsGUI.IF_DURABILITY)) {
			requestWriting.put(i.player, ItemConditionsGUI.IF_DURABILITY);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF DURABILITY:"));

			this.showCalculationGUI(i.player, "Durability", cache.get(i.player).getIfDurability());
			space(i.player);
		}
		else if(i.name.contains(ItemConditionsGUI.IF_USAGE)) {
			requestWriting.put(i.player, ItemConditionsGUI.IF_USAGE);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF USAGE:"));

			this.showCalculationGUI(i.player, "Usage", cache.get(i.player).getIfUsage());
			space(i.player);
		}
		else if(i.name.contains(ItemConditionsGUI.IF_USAGE2)) {
			requestWriting.put(i.player, ItemConditionsGUI.IF_USAGE2);
			i.player.closeInventory();
			space(i.player);
			i.player.sendMessage(StringConverter.coloredString("&a&l"+i.sPlugin.getNameDesign()+" &2&lEDITION IF USAGE2:"));

			this.showCalculationGUI(i.player, "Usage", cache.get(i.player).getIfUsage2());
			space(i.player);
		}
		/* =========================================================================== */
		else return false;
		
		return true;
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<ItemConditionsGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<ItemConditionsGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<ItemConditionsGUI> i) {
		String detail = cache.get(i.player).getDetail();
		saveItemConditionsEI(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
		ItemConditionsMessagesGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sObject.getActivator(i.sActivator.getID()).getItemConditions(), detail);
	
		return true;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<ItemConditionsGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<ItemConditionsGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<ItemConditionsGUI> i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<ItemConditionsGUI> interact) {
		// TODO Auto-generated method stub
		return false;
	}

	public void receivedMessage(Player p, String message) {
		boolean notExit = true;

		SPlugin sPlugin = cache.get(p).getsPlugin();
		//SObject sObject = cache.get(p).getSObject();
		//SActivator sAct = cache.get(p).getSAct();
		String plName = sPlugin.getNameDesign();

		String editMessage = message.trim();
		if(editMessage.contains("exit")) {
			boolean pass = false;
			if(StringConverter.decoloredString(editMessage).equals("exit with delete")) {
				if(requestWriting.get(p).equals(ItemConditionsGUI.IF_DURABILITY)) {
					cache.get(p).updateIfDurability("");
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}
				else if(requestWriting.get(p).equals(ItemConditionsGUI.IF_USAGE)) {
					cache.get(p).updateIfUsage("");
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}
				else if(requestWriting.get(p).equals(ItemConditionsGUI.IF_USAGE2)) {
					cache.get(p).updateIfUsage2("");
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}
				pass=true;
			}
			if(StringConverter.decoloredString(editMessage).equals("exit") || pass) {
				/*if(requestWriting.get(p).equals("WEATHER")) {
					List<String> result= new ArrayList<>();
					for(String str : currentWriting.get(p)) {
						result.add(str);
					}
					cache.get(p).updateIfWeather(result);
				}*/
				currentWriting.remove(p);
				requestWriting.remove(p);
				cache.get(p).openGUISync(p);
				notExit = false;
			}
		}
		if(notExit) {
			if(editMessage.contains("delete line <")) {	
				this.deleteLine(editMessage, p);
				//if(requestWriting.get(p).equals("WEATHER")) this.showIfWeatherEditor(p);
				space(p);
				space(p);			
			}
			else if(requestWriting.get(p).equals(ItemConditionsGUI.IF_DURABILITY)) {
				if(StringCalculation.isStringCalculation(editMessage)) {
					cache.get(p).updateIfDurability(editMessage);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for durability please !"));
					this.showCalculationGUI(p, "Durability", cache.get(p).getIfDurability());
				}
			}
			else if(requestWriting.get(p).equals(ItemConditionsGUI.IF_USAGE)) {
				if(StringCalculation.isStringCalculation(editMessage)) {
					cache.get(p).updateIfUsage(editMessage);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for usage please !"));
					this.showCalculationGUI(p, "Usage", cache.get(p).getIfUsage());
				}
			}
			else if(requestWriting.get(p).equals(ItemConditionsGUI.IF_USAGE2)) {
				if(StringCalculation.isStringCalculation(editMessage)) {
					cache.get(p).updateIfUsage2(editMessage);
					requestWriting.remove(p);
					cache.get(p).openGUISync(p);
				}else {
					p.sendMessage(StringConverter.coloredString("&c&l"+plName+" &4&lERROR &cEnter a valid condition for usage please !"));
					this.showCalculationGUI(p, "Usage", cache.get(p).getIfUsage());
				}
			}
		}
	}
	
	public void saveItemConditionsEI(Player p) {

		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sAct = cache.get(p).getSAct();
		//String plName = sPlugin.getNameDesign();

		ItemConditions iC = (ItemConditions) cache.get(p).getConditions();

		iC.setIfDurability(cache.get(p).getIfDurability());
		iC.setIfUsage(cache.get(p).getIfUsage());
		iC.setIfUsage2(cache.get(p).getIfUsage2());

		ItemConditions.saveItemConditions(sPlugin, sObject, sAct, iC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}


	public static ItemConditionsGUIManager getInstance() {
		if(instance == null) instance = new ItemConditionsGUIManager();
		return instance;
	}

}
