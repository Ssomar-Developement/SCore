package com.ssomar.score.menu.conditions.clean;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.Condition;
import com.ssomar.score.conditions.condition.ConditionType;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.RequestMessage;
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

public class NewConditionGUIManager extends GUIManagerSCore<NewConditionGUI> {

    private static NewConditionGUIManager instance;

    public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, String detail, NewConditions conditions, ConditionsManager conditionsManager, Condition condition) {
        cache.put(p, new NewConditionGUI(sPlugin, sObject, sActivator, detail, conditions, conditionsManager, condition));
        cache.get(p).openGUISync(p);
    }

    public boolean saveOrBackOrNothingNEW(InteractionClickedGUIManager<NewConditionGUI> i) {
        if (i.name.contains("Save")) {
            this.saveTheConfiguration(i.player);
            NewConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getDetail(), cache.get(i.player).getConditions(), cache.get(i.player).getConditionsManager());
        } else if (i.name.contains("Back")) {
            NewConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, cache.get(i.player).getDetail(), cache.get(i.player).getConditions(), cache.get(i.player).getConditionsManager());
        } else return false;

        cache.remove(i.player);
        requestWriting.remove(i.player);

        return true;
    }



    @Override
    public boolean allClicked(InteractionClickedGUIManager<NewConditionGUI> i) {
        return this.saveOrBackOrNothingNEW(i);
    }

    @Override
    public boolean noShiftclicked(InteractionClickedGUIManager<NewConditionGUI> i) {

        if (i.name.contains(NewConditionGUI.CONDITION)) {
            Condition condition = cache.get(i.player).getCondition();
            switch (condition.getConditionType()) {
                case BOOLEAN:
                    i.gui.changeBoolean(condition.getEditorName());
                    break;
                case NUMBER_CONDITION:
                    requestWriting.put(i.player, ConditionType.NUMBER_CONDITION.toString());
                    i.player.closeInventory();
                    space(i.player);
                    i.player.sendMessage(StringConverter.coloredString("&a&l" + i.sPlugin.getNameDesign() + " &2&lEDITION " + condition.getEditorName() + ":"));

                    this.showCalculationGUI(i.player, "Condition", cache.get(i.player).getCondition(NewConditionGUI.CONDITION));
                    space(i.player);
                    break;
                case CUSTOM_AROUND_BLOCK:
                    break;
                case LIST_WEATHER:
                    requestWriting.put(i.player, ConditionType.LIST_WEATHER.toString());
                    if (!currentWriting.containsKey(i.player)) {
                        currentWriting.put(i.player, cache.get(i.player).getConditionList(NewConditionGUI.CONDITION, "NO WEATHER IS REQUIRED"));
                    }
                    i.player.closeInventory();
                    space(i.player);
                    i.player.sendMessage(StringConverter.coloredString("&a&l" + i.sPlugin.getNameDesign() + " &2&lEDITION " + condition.getEditorName() + ":"));

                    this.showIfWeatherEditor(i.player);
                    space(i.player);
                    break;
            }
            return true;
        }
        else if (i.name.contains(NewConditionGUI.ERROR_MESSAGE)) {
            requestWriting.put(i.player, NewConditionGUI.ERROR_MESSAGE);
            i.msgInfos.actualMsg = cache.get(i.player).getActuallyWithColor(NewConditionGUI.ERROR_MESSAGE);
            RequestMessage.sendRequestMessage(i.msgInfos);
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

        if (message.contains("exit")) {
            boolean pass = false;
            if (StringConverter.decoloredString(message).equals("exit with delete")) {

                if (requestWriting.get(p).equals(ConditionType.LIST_WEATHER.toString())) {
                    cache.get(p).updateConditionList(NewConditionGUI.CONDITION, new ArrayList<>(), "&6➤ &eNO WEATHER IS REQUIRED");
                }
                else if (requestWriting.get(p).equals(ConditionType.NUMBER_CONDITION)) {
                    cache.get(p).updateCondition(NewConditionGUI.CONDITION, "");
                }

                requestWriting.remove(p);
                cache.get(p).openGUISync(p);
                pass = true;
            }
            if (StringConverter.decoloredString(message).equals("exit") || pass) {

                if (requestWriting.get(p).equals(ConditionType.LIST_WEATHER.toString())) {
                    List<String> result = new ArrayList<>(currentWriting.get(p));
                    cache.get(p).updateConditionList(NewConditionGUI.CONDITION, result, "&6➤ &eNO WEATHER IS REQUIRED");
                }

                currentWriting.remove(p);
                requestWriting.remove(p);
                cache.get(p).openGUISync(p);
                notExit = false;
            }
        }
        if (notExit) {

            String editMessage = StringConverter.decoloredString(message.trim());

            if (editMessage.contains("delete line <")) {
                this.deleteLine(editMessage, p);
                if (requestWriting.get(p).equals(ConditionType.LIST_WEATHER.toString())) this.showIfWeatherEditor(p);
                space(p);
                space(p);
            }
            else if (requestWriting.get(p).equals(NewConditionGUI.ERROR_MESSAGE)) {
                if(message .contains("NO MESSAGE")) cache.get(p).updateMessage(NewConditionGUI.ERROR_MESSAGE, "");
                else cache.get(p).updateMessage(NewConditionGUI.ERROR_MESSAGE, message);
                requestWriting.remove(p);
                cache.get(p).openGUISync(p);
            }
            else if (requestWriting.get(p).equals(ConditionType.NUMBER_CONDITION.toString())) {
                if (StringCalculation.isStringCalculation(editMessage)) {
                    cache.get(p).updateCondition(NewConditionGUI.CONDITION, editMessage);
                    requestWriting.remove(p);
                    cache.get(p).openGUISync(p);
                } else {
                    p.sendMessage(StringConverter.coloredString("&c&l" + plName + " &4&lERROR &cEnter a valid condition please !"));
                    this.showCalculationGUI(p, "Condition", cache.get(p).getCondition(requestWriting.get(p)));
                }
            } else if (requestWriting.get(p).equals(ConditionType.LIST_WEATHER.toString())) {
                if (!editMessage.isEmpty()) {
                    editMessage = editMessage.toUpperCase();
                    if (editMessage.equals("CLEAR") || editMessage.equals("STORM") || editMessage.equals("RAIN")) {
                        if (currentWriting.containsKey(p)) currentWriting.get(p).add(editMessage);
                        else {
                            ArrayList<String> list = new ArrayList<>();
                            list.add(editMessage);
                            currentWriting.put(p, list);
                        }
                        p.sendMessage(StringConverter.coloredString("&a&l"+sPlugin.getNameDesign()+" &2&lEDITION &aYou have added new weather!"));
                    } else
                        p.sendMessage(StringConverter.coloredString("&c&l"+sPlugin.getNameDesign()+" &4&lERROR &cEnter a valid weather (CLEAR, RAIN, STORM) please !"));
                } else
                    p.sendMessage(StringConverter.coloredString("&c&l"+sPlugin.getNameDesign()+" &4&lERROR &cEnter a valid weather (CLEAR, RAIN, STORM) please !"));

                this.showIfWeatherEditor(p);
            }
        }

    }

    @Override
    public boolean noShiftLeftclicked(InteractionClickedGUIManager<NewConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(InteractionClickedGUIManager<NewConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean shiftClicked(InteractionClickedGUIManager<NewConditionGUI> i) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(InteractionClickedGUIManager<NewConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(InteractionClickedGUIManager<NewConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean leftClicked(InteractionClickedGUIManager<NewConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean rightClicked(InteractionClickedGUIManager<NewConditionGUI> interact) {
        return false;
    }

    public void saveTheConfiguration(Player p) {
        SPlugin sPlugin = cache.get(p).getsPlugin();
        SObject sObject = cache.get(p).getSObject();
        SActivator sAct = cache.get(p).getSAct();
        //String plName = sPlugin.getNameDesign();

        NewConditions loadedConditions = cache.get(p).getConditions();

        Condition condition = cache.get(p).getCondition();
        switch (condition.getConditionType()) {

            case BOOLEAN:
                condition.setCondition(cache.get(p).getBoolean(NewConditionGUI.CONDITION));
                break;
            case NUMBER_CONDITION:
                condition.setCondition(cache.get(p).getCondition(NewConditionGUI.CONDITION));
                break;
            case CUSTOM_AROUND_BLOCK:
                break;
            case LIST_WEATHER:
                condition.setCondition(cache.get(p).getConditionList(NewConditionGUI.CONDITION, "NO WEATHER IS REQUIRED"));
                break;
        }
        condition.setCustomErrorMsg(Optional.ofNullable(cache.get(p).getMessage(NewConditionGUI.ERROR_MESSAGE)));
        loadedConditions.add(condition);


        cache.get(p).getConditionsManager().saveConditions(sPlugin, sObject, sAct, loadedConditions, cache.get(p).getDetail());
        //LinkedPlugins.reloadSObject(sPlugin, sObject.getId());
    }


    public void showIfWeatherEditor(Player p) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ ifWeather: (RAIN, CLEAR, or STORM)");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Weather", false, false, false, true, true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(p);
    }

    public static NewConditionGUIManager getInstance() {
        if(instance == null) instance = new NewConditionGUIManager();
        return instance;
    }
}
