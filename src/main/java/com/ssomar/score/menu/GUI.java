package com.ssomar.score.menu;

import com.ssomar.score.SCore;
import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class GUI implements IGUI {

    public final static String DEFAULT_ITEM_NAME = "&e&lDefault Name";

    public final static String CLICK_HERE_TO_CHANGE = "&a✎ Click here to change";

    public final static String TITLE_COLOR = "&e&l";

    public final static String PAGE = " - Page ";

    public final static String NEXT_PAGE = "&5&l▶ &dNext page";

    public final static String PREVIOUS_PAGE = "&dPrevious page &5&l◀";

    public final static String NEW = "&2&l▶ &aNew";

    public final static String EXIT = "&4&l▶ &cExit";

    public final static String BACK = "&4&l▶&c Back";

    public final static String SAVE = "&2&l✔ &aSave";

    public final static String RESET = "&4&l✘ &cReset";

    public final static String OBJECT_ID = "✚ OBJECT ID:";

    public final static String COLOR_OBJECT_ID = "&2&l✚ &a&lOBJECT ID:";

    public final static String ACTIVATOR_ID = "✚ ACTIVATOR ID:";

    public final static String COLOR_ACTIVATOR_ID = "&2&l✚ &a&lACTIVATOR ID:";

    public static Material NEXT_PAGE_MAT = null;

    public static Material PREVIOUS_PAGE_MAT = null;

    public static Material WRITABLE_BOOK = null;

    public static Material CLOCK = null;

    public static Material ENCHANTING_TABLE = null;

    public static Material HEAD = null;

    public static Material GRINDSTONE = null;

    public static Material COMPARATOR = null;

    public static Material RED = null;

    public static Material ORANGE = null;

    public static Material GREEN = null;

    public static Material YELLOW = null;

    public static Material PURPLE = null;

    public static Material BLUE = null;

    public static Material LIGHTNING_ROD = null;

    private Inventory inv;

    private int size;


    public GUI(String name, int size) {
        inv = Bukkit.createInventory(this, size, StringConverter.coloredString(name));
        this.size = size;
        for (int j = 0; j < size; j++) {
            createBackGroundItem(j);
        }
        init();
    }

    public GUI(Inventory inv) {
        this.inv = inv;
        this.init();
    }

    public void init() {
        if (SCore.is1v12Less()) {
            WRITABLE_BOOK = Material.valueOf("BOOK_AND_QUILL");
            CLOCK = Material.valueOf("WATCH");
            ENCHANTING_TABLE = Material.valueOf("ENCHANTMENT_TABLE");
            NEXT_PAGE_MAT = Material.ARROW;
            PREVIOUS_PAGE_MAT = Material.ARROW;
            HEAD = Material.valueOf("SKULL_ITEM");
            COMPARATOR = Material.valueOf("REDSTONE_COMPARATOR");
            RED = Material.REDSTONE_BLOCK;
            ORANGE = Material.BARRIER;
            GREEN = Material.EMERALD;
            YELLOW = Material.HOPPER;
            PURPLE = Material.HOPPER;
            BLUE = Material.ANVIL;
            GRINDSTONE = Material.LEVER;
        } else {
            WRITABLE_BOOK = Material.WRITABLE_BOOK;
            CLOCK = Material.CLOCK;
            ENCHANTING_TABLE = Material.ENCHANTING_TABLE;
            NEXT_PAGE_MAT = Material.PURPLE_STAINED_GLASS_PANE;
            PREVIOUS_PAGE_MAT = Material.PURPLE_STAINED_GLASS_PANE;
            HEAD = Material.PLAYER_HEAD;
            COMPARATOR = Material.COMPARATOR;
            RED = Material.RED_STAINED_GLASS_PANE;
            ORANGE = Material.ORANGE_STAINED_GLASS_PANE;
            GREEN = Material.LIME_STAINED_GLASS_PANE;
            YELLOW = Material.YELLOW_STAINED_GLASS_PANE;
            PURPLE = Material.MAGENTA_STAINED_GLASS_PANE;
            BLUE = Material.BLUE_STAINED_GLASS_PANE;
            GRINDSTONE = Material.GRINDSTONE;
        }

        if (SCore.is1v18Plus()) {
            LIGHTNING_ROD = Material.LIGHTNING_ROD;
        }
        else LIGHTNING_ROD = Material.TRIPWIRE_HOOK;
    }

    public void load() {

    }

    public void createItem(Material material, int amount, int invSlot, String displayName, boolean glow, boolean haveEnchant, String... loreString) {

        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();


        if (glow || haveEnchant) {
            meta.addEnchant(Enchantment.PROTECTION_FALL, 6, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setDisplayName(StringConverter.coloredString(displayName));

        for (String s : loreString) lore.add(StringConverter.coloredString(s));

        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot, item);

    }

    public void addItem(Material material, int amount, String displayName, boolean glow, boolean haveEnchant, String... loreString) {
        int i = 0;
        for (ItemStack item : inv) {
            if (item == null || (!SCore.is1v12Less() && item.getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE)))
                break;
            i++;
        }
        createItem(material, amount, i, displayName, glow, haveEnchant, loreString);
    }


    public void createItem(Material material, int amount, int invSlot, String displayName, boolean glow, boolean haveEnchant, int customTextureTag, String... loreString) {

        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();


        if (glow || haveEnchant) {
            meta.addEnchant(Enchantment.PROTECTION_FALL, 6, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setDisplayName(StringConverter.coloredString(displayName));

        for (String s : loreString) lore.add(StringConverter.coloredString(s));

        meta.setLore(lore);
        if (!SCore.is1v13Less()) meta.setCustomModelData(customTextureTag);
        item.setItemMeta(meta);
        inv.setItem(invSlot, item);

    }

    public void createItem(ItemStack itemS, int amount, int invSlot, String displayName, boolean glow, boolean haveEnchant, String... loreString) {

        ItemStack item = itemS;
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();


        if (glow || haveEnchant) {
            meta.addEnchant(Enchantment.PROTECTION_FALL, 6, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setDisplayName(StringConverter.coloredString(displayName));

        for (String s : loreString) lore.add(StringConverter.coloredString(s));

        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot, item);

    }

    public void createBackGroundItem(int slot) {
        if (!SCore.is1v13Less()) createItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1, slot, "&7", true, false);
        else removeItem(slot);
    }

    public void removeItem(int invSlot) {

        inv.clear(invSlot);
    }

    public void clear() {
        inv.clear();
    }

    public void clearAndSetBackground() {
        inv.clear();
        for (int j = 0; j < size; j++) {
            createBackGroundItem(j);
        }
    }

    public ItemStack getByName(String name) {
        for (ItemStack item : inv.getContents()) {
            if (item != null && item.hasItemMeta() && StringConverter.decoloredString(item.getItemMeta().getDisplayName()).equals(StringConverter.decoloredString(name))) {
                return item;
            }
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
        runnable.runTask(SCore.plugin);
    }

    public String getObjectID() {
        return this.getActually(this.getByName(OBJECT_ID));
    }

    @Nullable
    public SObject getSObject(SPlugin plugin) {
        try {
            return LinkedPlugins.getSObject(plugin, this.getObjectID());
        } catch (Exception e) {
            return null;
        }
    }

    public String getActivatorID() {
        return this.getActually(this.getByName(ACTIVATOR_ID));
    }

    @Nullable
    public SActivator getActivator(SPlugin plugin) {
        try {
            SObject sObject = this.getSObject(plugin);
            for (SActivator act : sObject.getActivators()) {
                if (act.getID().equals(this.getActivatorID())) {
                    return act;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public String getActually(ItemStack item) {
        List<String> lore = item.getItemMeta().getLore();
        for (String s : lore) {
            if (StringConverter.decoloredString(s).contains("actually: ")) {
                try {
                    return StringConverter.decoloredString(s).split("actually: ")[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }
        }
        return null;
    }

    public String getActuallyWithColor(ItemStack item) {
        List<String> lore = item.getItemMeta().getLore();
        for (String s : lore) {
            if (StringConverter.decoloredString(s).contains("actually: ")) {
                try {
                    return StringConverter.deconvertColor(s).split("actually: ")[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }
        }
        return null;
    }

    public void updateActually(ItemStack item, String update, boolean withColor) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        int cpt = 0;
        for (String s : lore) {
            if (StringConverter.decoloredString(s).contains("actually:")) break;
            cpt++;
        }
        if (withColor) lore.set(cpt, StringConverter.coloredString("&7actually: " + update));
        else lore.set(cpt, StringConverter.coloredString("&7actually: &e" + update));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public void updateActually(ItemStack item, String update) {
        updateActually(item, update, false);
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

    public void updateActually(String itemName, String update, Boolean withColor) {
        this.updateActually(this.getByName(itemName), update, withColor);
    }

    public void updateCondition(String name, String condition) {
        ItemStack item = this.getByName(name);
        if (condition.equals("")) this.updateActually(item, "&cNO CONDITION");
        else this.updateActually(item, condition);
    }

    public String getCondition(String name) {
        if (this.getActually(this.getByName(name)).contains("NO CONDITION")) return "";
        else return this.getActually(this.getByName(name));
    }

    public void updateConditionList(String name, List<String> list, String emptyStr) {
        ItemStack item = this.getByName(name);
        ItemMeta toChange = item.getItemMeta();
        List<String> loreUpdate = toChange.getLore().subList(0, 3);
        if (list.isEmpty()) loreUpdate.add(StringConverter.coloredString(emptyStr));
        else {
            for (String str : list) {
                loreUpdate.add(StringConverter.coloredString("&6➤ &e" + str));
            }
        }
        toChange.setLore(loreUpdate);
        item.setItemMeta(toChange);
    }

    public List<String> getConditionList(String name, String emptyStr) {
        ItemStack item = this.getByName(name);
        ItemMeta iM = item.getItemMeta();
        List<String> loreUpdate = iM.getLore().subList(3, iM.getLore().size());
        List<String> result = new ArrayList<>();
        for (String line : loreUpdate) {
            line = StringConverter.decoloredString(line);
            if (line.contains(emptyStr)) {
                return new ArrayList<>();
            } else result.add(line.replaceAll("➤ ", ""));
        }
        return result;
    }

    public List<String> getConditionListWithColor(String name, String emptyStr) {
        ItemStack item = this.getByName(name);
        ItemMeta iM = item.getItemMeta();
        List<String> loreUpdate = iM.getLore().subList(3, iM.getLore().size());
        List<String> result = new ArrayList<>();
        for (String line : loreUpdate) {
            line = StringConverter.deconvertColor(line);
            if (line.contains(emptyStr)) {
                return new ArrayList<>();
            }
            // If the player use a color the &e is not present in the line
            else result.add(line.replaceAll("&6➤ &e", "").replaceAll("&6➤ ", ""));
        }
        return result;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public void updateBoolean(String itemName, boolean value) {
        ItemStack item = this.getByName(itemName);
        if (value) updateActually(item, "&aTrue");
        else updateActually(item, "&cFalse");
    }

    public void changeBoolean(String itemName) {
        ItemStack item = this.getByName(itemName);
        updateBoolean(itemName, !getActually(item).contains("True"));
    }

    public boolean getBoolean(String itemName) {
        ItemStack item = this.getByName(itemName);
        return getActually(item).contains("True");
    }

    public void updateInt(String itemName, int value) {
        ItemStack item = this.getByName(itemName);
        updateActually(item, value + "");
    }

    public int getInt(String itemName) {
        ItemStack item = this.getByName(itemName);
        if(item == null) throw new NullPointerException("Item with the name: "+itemName+" is null");
        return Integer.parseInt(getActually(item));
    }

    public void updateDouble(String itemName, double value) {
        ItemStack item = this.getByName(itemName);
        updateActually(item, value + "");
    }

    public double getDouble(String itemName) {
        ItemStack item = this.getByName(itemName);
        return Double.parseDouble(getActually(item));
    }

}
