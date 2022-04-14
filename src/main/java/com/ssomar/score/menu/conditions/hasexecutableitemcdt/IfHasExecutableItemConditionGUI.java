package com.ssomar.score.menu.conditions.hasexecutableitemcdt;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockCondition;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockConditions;
import com.ssomar.score.conditions.condition.player.custom.IfPlayerHasExecutableItem;
import com.ssomar.score.conditions.condition.player.custom.IfPlayerHasExecutableItems;
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
public class IfHasExecutableItemConditionGUI extends GUIAbstract {

    private String detail;
    private Conditions conditions;
    private ConditionsManager conditionsManager;
    private IfPlayerHasExecutableItems condition;
    private boolean newIfPlayerHasEI;
    private IfPlayerHasExecutableItem aBC;

	public final static String TITLE = "Editor - Has EI condition";
	public final static String EXECUTABLEITEM = "EI id";
	public final static String SLOT = "Slot";
	public final static String USAGE = "Usage condition";
	
	public final static String CDT_ID = "Cdt ID:";

	public IfHasExecutableItemConditionGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, ConditionsManager conditionsManager, Conditions conditions, IfPlayerHasExecutableItems condition, String detail) {
		super(TITLE, 6 * 9, sPlugin, sObject, sActivator);
		
		newIfPlayerHasEI = true;
        this.conditionsManager = conditionsManager;
        this.conditions = conditions;
        this.condition = condition;
        this.detail = detail;

		int id = 1;
		for (int i = 0; i < condition.getCondition().size(); i++) {
			for (IfPlayerHasExecutableItem aBC : condition.getCondition()) {
				if (aBC.getId().equals("condition" + id)) {
					id++;
				}
			}
		}
		String idStr = "condition" + id;

		aBC = new IfPlayerHasExecutableItem(idStr);
		condition.getCondition().add(aBC);
		this.loadTheGUI();
	}

	public IfHasExecutableItemConditionGUI(SPlugin sPlugin, SObject sObject, SActivator sActivator, ConditionsManager conditionsManager, Conditions conditions, IfPlayerHasExecutableItems condition, IfPlayerHasExecutableItem aBC, String detail) {
		super("&8&l"+sPlugin.getShortName()+" "+TITLE, 6 * 9, sPlugin, sObject, sActivator);
        this.conditionsManager = conditionsManager;
        this.conditions = conditions;
        this.condition = condition;
        this.detail = detail;
        this.newIfPlayerHasEI = false;
        this.aBC = aBC;
		this.loadTheGUI();
	}


	public void loadTheGUI() {
		// Main Options
		createItem(Material.DIAMOND, 				1, 0, TITLE_COLOR+EXECUTABLEITEM, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateActually(EXECUTABLEITEM, aBC.getExecutableItemID());

		createItem(Material.COMPASS, 				1, 1, TITLE_COLOR+SLOT, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		this.updateInt(SLOT, aBC.getSlot());

		createItem(CLOCK, 				1, 2, TITLE_COLOR+USAGE, false, false, "", CLICK_HERE_TO_CHANGE,"&7actually: ");
		if(aBC.getUsageCalcul().isPresent()) this.updateCondition(USAGE, aBC.getUsageCalcul().get());
		else this.updateCondition(USAGE, "");



		// exit
		createItem(RED, 	1, 45, "&4&l▶&c Back to If has EI Conditions", false, false);

		// Reset menu
		createItem(ORANGE, 			1, 46, "&4&l✘ &cReset", false, false, "", "&c&oClick here to reset", "&c&oall options of this has EI Condition");

		createItem(Material.BOOK, 					1, 49, TITLE_COLOR+CDT_ID, false, false, "", "&7actually: &e" + aBC.getId());

		createItem(Material.BOOK, 					1, 50, COLOR_ACTIVATOR_ID, false, false, "", "&7actually: &e" + this.getSAct().getID());

		createItem(Material.BOOK, 					1, 51, COLOR_OBJECT_ID, false, false, "", "&7actually: &e" + this.getSObject().getId());

		// Save menu
		if(newIfPlayerHasEI) {
			createItem(GREEN, 1, 53, "&2&l✔ &aCreate this has EI Condition", false, false, "", "&a&oClick here to create this", "&a&ohas EI Condition");
		}
		else {
			createItem(GREEN, 1, 53, "&2&l✔ &aSave this has EI Condition", false, false, "", "&a&oClick here to save this", "&a&ohas EI Condition");
		}
	}

	public boolean isNewIfPlayerHasEI() {
		return newIfPlayerHasEI;
	}

	public void setNewIfPlayerHasEI(boolean newIfPlayerHasEI) {
		this.newIfPlayerHasEI = newIfPlayerHasEI;
	}

    @Override
    public void reloadGUI() {
        loadTheGUI();
    }
}


