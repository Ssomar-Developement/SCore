package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import com.ssomar.score.variables.VariableForEnum;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class VariableForFeature extends FeatureAbstract<Optional<VariableForEnum>, VariableForFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<VariableForEnum> value;
    private Optional<VariableForEnum> defaultValue;

    public VariableForFeature(FeatureParentInterface parent, Optional<VariableForEnum> defaultValue, FeatureSettingsInterface featureSettings) {
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
                VariableForEnum material = VariableForEnum.valueOf(colorStr);
                value = Optional.ofNullable(material);
                FeatureReturnCheckPremium<VariableForEnum> checkPremium = checkPremium("Variable For", material, defaultValue, isPremiumLoading);
                if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
            } catch (Exception e) {
                errors.add("&cERROR, Couldn't load the VariableFor value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> VariableType available: STRING, NUMBER");
                value = Optional.empty();
            }
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<VariableForEnum> value = getValue();
        value.ifPresent(variableType -> config.set(this.getName(), variableType.name()));
    }

    @Override
    public Optional<VariableForEnum> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public VariableForFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<VariableForEnum> value = getValue();
        VariableForEnum finalValue = value.orElse(VariableForEnum.GLOBAL);
        updateVariableFor(finalValue, gui);
    }

    @Override
    public VariableForFeature clone(FeatureParentInterface newParent) {
        VariableForFeature clone = new VariableForFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        updateVariableFor(nextVariableType(getVariableFor((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateVariableFor(prevVariableType(getVariableFor((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public VariableForEnum nextVariableType(VariableForEnum material) {
        boolean next = false;
        for (VariableForEnum check : getSortVariableFor()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortVariableFor().get(0);
    }

    public VariableForEnum prevVariableType(VariableForEnum material) {
        int i = -1;
        int cpt = 0;
        for (VariableForEnum check : getSortVariableFor()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortVariableFor().get(getSortVariableFor().size() - 1);
        else return getSortVariableFor().get(cpt - 1);
    }

    public void updateVariableFor(VariableForEnum typeTarget, GUI gui) {
        value = Optional.of(typeTarget);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortVariableFor().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (VariableForEnum check : getSortVariableFor()) {
            if (typeTarget.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + typeTarget.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (VariableForEnum check : getSortVariableFor()) {
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

    public VariableForEnum getVariableFor(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return VariableForEnum.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<VariableForEnum> getSortVariableFor() {
        SortedMap<String, VariableForEnum> map = new TreeMap<String, VariableForEnum>();
        for (VariableForEnum l : VariableForEnum.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
