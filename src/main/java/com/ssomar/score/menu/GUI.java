package com.ssomar.score.menu;

import com.ssomar.score.SCore;
import com.ssomar.score.languages.messages.TM;
import com.ssomar.score.languages.messages.Text;
import com.ssomar.score.utils.FixedMaterial;
import com.ssomar.score.utils.item.MakeItemGlow;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GUI implements IGUI {

    public static String CLICK_HERE_TO_CHANGE;

    public static String CLICK_TO_REMOVE;
    public static String SHIFT_CLICK_TO_REMOVE;
    public static String SHIFT_LEFT_CLICK_TO_REMOVE;

    public static String PREMIUM;

    public static String TITLE_COLOR;

    public static String PAGE;
    public static String NEXT_PAGE;
    public static String PREVIOUS_PAGE;

    public static String NEW;

    public static String EXIT;

    public static String BACK;

    public static String SAVE;

    public static String REMOVE;

    public static String RESET;

    public static String CREATION_ID;

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

    private final static boolean test = false;


    public GUI(String name, int size) {
        inv = Bukkit.createInventory(this, size, StringConverter.coloredString(name));
        this.size = size;
        for (int j = 0; j < size; j++) {
            createBackGroundItem(j);
        }
        if(WRITABLE_BOOK == null) init();
    }

    public GUI(Inventory inv) {
        this.inv = inv;
        if(WRITABLE_BOOK == null) init();
    }

    public static void init() {
        WRITABLE_BOOK = FixedMaterial.getMaterial(Arrays.asList("WRITABLE_BOOK", "BOOK_AND_QUILL"));
        CLOCK = FixedMaterial.getMaterial(Arrays.asList("CLOCK", "WATCH"));
        ENCHANTING_TABLE = FixedMaterial.getMaterial(Arrays.asList("ENCHANTING_TABLE", "ENCHANTMENT_TABLE"));
        HEAD = FixedMaterial.getMaterial(Arrays.asList("PLAYER_HEAD", "SKULL_ITEM"));
        COMPARATOR = FixedMaterial.getMaterial(Arrays.asList("COMPARATOR", "REDSTONE_COMPARATOR"));
        NEXT_PAGE_MAT = FixedMaterial.getMaterial(Arrays.asList("PURPLE_STAINED_GLASS_PANE", "ARROW"));
        PREVIOUS_PAGE_MAT = FixedMaterial.getMaterial(Arrays.asList("PURPLE_STAINED_GLASS_PANE", "ARROW"));
        RED = FixedMaterial.getMaterial(Arrays.asList("RED_STAINED_GLASS_PANE", "REDSTONE_BLOCK"));
        ORANGE = FixedMaterial.getMaterial(Arrays.asList("ORANGE_STAINED_GLASS_PANE", "BARRIER"));
        GREEN = FixedMaterial.getMaterial(Arrays.asList("LIME_STAINED_GLASS_PANE", "EMERALD"));
        YELLOW = FixedMaterial.getMaterial(Arrays.asList("YELLOW_STAINED_GLASS_PANE", "HOPPER"));
        PURPLE = FixedMaterial.getMaterial(Arrays.asList("MAGENTA_STAINED_GLASS_PANE", "HOPPER"));
        BLUE = FixedMaterial.getMaterial(Arrays.asList("BLUE_STAINED_GLASS_PANE", "ANVIL"));
        GRINDSTONE = FixedMaterial.getMaterial(Arrays.asList("GRINDSTONE", "ANVIL"));
        LIGHTNING_ROD = FixedMaterial.getMaterial(Arrays.asList("LIGHTNING_ROD", "TRIPWIRE_HOOK"));

        CREATION_ID = TM.g(Text.EDITOR_CREATION_ID_NAME);

        CLICK_HERE_TO_CHANGE = TM.g(Text.EDITOR_EDIT_DESCRIPTION);

        CLICK_TO_REMOVE = TM.g(Text.EDITOR_DELETE_NORMAL_DESCRIPTION);

        SHIFT_CLICK_TO_REMOVE = TM.g(Text.EDITOR_DELETE_SHIFT_DESCRIPTION);

        SHIFT_LEFT_CLICK_TO_REMOVE = TM.g(Text.EDITOR_DELETE_SHIFT_LEFT_DESCRIPTION);

        PREMIUM = TM.g(Text.EDITOR_PREMIUM_DESCRIPTION);

        TITLE_COLOR = TM.g(Text.EDITOR_TITLE_COLOR);

        PAGE = TM.g(Text.EDITOR_PAGE_NAME);

        NEXT_PAGE = TM.g(Text.EDITOR_PAGE_NEXT_NAME);

        PREVIOUS_PAGE = TM.g(Text.EDITOR_PAGE_PREVIOUS_NAME);

        NEW = TM.g(Text.EDITOR_NEW_NAME);

        EXIT = TM.g(Text.EDITOR_EXIT_NAME);

        BACK = TM.g(Text.EDITOR_BACK_NAME);

        SAVE = TM.g(Text.EDITOR_SAVE_NAME);

        REMOVE = TM.g(Text.EDITOR_DELETE_NAME);

        RESET = TM.g(Text.EDITOR_RESET_NAME);
    }

    public void load() {

    }

    public void createItem(Material material, int amount, int invSlot, String displayName, boolean glow, boolean haveEnchant, String... loreString) {

        if(test && size >= 54 & invSlot == 27) material = Material.DIAMOND_SWORD;

        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();


        if (glow || haveEnchant) {
            meta = MakeItemGlow.makeGlow(meta);
        }

        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemFlag additionnalFlag = SCore.is1v20v5Plus() ? ItemFlag.HIDE_ADDITIONAL_TOOLTIP : ItemFlag.valueOf("HIDE_POTION_EFFECTS");
        meta.addItemFlags(additionnalFlag);
        meta.setDisplayName(StringConverter.coloredString(displayName));

        for (String s : loreString) lore.add(StringConverter.coloredString(s));

        if(test && size >= 54 & invSlot == 27) meta.setCustomModelData(3);

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
            meta = MakeItemGlow.makeGlow(meta);
        }

        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemFlag additionnalFlag = SCore.is1v20v5Plus() ? ItemFlag.HIDE_ADDITIONAL_TOOLTIP : ItemFlag.valueOf("HIDE_POTION_EFFECTS");
        meta.addItemFlags(additionnalFlag);
        meta.setDisplayName(StringConverter.coloredString(displayName));

        for (String s : loreString) lore.add(StringConverter.coloredString(s));

        meta.setLore(lore);
        if (!SCore.is1v13Less()) meta.setCustomModelData(customTextureTag);
        item.setItemMeta(meta);
        inv.setItem(invSlot, item);

    }

    public void createItem(ItemStack itemS, int amount, int invSlot, String displayName, boolean glow, boolean haveEnchant, String... loreString) {

        ItemMeta meta = itemS.getItemMeta();
        List<String> lore = new ArrayList<>();

        if (glow || haveEnchant) {
            meta = MakeItemGlow.makeGlow(meta);
        }

        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemFlag additionnalFlag = SCore.is1v20v5Plus() ? ItemFlag.HIDE_ADDITIONAL_TOOLTIP : ItemFlag.valueOf("HIDE_POTION_EFFECTS");
        meta.addItemFlags(additionnalFlag);
        meta.setDisplayName(StringConverter.coloredString(displayName));
        //SsomarDev.testMsg("meta>>>>>>>>>: "+meta.getDisplayName(), true);

        for (String s : loreString) lore.add(StringConverter.coloredString(s));

        meta.setLore(lore);
        itemS.setItemMeta(meta);
        //SsomarDev.testMsg("meta>>>>>>>>>: "+itemS.getItemMeta().getDisplayName(), true);
        inv.setItem(invSlot, itemS);

    }

    public void createBackGroundItem(int slot) {
        if(test && (size < 54 || slot != 27)) return;
        if (!SCore.is1v13Less()){
            ItemStack item = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
            if(SCore.is1v20v5Plus()){
                ItemMeta meta = item.getItemMeta();
                meta.setHideTooltip(true);
                item.setItemMeta(meta);
            }
            createItem(item, 1, slot, "&7", true, false);
        }
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

    public int getSlotByName(String name) {
        int i = -1;
        for (ItemStack item : inv.getContents()) {
            i++;
            if (item != null && item.hasItemMeta() && StringConverter.decoloredString(item.getItemMeta().getDisplayName()).equals(StringConverter.decoloredString(name))) {
                return i;
            }
        }
        return i;
    }

    public void openGUISync(Player player) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                player.openInventory(inv);
            }
        };
        Entity entity = (Entity) player;
        SCore.schedulerHook.runEntityTask(runnable, null, entity, 0);
    }

    public String getCurrently(ItemStack item) {
        List<String> lore = item.getItemMeta().getLore();
        for (String s : lore) {
            if (StringConverter.decoloredString(s).contains("Currently: ")) {
                try {
                    return StringConverter.decoloredString(s).split("Currently: ")[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }
        }
        return null;
    }

    public String getCurrentlyWithColor(ItemStack item) {
        List<String> lore = item.getItemMeta().getLore();
        for (String s : lore) {
            if (StringConverter.decoloredString(s).contains("Currently: ")) {
                try {
                    return StringConverter.deconvertColor(s).split("Currently: ")[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }
        }
        return null;
    }

    public void updateCurrently(ItemStack item, String update, boolean withColor, String editorName) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        int cpt = 0;
        for (String s : lore) {
            if (StringConverter.decoloredString(s).contains("Currently:")) break;
            cpt++;
        }
        if (withColor) lore.set(cpt, StringConverter.coloredString("&7Currently: " + update));
        else lore.set(cpt, StringConverter.coloredString("&7Currently: &e" + update));
        meta.setLore(lore);
        item.setItemMeta(meta);

        //REQUIRED DUE TO A SPIGOT ISSUE THAT HAS BEEN FIXED UpdateItemInGUI.updateItemInGUI(this, editorName, meta.getDisplayName(), lore, item.getType());
    }

    public void updateCurrently(ItemStack item, String update, String editorName) {
        updateCurrently(item, update, false, editorName);
    }

    public void updateCurrently(ItemStack item, String update) {
        updateCurrently(item, update, false, "");
    }

    public String getCurrently(String itemName) {
        return this.getCurrently(this.getByName(itemName));
    }

    public String getCurrentlyWithColor(String itemName) {
        return this.getCurrentlyWithColor(this.getByName(itemName));
    }

    public void updateCurrently(String itemName, String update) {
        this.updateCurrently(this.getByName(itemName), update, itemName);
    }

    public void updateCurrently(String itemName, String update, Boolean withColor) {
        this.updateCurrently(this.getByName(itemName), update, withColor, itemName);
    }

    public void updateCondition(String name, String condition) {
        ItemStack item = this.getByName(name);
        if (condition.equals("")) this.updateCurrently(item, "&cNO CONDITION", name);
        else this.updateCurrently(item, condition, name);
    }

    public String getCondition(String name) {
        if (this.getCurrently(this.getByName(name)).contains("NO CONDITION")) return "";
        else return this.getCurrently(this.getByName(name));
    }

    public void updateConditionList(String name, List<String> list, String emptyStr) {
        ItemStack item = this.getByName(name);
        ItemMeta toChange = item.getItemMeta();

        List<String> loreUpdate = new ArrayList<>();
        for (String s : toChange.getLore()) {
            if (StringConverter.deconvertColor(s).contains(emptyStr) || StringConverter.deconvertColor(s).startsWith("&6➤ &e")) {
                break;
            }
            loreUpdate.add(s);
        }

        if (list.isEmpty()) loreUpdate.add(StringConverter.coloredString(emptyStr));
        else {
            for (String str : list) {
                loreUpdate.add(StringConverter.coloredString("&6➤ &e" + str));
            }
        }
        toChange.setLore(loreUpdate);
        item.setItemMeta(toChange);

        UpdateItemInGUI.updateItemInGUI(this, name, item.getItemMeta().getDisplayName(), loreUpdate, item.getType());
    }

    public List<String> getConditionList(String name, String emptyStr) {
        ItemStack item = this.getByName(name);
        ItemMeta iM = item.getItemMeta();
        List<String> loreUpdate = new ArrayList<>();
        for (String s : iM.getLore()) {
            if (s.contains(emptyStr) || StringConverter.deconvertColor(s).startsWith("&6➤ &e")) {
                loreUpdate.add(s);
            }
        }
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
        List<String> loreUpdate = new ArrayList<>();
        for (String s : iM.getLore()) {
            if (s.contains(emptyStr) || StringConverter.deconvertColor(s).startsWith("&6➤ &e")) {
                loreUpdate.add(s);
            }
        }
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
        if (value) {
            MakeItemGlow.makeGlow(item);
            updateCurrently(item, "&aTrue", itemName);
        } else {
            MakeItemGlow.makeUnGlow(item);
            updateCurrently(item, "&cFalse", itemName);
        }
    }

    public void changeBoolean(String itemName) {
        ItemStack item = this.getByName(itemName);
        updateBoolean(itemName, !getCurrently(item).contains("True"));
    }

    public boolean getBoolean(String itemName) {
        ItemStack item = this.getByName(itemName);
        return getCurrently(item).contains("True");
    }

    public void updateInt(String itemName, int value) {
        ItemStack item = this.getByName(itemName);
        updateCurrently(item, value + "", itemName);
    }

    public int getInt(String itemName) {
        ItemStack item = this.getByName(itemName);
        if (item == null) throw new NullPointerException("Item with the name: " + itemName + " is null");
        return Integer.parseInt(getCurrently(item));
    }

    public void updateDouble(String itemName, double value) {
        ItemStack item = this.getByName(itemName);
        updateCurrently(item, value + "", itemName);
    }

    public double getDouble(String itemName) {
        ItemStack item = this.getByName(itemName);
        return Double.parseDouble(getCurrently(item));
    }

}
