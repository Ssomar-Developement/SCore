package com.ssomar.score.menu.score;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.conditions.ConditionGUIAbstract;
import com.ssomar.score.menu.conditions.RequestMessageInfo;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;

public class InteractionClickedGUIManager<T> {

	public HashMap<Player, T> cache;
	public SPlugin sPlugin;
	public SObject sObject;
	public SActivator sActivator;
	public GUI gui;
	/* Item clicked name */
	public String name;
	public Player player;
	public RequestMessageInfo msgInfos;
	
	public String title;
	
	public void resetGUI() {
		gui = (GUI) cache.get(player);
		if(gui instanceof ConditionGUIAbstract) {
			((ConditionGUIAbstract) gui).reloadGUI();
		}
	}
}
