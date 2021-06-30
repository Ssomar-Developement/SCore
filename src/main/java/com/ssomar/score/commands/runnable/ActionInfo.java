package com.ssomar.score.commands.runnable;

import com.ssomar.executableitems.items.Item;

public class ActionInfo {
	
	private String name;
	
	private Integer slot;
	
	private Item item;
	
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
}
