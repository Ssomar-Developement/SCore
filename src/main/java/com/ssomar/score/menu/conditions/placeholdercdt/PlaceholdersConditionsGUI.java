package com.ssomar.score.menu.conditions.placeholdercdt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.PlaceholdersCdtType;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.PlaceholdersCondition;
import com.ssomar.score.splugin.SPlugin;

public class PlaceholdersConditionsGUI extends ConditionGUIAbstract {

	private static int index;
	
	private List<PlaceholdersCondition> list;

	// Page 1
	public PlaceholdersConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, List<PlaceholdersCondition> list, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Placeholders Conditions - Page 1", 5 * 9, sPlugin, sObject, sActivator, detail, null);
		this.list = list;
		setIndex(1);
		loadCdts(sObject, sActivator, list);
	}

	// other pages
	public PlaceholdersConditionsGUI(int index, SPlugin sPlugin, SObject sObject, SActivator sActivator, List<PlaceholdersCondition> list, String detail) {
		super("&8&l"+sPlugin.getShortName()+"Editor - Placeholders Conditions - Page " + index, 5 * 9, sPlugin, sObject, sActivator, detail, null);
		this.list = list;
		setIndex(index);
		loadCdts(sObject, sActivator, list);
	}
	
	@Override
	public void loadTheGUI() {
		/* DO NOTHING THERE IS NO RESET HERE */
		loadCdts(this.getSObject(), this.getSAct(), list);
	}

	public void loadCdts(SObject sObject, SActivator sActivator, List<PlaceholdersCondition> list) {
		int i = 0;
		int total = 0;
		for (PlaceholdersCondition pC : list) {
			if ((index - 1) * 36 <= total && total < index * 36) {
				ItemStack itemS = new ItemStack(Material.NAME_TAG);

				List<String> desc = new ArrayList<>();
				desc.add("");
				desc.add("&4(shift + left click to delete)");
				desc.add("&7(click to edit)");
				desc.add("&7• Type: " + pC.getType().toString());
				desc.add("&7• Part1: &e" + pC.getPart1());
				desc.add("&7• Comparator: &e" + pC.getComparator().toString());
				if(PlaceholdersCdtType.getpCdtTypeWithNumber().contains(pC.getType())) {
					desc.add("&7• Part2: &e" + pC.getPart2Number());
				}
				else desc.add("&7• Part2: &e" + pC.getPart2String());

				desc.add("&7• Message: &e" + pC.getMessage());
				desc.add("&7• cancelEvent: &e" + pC.isCancelEvent());


				String[] descArray = new String[desc.size()];
				for (int j = 0; j < desc.size(); j++) {
					if (desc.get(j).length() > 40)
						descArray[j] = desc.get(j).substring(0, 39) + "...";
					else
						descArray[j] = desc.get(j);
				}
				createItem(itemS, 1, i, "&2&l✦ ID: &a" + pC.getId(), true, false, descArray);
				i++;
			}
			total++;
		}

		// other button
		if (total > 27 && index * 27 < total) {
			createItem(NEXT_PAGE_MAT, 1, 44, "&5&l▶ &dNext page ", false, false);
		}
		if (index > 1) {
			createItem(PREVIOUS_PAGE_MAT, 1, 37, "&dPrevious page &5&l◀", false, false);
		}
		createItem(RED, 1, 36, "&4&l▶ &cBack to item config", false, false);

		createItem(GREEN, 1, 40, "&2&l✚ &aNew Placeholders cdt", false, false);

		createItem(Material.BOOK, 1, 42, COLOR_OBJECT_ID, false, false, "","&7actually: &e" + sObject.getID());

		createItem(Material.BOOK, 1, 43, COLOR_ACTIVATOR_ID, false, false, "","&7actually: &e" + sActivator.getID());
	}



	public static int getIndex() {
		return index;
	}

	public static void setIndex(int index) {
		PlaceholdersConditionsGUI.index = index;
	}

	public List<PlaceholdersCondition> getList() {
		return list;
	}

	public void setList(List<PlaceholdersCondition> list) {
		this.list = list;
	}
}

