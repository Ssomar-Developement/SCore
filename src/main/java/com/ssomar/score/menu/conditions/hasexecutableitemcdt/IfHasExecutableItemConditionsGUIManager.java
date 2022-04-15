package com.ssomar.score.menu.conditions.hasexecutableitemcdt;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockCondition;
import com.ssomar.score.conditions.condition.player.custom.IfPlayerHasExecutableItem;
import com.ssomar.score.conditions.condition.player.custom.IfPlayerHasExecutableItems;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.menu.conditions.general.ConditionsGUIManager;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.entity.Player;

public class IfHasExecutableItemConditionsGUIManager extends GUIManagerSCore<IfHasExecutableItemConditionsGUI> {

    private static IfHasExecutableItemConditionsGUIManager instance;

    public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, ConditionsManager conditionsManager, Conditions conditions, IfPlayerHasExecutableItems condition, String detail) {
        cache.put(p, new IfHasExecutableItemConditionsGUI(sPlugin, sObject, sActivator, conditionsManager, conditions, condition, detail));
        cache.get(p).openGUISync(p);
    }

    @Override
    public boolean noShiftclicked(InteractionClickedGUIManager<IfHasExecutableItemConditionsGUI> i) {
        if (!i.name.isEmpty()) {
			IfPlayerHasExecutableItem aBC = null;
            IfHasExecutableItemConditionsGUI gui = cache.get(i.player);
            for (IfPlayerHasExecutableItem place : gui.getCondition().getCondition()) {
                try {
                    if (place.getId().equals(StringConverter.decoloredString(i.name).split("✦ ID: ")[1]))
                        aBC = place;
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
            if (aBC != null) {
                IfHasExecutableItemConditionGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, gui.getConditionsManager(), gui.getConditions(), gui.getCondition(), aBC, gui.getDetail());
                cache.remove(i.player);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean allClicked(InteractionClickedGUIManager<IfHasExecutableItemConditionsGUI> i) {
        String cPage = StringConverter.decoloredString(i.title);
        IfHasExecutableItemConditionsGUI gui = cache.get(i.player);
        if (i.name.contains("Next page")) {
            cache.replace(i.player, new IfHasExecutableItemConditionsGUI(Integer.parseInt(cPage.split("Page ")[1]) + 1, i.sPlugin, i.sObject, i.sActivator, gui.getConditionsManager(), gui.getConditions(), gui.getCondition(), gui.getDetail()));
            cache.get(i.player).openGUISync(i.player);
        } else if (i.name.contains("Previous page")) {
            cache.replace(i.player, new IfHasExecutableItemConditionsGUI(Integer.parseInt(cPage.split("Page ")[1]) - 1, i.sPlugin, i.sObject, i.sActivator, gui.getConditionsManager(), gui.getConditions(), gui.getCondition(), gui.getDetail()));
            cache.get(i.player).openGUISync(i.player);
        } else if (i.name.contains("New if has EI cdt")) {
            i.player.closeInventory();
            IfHasExecutableItemConditionGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, gui.getConditionsManager(), gui.getConditions(), gui.getCondition(), gui.getDetail());
        } else if (i.name.contains("Back")) {
            ConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, gui.getDetail(), gui.getConditions(), gui.getConditionsManager());
        } else return false;

        return true;
    }

    @Override
    public boolean noShiftLeftclicked(InteractionClickedGUIManager<IfHasExecutableItemConditionsGUI> i) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(InteractionClickedGUIManager<IfHasExecutableItemConditionsGUI> i) {
        return false;
    }

    @Override
    public boolean shiftClicked(InteractionClickedGUIManager<IfHasExecutableItemConditionsGUI> i) {
        String cPage = StringConverter.decoloredString(i.title);
        try {
            String id = i.name.split("✦ ID: ")[1];
			IfPlayerHasExecutableItem.deleteIPHEICdt(i.sPlugin, i.sObject, i.sActivator, id, cache.get(i.player).getDetail());

            IfHasExecutableItemConditionsGUI gui = cache.get(i.player);
            gui.getCondition().removeCondition(id);
            cache.get(i.player).clear();
            cache.get(i.player).loadCdts();
        } catch (Exception ignored) {
        }

        return true;
    }

    @Override
    public boolean shiftLeftClicked(InteractionClickedGUIManager<IfHasExecutableItemConditionsGUI> i) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(InteractionClickedGUIManager<IfHasExecutableItemConditionsGUI> i) {
        return false;
    }

    @Override
    public boolean leftClicked(InteractionClickedGUIManager<IfHasExecutableItemConditionsGUI> i) {
        return false;
    }

    @Override
    public boolean rightClicked(InteractionClickedGUIManager<IfHasExecutableItemConditionsGUI> interact) {
        return false;
    }


    public static IfHasExecutableItemConditionsGUIManager getInstance() {
        if (instance == null) instance = new IfHasExecutableItemConditionsGUIManager();
        return instance;
    }

    @Override
    public void saveTheConfiguration(Player p) {

    }
}
