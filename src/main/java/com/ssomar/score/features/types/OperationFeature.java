package com.ssomar.score.features.types;

import com.ssomar.score.editor.NewGUIManager;
import com.ssomar.score.features.*;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.item.UpdateItemInGUI;
import com.ssomar.score.utils.strings.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Getter
@Setter
public class OperationFeature extends FeatureAbstract<Optional<AttributeModifier.Operation>, OperationFeature> implements FeatureRequireOnlyClicksInEditor {

    private Optional<AttributeModifier.Operation> value;
    private Optional<AttributeModifier.Operation> defaultValue;

    public OperationFeature(FeatureParentInterface parent, Optional<AttributeModifier.Operation> defaultValue, FeatureSettingsInterface featureSettings) {
        super(parent, featureSettings);
        this.defaultValue = defaultValue;
        this.value = Optional.empty();
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        String colorStr = config.getString(this.getName(), "NULL").toUpperCase();
        try {
            AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(colorStr.toUpperCase());
            value = Optional.ofNullable(operation);
            FeatureReturnCheckPremium<AttributeModifier.Operation> checkPremium = checkPremium("Operation", operation, defaultValue, isPremiumLoading);
            if (checkPremium.isHasError()) value = Optional.of(checkPremium.getNewValue());
        } catch (Exception e) {
            errors.add("&cERROR, Couldn't load the AttributeModifier.Operation value of " + this.getName() + " from config, value: " + colorStr + " &7&o" + getParent().getParentInfo() + " &6>> AttributeModifier.Operation available: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/attribute/AttributeModifier.Operation.html");
            value = Optional.empty();
        }
        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        Optional<AttributeModifier.Operation> value = getValue();
        value.ifPresent(operation -> config.set(this.getName(), operation.name()));
    }

    @Override
    public Optional<AttributeModifier.Operation> getValue() {
        if (value.isPresent()) return value;
        else return defaultValue;
    }

    @Override
    public OperationFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {
        Optional<AttributeModifier.Operation> value = getValue();
        AttributeModifier.Operation finalValue = value.orElse(AttributeModifier.Operation.ADD_NUMBER);
        updateOperation(finalValue, gui);
    }

    @Override
    public OperationFeature clone(FeatureParentInterface newParent) {
        OperationFeature clone = new OperationFeature(newParent, getDefaultValue(), getFeatureSettings());
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
        AttributeModifier.Operation operation = getOperation((GUI) manager.getCache().get(editor));
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        operation = nextOperation(operation);
        updateOperation(operation, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean shiftRightClicked(Player editor, NewGUIManager manager) {
        AttributeModifier.Operation operation = getOperation((GUI) manager.getCache().get(editor));
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        operation = prevOperation(operation);
        updateOperation(operation, (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean leftClicked(Player editor, NewGUIManager manager) {
        updateOperation(nextOperation(getOperation((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    @Override
    public boolean rightClicked(Player editor, NewGUIManager manager) {
        updateOperation(prevOperation(getOperation((GUI) manager.getCache().get(editor))), (GUI) manager.getCache().get(editor));
        return true;
    }

    public AttributeModifier.Operation nextOperation(AttributeModifier.Operation operation) {
        boolean next = false;
        for (AttributeModifier.Operation check : getSortOperations()) {
            if (check.equals(operation)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getSortOperations().get(0);
    }

    public AttributeModifier.Operation prevOperation(AttributeModifier.Operation operation) {
        int i = -1;
        int cpt = 0;
        for (AttributeModifier.Operation check : getSortOperations()) {
            if (check.equals(operation)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getSortOperations().get(getSortOperations().size() - 1);
        else return getSortOperations().get(cpt - 1);
    }

    public void updateOperation(AttributeModifier.Operation operation, GUI gui) {
        this.value = Optional.of(operation);
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore().subList(0, getEditorDescription().length + 2);
        int maxSize = lore.size();
        maxSize += getSortOperations().size();
        if (maxSize > 17) maxSize = 17;
        boolean find = false;
        for (AttributeModifier.Operation check : getSortOperations()) {
            if (operation.equals(check)) {
                lore.add(StringConverter.coloredString("&2➤ &a" + operation.name()));
                find = true;
            } else if (find) {
                if (lore.size() == maxSize) break;
                lore.add(StringConverter.coloredString("&6✦ &e" + check.name()));
            }
        }
        for (AttributeModifier.Operation check : getSortOperations()) {
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

    public AttributeModifier.Operation getOperation(GUI gui) {
        ItemStack item = gui.getByName(getEditorName());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore) {
            if (str.contains("➤ ")) {
                str = StringConverter.decoloredString(str).replaceAll(" Premium", "");
                return AttributeModifier.Operation.valueOf(str.split("➤ ")[1]);
            }
        }
        return null;
    }

    public List<AttributeModifier.Operation> getSortOperations() {
        SortedMap<String, AttributeModifier.Operation> map = new TreeMap<String, AttributeModifier.Operation>();
        for (AttributeModifier.Operation l : AttributeModifier.Operation.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
