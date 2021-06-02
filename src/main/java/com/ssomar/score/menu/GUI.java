package com.ssomar.score.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.StringConverter;

public abstract class GUI {
	
	public final static String PARKOUR_ID = "✚ PARKOUR ID:";

	public final static String CHECKPOINT_ID = "✚ CHECKPOINT ID:";
	
	//public final static String CATEGORY_ID = MessageMain.getInstance().getMessage(Message.M_categoryBook);
	
	public Material WRITABLE_BOOK = null;
	
	public Material CLOCK = null;
	
	public Material ENCHANTING_TABLE = null;
	
	public Material NEXT_PAGE_MAT = null;
	
	public Material PREVIOUS_PAGE_MAT = null;
	
	public Material RED = null;
	
	public final static String TITLE_COLOR= "&e&l";
	
	//public final static String CLOSE = MessageMain.getInstance().getMessage(Message.close);
	
	public final static String CLICK_HERE_TO_CHANGE = "&a✎ Click here to change";
	
	//public final static String BACK_TO_CATEGORIES = MessageMain.getInstance().getMessage(Message.backToCategories);
	
	//public static String PAGE = MessageMain.getInstance().getMessage(Message.M_page);

	private Inventory inv;


	public GUI(String name, int size) {
		inv = Bukkit.createInventory(null, size, StringConverter.coloredString(name));
		if(!SCore.is1v12()) {
			for(int j=0; j<size; j++) {
				//createItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 	1 , j, 	"&7", 	GeneralConfig.getInstance().isEnchantItemsInGUI(), false);
			}
		}
		this.init();
	}
	
	public GUI(Inventory inv) {
		this.inv = inv;
		this.init();
	}
	
	public void init() {
		if(SCore.is1v12()) {
			WRITABLE_BOOK = Material.valueOf("BOOK_AND_QUILL");
			CLOCK = Material.valueOf("WATCH");
			ENCHANTING_TABLE = Material.valueOf("ENCHANTMENT_TABLE");
			NEXT_PAGE_MAT = Material.ARROW;
			PREVIOUS_PAGE_MAT = Material.ARROW;
			RED = Material.BARRIER;
		}
		else {
			WRITABLE_BOOK = Material.WRITABLE_BOOK;
			CLOCK = Material.CLOCK;
			ENCHANTING_TABLE = Material.ENCHANTING_TABLE;
			NEXT_PAGE_MAT = Material.PURPLE_STAINED_GLASS_PANE;
			PREVIOUS_PAGE_MAT = Material.PURPLE_STAINED_GLASS_PANE;
			RED = Material.RED_STAINED_GLASS_PANE;
		}
	}

