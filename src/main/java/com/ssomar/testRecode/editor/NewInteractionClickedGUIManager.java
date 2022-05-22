package com.ssomar.testRecode.editor;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.conditions.RequestMessageInfo;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class NewInteractionClickedGUIManager<T extends GUI> {

	public HashMap<Player, T> cache;
	public T gui;
	/* Item clicked name */
	public String name;
	public String decoloredName;
	public String coloredDeconvertName;
	public String message;
	public Player player;
	public RequestMessageInfo msgInfos;
	
	public String title;

	public void setName(String name) {
		this.name = name;
		this.decoloredName = StringConverter.decoloredString(name);
		this.coloredDeconvertName = StringConverter.deconvertColor(name);
	}
}
