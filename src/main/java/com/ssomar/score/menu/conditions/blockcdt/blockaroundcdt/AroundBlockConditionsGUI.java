package com.ssomar.score.menu.conditions.blockcdt.blockaroundcdt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.AroundBlockCondition;
import com.ssomar.score.splugin.SPlugin;

public class AroundBlockConditionsGUI extends ConditionGUIAbstract {

	private static int index;

	private List<AroundBlockCondition> list;

	// Page 1
	public AroundBlockConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, List<AroundBlockCondition> list, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Around block Conditions - Page 1", 5 * 9, sPlugin, sObject, sActivator, detail, null);
		this.list = list;
		setIndex(1);
		loadCdts(sObject, sActivator, list);
	}

	// other pages
	public AroundBlockConditionsGUI(int index, SPlugin sPlugin, SObject sObject, SActivator sActivator, List<AroundBlockCondition> list, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Around block Conditions - Page " + index, 5 * 9, sPlugin, sObject, sActivator, detail, null);
		this.list = list;
		setIndex(index);
		loadCdts(sObject, sActivator, list);
	}

	@Override
	public void loadTheGUI() {
		/* DO NOTHING THERE IS NO RESET HERE */
		loadCdts(this.getSObject(), this.getSAct(), list);
	}

	public void loadCdts(SObject sObject, SActivator sActivator, List<AroundBlockCondition> list) {
		int i = 0;
		int total = 0;
		if(list != null) {
			for (AroundBlockCondition bAC : list) {
				if ((index - 1) * 36 <= total && total < index * 36) {
					ItemStack itemS = new ItemStack(Material.NAME_TAG);

					List<String> desc = new ArrayList<>();
					desc.add("");
					desc.add("&4(shift + left click to delete)");
					desc.add("&7(click to edit)");
					desc.add("&7• southValue: " + bAC.getSouthValue()+"");
					desc.add("&7• northValue: " + bAC.getNorthValue()+"");
					desc.add("&7• westValue: " + bAC.getWestValue()+"");
					desc.add("&7• eastValue: " + bAC.getEastValue()+"");
					desc.add("&7• aboveValue: " + bAC.getAboveValue()+"");
					desc.add("&7• underValue: " + bAC.getUnderValue()+"");
					
					if(bAC.getBlockMustBeExecutableBlock().isEmpty()) desc.add("&7• MustBeEB: &cEMPTY");
					else desc.add("&7• MustBeEB: &e");
					for(String s : bAC.getBlockMustBeExecutableBlock()) {
						desc.add("   &7> &e"+s);
					}
					
					if(bAC.getBlockTypeMustBe().isEmpty()) desc.add("&7• MustBeType: &cEMPTY");
					else desc.add("&7• MustBeType: &e");
					for(Material mat : bAC.getBlockTypeMustBe()) {
						desc.add("   &7> &e"+mat.toString());
					}


					String[] descArray = new String[desc.size()];
					for (int j = 0; j < desc.size(); j++) {
						if (desc.get(j).length() > 40)
							descArray[j] = desc.get(j).substring(0, 39) + "...";
						else
							descArray[j] = desc.get(j);
					}
					createItem(itemS, 1, i, "&2&l✦ ID: &a" + bAC.getId(), true, false, descArray);
					i++;
				}
				total++;
			}
		}
		// other button
		if (total > 27 && index * 27 < total) {
			createItem(NEXT_PAGE_MAT, 1, 44, "&5&l▶ &dNext page ", false, false);
		}
		if (index > 1) {
			createItem(PREVIOUS_PAGE_MAT, 1, 37, "&dPrevious page &5&l◀", false, false);
		}
		createItem(RED, 1, 36, "&4&l▶ &cBack", false, false);

		createItem(GREEN, 1, 40, "&2&l✚ &aNew Around block cdt", false, false);

		createItem(Material.BOOK, 1, 42, COLOR_OBJECT_ID, false, false, "","&7actually: &e" + sObject.getID());

		createItem(Material.BOOK, 1, 43, COLOR_ACTIVATOR_ID, false, false, "","&7actually: &e" + sActivator.getID());
	}



	public static int getIndex() {
		return index;
	}

	public static void setIndex(int index) {
		AroundBlockConditionsGUI.index = index;
	}

	public List<AroundBlockCondition> getList() {
		return list;
	}

	public void setList(List<AroundBlockCondition> list) {
		this.list = list;
	}
}
