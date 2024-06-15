package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.VariableType;
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
public class VariableTypeFeature extends FeatureAbstract<Optional<VariableType>, VariableTypeFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<VariableType> value;
    private Optional<VariableType> defaultValue;
    private boolean noList;

    public VariableTypeFeature(FeatureParentInterface parent, Optional<VariableType> defaultValue, FeatureSettingsInterface featureSettings, boolean noList) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
        this.noList = noList;
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        if (colorStr.equals("NULL")) {
            value = defaultValue;
        } else {
            try {
                VariableType type = VariableType.valueOf(colorStr);
                value = Optional.ofNullable(type);
                FeatureReturnCheckPremium<VariableType> checkPremium = checkPremium("Variable Type", type, defaultValue, isPremiumLoading);
                if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the VariableType value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> VariableType available: STRING, NUMBER");
                value = Optional.empty();
            }
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<VariableType> value = getValue();
        value.ifPresent(variableType -> config.set(this.getName(), variableType.name()));
    }

    @Override
    public Optional<VariableType> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public VariableTypeFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<VariableType> value = getValue();
        VariableType finalValue = value.orElse(VariableType.STRING);
        updateVariableType(finalValue, gui);
    }

    @Override
    public VariableTypeFeature clone(FeatureParentInterface newParent) {
        VariableTypeFeature clone = new VariableTypeFeature(newParent, getDefaultValue(), getFeatureSettings(), isNoList());
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
        updateVariableType(nextVariableType(getVariableType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateVariableType(prevVariableType(getVariableType((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public VariableType nextVariableType(VariableType material) {
        boolean next = false;
        for (VariableType check : getSortVariableTypes()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortVariableTypes().get(0);
    }

    public VariableType prevVariableType(VariableType material) {
        int i = -1;
        int cpt = 0;
        for (VariableType check : getSortVariableTypes()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortVariableTypes().get(getSortVariableTypes().size() - 1);
        else return getSortVariableTypes().get(cpt - 1);
    }

    public void updateVariableType(VariableType typeTarget, GUI gui) {
        value = Optional.of(typeTarget);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortVariableTypes().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (VariableType check : getSortVariableTypes()) {
            if (typeTarget.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + typeTarget.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (VariableType check : getSortVariableTypes()) {
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

    public VariableType getVariableType(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return VariableType.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<VariableType> getSortVariableTypes() {
        SortedMap<String, VariableType> map = new TreeMap<String, VariableType>();
        for (VariableType l : VariableType.values()) {
            if(noList && l == VariableType.LIST) continue;
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
