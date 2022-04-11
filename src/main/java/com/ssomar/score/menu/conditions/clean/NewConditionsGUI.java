package com.ssomar.score.menu.conditions.clean;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.menu.IGUI;
import com.ssomar.score.menu.conditions.home.ConditionsGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class NewConditionsGUI extends GUIAbstract {

    private String detail;
    private NewConditions conditions;
    private ConditionsManager conditionsManager;

    public NewConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sAct, String detail, NewConditions conditions, ConditionsManager conditionsManager) {
        super("&8&l"+sPlugin.getShortName()+" Editor - "+detail, 6*9, sPlugin, sObject, sAct);
        this.conditions = conditions;
        this.conditionsManager = conditionsManager;
        this.detail = detail;
        this.loadTheGUI();
    }

    @Override
    public void reloadGUI() {
        this.loadTheGUI();
    }

    public void loadTheGUI() {
        int i = 0;
        for (Object object : conditionsManager.getConditionsList()) {
            Condition condition = (Condition) object;

			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+condition.getConfigName(), 	false,	false, "", TITLE_COLOR+condition.getEditorName(), "&a✎ Click here to edit");
			i++;
		}


        //createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+AROUND_BLOCK_CDT, 	false,	false, "&7&oAround blocks conditions", "&a✎ Click here to edit", "&7>> &e"+conditions.getBlockAroundConditions().size()+" &7conditions");
        //					i++;

        createItem(RED, 1, 54, "&4&l▶ &cBack to conditions config", false, false);

        createItem(Material.BOOK, 1, 60, COLOR_OBJECT_ID, false, false, "", "&7actually: &e" + this.getSObject().getId());
        createItem(Material.BOOK, 1, 61, COLOR_ACTIVATOR_ID, false, false, "", "&7actually: &e" + this.getSAct().getID());
    }
}
