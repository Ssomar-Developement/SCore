package com.ssomar.score.menu.conditions.clean;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.RequestMessage;
import com.ssomar.score.menu.conditions.home.ConditionsGUIManager;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class NewConditionsGUIManager extends GUIManagerSCore<NewConditionsGUI> {

    private static NewConditionsGUIManager instance;

    public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, String detail, NewConditions conditions, ConditionsManager conditionsManager) {
        cache.put(p, new NewConditionsGUI(sPlugin, sObject, sActivator, detail, conditions, conditionsManager));
        cache.get(p).openGUISync(p);
    }

    @Override
    public boolean allClicked(InteractionClickedGUIManager i) {

        String itemName = StringConverter.decoloredString(i.name);

        if(itemName.contains("Back")) {
            ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator);
        }
        else if(!itemName.contains("ERROR ID")) {

            if(cache.get(i.player).getConditions().contains(itemName)){
                NewConditionGUIManager.getInstance().startEditing(i.player,i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getDetail(), cache.get(i.player).getConditions(), cache.get(i.player).getConditionsManager(), cache.get(i.player).getConditions().get(itemName));
            }
            else{
                NewConditionGUIManager.getInstance().startEditing(i.player,i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getDetail(), cache.get(i.player).getConditions(), cache.get(i.player).getConditionsManager(), cache.get(i.player).getConditionsManager().get(itemName));
            }
        }
        return true;
    }

    @Override
    public boolean noShiftLeftclicked(InteractionClickedGUIManager interact) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(InteractionClickedGUIManager interact) {
        return false;
    }

    @Override
    public boolean shiftClicked(InteractionClickedGUIManager i) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(InteractionClickedGUIManager interact) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(InteractionClickedGUIManager interact) {
        return false;
    }

    @Override
    public boolean leftClicked(InteractionClickedGUIManager interact) {
        return false;
    }

    @Override
    public boolean rightClicked(InteractionClickedGUIManager interact) {
        return false;
    }

    public static NewConditionsGUIManager getInstance() {
        if(instance == null) instance = new NewConditionsGUIManager();
        return instance;
    }

    @Override
    public void saveTheConfiguration(Player p) {

    }

    @Override
    public boolean noShiftclicked(InteractionClickedGUIManager<NewConditionsGUI> i) {
        return false;
    }
}
