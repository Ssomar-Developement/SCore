package com.ssomar.score.menu.conditions;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.Comparator;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.PlaceholdersCdtType;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.PlaceholdersCondition;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class PlaceholdersConditionGUI extends ConditionGUIAbstract{

	private boolean newPlaceholdersCondition = false;

	public final static String TITLE = "Editor - Plch condition";
	public final static String TYPE = "Type";
	public final static String PART1 = "First part";
	public final static String COMPARATOR = "Comparator";
	public final static String PART2 = "Second part";
	public final static String MESSAGE = "Message if not valid";
	public final static String CANCEL_EVENT = "Cancel event if not valid";
	public final static String CDT_ID = "Cdt ID:";

	public PlaceholdersConditionGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, List<PlaceholdersCondition> list, String detail) {
		super(TITLE, 6 * 9, sPlugin, sObject, sActivator, detail);
		newPlaceholdersCondition = true;

		int id = 1;
		for (int i = 0; i < list.size(); i++) {
			for (PlaceholdersCondition pC : list) {
				if (pC.getId().equals("plchC" + id)) {
					id++;
				}
			}
		}
		String idStr = "plchC" + id;

		PlaceholdersCondition pC = new PlaceholdersCondition(idStr, PlaceholdersCdtType.PLAYER_NUMBER, "", "", Comparator.EQUALS, 0);

		this.fillTheGUI(sPlugin, sObject, sActivator, pC, true);
	}

	public PlaceholdersConditionGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, PlaceholdersCondition pC, String detail) {
		super("&8&l"+sPlugin.getShortName()+" "+TITLE, 6 * 9, sPlugin, sObject, sActivator, detail);
		this.fillTheGUI(sPlugin, sObject, sActivator, pC, false);
	}

	public void fillTheGUI(SPlugin sPlugi, SObject sObject, SActivator sActivator, PlaceholdersCondition pC, boolean newPlaceholdersCondition) {

		//String str = "";
		//if (PlaceholderAPI.isLotOfWork()) str = "&7&oPremium";
		
		// Main Options
		createItem(Material.NAME_TAG, 				1, 0, TITLE_COLOR+TYPE, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateType(pC.getType());
		
		createItem(Material.PAPER, 					1, 1, TITLE_COLOR+PART1, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: &e"+ pC.getPart1());

		createItem(Material.ARROW, 				    1, 2, TITLE_COLOR+COMPARATOR, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateComparator(pC.getComparator());
		
		if(PlaceholdersCdtType.getpCdtTypeWithNumber().contains(pC.getType()))
		createItem(Material.PAPER, 					1, 3, TITLE_COLOR+PART2, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: &e"+ pC.getPart2Number());

		else createItem(Material.PAPER, 			1, 3, TITLE_COLOR+PART2, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: &e"+ pC.getPart2String());

		createItem(WRITABLE_BOOK, 					1, 4, TITLE_COLOR+MESSAGE, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: &e"+ pC.getMessage());

		createItem(Material.LEVER, 					1, 5, TITLE_COLOR+CANCEL_EVENT, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateBoolean(CANCEL_EVENT, pC.isCancelEvent());

		
		// exit
		createItem(RED, 	1, 45, "&4&l▶&c Back to Placeholders Conditions", false, false);

		// Reset menu
		createItem(ORANGE, 			1, 46, "&4&l✘ &cReset", false, false, "", "&c&oClick here to reset", "&c&oall options of this Placeholders Condition");

		createItem(Material.BOOK, 					1, 49, TITLE_COLOR+CDT_ID, false, false, "", "&7actually: &e" + pC.getId());
		
		createItem(Material.BOOK, 					1, 50, COLOR_ACTIVATOR_ID, false, false, "", "&7actually: &e" + sActivator.getID());

		createItem(Material.BOOK, 					1, 51, COLOR_OBJECT_ID, false, false, "", "&7actually: &e" + sObject.getID());

		// Save menu
		if(newPlaceholdersCondition) {
			createItem(GREEN, 1, 53, "&2&l✔ &aCreate this Placeholders Condition", false, false, "", "&a&oClick here to create this", "&a&oPlaceholders Condition");
		}
		else {
			createItem(GREEN, 1, 53, "&2&l✔ &aSave this Placeholders Condition", false, false, "", "&a&oClick here to save this", "&a&oPlaceholders Condition");
		}
	}

	public void changeType() {
		ItemStack item = this.getByName(TYPE);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		PlaceholdersCdtType type = PlaceholdersCdtType.PLAYER_NUMBER;
		for (String str : lore) {
			str = StringConverter.decoloredString(str);
			if (str.contains("➤")) {
				type = PlaceholdersCdtType.valueOf(str.split("➤ ")[1]).getNext();
				break;
			}
		}
		this.updateType(type);
	}

	public void updateType(PlaceholdersCdtType option) {
//		if (SsomarDev.isLotOfWork() && Option.getPremiumOption().contains(option)) {
//			this.updateOption(option.getNext());
//			return;
//		}
		ItemStack item = this.getByName(TYPE);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore().subList(0, 2);
		boolean find = false;
		
		int cast =17;
		if(PlaceholdersCdtType.values().length+2<17) cast = PlaceholdersCdtType.values().length+2;
		
		for (PlaceholdersCdtType opt : PlaceholdersCdtType.values()) {
			if (option.equals(opt)) {
				lore.add(StringConverter.coloredString("&2➤ &a" + option));
				find=true;
			}	
			else if(find){
				if(lore.size() == cast) break;
				lore.add(StringConverter.coloredString("&6✦ &e" + opt));
			}
		}
		for (PlaceholdersCdtType opt : PlaceholdersCdtType.values()) {
			if (lore.size() == cast) break;
			else {
				lore.add(StringConverter.coloredString("&6✦ &e" + opt));
			}
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public PlaceholdersCdtType getType() {
		ItemStack item = this.getByName(TYPE);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		for (String str : lore) {
			if (str.contains("➤ ")) {
				str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
				return PlaceholdersCdtType.valueOf(str.split("➤ ")[1]);
			}
		}
		return null;
	}
	
	public void changeComparator() {
		ItemStack item = this.getByName(COMPARATOR);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		Comparator type = Comparator.EQUALS;
		for (String str : lore) {
			str = StringConverter.decoloredString(str);
			if (str.contains("➤")) {
				type = Comparator.valueOf(str.split("➤ ")[1]).getNext();
				break;
			}
		}
		this.updateComparator(type);
	}

	public void updateComparator(Comparator option) {
//		if (SsomarDev.isLotOfWork() && Option.getPremiumOption().contains(option)) {
//			this.updateOption(option.getNext());
//			return;
//		}
		ItemStack item = this.getByName(COMPARATOR);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore().subList(0, 2);
		boolean find = false;
		
		int cast =17;
		if(Comparator.values().length+2<17) cast = Comparator.values().length+2;
		
		for (Comparator opt : Comparator.values()) {
			if (option.equals(opt)) {
				lore.add(StringConverter.coloredString("&2➤ &a" + option));
				find=true;
			}	
			else if(find){
				if(lore.size() == cast) break;
				lore.add(StringConverter.coloredString("&6✦ &e" + opt));
			}
		}
		for (Comparator opt : Comparator.values()) {
			if (lore.size() == cast) break;
			else {
				lore.add(StringConverter.coloredString("&6✦ &e" + opt));
			}
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	public Comparator getComparator() {
		ItemStack item = this.getByName(COMPARATOR);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		for (String str : lore) {
			if (str.contains("➤ ")) {
				str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
				return Comparator.valueOf(str.split("➤ ")[1]);
			}
		}
		return null;
	}

	public boolean isNewPlaceholdersCondition() {
		return newPlaceholdersCondition;
	}

	public void setNewPlaceholdersCondition(boolean newPlaceholdersCondition) {
		this.newPlaceholdersCondition = newPlaceholdersCondition;
	}	
	
}
