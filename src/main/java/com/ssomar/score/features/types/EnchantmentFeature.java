package com.ssomar.score.features.types;

import com.ssomar.score.SCore;
import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.features.custom.enchantments.enchantment.EnchantmentWithLevelFeature;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class EnchantmentFeature extends FeatureAbstract<Optional<Enchantment>, EnchantmentFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<Enchantment> value;
    private Optional<Enchantment> defaultValue;

    public EnchantmentFeature(FeatureParentInterface parent, Optional<Enchantment> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String enchantStr = config.getString(this.getName(), "NULL");
        enchantStr = transformOldFormat(enchantStr);
        if(!enchantStr.contains(">>")) enchantStr = enchantStr.toUpperCase();
        Optional<Enchantment> optional = getEnchantment(enchantStr);
        if (!optional.isPresent()) {
            errors.add("&cERROR, Couldn't load the Enchantment value of " + this.getName() + " from config, value: " + enchantStr + " &7&o" + getParent().getParentInfo() + " &6>> Enchantments available: Look in-game, it's the same name");
            value = Optional.empty();
        } else {
            value = optional;
            FeatureReturnCheckPremium<Enchantment> checkPremium = checkPremium("Enchantment", optional.get(), defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        }
        return errors;
    }

    public String transformOldFormat(String str){
        if(str.contains("SPACE_")){
            str = str.replace("SPACE_", "SPACE>>");
        }
        else if(str.contains("TOKEN-ENCHANT_")){
            str = str.replace("TOKEN-ENCHANT_", "TOKENENCHANT>>");
        }
        else if(str.contains("ENCHANTS-SQUARED_")){
           str = str.replace("ENCHANTS-SQUARED_", "ENCHANTSSQUARED>>");
        }
        else if(str.contains("BETTER-ENCHANTMENTS_")){
            str = str.replace("BETTER-ENCHANTMENTS_", "BETTERENCHANTMENTS>>");
        }
        else if(str.contains("FILTERED-HOPPERS_")){
            str = str.replace("FILTERED-HOPPERS_", "FILTEREDHOPPERS>>");
        }
        return str;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<Enchantment> optional = getValue();
        if (optional.isPresent()) {
            String enchantName = getEnchantmentName(optional.get());
            config.set(this.getName(), enchantName);
        } else config.set(this.getName(), null);
    }

    @Override
    public Optional<Enchantment> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public EnchantmentFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<Enchantment> optional = getValue();
        if (optional.isPresent()) updateEnchantment(optional.get(), gui);
        else updateEnchantment(EnchantmentWithLevelFeature.getDefaultEnchantment(), gui);
    }

    @Override
    public EnchantmentFeature clone(FeatureParentInterface newParent) {
        EnchantmentFeature clone = new EnchantmentFeature(newParent,defaultValue, getFeatureSettings());
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
        Enchantment enchantment = getEnchantment((GUI) manager.getCache().get(editor));
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        enchantment = nextEnchantment(enchantment);
        updateEnchantment(enchantment, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        Enchantment enchantment = getEnchantment((GUI) manager.getCache().get(editor));
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        enchantment = prevEnchantment(enchantment);
        updateEnchantment(enchantment, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateEnchantment(nextEnchantment(getEnchantment((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateEnchantment(prevEnchantment(getEnchantment((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public Enchantment nextEnchantment(Enchantment enchantment) {
        boolean next = false;
        for (Enchantment check : getSortEnchantments()) {
            if (check.equals(enchantment)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortEnchantments().get(0);
    }

    public Enchantment prevEnchantment(Enchantment enchantment) {
        int i = -1;
        int cpt = 0;
        for (Enchantment check : getSortEnchantments()) {
            if (check.equals(enchantment)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortEnchantments().get(getSortEnchantments().size() - 1);
        else return getSortEnchantments().get(cpt - 1);
    }

    public void updateEnchantment(Enchantment enchantment, GUI gui) {
        this.value = Optional.of(enchantment);
        ItemStack item = gui.getByName(getEditorName());
        /*
         * Design set Type ENCHANTED_BOOK AND ADD THE GOOD ENCHANTMENT
         *
         * */
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        boolean find = false;
        for (Enchantment check : getSortEnchantments()) {
            if (enchantment.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + getEnchantmentName(enchantment)));
                find = true;
            } else if (find) {
                if (lore.size() == 17) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + getEnchantmentName(check)));
            }
        }
        for (Enchantment check : getSortEnchantments()) {
            if (lore.size() == 17) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + getEnchantmentName(check)));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public Enchantment getEnchantment(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return getEnchantment(str.split("➤ ")[1]).get();
            }
        }
        return null;
    }

    public List<Enchantment> getSortEnchantments() {
        SortedMap<String, Enchantment> map = new TreeMap<String, Enchantment>();
        for (Enchantment l : Enchantment.values()) {
            map.put(getEnchantmentName(l), l);
        }
        return new ArrayList<>(map.values());
    }

    public String getEnchantmentName(Enchantment enchantment) {
        String name = "";
        if (!SCore.is1v12Less()) {
            name = enchantment.getKey().toString();
            //SsomarDev.testMsg("Enchantment name : " + name, true);
            if (name.contains("minecraft:")) {
                name = name.split("minecraft:")[1];
            }
            else{
                name = name.split(":")[0].toUpperCase()+">>"+enchantment.getName();
            }
        } else {
            name = enchantment.getName();
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public Optional<Enchantment> getEnchantment(String enchantmentName) {
        Enchantment enchantment = null;
        //SsomarDev.testMsg("Enchantment name2 : " + enchantmentName, true);
        try {
            if(enchantmentName.contains(">>")){
                enchantmentName = enchantmentName.split(">>")[1];
                enchantment = Enchantment.getByName(enchantmentName);
            }
            else if (!SCore.is1v12Less()) {
                enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantmentName.toLowerCase()));
            } else {
                enchantment = Enchantment.getByName(enchantmentName);
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.ofNullable(enchantment);

    }


}
