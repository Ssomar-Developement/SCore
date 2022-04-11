package com.ssomar.score.menu.conditions.clean;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.menu.conditions.home.ConditionsGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
@Setter
public class NewConditionsGUI extends GUIAbstract {

    private String detail;
    private NewConditions conditions;
    private ConditionsManager conditionsManager;

    public NewConditionsGUI(SPlugin sPlugin, SObject sObject, SActivator sAct, String detail, NewConditions conditions, ConditionsManager conditionsManager) {
        super(sPlugin.getShortName()+" Conditions", 1*9, sPlugin, sObject, sAct);
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
        for (Object object : conditionsManager.getConditions().values()) {
            Condition condition = (Condition) object;

			createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+condition.getEditorName(), 	false,	false, "&a✎ Click here to edit");
			i++;
		}


        //createItem(Material.ANVIL,							1 , i, 	TITLE_COLOR+AROUND_BLOCK_CDT, 	false,	false, "&7&oAround blocks conditions", "&a✎ Click here to edit", "&7>> &e"+conditions.getBlockAroundConditions().size()+" &7conditions");
        //					i++;

        createItem(RED, 1, 18, "&4&l▶ &cBack to conditions config", false, false);

        createItem(Material.BOOK, 1, 24, COLOR_OBJECT_ID, false, false, "", "&7actually: &e" + this.getSObject().getId());
        createItem(Material.BOOK, 1, 25, COLOR_ACTIVATOR_ID, false, false, "", "&7actually: &e" + this.getSAct().getID());
    }

	public void clicked(Player p, String name) {

		String itemName = StringConverter.decoloredString(name);

		if(itemName.contains("Back")) {
			ConditionsGUIManager.getInstance().startEditing(p, getsPlugin(), getSObject(), getSAct());
		}
		else if(!itemName.contains("ERROR ID")) {

			if(conditions.contains(itemName)){
                NewConditionGUIManager.getInstance().startEditing(p, getsPlugin(), getSObject(), getSAct(), detail, conditions, conditionsManager, conditions.get(itemName));
			}
			else{
                NewConditionGUIManager.getInstance().startEditing(p, getsPlugin(), getSObject(), getSAct(), detail, conditions, conditionsManager, conditionsManager.get(itemName));
			}
		}
	}

}
