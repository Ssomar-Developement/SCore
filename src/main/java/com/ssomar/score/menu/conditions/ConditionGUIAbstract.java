package com.ssomar.score.menu.conditions;

import com.ssomar.score.menu.GUIAbstract;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;

public abstract class ConditionGUIAbstract extends GUIAbstract{
	
	String detail;

	public ConditionGUIAbstract(String name, int size, SPlugin sPlugin, SObject sObject, SActivator sAct, String detail) {
		super(name, size, sPlugin, sObject, sAct);	
		this.detail = detail;
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
	
}
