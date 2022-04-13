package com.ssomar.score.conditions.condition.conditiontype;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.menu.conditions.general.ConditionGUI;
import com.ssomar.score.menu.conditions.general.ConditionGUIManager;
import com.ssomar.score.menu.conditions.general.ConditionsGUIManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.entity.Player;

import java.util.Map;

public abstract class AConditionTypeWithSubMenu implements IConditionType {

    private static final Boolean DEBUG = false;

    public void loadParameterEnterByTheUserGUI(ConditionGUIManager guiManager, Player player, String message){
        Map<Player, ConditionGUI> cache = guiManager.getCache();
        SPlugin sPlugin = cache.get(player).getsPlugin();
        //SObject sObject = cache.get(p).getSObject();
        //SActivator sAct = cache.get(p).getSAct();
        String plName = sPlugin.getNameDesign();
        String uncolored = StringConverter.decoloredString(message).trim();

        if(DEBUG) SsomarDev.testMsg("Uncolored message: " + uncolored);

        if (uncolored.startsWith("exit")) {

            if(DEBUG) SsomarDev.testMsg("Exit condition");

            if (uncolored.equals("exit with delete")){
                if(DEBUG) SsomarDev.testMsg("Exit with delete");
                exitWithDelete(guiManager, player);
            }

            else if (uncolored.equals("exit")){
                if(DEBUG) SsomarDev.testMsg("Exit");
                exit(guiManager, player);
            }

            guiManager.getCurrentWriting().remove(player);
            guiManager.getRequestWriting().remove(player);
            cache.get(player).openGUISync(player);
        }
        else{
            if(DEBUG) SsomarDev.testMsg("Entering parameter");
            if (uncolored.contains("delete line <")) {
                if(DEBUG) SsomarDev.testMsg("Delete line "+message);
                guiManager.deleteLine(uncolored, player);
                deleteLine(guiManager, player);
                guiManager.space(player);
                guiManager.space(player);
            }
            else{
                if(DEBUG) SsomarDev.testMsg("Adding parameter "+message);
                addLine(guiManager, player, message);
            }
        }
    }

    public void updateGUI(ConditionGUI gui, Conditions conditions, Condition condition) {
        if (conditions.contains(condition)) updateGUIContains(gui, conditions.get(condition).getCondition());
        else updateGUINotContains(gui);
    }

    public abstract void openSubMenu(ConditionsGUIManager manager, Condition condition, Player player);
}
