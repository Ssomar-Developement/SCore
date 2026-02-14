package com.ssomar.score.features.types;


import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.emums.DetailedInput;
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
public class DetailedInputFeature extends FeatureAbstract<Optional<DetailedInput>, DetailedInputFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<DetailedInput> value;
    private Optional<DetailedInput> defaultValue;

    public DetailedInputFeature(FeatureParentInterface parent, Optional<DetailedInput> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            DetailedInput material = DetailedInput.valueOf(colorStr);
            value = Optional.ofNullable(material);
            FeatureReturnCheckPremium<DetailedInput> checkPremium = checkPremium("DetailedInput", material, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the DetailedInput value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> DetailedInput available: LEFT_PRESS, RIGHT_PRESS, FORWARD_PRESS, BACKWARD_PRESS, JUMP_PRESS, SNEAK_PRESS, SPRINT_PRESS, LEFT_RELEASE, RIGHT_RELEASE, FORWARD_RELEASE, BACKWARD_RELEASE, JUMP_RELEASE, SNEAK_RELEASE, SPRINT_RELEASE, LEFT_PRESS_OR_RELEASE, RIGHT_PRESS_OR_RELEASE, FORWARD_PRESS_OR_RELEASE, BACKWARD_PRESS_OR_RELEASE, JUMP_PRESS_OR_RELEASE, SNEAK_PRESS_OR_RELEASE, SPRINT_PRESS_OR_RELEASE");
            value = Optional.empty();
        }
        return errors;
    }

    public boolean verifInput(DetailedInput dC) {
        if (dC != null && value.isPresent()) {
            DetailedInput configured = value.get();
            // Check if the configured value matches the input (handles grouped options like LEFT_PRESS_OR_RELEASE)
            return configured.matches(dC) || dC.equals(configured);
        }
        return false;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<DetailedInput> value = getValue();
        value.ifPresent(detailedInput -> config.set(this.getName(), detailedInput.name()));
    }

    @Override
    public Optional<DetailedInput> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public DetailedInputFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&8>> &6UP: &eRIGHT | &6DOWN: &eLEFT";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<DetailedInput> value = getValue();
        DetailedInput finalValue = value.orElse(DetailedInput.FORWARD_PRESS);
        updateDetailedInput(finalValue, gui);
    }

    @Override
    public DetailedInputFeature clone(FeatureParentInterface newParent) {
        DetailedInputFeature clone = new DetailedInputFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        updateDetailedInput(nextDetailedInput(getDetailedInput((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateDetailedInput(prevDetailedInput(getDetailedInput((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean doubleClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    @Override
    public boolean middleClicked(Player editor, NewGUIManager manager) {
        return false;
    }

    public DetailedInput nextDetailedInput(DetailedInput material) {
        boolean next = false;
        for (DetailedInput check : getSortDetailedInputs()) {
            if (check.equals(material)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortDetailedInputs().get(0);
    }

    public DetailedInput prevDetailedInput(DetailedInput material) {
        int i = -1;
        int cpt = 0;
        for (DetailedInput check : getSortDetailedInputs()) {
            if (check.equals(material)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortDetailedInputs().get(getSortDetailedInputs().size() - 1);
        else return getSortDetailedInputs().get(cpt - 1);
    }

    public void updateDetailedInput(DetailedInput typeTarget, GUI gui) {
        value = Optional.of(typeTarget);
        ItemStack item = gui.getByIdentifier(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortDetailedInputs().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (DetailedInput check : getSortDetailedInputs()) {
            if (typeTarget.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + typeTarget.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (DetailedInput check : getSortDetailedInputs()) {
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

    public DetailedInput getDetailedInput(GUI gui) {
        ItemStack item = gui.getByIdentifier(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return DetailedInput.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<DetailedInput> getSortDetailedInputs() {
        SortedMap<String, DetailedInput> map = new TreeMap<String, DetailedInput>();
        for (DetailedInput l : DetailedInput.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
