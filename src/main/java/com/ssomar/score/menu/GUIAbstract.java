package com.ssomar.score.menu;

import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;

public abstract class GUIAbstract extends GUI{
	
	SPlugin sPlugin;
	SObject sObject;
	SActivator sAct;

	public GUIAbstract(String name, int size, SPlugin sPlugin, SObject sObject, SActivator sAct) {
		super(name, size);	
		this.sPlugin = sPlugin;
		this.sObject = sObject;
		this.sAct = sAct;
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
}

