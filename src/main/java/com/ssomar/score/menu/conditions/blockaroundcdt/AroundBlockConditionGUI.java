package com.ssomar.score.menu.conditions.blockaroundcdt;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockCondition;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockConditions;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AroundBlockConditionGUI extends GUIAbstract {

    private String detail;
    private Conditions conditions;
    private ConditionsManager conditionsManager;
    private AroundBlockConditions condition;
    private boolean newAroundBlockCondition;
    private AroundBlockCondition aBC;

	public final static String TITLE = "Editor - Around-Block condition";
	public final static String SOUTH_VALUE = "South value";
	public final static String NORTH_VALUE = "North value";
	public final static String WEST_VALUE = "West value";
	public final static String EAST_VALUE = "East value";
	public final static String ABOVE_VALUE = "Above value";
	public final static String UNDER_VALUE = "Under value";
	
	public final static String MUST_BE_EXECUTABLEBLOCKS = "Block must be executableblock";
	
	public final static String MUST_BE_TYPE = "Block type must be";

	public final static String MUST_NOT_BE_TYPE = "Block type must not be";
	
	public final static String ERROR_MSG = "Error message";
	
	public final static String CDT_ID = "Cdt ID:";

	public AroundBlockConditionGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, ConditionsManager conditionsManager, Conditions conditions, AroundBlockConditions condition, String detail) {
		super(TITLE, 6 * 9, sPlugin, sObject, sActivator);
		
		newAroundBlockCondition = true;
        this.conditionsManager = conditionsManager;
        this.conditions = conditions;
        this.condition = condition;
        this.detail = detail;

		int id = 1;
		for (int i = 0; i < condition.getCondition().size(); i++) {
			for (AroundBlockCondition aBC : condition.getCondition()) {
				if (aBC.getId().equals("aBC" + id)) {
					id++;
				}
			}
		}
		String idStr = "aBC" + id;

		aBC = new AroundBlockCondition(idStr);
		condition.getCondition().add(aBC);
		this.loadTheGUI();
	}

	public AroundBlockConditionGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, ConditionsManager conditionsManager, Conditions conditions, AroundBlockConditions condition, AroundBlockCondition aBC, String detail) {
		super("&8&l"+sPlugin.getShortName()+" "+TITLE, 6 * 9, sPlugin, sObject, sActivator);
        this.conditionsManager = conditionsManager;
        this.conditions = conditions;
        this.condition = condition;
        this.detail = detail;
        this.newAroundBlockCondition = false;
        this.aBC = aBC;
	}


	public void loadTheGUI() {
		// Main Options
		createItem(Material.COMPASS, 				1, 0, TITLE_COLOR+SOUTH_VALUE, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateInt(SOUTH_VALUE, aBC.getSouthValue());

		createItem(Material.COMPASS, 				1, 1, TITLE_COLOR+NORTH_VALUE, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateInt(NORTH_VALUE, aBC.getNorthValue());
		
		createItem(Material.COMPASS, 				1, 2, TITLE_COLOR+WEST_VALUE, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateInt(WEST_VALUE, aBC.getWestValue());
		
		createItem(Material.COMPASS, 				1, 3, TITLE_COLOR+EAST_VALUE, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateInt(EAST_VALUE, aBC.getEastValue());
		
		createItem(Material.COMPASS, 				1, 4, TITLE_COLOR+ABOVE_VALUE, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateInt(ABOVE_VALUE, aBC.getAboveValue());
		
		createItem(Material.COMPASS, 				1, 5, TITLE_COLOR+UNDER_VALUE, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateInt(UNDER_VALUE, aBC.getUnderValue());
		
		createItem(Material.BEACON, 				1, 6, TITLE_COLOR+MUST_BE_EXECUTABLEBLOCKS, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateMustBeExecutableBlock(aBC.getBlockMustBeExecutableBlock());
		
		createItem(Material.BEACON, 				1, 7, TITLE_COLOR+MUST_BE_TYPE, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateMustBeType(aBC.getBlockTypeMustBe());

		createItem(Material.BEACON, 				1, 8, TITLE_COLOR+MUST_NOT_BE_TYPE, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateMustNotBeType(aBC.getBlockTypeMustNotBe());
		
		createItem(Material.BEACON, 				1, 9, TITLE_COLOR+ERROR_MSG, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateActually(ERROR_MSG, aBC.getErrorMsg());

		// exit
		createItem(RED, 	1, 45, "&4&l▶&c Back to Around block Conditions", false, false);

		// Reset menu
		createItem(ORANGE, 			1, 46, "&4&l✘ &cReset", false, false, "", "&c&oClick here to reset", "&c&oall options of this around block Condition");

		createItem(Material.BOOK, 					1, 49, TITLE_COLOR+CDT_ID, false, false, "", "&7actually: &e" + aBC.getId());

		createItem(Material.BOOK, 					1, 50, COLOR_ACTIVATOR_ID, false, false, "", "&7actually: &e" + this.getSAct().getID());

		createItem(Material.BOOK, 					1, 51, COLOR_OBJECT_ID, false, false, "", "&7actually: &e" + this.getSObject().getId());

		// Save menu
		if(newAroundBlockCondition) {
			createItem(GREEN, 1, 53, "&2&l✔ &aCreate this Around block Condition", false, false, "", "&a&oClick here to create this", "&a&oAround block Condition");
		}
		else {
			createItem(GREEN, 1, 53, "&2&l✔ &aSave this Around block Condition", false, false, "", "&a&oClick here to save this", "&a&oAround block Condition");
		}
	}
	
	public void updateMustBeExecutableBlock(List<String> list) {
		ItemStack item = this.getByName(MUST_BE_EXECUTABLEBLOCKS);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate = toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eEMPTY"));
		else {
			for(String str: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+str));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<String> getMustBeExecutableBlock(){
		ItemStack item = this.getByName(MUST_BE_EXECUTABLEBLOCKS);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate = iM.getLore().subList(3, iM.getLore().size());
		List<String> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line=StringConverter.decoloredString(line);
			if(line.contains("EMPTY")) {
				return new ArrayList<>();
			}else result.add(line.replaceAll("➤ ", ""));
		}
		return result;
	}

	
	public void updateMustBeType(List<Material> list) {
		ItemStack item = this.getByName(MUST_BE_TYPE);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate = toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eEMPTY"));
		else {
			for(Material mat: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+mat.toString()));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<Material> getMustBeType(){
		ItemStack item = this.getByName(MUST_BE_TYPE);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate = iM.getLore().subList(3, iM.getLore().size());
		List<Material> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line = StringConverter.decoloredString(line);
			if(line.contains("EMPTY")) {
				return new ArrayList<>();
			}
			else {
				try {
					result.add(Material.valueOf(line.replaceAll("➤ ", "")));
				}catch(Exception ignored) {}
			}
		}
		return result;
	}

	public void updateMustNotBeType(List<Material> list) {
		ItemStack item = this.getByName(MUST_NOT_BE_TYPE);
		ItemMeta toChange = item.getItemMeta();
		List<String> loreUpdate = toChange.getLore().subList(0, 3);
		if(list.isEmpty()) loreUpdate.add(StringConverter.coloredString("&6➤ &eEMPTY"));
		else {
			for(Material mat: list) {
				loreUpdate.add(StringConverter.coloredString("&6➤ &e"+mat.toString()));
			}
		}
		toChange.setLore(loreUpdate);
		item.setItemMeta(toChange);
	}

	public List<Material> getMustNotBeType(){
		ItemStack item = this.getByName(MUST_NOT_BE_TYPE);
		ItemMeta iM = item.getItemMeta();
		List<String> loreUpdate = iM.getLore().subList(3, iM.getLore().size());
		List<Material> result = new ArrayList<>();
		for(String line: loreUpdate) {
			line = StringConverter.decoloredString(line);
			if(line.contains("EMPTY")) {
				return new ArrayList<>();
			}
			else {
				try {
					result.add(Material.valueOf(line.replaceAll("➤ ", "")));
				}catch(Exception ignored) {}
			}
		}
		return result;
	}

	public void incrValue(String itemName) {
		int i = this.getInt(itemName);
		this.updateInt(itemName, i+1);
	}
	
	public void decrValue(String itemName) {
		int i = this.getInt(itemName);
		if(i == 0) return;
		this.updateInt(itemName, i-1);
	}

	public boolean isNewAroundBlockCondition() {
		return newAroundBlockCondition;
	}

	public void setNewAroundBlockCondition(boolean newAroundBlockCondition) {
		this.newAroundBlockCondition = newAroundBlockCondition;
	}

    @Override
    public void reloadGUI() {
        loadTheGUI();
    }
}


