package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.VariableUpdateType;
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
public class VariableUpdateTypeFeature extends FeatureAbstract<Optional<VariableUpdateType>, VariableUpdateTypeFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<VariableUpdateType> value;
    private Optional<VariableUpdateType> defaultValue;

    public VariableUpdateTypeFeature(FeatureParentInterface parent, Optional<VariableUpdateType> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        if (colorStr.equals("NULL")) {
            value = defaultValue;
        } else {
            try {
                VariableUpdateType material = VariableUpdateType.valueOf(colorStr);
                value = Optional.ofNullable(material);
                FeatureReturnCheckPremium<VariableUpdateType> checkPremium = checkPremium("VariableUpdate Type", material, defaultValue, isPremiumLoading);
                if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load theVariableUpdateType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> VariableUpdateType available: SET, MODIFICATION");
                value = Optional.empty();
            }
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<VariableUpdateType> value = getValue();
        value.ifPresent(variableUpdateType -> config.set(this.getName(), variableUpdateType.name()));
    }

    @Override
    public Optional<VariableUpdateType> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public VariableUpdateTypeFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<VariableUpdateType> value = getValue();
        VariableUpdateType finalValue = value.orElse(VariableUpdateType.MODIFICATION);
        updateVariableUpdateType(finalValue, gui);
    }

    @Override
    public VariableUpdateTypeFeature clone(FeatureParentInterface newParent) {
        VariableUpdateTypeFeature clone = new VariableUpdateTypeFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        updateVariableUpdateType(nextVariableUpdateType(getVariableUpdateType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateVariableUpdateType(prevVariableUpdateType(getVariableUpdateType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public VariableUpdateType nextVariableUpdateType(VariableUpdateType material) {
        boolean next = false;
        for (VariableUpdateType check : getSortVariableUpdateTypes()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortVariableUpdateTypes().get(0);
    }

    public VariableUpdateType prevVariableUpdateType(VariableUpdateType material) {
        int i = -1;
        int cpt = 0;
        for (VariableUpdateType check : getSortVariableUpdateTypes()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortVariableUpdateTypes().get(getSortVariableUpdateTypes().size() - 1);
        else return getSortVariableUpdateTypes().get(cpt - 1);
    }

    public void updateVariableUpdateType(VariableUpdateType typeTarget, GUI gui) {
        value = Optional.of(typeTarget);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortVariableUpdateTypes().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (VariableUpdateType check : getSortVariableUpdateTypes()) {
            if (typeTarget.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + typeTarget.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (VariableUpdateType check : getSortVariableUpdateTypes()) {
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

    public VariableUpdateType getVariableUpdateType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return VariableUpdateType.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<VariableUpdateType> getSortVariableUpdateTypes() {
        SortedMap<String, VariableUpdateType> map = new TreeMap<String, VariableUpdateType>();
        for (VariableUpdateType l : VariableUpdateType.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
