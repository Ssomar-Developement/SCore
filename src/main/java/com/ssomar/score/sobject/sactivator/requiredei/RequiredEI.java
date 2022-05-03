package com.ssomar.score.sobject.sactivator.requiredei;

import com.ssomar.executableitems.executableitems.ExecutableItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class RequiredEI {
	
	private String id;
	
	private String EI_ID;

	private ExecutableItem item;
	
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
}
