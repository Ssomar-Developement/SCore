package com.ssomar.score.utils;

public enum DetailedInteraction {

	AIR("AIR"), 
	BLOCK("BLOCK");
	
	private String[] names;

	DetailedInteraction(String... names) {
		this.names = names;
	}
	
	public static boolean isDetailedInteraction(String entry) {
		for (DetailedInteraction dI : values()) {
			for (String name : dI.getNames()) {
				if (name.equalsIgnoreCase(entry)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static DetailedInteraction getDetailedInteraction(String entry) {
		for (DetailedInteraction dI : values()) {
			for (String name : dI.getNames()) {
				if (name.equalsIgnoreCase(entry)) {
					return dI;
				}
			}
		}
		return null;
	}
	
	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}
}