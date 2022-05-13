package com.ssomar.testRecode.menu;

import com.ssomar.score.menu.conditions.RequestMessageInfo;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class NewInteractionClickedGUIManager<T> {

	public HashMap<Player, T> cache;
	public T gui;
	/* Item clicked name */
	public String name;
	public Player player;
	public RequestMessageInfo msgInfos;
	
	public String title;
}
