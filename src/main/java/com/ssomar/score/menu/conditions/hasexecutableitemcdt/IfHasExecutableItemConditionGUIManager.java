package com.ssomar.score.menu.conditions.hasexecutableitemcdt;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.api.ExecutableItemsAPI;
import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockCondition;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockConditions;
import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.custom.IfPlayerHasExecutableItem;
import com.ssomar.score.conditions.condition.player.custom.IfPlayerHasExecutableItems;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.conditions.general.ConditionGUI;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.NTools;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class IfHasExecutableItemConditionGUIManager extends GUIManagerSCore<IfHasExecutableItemConditionGUI> {

    private static IfHasExecutableItemConditionGUIManager instance;

    private static final Boolean DEBUG = false;

    public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, ConditionsManager conditionsManager, Conditions conditions, IfPlayerHasExecutableItems condition, String detail) {
        cache.put(p, new IfHasExecutableItemConditionGUI(sPlugin, sObject, sActivator, conditionsManager, conditions, condition, detail));
        cache.get(p).openGUISync(p);
    }

    public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, ConditionsManager conditionsManager, Conditions conditions, IfPlayerHasExecutableItems condition, IfPlayerHasExecutableItem aBC, String detail) {
        cache.put(p, new IfHasExecutableItemConditionGUI(sPlugin, sObject, sActivator, conditionsManager, conditions, condition, aBC, detail));
        cache.get(p).openGUISync(p);
    }

    @Override
    public boolean allClicked(InteractionClickedGUIManager<IfHasExecutableItemConditionGUI> i) {
        if (i.name.contains(IfHasExecutableItemConditionGUI.EXECUTABLEITEM)) {
            requestWriting.put(i.player, IfHasExecutableItemConditionGUI.EXECUTABLEITEM);
            i.player.closeInventory();
            space(i.player);
            i.player.sendMessage(StringConverter.coloredString("&2&l" + i.sPlugin.getNameDesign() + " &a&lWrite your EI id:"));
            space(i.player);
        }
        else if (i.name.contains(IfHasExecutableItemConditionGUI.SLOT)) {
            requestWriting.put(i.player, IfHasExecutableItemConditionGUI.SLOT);
            i.player.closeInventory();
            space(i.player);
            i.player.sendMessage(StringConverter.coloredString("&2&l" + i.sPlugin.getNameDesign() + " &a&lWrite your slot:  (Integer) (-1 for main hand slot)"));
            space(i.player);
        }
        else if (i.name.contains(IfHasExecutableItemConditionGUI.USAGE)) {
            requestWriting.put(i.player, IfHasExecutableItemConditionGUI.USAGE);
            i.player.closeInventory();
            space(i.player);
            i.player.sendMessage(StringConverter.coloredString("&a&l" + cache.get(i.player).getsPlugin().getNameDesign() + " &2&lEDITION " + cache.get(i.player).getCondition().getEditorName() + ": &7&o(Example: &6>6 &7&o, &6 4 < CONDITION <= 8 &7&o)"));

            showCalculationGUI(i.player, "Condition", cache.get(i.player).getCondition(ConditionGUI.CONDITION));
            space(i.player);
        }else if (i.name.contains("Save") || i.name.contains("Create this has EI Condition")) {
            this.saveTheConfiguration(i.player);
            IfHasExecutableItemConditionGUI gui = cache.get(i.player);
            IfHasExecutableItemConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, gui.getConditionsManager(), gui.getConditions(), gui.getCondition(), gui.getDetail());
            cache.remove(i.player);
            requestWriting.remove(i.player);
        } else if (i.name.contains("Back")) {
            IfHasExecutableItemConditionGUI gui = cache.get(i.player);
            IfHasExecutableItemConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, gui.getConditionsManager(), gui.getConditions(), gui.getCondition(), gui.getDetail());
            cache.remove(i.player);
            requestWriting.remove(i.player);
        } else return false;

        return true;
    }

    @Override
    public boolean shiftClicked(InteractionClickedGUIManager<IfHasExecutableItemConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean noShiftclicked(InteractionClickedGUIManager<IfHasExecutableItemConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(InteractionClickedGUIManager<IfHasExecutableItemConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(InteractionClickedGUIManager<IfHasExecutableItemConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(InteractionClickedGUIManager<IfHasExecutableItemConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(InteractionClickedGUIManager<IfHasExecutableItemConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean leftClicked(InteractionClickedGUIManager<IfHasExecutableItemConditionGUI> i) {
       return false;
    }

    @Override
    public boolean rightClicked(InteractionClickedGUIManager<IfHasExecutableItemConditionGUI> i) {
       return false;
    }


    public void receivedMessage(Player player, String message) {
        boolean notExit = true;
        SPlugin sPlugin = cache.get(player).getsPlugin();
        String plName = sPlugin.getNameDesign();

        String uncolored = StringConverter.decoloredString(message).trim();

        if(DEBUG) SsomarDev.testMsg("Uncolored message: " + uncolored);

        if (uncolored.startsWith("exit")) {

            if(DEBUG) SsomarDev.testMsg("Exit condition");

            if (uncolored.equals("exit with delete")){
                if(DEBUG) SsomarDev.testMsg("Exit with delete");
                if(requestWriting.get(player).equals(IfHasExecutableItemConditionGUI.USAGE)){
                    cache.get(player).updateCondition(IfHasExecutableItemConditionGUI.USAGE, "");
                }

            }

            else if (uncolored.equals("exit")){
                if(DEBUG) SsomarDev.testMsg("Exit");
            }

            currentWriting.remove(player);
            requestWriting.remove(player);
            cache.get(player).openGUISync(player);
        }
        else{
            if(DEBUG) SsomarDev.testMsg("Entering parameter");
            if (uncolored.contains("delete line <")) {
                if(DEBUG) SsomarDev.testMsg("Delete line "+message);
                /*guiManager.deleteLine(uncolored, player);
                deleteLine(guiManager, player);
                guiManager.space(player);
                guiManager.space(player);*/
            }
            else{
                if(DEBUG) SsomarDev.testMsg("Adding parameter "+message);
                if(requestWriting.get(player).equals(IfHasExecutableItemConditionGUI.USAGE)) {
                    if (StringCalculation.isStringCalculation(uncolored)) {
                        cache.get(player).updateCondition(IfHasExecutableItemConditionGUI.USAGE, uncolored);
                        requestWriting.remove(player);
                        cache.get(player).openGUISync(player);
                    } else
                        player.sendMessage(StringConverter.coloredString("&c&l" + cache.get(player).getsPlugin().getNameDesign() + " &4&lERROR &cEnter a condition ! &7&o(Example: &6>6 &7&o, &6 4 < CONDITION <= 8 &7&o)"));
                }
                else  if(requestWriting.get(player).equals(IfHasExecutableItemConditionGUI.SLOT)) {
                    Optional<Integer> slot = NTools.getInteger(uncolored);
                    if (slot.isPresent() && slot.get() >= -1) {
                        cache.get(player).updateInt(IfHasExecutableItemConditionGUI.SLOT, slot.get());
                        requestWriting.remove(player);
                        cache.get(player).openGUISync(player);
                    } else
                        player.sendMessage(StringConverter.coloredString("&c&l" + cache.get(player).getsPlugin().getNameDesign() + " &4&lERROR &cEnter a valid slot ! (Integer) (-1 for main hand slot)"));

                }
                else  if(requestWriting.get(player).equals(IfHasExecutableItemConditionGUI.EXECUTABLEITEM)) {
                    if (ExecutableItemsAPI.getExecutableItemIdsList().contains(uncolored)) {
                        cache.get(player).updateActually(IfHasExecutableItemConditionGUI.EXECUTABLEITEM, uncolored);
                        requestWriting.remove(player);
                        cache.get(player).openGUISync(player);
                    }
                    else
                        player.sendMessage(StringConverter.coloredString("&c&l" + cache.get(player).getsPlugin().getNameDesign() + " &4&lERROR &cEnter a valid EI id !"));
                }
            }
        }
    }


    public static IfHasExecutableItemConditionGUIManager getInstance() {
        if (instance == null) instance = new IfHasExecutableItemConditionGUIManager();
        return instance;
    }

    @Override
    public void saveTheConfiguration(Player p) {
        SPlugin sPlugin = cache.get(p).getsPlugin();
        SObject sObject = cache.get(p).getSObject();
        SActivator sActivator = cache.get(p).getSAct();

        IfPlayerHasExecutableItem aBC = cache.get(p).getABC();
        IfHasExecutableItemConditionGUI aGUI = cache.get(p);
        aBC.setExecutableItemID(aGUI.getActually(IfHasExecutableItemConditionGUI.EXECUTABLEITEM));
        aBC.setSlot(aGUI.getInt(IfHasExecutableItemConditionGUI.SLOT));
        String usageCdt = aGUI.getCondition(IfHasExecutableItemConditionGUI.USAGE);
        if(!usageCdt.equals("")){
            aBC.setUsageCalcul(Optional.ofNullable(usageCdt));
        }
        aBC.setValid(true);

        IfPlayerHasExecutableItem.saveCdt(sPlugin, sObject, sActivator, aBC, cache.get(p).getDetail());
    }
}

