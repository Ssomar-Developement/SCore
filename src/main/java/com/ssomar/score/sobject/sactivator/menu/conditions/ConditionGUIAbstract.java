package com.ssomar.score.sobject.sactivator.menu.conditions;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;

public abstract class ConditionGUIAbstract extends GUI{
	
	SPlugin sPlugin;
	SObject sObject;
	SActivator sAct;
	String detail;

	public ConditionGUIAbstract(String name, int size, SPlugin sPlugin, SObject sObject, SActivator sAct, String detail) {
		super(name, size);	
		this.sPlugin = sPlugin;
		this.sObject = sObject;
		this.sAct = sAct;
		this.detail = detail;
	}
	
	

	public SPlugin getsPlugin() {
		return sPlugin;
	}

	public void setsPlugin(SPlugin sPlugin) {
		this.sPlugin = sPlugin;
	}

	public SObject getSObject() {
		return sObject;
	}

	public void setSObject(SObject sObject) {
		this.sObject = sObject;
	}

	public SActivator getSAct() {
		return sAct;
	}

	public void setSAct(SActivator sAct) {
		this.sAct = sAct;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
