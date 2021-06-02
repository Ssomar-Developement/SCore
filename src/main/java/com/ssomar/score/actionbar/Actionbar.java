package com.ssomar.score.actionbar;

public class Actionbar {

	private String name;

	private boolean Active= false;
	
	private boolean desactivation= false;

	private Integer time;


	public Actionbar(String name, Integer time) {
		this.name = name;
		this.time = time;
	}


	public boolean isDesactivation() {
		return desactivation;
	}

	public void setDesactivation(boolean desactivation) {
		this.desactivation = desactivation;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isActive() {
		return Active;
	}

	public void setActive(boolean active) {
		Active = active;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}



}
