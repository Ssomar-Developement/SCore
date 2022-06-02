package com.ssomar.testRecode.features.types;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.testRecode.editor.NewGUIManager;
import com.ssomar.testRecode.features.FeatureAbstract;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.FeatureRequireOnlyClicksInEditor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
public class EnchantmentFeature extends FeatureAbstract<Optional<Enchantment>, EnchantmentFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<Enchantment> value;
    private Optional<Enchantment> defaultValue;

    public EnchantmentFeature(FeatureParentInterface parent, String name, Optional<Enchantment> defaultValue, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
        this.defaultValue = defaultValue;
        reset();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String enchantStr = config.getString(this.getName());
        Optional<Enchantment> optional = getEnchantment(enchantStr);
        if(!optional.isPresent()){
            errors.add("&cERROR, Couldn't load the Enchantment value of " + this.getName() + " from config, value: " + enchantStr + " &7&o" + getParent().getParentInfo() + " &6>> Enchantments available: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/enchantments/Enchantment.html");
            value = Optional.empty();
        }
        else value = optional;
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<Enchantment> optional = getValue();
        if (optional.isPresent()){
            String enchantName = getEnchantmentName(optional.get());
            config.set(this.getName(), enchantName);
        }
        else config.set(this.getName(), null);
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
        finalDescription[finalDescription.length - 2] = gui.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7actually: ";

        gui.createItem(getEditorMaterial(), 1, slot, gui.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<Enchantment> optional = getValue();
        if (optional.isPresent()) updateEnchantment(optional.get(), gui);
        else updateEnchantment(Enchantment.DURABILITY, gui);
    }

    @Override
    public void extractInfoFromParentEditor(NewGUIManager manager, Player player) {
        value =  Optional.of(getEnchantment((GUI) manager.getCache().get(player)));
    }

    @Override
    public EnchantmentFeature clone() {
        EnchantmentFeature clone = new EnchantmentFeature(getParent(), this.getName(), defaultValue, getEditorName(), getEditorDescription(), getEditorMaterial(), isRequirePremium());
        clone.value = value;
        return clone;
    }

    @Override
    public void reset() {
        if (defaultValue.isPresent()) this.value = Optional.of(defaultValue.get());
        else this.value = Optional.empty();
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
        Enchantment enchantment = getEnchantment( (GUI) manager.getCache().get(editor));
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
        Enchantment enchantment = getEnchantment( (GUI) manager.getCache().get(editor));
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
        updateEnchantment(nextEnchantment(getEnchantment( (GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateEnchantment(prevEnchantment(getEnchantment( (GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
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
        if (i == 0) return getSortEnchantments().get(getSortEnchantments().size()- 1);
        else return getSortEnchantments().get(cpt - 1);
    }

    public void updateEnchantment(Enchantment enchantment, GUI gui) {
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
                lore.add(StringConverter.coloredString("&2➤ &a" +getEnchantmentName(enchantment)));
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
        /* Update the gui only for the right click , for the left it updated automaticaly idk why */
        for (HumanEntity e : gui.getInv().getViewers()) {
            if (e instanceof Player) {
                Player p = (Player) e;
                p.updateInventory();
            }
        }
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
        if (!SCore.is1v12Less()) {
           return enchantment.getKey().toString().split("minecraft:")[1];
        } else {
           return enchantment.getName();
        }
    }

    public Optional<Enchantment> getEnchantment(String enchantmentName) {
        Enchantment enchantment = null;
        try {
            if (!SCore.is1v12Less()) {
                enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantmentName.toLowerCase()));
            } else {
                enchantment = Enchantment.getByName(enchantmentName);
            }
        }
        catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(enchantment);

    }


}
