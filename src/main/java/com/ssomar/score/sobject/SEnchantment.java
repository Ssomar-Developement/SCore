package com.ssomar.score.sobject;

import org.bukkit.enchantments.Enchantment;

public class SEnchantment {

	private Enchantment enchantment;

	private String id;

	public SEnchantment(Enchantment enchantment, String id) {
		this.enchantment = enchantment;
		this.id = id;
	}

	
	public Enchantment getEnchantment() {
		return enchantment;
	}

	public void setEnchantment(Enchantment enchantment) {
		this.enchantment = enchantment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
