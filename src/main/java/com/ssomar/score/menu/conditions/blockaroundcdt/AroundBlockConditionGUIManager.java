package com.ssomar.score.menu.conditions.blockaroundcdt;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockCondition;
import com.ssomar.score.conditions.condition.blockcondition.custom.AroundBlockConditions;
import com.ssomar.score.conditions.managers.ConditionsManager;
import com.ssomar.score.menu.EditorCreator;
import com.ssomar.score.menu.score.GUIManagerSCore;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AroundBlockConditionGUIManager extends GUIManagerSCore<AroundBlockConditionGUI> {

    private static AroundBlockConditionGUIManager instance;

    public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, ConditionsManager conditionsManager, Conditions conditions, AroundBlockConditions condition, String detail) {
        cache.put(p, new AroundBlockConditionGUI(sPlugin, sObject, sActivator, conditionsManager, conditions, condition, detail));
        cache.get(p).openGUISync(p);
    }

    public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sActivator, ConditionsManager conditionsManager, Conditions conditions, AroundBlockConditions condition, AroundBlockCondition aBC, String detail) {
        cache.put(p, new AroundBlockConditionGUI(sPlugin, sObject, sActivator, conditionsManager, conditions, condition, aBC, detail));
        cache.get(p).openGUISync(p);
    }

    @Override
    public boolean allClicked(InteractionClickedGUIManager<AroundBlockConditionGUI> i) {
        if (i.name.contains(AroundBlockConditionGUI.MUST_BE_EXECUTABLEBLOCKS)) {
            requestWriting.put(i.player, AroundBlockConditionGUI.MUST_BE_EXECUTABLEBLOCKS);
            if (!currentWriting.containsKey(i.player)) {
                currentWriting.put(i.player, cache.get(i.player).getMustBeExecutableBlock());
            }
            i.player.closeInventory();
            space(i.player);
            i.player.sendMessage(StringConverter.coloredString("&a&l" + i.sPlugin.getNameDesign() + " &2&lEDITION MUST BE EXECUTABLEBLOCKS:"));
            this.showMustbeExecutableblocksEditor(i.player);
            space(i.player);
        } else if (i.name.contains(AroundBlockConditionGUI.MUST_BE_TYPE)) {
            requestWriting.put(i.player, AroundBlockConditionGUI.MUST_BE_TYPE);
            if (!currentWriting.containsKey(i.player)) {

                List<String> convert = new ArrayList<>();
                for (Material mat : cache.get(i.player).getMustBeType()) {
                    convert.add(mat.toString());
                }
                currentWriting.put(i.player, convert);
            }
            i.player.closeInventory();
            space(i.player);
            i.player.sendMessage(StringConverter.coloredString("&2&l" + i.sPlugin.getNameDesign() + " &a&lEDITION TYPE MUST BE:"));
            this.showTypeMustBeEditor(i.player);
            space(i.player);
        } else if (i.name.contains(AroundBlockConditionGUI.MUST_NOT_BE_TYPE)) {
            requestWriting.put(i.player, AroundBlockConditionGUI.MUST_NOT_BE_TYPE);
            if (!currentWriting.containsKey(i.player)) {

                List<String> convert = new ArrayList<>();
                for (Material mat : cache.get(i.player).getMustNotBeType()) {
                    convert.add(mat.toString());
                }
                currentWriting.put(i.player, convert);
            }
            i.player.closeInventory();
            space(i.player);
            i.player.sendMessage(StringConverter.coloredString("&2&l" + i.sPlugin.getNameDesign() + " &a&lEDITION TYPE MUST NOT BE:"));
            this.showTypeMustNotBeEditor(i.player);
            space(i.player);
        } else if (i.name.contains(AroundBlockConditionGUI.ERROR_MSG)) {
            requestWriting.put(i.player, AroundBlockConditionGUI.ERROR_MSG);
            i.player.closeInventory();
            space(i.player);
            i.player.sendMessage(StringConverter.coloredString("&2&l" + i.sPlugin.getNameDesign() + " &a&lWrite your error message:"));
            space(i.player);
        } else if (i.name.contains("Save") || i.name.contains("Create this Around block Condition")) {
            this.saveTheConfiguration(i.player);
            AroundBlockConditionGUI gui = cache.get(i.player);
            AroundBlockConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, gui.getConditionsManager(), gui.getConditions(), gui.getCondition(), gui.getDetail());
            cache.remove(i.player);
            requestWriting.remove(i.player);
        } else if (i.name.contains("Back")) {
            AroundBlockConditionGUI gui = cache.get(i.player);
            AroundBlockConditionsGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, gui.getConditionsManager(), gui.getConditions(), gui.getCondition(), gui.getDetail());
            cache.remove(i.player);
            requestWriting.remove(i.player);
        } else return false;

        return true;
    }

    @Override
    public boolean shiftClicked(InteractionClickedGUIManager<AroundBlockConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean noShiftclicked(InteractionClickedGUIManager<AroundBlockConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(InteractionClickedGUIManager<AroundBlockConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(InteractionClickedGUIManager<AroundBlockConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(InteractionClickedGUIManager<AroundBlockConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean shiftRightClicked(InteractionClickedGUIManager<AroundBlockConditionGUI> interact) {
        return false;
    }

    @Override
    public boolean leftClicked(InteractionClickedGUIManager<AroundBlockConditionGUI> i) {
        AroundBlockConditionGUI gui = cache.get(i.player);

        if (i.name.contains(AroundBlockConditionGUI.SOUTH_VALUE)
                || i.name.contains(AroundBlockConditionGUI.NORTH_VALUE)
                || i.name.contains(AroundBlockConditionGUI.WEST_VALUE)
                || i.name.contains(AroundBlockConditionGUI.EAST_VALUE)
                || i.name.contains(AroundBlockConditionGUI.ABOVE_VALUE)
                || i.name.contains(AroundBlockConditionGUI.UNDER_VALUE)) {

            gui.incrValue(StringConverter.decoloredString(i.name));
        } else return false;

        return true;
    }

    @Override
    public boolean rightClicked(InteractionClickedGUIManager<AroundBlockConditionGUI> i) {
        if (i.name.contains(AroundBlockConditionGUI.SOUTH_VALUE)
                || i.name.contains(AroundBlockConditionGUI.NORTH_VALUE)
                || i.name.contains(AroundBlockConditionGUI.WEST_VALUE)
                || i.name.contains(AroundBlockConditionGUI.EAST_VALUE)
                || i.name.contains(AroundBlockConditionGUI.ABOVE_VALUE)
                || i.name.contains(AroundBlockConditionGUI.UNDER_VALUE)) {

            cache.get(i.player).decrValue(i.name);
        } else return false;

        return true;
    }


    public void receivedMessage(Player p, String message) {
        boolean notExit = true;
        SPlugin sPlugin = cache.get(p).getsPlugin();
        String plName = sPlugin.getNameDesign();

        if (message.contains("exit")) {
            boolean pass = false;
            if (StringConverter.decoloredString(message).equals("exit") || pass) {
                if (requestWriting.get(p).equals(AroundBlockConditionGUI.MUST_BE_TYPE)) {
                    List<Material> result = new ArrayList<>();
                    for (String str : currentWriting.get(p)) {
                        try {
                            result.add(Material.valueOf(str));
                        } catch (Exception ignored) {
                        }
                    }
                    cache.get(p).updateMustBeType(result);
                } else if (requestWriting.get(p).equals(AroundBlockConditionGUI.MUST_NOT_BE_TYPE)) {
                    List<Material> result = new ArrayList<>();
                    for (String str : currentWriting.get(p)) {
                        try {
                            result.add(Material.valueOf(str));
                        } catch (Exception ignored) {
                        }
                    }
                    cache.get(p).updateMustNotBeType(result);
                } else if (requestWriting.get(p).equals(AroundBlockConditionGUI.MUST_BE_EXECUTABLEBLOCKS)) {
                    List<String> result = new ArrayList<>(currentWriting.get(p));
                    cache.get(p).updateMustBeExecutableBlock(result);
                }
                currentWriting.remove(p);
                requestWriting.remove(p);
                cache.get(p).openGUISync(p);
                notExit = false;
            }
        }
        if (notExit) {
            if (message.contains("delete line <")) {
                this.deleteLine(message, p);
                this.showTheGoodEditor(requestWriting.get(p), p);
                space(p);
                space(p);
            } else if (requestWriting.get(p).equals(AroundBlockConditionGUI.MUST_BE_TYPE)) {
                try {
                    Material mat = Material.valueOf(message.toUpperCase());
                    if (currentWriting.containsKey(p)) {
                        currentWriting.get(p).add(mat.toString());
                    } else {
                        ArrayList<String> list = new ArrayList<>();
                        list.add(mat.toString());
                        currentWriting.put(p, list);
                    }
                    p.sendMessage(StringConverter.coloredString("&a&l" + plName + " &2&lEDITION &aYou have added new accepted type!"));
                } catch (Exception e) {
                    p.sendMessage(StringConverter.coloredString("&c&l" + plName + " &4&lERROR &cEnter a valid type/material please !"));
                }
                this.showTheGoodEditor(requestWriting.get(p), p);
            } else if (requestWriting.get(p).equals(AroundBlockConditionGUI.MUST_NOT_BE_TYPE)) {
                try {
                    Material mat = Material.valueOf(message.toUpperCase());
                    if (currentWriting.containsKey(p)) {
                        currentWriting.get(p).add(mat.toString());
                    } else {
                        ArrayList<String> list = new ArrayList<>();
                        list.add(mat.toString());
                        currentWriting.put(p, list);
                    }
                    p.sendMessage(StringConverter.coloredString("&a&l" + plName + " &2&lEDITION &aYou have added new denied type!"));
                } catch (Exception e) {
                    p.sendMessage(StringConverter.coloredString("&c&l" + plName + " &4&lERROR &cEnter a valid type/material please !"));
                }
                this.showTheGoodEditor(requestWriting.get(p), p);
            } else if (requestWriting.get(p).equals(AroundBlockConditionGUI.MUST_BE_EXECUTABLEBLOCKS)) {
                if (currentWriting.containsKey(p)) {
                    currentWriting.get(p).add(message);
                } else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(message);
                    currentWriting.put(p, list);
                }
                p.sendMessage(StringConverter.coloredString("&a&l" + plName + " &2&lEDITION &aYou have added new accepted EB!"));

                this.showTheGoodEditor(requestWriting.get(p), p);
            } else if (requestWriting.get(p).equals(AroundBlockConditionGUI.ERROR_MSG)) {
                cache.get(p).updateActually(AroundBlockConditionGUI.ERROR_MSG, StringConverter.coloredString(message));
                cache.get(p).openGUISync(p);
                requestWriting.remove(p);
            }
        }
    }

    public void showMustbeExecutableblocksEditor(Player p) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ MustBeEB:");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "EB:", false, false, false, true, true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(p);
    }

    public void showTypeMustBeEditor(Player p) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ TypeMustBe:");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Type:", false, false, false, true, true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(p);
    }

    public void showTypeMustNotBeEditor(Player p) {
        List<String> beforeMenu = new ArrayList<>();
        beforeMenu.add("&7➤ TypeMustNotBe:");

        HashMap<String, String> suggestions = new HashMap<>();

        EditorCreator editor = new EditorCreator(beforeMenu, currentWriting.get(p), "Type:", false, false, false, true, true, true, false, "", suggestions);
        editor.generateTheMenuAndSendIt(p);
    }

    public void showTheGoodEditor(String value, Player p) {

        switch (value) {
            case AroundBlockConditionGUI.MUST_BE_EXECUTABLEBLOCKS:
                showMustbeExecutableblocksEditor(p);
                break;

            case AroundBlockConditionGUI.MUST_BE_TYPE:
                showTypeMustBeEditor(p);
                break;

            case AroundBlockConditionGUI.MUST_NOT_BE_TYPE:
                showTypeMustNotBeEditor(p);
                break;

            default:
                break;
        }
    }


    public static AroundBlockConditionGUIManager getInstance() {
        if (instance == null) instance = new AroundBlockConditionGUIManager();
        return instance;
    }

    @Override
    public void saveTheConfiguration(Player p) {
        SPlugin sPlugin = cache.get(p).getsPlugin();
        SObject sObject = cache.get(p).getSObject();
        SActivator sActivator = cache.get(p).getSAct();

        AroundBlockCondition aBC = cache.get(p).getABC();

        aBC.setSouthValue(cache.get(p).getInt(AroundBlockConditionGUI.SOUTH_VALUE));
        aBC.setNorthValue(cache.get(p).getInt(AroundBlockConditionGUI.NORTH_VALUE));
        aBC.setWestValue(cache.get(p).getInt(AroundBlockConditionGUI.WEST_VALUE));
        aBC.setEastValue(cache.get(p).getInt(AroundBlockConditionGUI.EAST_VALUE));
        aBC.setAboveValue(cache.get(p).getInt(AroundBlockConditionGUI.ABOVE_VALUE));
        aBC.setUnderValue(cache.get(p).getInt(AroundBlockConditionGUI.UNDER_VALUE));

        aBC.setErrorMsg(cache.get(p).getActuallyWithColor(AroundBlockConditionGUI.ERROR_MSG));
        aBC.setBlockMustBeExecutableBlock(cache.get(p).getMustBeExecutableBlock());
        aBC.setBlockTypeMustBe(cache.get(p).getMustBeType());
		aBC.setBlockTypeMustNotBe(cache.get(p).getMustNotBeType());

        AroundBlockCondition.saveAroundBlockCdt(sPlugin, sObject, sActivator, aBC, cache.get(p).getDetail());
    }
}

