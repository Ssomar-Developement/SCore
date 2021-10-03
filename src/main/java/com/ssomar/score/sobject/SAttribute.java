package com.ssomar.score.sobject;

import org.bukkit.attribute.Attribute;

public class SAttribute {
	
	private Attribute attribute;
	
	private String id;

	public SAttribute(Attribute attribute, String id) {
		super();
		this.attribute = attribute;
		this.id = id;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
