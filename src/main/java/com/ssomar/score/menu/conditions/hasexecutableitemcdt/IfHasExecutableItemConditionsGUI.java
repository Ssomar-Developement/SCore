package com.ssomar.score.menu.conditions.hasexecutableitemcdt;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.player.custom.IfPlayerHasExecutableItem;
import com.ssomar.score.conditions.condition.player.custom.IfPlayerHasExecutableItems;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IfHasExecutableItemConditionsGUI extends GUIAbstract {

	private static int index;

    private String detail;
    private Conditions conditions;
    private ConditionsManager conditionsManager;
    private IfPlayerHasExecutableItems condition;

	// Page 1
	public IfHasExecutableItemConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, ConditionsManager conditionsManager, Conditions conditions, IfPlayerHasExecutableItems condition, String detail) {
		super("&8&l"+sPlugin.getShortName()+" Editor - Has ExecutableItem Conditions - Page 1", 5 * 9, sPlugin, sObject, sActivator);
		this.conditionsManager = conditionsManager;
        this.conditions = conditions;
        this.condition = condition;
        this.detail = detail;
		setIndex(1);
		loadCdts();
	}

	// other pages
	public IfHasExecutableItemConditionsGUI(int index, SPlugin sPlugin, SObject sObject, SActivator sActivator, ConditionsManager conditionsManager, Conditions conditions, IfPlayerHasExecutableItems condition, String detail) {
        super("&8&l" + sPlugin.getShortName() + " Editor - Has ExecutableItem Conditions - Page " + index, 5 * 9, sPlugin, sObject, sActivator);
        this.conditionsManager = conditionsManager;
        this.conditions = conditions;
        this.condition = condition;
        this.detail = detail;
        setIndex(index);
        loadCdts();
    }

	public void loadCdts() {
		int i = 0;
		int total = 0;
		if(condition.getCondition() != null) {
			for (IfPlayerHasExecutableItem bAC : condition.getCondition()) {
				if ((index - 1) * 36 <= total && total < index * 36) {
					ItemStack itemS = new ItemStack(Material.NAME_TAG);

					List<String> desc = new ArrayList<>();
					desc.add("");
					desc.add("&4(shift + left click to delete)");
					desc.add("&7(click to edit)");
					desc.add("&7• EI id: &e" + bAC.getExecutableItemID());
					desc.add("&7• Slot: &e" + bAC.getSlot());
					if(bAC.getUsageCalcul().isPresent())
						desc.add("&7• Usage cdt: &e" + bAC.getUsageCalcul().get());
					else
						desc.add("&7• Usage cdt: " + "&cNO CONDITION");
					if(bAC.isValid())
						desc.add("&7• isValid: &a" + bAC.isValid());
					else
						desc.add("&7• isValid: &c" + bAC.isValid());

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

		createItem(GREEN, 1, 40, "&2&l✚ &aNew if has EI cdt", false, false);

		createItem(Material.BOOK, 1, 42, COLOR_OBJECT_ID, false, false, "","&7actually: &e" + getSObject().getId());

		createItem(Material.BOOK, 1, 43, COLOR_ACTIVATOR_ID, false, false, "","&7actually: &e" + getSAct().getID());
	}



	public static int getIndex() {
		return index;
	}

	public static void setIndex(int index) {
		IfHasExecutableItemConditionsGUI.index = index;
	}

    @Override
    public void reloadGUI() {
        loadCdts();
    }
}

