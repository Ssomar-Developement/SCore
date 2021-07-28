package com.ssomar.score.menu.conditions;

import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.sobject.sactivator.conditions.Conditions;
import com.ssomar.score.splugin.SPlugin;

public abstract class ConditionGUIAbstract extends GUIAbstract{
	
	private String detail;
	
	private Conditions conditions;
	
	public ConditionGUIAbstract(String name, int size, SPlugin sPlugin, SObject sObject, SActivator sAct, String detail, Conditions conditions) {
		super(name, size, sPlugin, sObject, sAct);	
		this.conditions = conditions;
		this.detail = detail;
		this.loadTheGUI();
	}
	
	public abstract void loadTheGUI();
	
	public void reloadGUI() {
		conditions.init();
		this.loadTheGUI();
	}
	
	public void updateMessage(String itemName, String message) {
		if (message.isEmpty() || message.equals(" ")) this.updateActually(itemName, "&cNO MESSAGE");
		else this.updateActually(itemName, message);
	}
	
	public String getMessage(String itemName) {
		String msg = this.getActuallyWithColor(itemName);
		
		if (msg.contains("NO MESSAGE")) return "";
		else return msg;
	}	

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Conditions getConditions() {
		return conditions;
	}

	public void setConditions(Conditions conditions) {
		this.conditions = conditions;
	}
	
}
