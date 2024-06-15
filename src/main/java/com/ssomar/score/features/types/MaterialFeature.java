package com.ssomar.score.features.types;

import com.ssomar.score.SCore;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
@Setter
public class MaterialFeature extends FeatureAbstract<Optional<Material>, MaterialFeature> implements FeatureRequireClicksOrOneMessageInEditor {

    private Optional<Material> value;
    private Optional<Material> defaultValue;
    private boolean onlyItemMaterial;

    public MaterialFeature(FeatureParentInterface parent, Optional<Material> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
        this.onlyItemMaterial = false;
    }

    public MaterialFeature(FeatureParentInterface parent, Optional<Material> defaultValue, FeatureSettingsInterface featureSettings, boolean onlyItemMaterial) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
        this.onlyItemMaterial = onlyItemMaterial;
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            Material material = Material.valueOf(colorStr);
            // isItem is only available in 1.12+
            if(!SCore.is1v11Less() && onlyItemMaterial && !material.isItem()) material = Material.STONE;
            value = Optional.ofNullable(material);
            FeatureReturnCheckPremium<Material> checkPremium = checkPremium("Material", material, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            if(!colorStr.equals("NULL")) errors.add("&cERROR, Couldn't load the Material value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> Materials available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html");
            value = Optional.empty();
        }
        return errors;
    }


    @Override
    public void save(ConfigurationSection config) {
        Optional<Material> value = getValue();
        value.ifPresent(material -> config.set(this.getName(), material.name()));
    }

    @Override
    public Optional<Material> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public MaterialFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 4];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 4] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 3] = "&8>> &6SHIFT : &eBOOST SCROLL";
        finalDescription[finalDescription.length - 2] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";
        finalDescription[finalDescription.length - 1] = "&8>> &6Type manually: &eMIDDLE &a(Creative only)";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<Material> value = getValue();
        Material finalValue = value.orElse(Material.STONE);
        updateMaterial(finalValue, gui);
    }

    @Override
    public MaterialFeature clone(FeatureParentInterface newParent) {
        MaterialFeature clone = new MaterialFeature(newParent, getDefaultValue(), getFeatureSettings(), isOnlyItemMaterial());
        clone.value = value;
        return clone;
    }

    @Override
    public void reset() {
        this.value = defaultValue;
    }

    @Override
    public void clickParentEditor(Player editor, NewGUIManager manager) {
        return;
    }

    @Override
    public boolean noShiftclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean noShiftLeftclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean noShiftRightclicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean shiftLeftClicked(Player editor, NewGUIManager manager) {
        Material material = getMaterial((GUI) manager.getCache().get(editor));
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        material = nextMaterial(material);
        updateMaterial(material, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        Material material = getMaterial((GUI) manager.getCache().get(editor));
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        material = prevMaterial(material);
        updateMaterial(material, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateMaterial(nextMaterial(getMaterial((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateMaterial(prevMaterial(getMaterial((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public Material nextMaterial(Material material) {
        boolean next = false;
        for (Material check : getSortMaterials()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next){
                if(!SCore.is1v11Less() && onlyItemMaterial && !check.isItem()) continue;
                return check;
            }
        }
        return getSortMaterials().get(0);
    }

    public Material prevMaterial(Material material) {
        int i = -1;
        int cpt = 0;
        for (Material check : getSortMaterials()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        Material finalMaterial;
        if (i == 0) finalMaterial = getSortMaterials().get(getSortMaterials().size() - 1);
        else finalMaterial = getSortMaterials().get(cpt - 1);

        if(!SCore.is1v11Less() && onlyItemMaterial && !finalMaterial.isItem()) return prevMaterial(finalMaterial);
        return finalMaterial;
    }

    public void updateMaterial(Material material, GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        value = Optional.of(material);
        item.setType(material);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null){
            lore = new ArrayList<>();
            for (String str : getEditorDescription()) lore.add(StringConverter.coloredString(str));
        }
        else lore = lore.subList(0, getEditorDescription().length + 4);
        boolean find = false;
        for (Material check : getSortMaterials()) {
            if (material.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + material.name()));
                find = true;
            } else if (find) {
                if (lore.size() == 17) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (Material check : getSortMaterials()) {
            if (lore.size() == 17) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public Material getMaterial(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return Material.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<Material> getSortMaterials() {
        SortedMap<String, Material> map = new TreeMap<String, Material>();
        for (Material l : Material.values()) {
            if (SCore.is1v12Less()) {
                /* To check if the item is an item. */
                try {
                    ItemStack item = new ItemStack(l);
                    if (item == null || item.getType() == Material.AIR) continue;
                    Inventory inv = Bukkit.createInventory(null, 9);
                    inv.setItem(0, new ItemStack(l));
                    if (inv.getItem(0) == null) continue;
                    map.put(l.name(), l);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            } else if (l.isItem() && !l.isAir()) map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public void askInEditor(Player editor, NewGUIManager manager) {
        manager.requestWriting.put(editor, getEditorName());
        /* Close inventory sync */
        BukkitRunnable runnable = new BukkitRunnable() {
            public void run() {
                editor.closeInventory();
            }
        };
        SCore.schedulerHook.runTask(runnable, 0);
        space(editor);

        TextComponent message = new TextComponent(StringConverter.coloredString("&a&l[Editor] &aEnter the material or &aedit &athe &aactual: "));

        TextComponent edit = new TextComponent(StringConverter.coloredString("&e&l[EDIT]"));
        edit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, StringConverter.deconvertColor(((GUI) manager.getCache().get(editor)).getCurrently(getEditorName()))));
        edit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&eClick here to edit the current material")).create()));

        TextComponent newName = new TextComponent(StringConverter.coloredString("&a&l[NEW]"));
        newName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "Type the new string here.."));
        newName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&aClick here to set new material")).create()));

        TextComponent noValue = new TextComponent(StringConverter.coloredString("&c&l[NO VALUE / EXIT]"));
        noValue.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/score interact NO VALUE / EXIT"));
        noValue.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(StringConverter.coloredString("&cClick here to exit or don't set a value")).create()));


        message.addExtra(new TextComponent(" "));
        message.addExtra(edit);
        message.addExtra(new TextComponent(" "));
        message.addExtra(newName);
        message.addExtra(new TextComponent(" "));
        message.addExtra(noValue);

        editor.spigot().sendMessage(message);
        space(editor);
    }

    @Override
    public Optional<String> verifyMessageReceived(String message) {
        try {
            Material.valueOf(StringConverter.decoloredString(message).trim().toUpperCase());
        } catch (Exception e) {
            return Optional.of(StringConverter.coloredString("&4&l[ERROR] &cThe message you entered is not a material"));
        }
        return Optional.empty();
    }

    @Override
    public void finishEditInEditor(Player editor, NewGUIManager manager, String message) {
        this.value = Optional.of(Material.valueOf(StringConverter.decoloredString(message).trim().toUpperCase()));
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }

    @Override
    public void finishEditInEditorNoValue(Player editor, NewGUIManager manager) {
        this.value = Optional.empty();
        manager.requestWriting.remove(editor);
        updateItemParentEditor((GUI) manager.getCache().get(editor));
    }
}
