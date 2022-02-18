package com.ssomar.score.menu.conditions.customcdt.ei;

import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.menu.conditions.GUIManagerConditions;
import com.ssomar.score.menu.score.InteractionClickedGUIManager;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.CustomEIConditions;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;


public class CustomConditionsGUIManager extends GUIManagerConditions<CustomConditionsGUI>{

	private static CustomConditionsGUIManager instance;

	public void startEditing(Player p, SPlugin sPlugin, SObject sObject, SActivator sAct, CustomEIConditions cC, String detail) {
		cache.put(p, new CustomConditionsGUI(sPlugin, sObject, sAct, cC, detail));
		cache.get(p).openGUISync(p);
	}
	
	@Override
	public boolean noShiftclicked(InteractionClickedGUIManager<CustomConditionsGUI> i) {

		if(i.name.contains(CustomConditionsGUI.IF_NEED_PLAYER_CONFIRMATION)) i.gui.changeBoolean(CustomConditionsGUI.IF_NEED_PLAYER_CONFIRMATION);

		else if(i.name.contains(CustomConditionsGUI.IF_OWNER_OF_THE_EI)) i.gui.changeBoolean(CustomConditionsGUI.IF_OWNER_OF_THE_EI);

		else if(i.name.contains(CustomConditionsGUI.IF_NOT_OWNER_OF_THE_EI)) i.gui.changeBoolean(CustomConditionsGUI.IF_NOT_OWNER_OF_THE_EI);

		else if(i.name.contains(CustomConditionsGUI.IF_PLAYER_MUST_BE_ON_HIS_ISLAND)) {
			if(SCore.hasIridiumSkyblock) i.gui.changeBoolean(CustomConditionsGUI.IF_PLAYER_MUST_BE_ON_HIS_ISLAND);
			else i.player.sendMessage(StringConverter.coloredString("&4&l"+i.sPlugin.getNameDesign()+" &cYou haven't a compatible skyblock plugin to change this option ! (IridiumSkyblock)"));
		}
		
		else if(i.name.contains(CustomConditionsGUI.IF_PLAYER_MUST_BE_ON_HIS_CLAIM)) {
			if(SCore.hasLands || SCore.hasGriefPrevention || SCore.hasGriefDefender) i.gui.changeBoolean(CustomConditionsGUI.IF_PLAYER_MUST_BE_ON_HIS_CLAIM);
			else i.player.sendMessage(StringConverter.coloredString("&4&l"+i.sPlugin.getNameDesign()+" &cYou haven't a compatible claim plugin to change this option ! (Lands, GriefPrevention, GriefDefender)"));
		}

		else if(i.name.contains(CustomConditionsGUI.IF_PLAYER_MUST_BE_ON_HIS_PLOT)) {
			if(SCore.hasPlotSquared) i.gui.changeBoolean(CustomConditionsGUI.IF_PLAYER_MUST_BE_ON_HIS_PLOT);
			else i.player.sendMessage(StringConverter.coloredString("&4&l"+i.sPlugin.getNameDesign()+" &cYou haven't a compatible claim plugin to change this option ! (PlotSquared)"));
		}

		else return false;
		
		return true;
	}
	
	@Override
	public boolean allClicked(InteractionClickedGUIManager<CustomConditionsGUI> i) {
		return this.saveOrBackOrNothing(i);
	}

	@Override
	public boolean noShiftLeftclicked(InteractionClickedGUIManager<CustomConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean noShiftRightclicked(InteractionClickedGUIManager<CustomConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean shiftClicked(InteractionClickedGUIManager<CustomConditionsGUI> i) {
		String detail = cache.get(i.player).getDetail();
		this.saveTheConfiguration(i.player);
		i.sObject = LinkedPlugins.getSObject(i.sPlugin, i.sObject.getID());
		CustomConditionsMessagesGUIManager.getInstance().startEditing(i.player, i.sPlugin, i.sObject, i.sActivator, i.sObject.getActivator(i.sActivator.getID()).getCustomEIConditions(), detail);
	
		return true;
	}

	@Override
	public boolean shiftLeftClicked(InteractionClickedGUIManager<CustomConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean shiftRightClicked(InteractionClickedGUIManager<CustomConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean leftClicked(InteractionClickedGUIManager<CustomConditionsGUI> i) {
		return false;
	}

	@Override
	public boolean rightClicked(InteractionClickedGUIManager<CustomConditionsGUI> interact) {
		return false;
	}

	public void receivedMessage(Player p, String message) {

	}

	public static CustomConditionsGUIManager getInstance() {
		if(instance == null) instance = new CustomConditionsGUIManager();
		return instance;
	}

	@Override
	public void saveTheConfiguration(Player p) {
		SPlugin sPlugin = cache.get(p).getsPlugin();
		SObject sObject = cache.get(p).getSObject();
		SActivator sActivator = cache.get(p).getSAct();
		CustomEIConditions cC = (CustomEIConditions) cache.get(p).getConditions();

		cC.setIfNeedPlayerConfirmation(cache.get(p).getBoolean(CustomConditionsGUI.IF_NEED_PLAYER_CONFIRMATION));
		cC.setIfOwnerOfTheEI(cache.get(p).getBoolean(CustomConditionsGUI.IF_OWNER_OF_THE_EI));
		cC.setIfNotOwnerOfTheEI(cache.get(p).getBoolean(CustomConditionsGUI.IF_NOT_OWNER_OF_THE_EI));
		cC.setIfPlayerMustBeOnHisIsland(cache.get(p).getBoolean(CustomConditionsGUI.IF_PLAYER_MUST_BE_ON_HIS_ISLAND));
		cC.setIfPlayerMustBeOnHisClaim(cache.get(p).getBoolean(CustomConditionsGUI.IF_PLAYER_MUST_BE_ON_HIS_CLAIM));
		cC.setIfPlayerMustBeOnHisPlot(cache.get(p).getBoolean(CustomConditionsGUI.IF_PLAYER_MUST_BE_ON_HIS_PLOT));

		CustomEIConditions.saveCustomConditions(sPlugin, sObject, sActivator, cC, cache.get(p).getDetail());
		cache.remove(p);
		requestWriting.remove(p);
		LinkedPlugins.reloadSObject(sPlugin, sObject.getID());
	}
}
