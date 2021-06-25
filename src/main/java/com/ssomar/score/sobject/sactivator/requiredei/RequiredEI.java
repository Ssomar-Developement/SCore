package com.ssomar.score.sobject.sactivator.requiredei;

import java.util.ArrayList;
import java.util.List;

import com.ssomar.executableitems.items.Item;

public class RequiredEI {
	
	private String id;
	
	private String EI_ID;

	private Item item;
	
	private int amount;
	
	private boolean consume;
	
	private List<Integer> validUsages;
	
	public RequiredEI (String id) {
		this.id = id;
		this.EI_ID = "NULL";
		this.item = null;
		this.amount = 1;
		this.consume = true;
		this.validUsages = new ArrayList<>();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEI_ID() {
		return EI_ID;
	}

	public void setEI_ID(String EI_ID) {
		this.EI_ID = EI_ID;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isConsume() {
		return consume;
	}

	public void setConsume(boolean consume) {
		this.consume = consume;
	}

	public List<Integer> getValidUsages() {
		return validUsages;
	}

	public void setValidUsages(List<Integer> validUsages) {
		this.validUsages = validUsages;
	}

	
	
}
