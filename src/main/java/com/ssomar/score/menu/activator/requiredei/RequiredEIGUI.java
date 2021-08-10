package com.ssomar.score.menu.activator.requiredei;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class RequiredEIGUI extends GUIAbstract{

	private boolean newRequiredEI = false;	
	public static final String ID = "ID:";
	public static final String EI_ID = "ExecutableItem ID";
	public static final String AMOUNT = "Amount";
	public static final String CONSUME = "Consume";
	public static final String VALID_USAGES = "Valid usages";

	public RequiredEIGUI(SPlugin sPlugin, SObject sObject, SActivator activator) {
		super("&8&l"+sPlugin.getShortName()+" Editor - RequiredEI", 4*9, sPlugin, sObject, activator);
		newRequiredEI = true;

		int id=1;
		List<RequiredEI> rEIs = activator.getRequiredExecutableItems();
		for (int i = 0; i < rEIs.size(); i++) {
			for(RequiredEI rEI: rEIs) {
				if(rEI.getId().equals("requiredEI"+id)) {
					id++;
				}
			}
		}
		String idStr = "requiredEI"+id;
		RequiredEI rEI = new RequiredEI(idStr);
		this.fillTheGUI(rEI);
	}
	
	public RequiredEIGUI(SPlugin sPlugin, SObject sObject, SActivator activator, RequiredEI rEI) {
		super("&8&l"+sPlugin.getShortName()+" Editor - RequiredEI", 4*9, sPlugin, sObject, activator);
		this.fillTheGUI(rEI);
	}

	public void fillTheGUI(RequiredEI rEI) {
		//Main Options
		createItem(Material.NAME_TAG,						1 , 0, 	TITLE_COLOR+EI_ID, 	false,	false, "", "&a✎ Click here to change", "&7actually:");
		this.updateActually(EI_ID, rEI.getEI_ID());
		
		createItem(Material.CHEST,							1 , 1, 	TITLE_COLOR+AMOUNT, 	false,	false, "", "&a✎ Click here to change", "&7actually:");
		this.updateInt(AMOUNT, rEI.getAmount());
		
		createItem(Material.LEVER,							1 , 2, 	TITLE_COLOR+CONSUME, 	false,	false, "", "&a✎ Click here to change", "&7actually:" );
		this.updateBoolean(CONSUME, rEI.isConsume());
		
		createItem(Material.REDSTONE,						1 , 3, 	TITLE_COLOR+VALID_USAGES, 	false,	false, "&7&oNot necessary if you don't want a verification of usage", "&a✎ Click here to change", "&7actually:", "" );
		this.updateValidUsages(rEI.getValidUsages());

		createItem(Material.BOOK,							1 , 4, 	"&a&l"+ID, 	false,	false, "", "&7actually: &e"+rEI.getId());
		createItem(Material.BOOK, 							1 , 33, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getID());
		createItem(Material.BOOK, 							1 , 34, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());

		//Reset menu
		createItem(ORANGE, 					1 , 28, "&4&l✘ &cReset", 		false,	false, 	"", "&c&oClick here to reset", "&c&oall options of this required EI" );
		// exit
		createItem(RED, 					1 , 27, "&4&l▶&c Back to the list of required EI", 		false,	false);
		//Save menu
		createItem(GREEN, 					1 , 35, "&2&l✔ &aSave this required EI", 		false,	false, 	"", "&a&oClick here to save this" , "&a&orequired EI" );
	}
	
	public void updateValidUsages(List<Integer> list) {
		ItemStack item = this.getByName(VALID_USAGES);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate= toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eALL USAGE IS VALID"));
		else {
			for(Integer n: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+n.toString()));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<Integer> getValidUsages(){
		ItemStack item = this.getByName(VALID_USAGES);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore().subList(3, meta.getLore().size());
		List<Integer> result = new ArrayList<>();
		for(String str: lore) {
			if(str.contains("ALL USAGE IS VALID")) break;
			else result.add(Integer.valueOf(StringConverter.decoloredString(str).split("➤ ")[1]));
		}
		return result;
	}

	public boolean isNewRequiredEI() {
		return newRequiredEI;
	}

	public void setNewRequiredEI(boolean newRequiredEI) {
		this.newRequiredEI = newRequiredEI;
	}

	@Override
	public void reloadGUI() {
		RequiredEI rEI = new RequiredEI(this.getActually(RequiredEIGUI.ID));
		this.fillTheGUI(rEI);
	}



}
