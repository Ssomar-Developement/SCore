package com.ssomar.score.features.types;


import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.DetailedClick;
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
public class DetailedClickFeature extends FeatureAbstract<Optional<DetailedClick>, DetailedClickFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<DetailedClick> value;
    private Optional<DetailedClick> defaultValue;

    public DetailedClickFeature(FeatureParentInterface parent, Optional<DetailedClick> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            DetailedClick material = DetailedClick.valueOf(colorStr);
            value = Optional.ofNullable(material);
            FeatureReturnCheckPremium<DetailedClick> checkPremium = checkPremium("DetailedClick", material, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the DetailedClick value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> DetailedClick available: RIGHT, LEFT, RIGHT_OR_LEFT");
            value = Optional.empty();
        }
        return errors;
    }

    public boolean verifClick(DetailedClick dC) {
        if (dC != null && value.isPresent()) {
            switch (value.get()) {
                case RIGHT_OR_LEFT:
                    return true;
                case LEFT:
                    return dC.equals(DetailedClick.LEFT);
                case RIGHT:
                    return dC.equals(DetailedClick.RIGHT);
                default:
                    return false;
            }
        }
        return false;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<DetailedClick> value = getValue();
        value.ifPresent(detailedClick -> config.set(this.getName(), detailedClick.name()));
    }

    @Override
    public Optional<DetailedClick> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public DetailedClickFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<DetailedClick> value = getValue();
        DetailedClick finalValue = value.orElse(DetailedClick.RIGHT);
        updateDetailedClick(finalValue, gui);
    }

    @Override
    public DetailedClickFeature clone(FeatureParentInterface newParent) {
        DetailedClickFeature clone = new DetailedClickFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        updateDetailedClick(nextDetailedClick(getDetailedClick((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateDetailedClick(prevDetailedClick(getDetailedClick((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public DetailedClick nextDetailedClick(DetailedClick material) {
        boolean next = false;
        for (DetailedClick check : getSortDetailedClicks()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortDetailedClicks().get(0);
    }

    public DetailedClick prevDetailedClick(DetailedClick material) {
        int i = -1;
        int cpt = 0;
        for (DetailedClick check : getSortDetailedClicks()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortDetailedClicks().get(getSortDetailedClicks().size() - 1);
        else return getSortDetailedClicks().get(cpt - 1);
    }

    public void updateDetailedClick(DetailedClick typeTarget, GUI gui) {
        value = Optional.of(typeTarget);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortDetailedClicks().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (DetailedClick check : getSortDetailedClicks()) {
            if (typeTarget.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + typeTarget.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (DetailedClick check : getSortDetailedClicks()) {
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

    public DetailedClick getDetailedClick(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return DetailedClick.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<DetailedClick> getSortDetailedClicks() {
        SortedMap<String, DetailedClick> map = new TreeMap<String, DetailedClick>();
        for (DetailedClick l : DetailedClick.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
