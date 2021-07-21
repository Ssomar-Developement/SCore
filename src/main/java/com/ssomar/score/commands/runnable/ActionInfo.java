package com.ssomar.score.commands.runnable;

import com.ssomar.executableitems.items.Item;
import com.ssomar.score.sobject.sactivator.SActivator;

public class ActionInfo {
	
	private String name;
	
	/* The slot where the action was activated */
	private Integer slot;
	
	/* The item (ExecutableItems) that actives the action */
	private Item item;
	
	/* The activator that actives the action */
	private SActivator sActivator;
	
	private boolean isEventCallByMineInCube = false;
	
	public ActionInfo(String name, Integer slot) {
		this.name = name;
		this.slot = slot;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSlot() {
		return slot;
	}

	public void setSlot(Integer slot) {
		this.slot = slot;
	}

	public boolean isEventCallByMineInCube() {
		return isEventCallByMineInCube;
	}

	public void setEventCallByMineInCube(boolean isEventCallByMineInCube) {
		this.isEventCallByMineInCube = isEventCallByMineInCube;
	}

	public SActivator getsActivator() {
		return sActivator;
	}

	public void setsActivator(SActivator sActivator) {
		this.sActivator = sActivator;
	}
}
