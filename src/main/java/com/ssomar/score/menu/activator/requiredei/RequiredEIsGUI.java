package com.ssomar.score.menu.activator.requiredei;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEI;
import com.ssomar.score.splugin.SPlugin;

public class RequiredEIsGUI extends GUIAbstract {

	static int index;

	//Page 1
	public RequiredEIsGUI(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator) {
		super("&8&l"+sPlugin.getShortName()+" Editor - RequiredEIs - Page 1", 5*9, sPlugin, sObject, sActivator);
		setIndex(1);
		loadItems(p);
	}

	// other pages
	public RequiredEIsGUI(int index, Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator) {
		super("&8&l"+sPlugin.getShortName()+" Editor - RequiredEIs - Page "+index, 5*9, sPlugin, sObject, sActivator);
		setIndex(index);
		loadItems(p);
	}

	public void loadItems(Player p) {
		List<RequiredEI> rEIs = this.getSAct().getRequiredExecutableItems();
		int i = 0;
		int total = 0;
		for(RequiredEI rEI : rEIs) {
			if((index-1)*27 <= total && total < index*27) {
				ItemStack itemS = new ItemStack(Material.BOOK);
				List<String> desc = new ArrayList<>();
				desc.add("");
				desc.add("&4(shift + left click to delete)");
				desc.add("&7(click to edit)");
				desc.add("&7• EI ID: &e" + rEI.getEI_ID());
				desc.add("&7• Amount: &e" + rEI.getAmount());
				desc.add("&7• Consume: &e" + rEI.isConsume());
				if(rEI.getValidUsages().isEmpty()) desc.add("&7• Valid usages: &eALL");
				else desc.add("&7• Valid usages: &e" + rEI.getValidUsages().toString());
				

				String[]descArray = new String[desc.size()];
				for(int j = 0; j < desc.size(); j++) {
					if(desc.get(j).length() > 40) {
						descArray[j] = desc.get(j).substring(0, 39)+"...";
					}
					else {
						descArray[j] = desc.get(j);
					}
				}
				createItem(itemS, 	1 , i, 	"&2&l✦ ID: &a"+rEI.getId(), true, false, descArray);
				i++;
			}
			total++;

		}
	
		//other button
		if(total>27 && index*27<total) createItem(PURPLE, 	1 , 44, 	"&5&l▶ &dNext page ", 	false, false);

		if(index>1) createItem(PURPLE, 	1 , 37, 	"&dPrevious page &5&l◀", 	false, false);

		createItem(RED, 	1 , 36, 	"&4&l▶ &cBack to activator config", 	false, false);

		createItem(GREEN, 	1 , 40, 	"&2&l✚ &aNew RequiredEI", 	false, false);
		
		createItem(Material.BOOK, 							1 , 42, COLOR_OBJECT_ID, 	false, false, "", "&7actually: &e"+this.getSObject().getID());
		createItem(Material.BOOK, 							1 , 43, COLOR_ACTIVATOR_ID, 	false, false, "", "&7actually: &e"+this.getSAct().getID());

		//Last Edit
		if(p != null && RequiredEIGUIManager.getInstance().getCache().containsKey(p)) {
			createItem(BLUE, 							1 , 39, "&3&l✦ &bReturn to your last edit", 		false, false, 	"", "&7&oClick here to continue" , "&7&oyour last edit" );
		}
	}

	public static int getIndex() {
		return index;
	}

	public static void setIndex(int index) {
		RequiredEIsGUI.index = index;
	}

	@Override
	public void reloadGUI() {
		this.loadItems(null);
	}
}
