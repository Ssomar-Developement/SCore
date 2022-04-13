package com.ssomar.score.menu.conditions.general;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter
@Setter
public class ConditionsGUI extends GUIAbstract {

    private String detail;
    private Conditions conditions;
    private ConditionsManager conditionsManager;

    public ConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sAct, String detail, Conditions conditions, ConditionsManager conditionsManager) {
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

            String enabled = "&c&lDisabled";
            if(conditions.contains(condition) && conditions.get(condition).isDefined()) enabled = "&a&lEnabled";

			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+condition.getConfigName(), 	false,	false, "", "&7&o"+condition.getEditorName(), enabled, "&a✎ Click here to edit");
			i++;
		}


        //createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+AROUND_BLOCK_CDT, 	false,	false, "&7&oAround blocks conditions", "&a✎ Click here to edit", "&7>> &e"+conditions.getBlockAroundConditions().size()+" &7conditions");
        //					i++;

        createItem(RED, 1, 45, "&4&l▶ &cBack to conditions config", false, false);

        createItem(Material.BOOK, 1, 51, COLOR_OBJECT_ID, false, false, "", "&7actually: &e" + this.getSObject().getId());
        createItem(Material.BOOK, 1, 52, COLOR_ACTIVATOR_ID, false, false, "", "&7actually: &e" + this.getSAct().getID());
    }
}