	public void createItem(Material material, int amount, int invSlot, String displayName, boolean glow, boolean haveEnchant, String... loreString) {

		ItemStack item= new ItemStack(material,amount);
		ItemMeta meta= item.getItemMeta();
		List<String> lore = new ArrayList<>();


		if(glow || haveEnchant) {
			meta.addEnchant(Enchantment.PROTECTION_FALL, 6, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}

		//meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setDisplayName(StringConverter.coloredString(displayName));

		for(String s : loreString) lore.add(StringConverter.coloredString(s));

		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(invSlot, item);	

	}
	
	public void createItem(Material material, int amount, int invSlot, String displayName, String localizedName, boolean glow, boolean haveEnchant, String... loreString) {

		ItemStack item= new ItemStack(material,amount);
		ItemMeta meta= item.getItemMeta();
		List<String> lore = new ArrayList<>();

		meta.setLocalizedName(localizedName);
		if(glow || haveEnchant) {
			meta.addEnchant(Enchantment.PROTECTION_FALL, 6, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}

		//meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setDisplayName(StringConverter.coloredString(displayName));

		for(String s : loreString) lore.add(StringConverter.coloredString(s));

		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(invSlot, item);	

	}
	
	public void createItem(Material material, int amount, int invSlot, String displayName, boolean glow, boolean haveEnchant, int customTextureTag, String... loreString) {

		ItemStack item= new ItemStack(material,amount);
		ItemMeta meta= item.getItemMeta();
		List<String> lore = new ArrayList<>();


		if(glow || haveEnchant) {
			meta.addEnchant(Enchantment.PROTECTION_FALL, 6, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}

		//meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setDisplayName(StringConverter.coloredString(displayName));

		for(String s : loreString) lore.add(StringConverter.coloredString(s));

		meta.setLore(lore);
		meta.setCustomModelData(customTextureTag);
		item.setItemMeta(meta);
		inv.setItem(invSlot, item);	

	}

	public void createItem(ItemStack itemS, int amount, int invSlot, String displayName, boolean glow, boolean haveEnchant, String... loreString) {

		ItemStack item= itemS;
		ItemMeta meta= item.getItemMeta();
		List<String> lore = new ArrayList<>();


		if(glow || haveEnchant) {
			meta.addEnchant(Enchantment.PROTECTION_FALL, 6, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}

		//meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setDisplayName(StringConverter.coloredString(displayName));

		for(String s : loreString) lore.add(StringConverter.coloredString(s));

		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(invSlot, item);	

	}
	
	public void createItem(ItemStack itemS, int amount, int invSlot, String displayName, String localizedName, boolean glow, boolean haveEnchant, String... loreString) {
		ItemStack item= itemS;
		ItemMeta meta= item.getItemMeta();
		meta.setLocalizedName(localizedName);
		item.setItemMeta(meta);
		
		this.createItem(item, amount, invSlot, displayName, glow, haveEnchant, loreString);
	}
	

	public void removeItem(int invSlot) {

		inv.clear(invSlot);
	}

	public ItemStack getByName(String name) {
		for(ItemStack item: inv.getContents()) {
			if(item!=null && item.hasItemMeta() && StringConverter.decoloredString(item.getItemMeta().getDisplayName()).equals(StringConverter.decoloredString(name))) return item;
		}
		return null;
	}

	public void openGUISync(Player player) {
		BukkitRunnable runnable = new BukkitRunnable() {
			@Override
			public void run() {		
				player.openInventory(inv);
			}
		};
		runnable.runTask(SCore.getPlugin());
	}

	public String getActually(ItemStack item) {
		List<String> lore = item.getItemMeta().getLore();
		for(String s: lore) {
			if(StringConverter.decoloredString(s).contains("actually: ")) return StringConverter.decoloredString(s).split("actually: ")[1];
		}
		return null;
	}

	public String getActuallyWithColor(ItemStack item) {
		List<String> lore = item.getItemMeta().getLore();
		for(String s: lore) {
			if(StringConverter.decoloredString(s).contains("actually: ")) return StringConverter.deconvertColor(s).split("actually: ")[1];
		}
		return null;
	}

	public void updateActually(ItemStack item, String update) {
		ItemMeta meta= item.getItemMeta();
		List<String> lore = meta.getLore();
		int cpt=0;
		for(String s: lore) {
			if(StringConverter.decoloredString(s).contains("actually:")) break;
			cpt++;
		}
		lore.set(cpt, StringConverter.coloredString("&7actually: &e"+update));
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	
	public String getActually(String itemName) {
		return this.getActually(this.getByName(itemName));
	}

	public String getActuallyWithColor(String itemName) {	
		return this.getActuallyWithColor(this.getByName(itemName));	
	}

	public void updateActually(String itemName, String update) {
		this.updateActually(this.getByName(itemName), update);
	}

	public void updateCondition(String name, String condition){
		ItemStack item = this.getByName(name);
		if(condition.equals("")) this.updateActually(item, "&cNO CONDITION");
		else this.updateActually(item, condition);
	}

	public String getCondition(String name) {
		if(this.getActually(this.getByName(name)).contains("NO CONDITION")) return "";
		else return this.getActually(this.getByName(name));
	}

	public Inventory getInv() {
		return inv;
	}

	public void setInv(Inventory inv) {
		this.inv = inv;
	}

	public void updateBoolean(String itemName, boolean value) {
		ItemStack item = this.getByName(itemName);
		if(value) updateActually(item, "&aTrue");
		else updateActually(item, "&cFalse");
	}

	public void changeBoolean(String itemName) {
		ItemStack item = this.getByName(itemName);
		if(getActually(item).contains("True")) {
			updateBoolean(itemName, false);
		}
		else {
			updateBoolean(itemName, true);
		}
	}

	public boolean getBoolean(String itemName) {
		ItemStack item = this.getByName(itemName);
		return getActually(item).contains("True");	
	}
	
	public void updateInt(String itemName, int value) {
		ItemStack item = this.getByName(itemName);
		updateActually(item, value+"");
	}

	public int getInt(String itemName) {
		ItemStack item = this.getByName(itemName);
		return Integer.valueOf(getActually(item));	
	}
	
	public void updateDouble(String itemName, double value) {
		ItemStack item = this.getByName(itemName);
		updateActually(item, value+"");
	}

	public double getDouble(String itemName) {
		ItemStack item = this.getByName(itemName);
		return Double.valueOf(getActually(item));	
	}
	
	public String getParkourID() {
		return this.getActually(this.getByName(PARKOUR_ID));
	}
	
	public String getCheckpointID() {
		return this.getActually(this.getByName(CHECKPOINT_ID));
	}
	
//	public String getCategoryID() {
//		return this.getActually(this.getByName(CATEGORY_ID));
//	}
	
}
