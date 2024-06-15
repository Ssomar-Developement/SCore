package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.Comparator;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class ComparatorFeature extends FeatureAbstract<Optional<Comparator>, ComparatorFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<Comparator> value;
    private Optional<Comparator> defaultValue;

    public ComparatorFeature(FeatureParentInterface parent, Optional<Comparator> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    public static ComparatorFeature buildNull(Comparator comparator){
        ComparatorFeature c = new ComparatorFeature(null,  Optional.empty(), null);
        c.setValue(Optional.of(comparator));
        return c;
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            Comparator material = Comparator.valueOf(colorStr);
            value = Optional.ofNullable(material);
            FeatureReturnCheckPremium<Comparator> checkPremium = checkPremium("Comparator", material, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the Comparator value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> Type target available: EQUALS, DIFFERENT, INFERIOR, SUPERIOR, INFERIOR_OR_EQUALS, SUPERIOR_OR_EQUALS, IS_CONTAINED_IN, IS_NOT_CONTAINED_IN");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<Comparator> value = getValue();
        value.ifPresent(comparator -> config.set(this.getName(), comparator.name()));
    }

    @Override
    public Optional<Comparator> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public ComparatorFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<Comparator> value = getValue();
        Comparator finalValue = value.orElse(Comparator.EQUALS);
        updateComparator(finalValue, gui);
    }

    @Override
    public ComparatorFeature clone(FeatureParentInterface newParent) {
        ComparatorFeature clone = new ComparatorFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        return false;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateComparator(nextComparator(getComparator((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateComparator(prevComparator(getComparator((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public Comparator nextComparator(Comparator material) {
        boolean next = false;
        for (Comparator check : getSortComparators()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortComparators().get(0);
    }

    public Comparator prevComparator(Comparator material) {
        int i = -1;
        int cpt = 0;
        for (Comparator check : getSortComparators()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortComparators().get(getSortComparators().size() - 1);
        else return getSortComparators().get(cpt - 1);
    }

    public void updateComparator(Comparator typeTarget, GUI gui) {
        value = Optional.of(typeTarget);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortComparators().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (Comparator check : getSortComparators()) {
            if (typeTarget.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + typeTarget.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (Comparator check : getSortComparators()) {
            if (lore.size() == maxSize) break;
            else {
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        /* Bug item no update idk why */
        UpdateItemInGUI.updateItemInGUI(gui, getEditorName(), meta.getDisplayName(), lore, item.getType());
    }

    public Comparator getComparator(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return Comparator.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<Comparator> getSortComparators() {
        SortedMap<String, Comparator> map = new TreeMap<String, Comparator>();
        for (Comparator l : Comparator.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
