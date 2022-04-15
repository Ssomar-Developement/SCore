package com.ssomar.score.menu.conditions.general;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.menu.conditions.RequestMessage;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.entity.Player;

import java.util.Optional;

public class ConditionGUIManager extends GUIManagerSCore<ConditionGUI> {

    private static ConditionGUIManager instance;

    public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, String detail, Conditions conditions, ConditionsManager conditionsManager, Condition condition) {
        cache.put(p, new ConditionGUI(sPlugin, sObject, sActivator, detail, conditions, conditionsManager, condition));
        cache.get(p).openGUISync(p);
    }

    public boolean saveOrBackOrNothingNEW(InteractionClickedGUIManager<ConditionGUI> i) {
        if (i.name.contains("Save")) {
            this.saveTheConfiguration(i.player);
            ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getDetail(), cache.get(i.player).getConditions(), cache.get(i.player).getConditionsManager());
        } else if (i.name.contains("Back")) {
            ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getDetail(), cache.get(i.player).getConditions(), cache.get(i.player).getConditionsManager());
        } else return false;

        cache.remove(i.player);
        requestWriting.remove(i.player);

        return true;
    }


    @Override
    public boolean allClicked(InteractionClickedGUIManager<ConditionGUI> i) {
        return this.saveOrBackOrNothingNEW(i);
    }

    @Override
    public boolean noShiftclicked(InteractionClickedGUIManager<ConditionGUI> i) {

        if (i.name.contains(ConditionGUI.CONDITION)) {
            ConditionType type = this.getCache().get(i.player).getCondition().getConditionType();
            type.clickGUI(this, i.player);
            return true;
        } else if (i.name.contains(ConditionGUI.ERROR_MESSAGE)) {
            requestWriting.put(i.player, ConditionGUI.ERROR_MESSAGE);
            i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(ConditionGUI.ERROR_MESSAGE);
            RequestMessage.sendRequestMessage(i.msgInfos);
            return true;
        }
        else if (i.name.contains(ConditionGUI.CANCEL_EVENT)) {
            cache.get(i.player).changeBoolean(ConditionGUI.CANCEL_EVENT);
            return true;
        }

        return false;
    }

    public void receivedMessage(Player p, String message) {
        boolean notExit = true;
        SPlugin sPlugin = cache.get(p).getsPlugin();
        //SObject sObject = cache.get(p).getSObject();
        //SActivator sAct = cache.get(p).getSAct();
        String plName = sPlugin.getNameDesign();

        SsomarDev.testMsg("requestWriting.get(p) = " + requestWriting.get(p));
        ConditionType type = null;
        try {
            type = ConditionType.valueOf(requestWriting.get(p));
            if (type != null) {
                SsomarDev.testMsg("type = " + type);
                type.loadParameterEnterByTheUserGUI(this, p, message);
                return;
            }
            else
                SsomarDev.testMsg("type = null");
        } catch (Exception e) {}



        if (requestWriting.get(p).equals(ConditionGUI.ERROR_MESSAGE)) {
            if (message.contains("NO MESSAGE")) cache.get(p).updateMessage(ConditionGUI.ERROR_MESSAGE, "");
            else cache.get(p).updateMessage(ConditionGUI.ERROR_MESSAGE, message);
            requestWriting.remove(p);
            cache.get(p).openGUISync(p);
        }
    }

    @Override
    public boolean noShiftLeftclicked(InteractionClickedGUIManager<ConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(InteractionClickedGUIManager<ConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean shiftClicked(InteractionClickedGUIManager<ConditionGUI> i) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(InteractionClickedGUIManager<ConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(InteractionClickedGUIManager<ConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean leftClicked(InteractionClickedGUIManager<ConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean rightClicked(InteractionClickedGUIManager<ConditionGUI> interact) {
        return false;
    }

    public void saveTheConfiguration(Player p) {
        SPlugin sPlugin = cache.get(p).getsPlugin();
        SObject sObject = cache.get(p).getSObject();
        SActivator sAct = cache.get(p).getSAct();
        //String plName = sPlugin.getNameDesign();

        Conditions loadedConditions = cache.get(p).getConditions();

        Condition condition = (Condition) cache.get(p).getCondition().clone();
        condition.getConditionType().saveIn(cache.get(p), condition);
        condition.setCustomErrorMsg(Optional.ofNullable(cache.get(p).getMessage(ConditionGUI.ERROR_MESSAGE)));
        condition.setErrorCancelEvent(cache.get(p).getBoolean(ConditionGUI.CANCEL_EVENT));
        loadedConditions.add(condition);


        cache.get(p).getConditionsManager().saveConditions(sPlugin, sObject, sAct, loadedConditions, cache.get(p).getDetail());
        //LinkedPlugins.reloadSObject(sPlugin, sObject.getId());
    }


    public static ConditionGUIManager getInstance() {
        if (instance == null) instance = new ConditionGUIManager();
        return instance;
    }
}
