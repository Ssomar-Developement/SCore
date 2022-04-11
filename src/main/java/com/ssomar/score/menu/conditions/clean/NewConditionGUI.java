package com.ssomar.score.menu.conditions.clean;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NewConditionGUI extends GUIAbstract {


    public static final String CONDITION = "Condition";
    public static final String ERROR_MESSAGE = "Error Message";

    private String detail;
    private NewConditions conditions;
    private ConditionsManager conditionsManager;
    private Condition condition;

    public NewConditionGUI(SPlugin sPlugin, SObject sObject, SActivator sAct, String detail, NewConditions conditions, ConditionsManager conditionsManager, Condition condition) {
        super(sPlugin.getShortName()+" Condition: "+condition.getEditorName(), 3*9, sPlugin, sObject, sAct);
        this.conditions = conditions;
        this.conditionsManager = conditionsManager;
        this.condition = condition;
        this.detail = detail;
        this.loadTheGUI();
    }

    @Override
    public void reloadGUI() {
        this.loadTheGUI();
    }

    public void loadTheGUI() {
        int i = 0;

        createConditionItem(i);

        createMessageItem(i);

        createItem(RED, 1, 18, "&4&l▶ &cBack to "+detail, false, false);

        createItem(ORANGE, 1, 19, "&4&l✘ &cReset", false, false, "", "&c&oClick here to reset", "&c&oall otpions to default");

        createItem(GREEN, 1, 26, "&2&l✔ &aSave", false, false, "", "&a&oClick here to save", "&a&oyour modification of this condition");

        createItem(Material.BOOK, 1, 24, COLOR_OBJECT_ID, false, false, "", "&7actually: &e" + this.getSObject().getId());
        createItem(Material.BOOK, 1, 25, COLOR_ACTIVATOR_ID, false, false, "", "&7actually: &e" + this.getSAct().getID());
    }


    public void createConditionItem(int i){
        String[] finalDescription = null;

        switch (condition.getConditionType()) {

            case BOOLEAN:
            case NUMBER_CONDITION:
                finalDescription = new String[condition.getEditorDescription().length + 2];
                for (int j = 0; j < condition.getEditorDescription().length; j++) {
                    finalDescription[j] = condition.getEditorDescription()[j];
                }
                finalDescription[finalDescription.length - 2] = "&a✎ Click here to change";
                finalDescription[finalDescription.length - 1] = "&7actually:";
                break;
            case CUSTOM_AROUND_BLOCK:
                break;
            case WEATHER_LIST:
                finalDescription = new String[condition.getEditorDescription().length + 3];
                for (int j = 0; j < condition.getEditorDescription().length; j++) {
                    finalDescription[j] = condition.getEditorDescription()[j];
                }
                finalDescription[finalDescription.length - 3] = "&a✎ Click here to change";
                finalDescription[finalDescription.length - 2] = "&7actually:";
                finalDescription[finalDescription.length - 1] = "";
                break;
        }

        createItem(Material.ANVIL, 1, i, TITLE_COLOR + CONDITION, false, false, finalDescription);
        i++;

        switch (condition.getConditionType()) {

            case BOOLEAN:
                if (conditions.contains(condition)) {
                    this.updateBoolean(condition.getEditorName(), (Boolean) conditions.get(condition).getCondition());
                } else this.updateBoolean(condition.getEditorName(), false);
                break;
            case NUMBER_CONDITION:
                if (conditions.contains(condition)) {
                    this.updateCondition(condition.getEditorName(), (String) conditions.get(condition).getCondition());
                } else this.updateCondition(condition.getEditorName(), "");
                break;
            case CUSTOM_AROUND_BLOCK:
                break;

            case WEATHER_LIST:
                if (conditions.contains(condition)) {
                    this.updateConditionList(condition.getEditorName(), (List<String>) conditions.get(condition).getCondition(), "&6➤ &eNO WEATHER IS REQUIRED");
                } else
                    this.updateConditionList(condition.getEditorName(), new ArrayList<>(), "&6➤ &eNO WEATHER IS REQUIRED");
                break;

        }
    }

    public void createMessageItem(int i){
        createItem(WRITABLE_BOOK,							1 , i, 	TITLE_COLOR+ERROR_MESSAGE, 	false,	false, "&a✎ Click here to change", "&7actually:");
        i++;
        if(conditions.contains(condition)){
            Condition loadedCondition = conditions.get(condition);
            if(loadedCondition.getCustomErrorMsg().isPresent()){
                this.updateMessage(condition.getEditorName()+" message", (String) loadedCondition.getCustomErrorMsg().get());
            }
            else this.updateMessage(condition.getEditorName()+" message", "");
        }
        else this.updateMessage(condition.getEditorName()+" message", "");
    }


    public void updateMessage(String itemName, String message) {
        if (message.isEmpty() || message.equals(" ")) this.updateActually(itemName, "&cNO MESSAGE");
        else this.updateActually(itemName, message);
    }

    public String getMessage(String itemName) {
        String msg = this.getActuallyWithColor(itemName);

        if (msg.contains("NO MESSAGE")) return "";
        else return msg;
    }

}
